package me.wietlol.bitblock.codegenerator.generators.kotlin

import me.wietlol.bitblock.codegenerator.data.models.BitDirectTypeReference
import me.wietlol.bitblock.codegenerator.data.models.BitModule
import me.wietlol.bitblock.codegenerator.generators.CodeGeneratorOptions
import me.wietlol.bitblock.codegenerator.utils.bitBlockVersion
import me.wietlol.tomplot.kotlin.builders.body
import me.wietlol.tomplot.kotlin.builders.ktComment
import me.wietlol.tomplot.kotlin.builders.ktFunction
import me.wietlol.tomplot.kotlin.builders.ktImport
import me.wietlol.tomplot.kotlin.builders.ktInterface
import me.wietlol.tomplot.kotlin.builders.ktPackage
import me.wietlol.tomplot.kotlin.builders.ktParameter
import me.wietlol.tomplot.kotlin.engine.KotlinTomplotEngine
import me.wietlol.utils.common.recursiveMapBreathFirst
import java.io.File

class KotlinVisitorGenerator(
	val engine: KotlinTomplotEngine<*>
)
{
	companion object : KotlinGeneratorHelper
	
	fun generateVisitor(bitModule: BitModule, apiRoot: File, options: CodeGeneratorOptions)
	{
		val relativePath = (bitModule.apiRootPackage + ".tools").replace(".", "/")
		val folder = apiRoot.resolve(relativePath)
		
		options.logger("exporting visitor to               ${folder.absolutePath}")
		
		folder.mkdirs()
		
		// todo assert better order
		val allNormalModels = bitModule
			.models
			.toList()
			.reversed()
			.map { Pair(it, it.getExtendedBlueprints(bitModule).toSet()) }
		
		val allBlueprintExtenders = allNormalModels
			.toMap()
		
		val allModelDirectExtenders = allNormalModels
			.map { (key, value) ->
				Pair(key, value
					.filter { value.flatMap { allBlueprintExtenders.getValue(it) }.contains(it).not() }
					.toSet())
			}
			.toMap()
		
		val blueprintNames = allNormalModels
			.map { it.first }
			.map { it.name }
			.toSet()
		
		val visitorName = "${bitModule.module}Visitor"
		engine.createScript(visitorName, apiRoot) {
			ktComment("Generated by BitBlock version $bitBlockVersion")
			
			`package` = ktPackage(bitModule.implRootPackage + ".tools")
			
			addImportsFromAnnotations(bitModule)
			
			ktImport("${bitModule.apiRootPackage}${bitModule.apiModelPackage}.*")
			
			ktInterface(visitorName) {
				bitModule
					.models
					.forEach { model ->
						val parameterName = model.name.applyCamelCase()
						ktFunction("visit") {
							parameters.add(ktParameter(parameterName, model.asReference()))
							
							body {
								val extendingBlueprints = allNormalModels
									.filter { it.second.contains(model) }
									.map { it.first }
								
								fun generateVisitCode()
								{
									sequenceOf(model)
										.recursiveMapBreathFirst { allModelDirectExtenders.getValue(it).asSequence() }
										.toList()
										.reversed()
										.distinct()
										.filter { it != model }
										.forEach {
											append("accept(")
											append(parameterName)
											append(" as ")
											append(it.name)
											appendLine(")")
										}
									append("accept(")
									append(parameterName)
									appendLine(")")
									
									model
										.getExtendedProperties(bitModule)
										.mapNotNull { property ->
											if (property.type.isCollective())
											{
												val elementType = property.type.generics.first() as BitDirectTypeReference
												if (blueprintNames.contains(elementType.typeName.parts.last()))
												{
													if (property.type.isOptional)
														"$parameterName.${property.name}?.forEach { visit(it) }"
													else if (property.type.generics[0].isOptional)
														"$parameterName.${property.name}.forEach { if (it != null) visit(it) }"
													else
														"$parameterName.${property.name}.forEach { visit(it) }"
												}
												else
													null
											}
											else
											{
												if (blueprintNames.contains((property.type as BitDirectTypeReference).typeName.parts.last()))
													if (property.type.isOptional)
														"$parameterName.${property.name}?.also { visit(it) }"
													else
														"$parameterName.${property.name}.also { visit(it) }"
												else
													null
											}
										}
										.also {
											if (it.isNotEmpty())
												appendLine()
										}
										.forEach { appendLine(it) }
								}
								
								if (extendingBlueprints.isNotEmpty())
								{
									append("when (")
									appendLine(parameterName)
									appendLine(")")
									appendLine("{")
									indented {
										extendingBlueprints.forEach {
											append("is ")
											append(it.name)
											append(" -> visit(")
											append(parameterName)
											appendLine(")")
										}
										
										appendLine("else -> {")
										generateVisitCode()
										appendLine("}")
									}
									appendLine("}")
								}
								else
									generateVisitCode()
								
							}
						}
					}
				
				bitModule
					.models
					.forEach {
						ktFunction("accept") {
							parameters.add(
								ktParameter(it.name.applyCamelCase(), it.asReference())
							)
							
							body { }
						}
					}
			}
		}
	}
	
	private fun String.applyCamelCase() =
		split(".").joinToString(".") {
			if (it.isEmpty())
				it
			else
				it[0].toLowerCase() + it.drop(1)
		}
}