package me.wietlol.bitblock.codegenerator.parser

import me.wietlol.bitblock.codegenerator.data.models.BitAnnotation
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitAnnotation
import me.wietlol.bitblock.codegenerator.data.models.BitDirectTypeReference
import me.wietlol.bitblock.codegenerator.data.models.BitExpression
import me.wietlol.bitblock.codegenerator.data.models.BitIntegerExpression
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitIntegerExpression
import me.wietlol.bitblock.codegenerator.data.models.BitLiteralExpression
import me.wietlol.bitblock.codegenerator.data.models.BitMemberExpression
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitMemberExpression
import me.wietlol.bitblock.codegenerator.data.models.BitModel
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModel
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModelType.Companion.blueprint
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModelType.Companion.contract
import me.wietlol.bitblock.codegenerator.data.models.BitModule
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModule
import me.wietlol.bitblock.codegenerator.data.models.BitName
import me.wietlol.bitblock.codegenerator.data.models.BitNameExpression
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitNameExpression
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitName
import me.wietlol.bitblock.codegenerator.data.models.BitProperty
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitProperty
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitPropertyType.Companion.property
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitPropertyType.Companion.value
import me.wietlol.bitblock.codegenerator.data.models.BitStringExpression
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitStringExpression
import me.wietlol.bitblock.codegenerator.data.models.BitTypeReference
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitDirectTypeReference
import me.wietlol.bitblock.codegenerator.data.models.BitGenericTypeDeclaration
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitGenericTypeDeclaration
import me.wietlol.bitblock.codegenerator.data.models.BitGenericVariance
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitGenericVariance.Companion.inVariance
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitGenericVariance.Companion.outVariance
import me.wietlol.bitblock.codegenerator.data.models.BitModelType
import me.wietlol.bitblock.codegenerator.data.models.BitStarTypeReference
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitStarTypeReference
import me.wietlol.bitblock.codegenerator.data.models.BitValue
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitValue
import me.wietlol.bitblock.codegenerator.utils.BitModuleHelper
import me.wietlol.metacode.api.lexer.MetaCodeLexer
import me.wietlol.metacode.api.lexer.MetaCodeToken
import me.wietlol.metacode.api.lexer.sources.CodeSource
import me.wietlol.metacode.api.parser.MetaCodeParser
import me.wietlol.metacode.api.parser.results.ParserResult
import me.wietlol.metacode.core.grammar.parseLexer
import me.wietlol.metacode.core.grammar.parseParser
import me.wietlol.metacode.core.lexer.sources.FileSource
import me.wietlol.metacode.core.lexer.sources.StreamSource
import java.io.File

class BitModuleParser
{
	companion object : BitModuleHelper
	
	fun parseBitModule(file: File): BitModule
	{
		val lexer: MetaCodeLexer = parseLexer(StreamSource("lexer", javaClass.getResourceAsStream("/me/wietlol/bitblock/codegenerator/BitModule.lexer.mcg")!!))
		val parser: MetaCodeParser = parseParser(StreamSource("parser", javaClass.getResourceAsStream("/me/wietlol/bitblock/codegenerator/BitModule.parser.mcg")!!))
		val source: CodeSource = FileSource(file)
		
		val tokens: List<MetaCodeToken> = lexer.lex(source).toList()
		val result: ParserResult = parser.parseExhaustive("file", tokens).first()
		
		return result.parseBitModule()
	}
	
	private fun ParserResult.parseBitModule(): BitModule
	{
		val annotations = members["header"].single().members["annotations"].map { it.parseAnnotation() }
		
		val module: String = annotations
			.findSingleByName("Module")
			?.getValue<String>()
			?: throw IllegalArgumentException("Missing @Module(\"\") annotation")
		val owner: String = annotations
			.findSingleByName("Owner")
			?.getValue<String>()
			?: throw IllegalArgumentException("Missing @Owner(\"\") annotation")
		val version: String = annotations
			.findSingleByName("Version")
			?.getValue<String>()
			?: throw IllegalArgumentException("Missing @Version(\"\") annotation")
		
		val rootPackage: String = annotations
			.findSingleByName("RootPackage")
			?.getValue<String>()
			?: throw IllegalArgumentException("Missing @RootPackage(\"\") annotation")
		val builderPackage: String = annotations
			.findSingleByName("BuilderPackage")
			?.getValue<String>()
			?: ""
		val modelPackage: String = annotations
			.findSingleByName("ModelPackage")
			?.getValue<String>()
			?: ""
		val serializerPackage: String = annotations
			.findSingleByName("SerializerPackage")
			?.getValue<String>()
			?: ""
		
		val apiRootPackage = annotations
			.findSingleByName("ApiRootPackage")
			?.getValue<String>()
			?: rootPackage
		val apiModelPackage = annotations
			.findSingleByName("ApiModelPackage")
			?.getValue<String>()
			?: modelPackage
		
		annotations
			.asSequence()
			.filter { it.name.stringNotEquals("Module") } // model name
			.filter { it.name.stringNotEquals("Owner") } // owner name
			.filter { it.name.stringNotEquals("Version") } // version text
			.filter { it.name.stringNotEquals("RootPackage") } // root package path
			.filter { it.name.stringNotEquals("ApiRootPackage") } // api root package path
			.filter { it.name.stringNotEquals("BuilderPackage") } // builders package relative path
			.filter { it.name.stringNotEquals("ModelPackage") } // models package relative path
			.filter { it.name.stringNotEquals("ApiModelPackage") } // api models package relative path
			.filter { it.name.stringNotEquals("SerializerPackage") } // serializers package relative path
			.filter { it.name.stringNotEquals("Import") } // import statement
			.filter { it.name.stringNotEquals("ProjectName") } // project name // unused currently
			.filter { it.name.stringNotEquals("GenerateVisitor") } // generating visitors // unused currently
			.filter { it.name.stringNotEquals("Root") } // root folder relative path
			.filter { it.name.stringNotEquals("ApiRoot") } // api root folder relative path
			.filter { it.name.stringNotEquals("ImplRoot") } // impl root folder relative path
			.filter { it.name.stringNotEquals("BuilderFacade") } // builder facade fully qualified name
			.filter { it.name.stringNotEquals("ExistingModels") } // use existing models instead of creating new ones
			.filter { it.name.stringNotEquals("Visitor") } // generate a visitor for the entire data structure of the models
			.filter { it.name.stringNotEquals("ImplementationNameFormat") } // format provider for implementation names
			.filter { it.name.stringNotEquals("InlinedValues") } // avoid headers for basic types, such as int, string, boolean, etc
			.forEach { throw IllegalArgumentException("Unknown annotation '${it.name.parts.joinToString(".")}'.") }
		
		val models: List<BitModel> = members["members"].map { it.parseFileMember() }
		
		val relativeRoot = annotations.findSingleByName("Root")?.getValue<String>()
		val relativeApiRoot = annotations.findSingleByName("ApiRoot")?.getValue<String>()
		val relativeImplRoot = annotations.findSingleByName("ImplRoot")?.getValue<String>()
		val useExistingModels = annotations.any { it.name.stringEquals("ExistingModels") }
		val builderFacade = annotations.findSingleByName("BuilderFacade")?.getValue<String>()
		val mutableNameFormat = annotations.findSingleByName("MutableNameFormat")?.getValue<String>() ?: "Mutable%"
		val implementationNameFormat = annotations.findSingleByName("ImplementationNameFormat")?.getValue<String>() ?: "Default%"
		
		return DefaultBitModule(
			module, owner, version,
			apiRootPackage, rootPackage,
			builderPackage, apiModelPackage, modelPackage, serializerPackage,
			annotations, models,
			relativeRoot, relativeApiRoot, relativeImplRoot,
			useExistingModels,
			builderFacade,
			mutableNameFormat,
			implementationNameFormat,
		)
	}
	
	private fun ParserResult.parseAnnotation(): BitAnnotation =
		DefaultBitAnnotation(
			members["name"].single().parseName(),
			members["arguments"].map { it.parseExpression() }
		)
	
	private fun ParserResult.parseExpression(): BitExpression =
		when (option?.name)
		{
			"LiteralExpression" -> members["value"].single().parseLiteralExpression()
			"MemberExpression" -> parseMemberExpression()
			"NameExpression" -> parseNameExpression()
			else -> throw IllegalArgumentException("Invalid option for a expression: '${option?.name}'.")
		}
	
	private fun ParserResult.parseLiteralExpression(): BitLiteralExpression =
		when (option?.name)
		{
			"StringLiteral" -> members["value"].single().parseStringExpression()
			"IntegerLiteral" -> members["value"].single().parseIntegerExpression()
			else -> throw IllegalArgumentException("Invalid option for a literal expression: '${option?.name}'.")
		}
	
	private fun ParserResult.parseStringExpression(): BitStringExpression =
		DefaultBitStringExpression(
			members["value"].single().text.parseString()
		)
	
	private fun String.parseString(): String =
		substring(1, length - 1)
			.replace("\\\\(?:(?<symbolu>u)(?<codepoint>[a-fA-F0-9]{4})|(?<symbol>.))".toRegex()) {
				when (val symbol = it.groups["symbol"]?.value)
				{
					"\"", "\\" -> symbol
					"t" -> "\t"
					"r" -> "\r"
					"n" -> "\n"
					"b" -> "\b"
					null -> when (it.groups["symbolu"]?.value)
					{
						"u" -> Integer.parseInt(it.groups["codepoint"]!!.value, 16)
							.toChar()
							.toString()
						else -> throw IllegalArgumentException("Invalid escape sequence '${it.value}'.")
					}
					else -> throw IllegalArgumentException("Invalid escape sequence '${it.value}'.")
				}
			}
	
	private fun ParserResult.parseIntegerExpression(): BitIntegerExpression =
		DefaultBitIntegerExpression(
			members["value"].single().text.parseInteger()
		)
	
	private fun String.parseInteger(): Int =
		this.toInt()
	
	private fun ParserResult.parseMemberExpression(): BitMemberExpression =
		DefaultBitMemberExpression(
			members["qualifier"].single().parseExpression(),
			members["member"].single().text
		)
	
	private fun ParserResult.parseNameExpression(): BitNameExpression =
		DefaultBitNameExpression(
			members["name"].single().text
		)
	
	private fun ParserResult.parseFileMember(): BitModel =
		when (option?.name)
		{
			"Blueprint" -> members["value"].single().parseModel(blueprint)
			"Contract" -> members["value"].single().parseModel(contract)
			else -> throw IllegalArgumentException("Invalid option for a file member: '${option?.name}'.")
		}
	
	private fun ParserResult.parseModel(type: BitModelType): BitModel
	{
		val annotations = members["annotations"].map { it.parseAnnotation() }
		
		val mutable = annotations.singleOrNull { it.name.stringEquals("Mutable") }
		val mutableName = if (mutable != null) mutable.findValue(1) ?: "" else null
		
		return DefaultBitModel(
			members["name"].single().text,
			members["generics"].map { it.parseGenericTypeDeclaration() },
			members["extends"].map { it.parseTypeReference() },
			members["values"].map { it.parseValue() },
			members["properties"].map { it.parseProperty() },
			annotations,
			type,
			mutableName,
			annotations.findSingleByName("ReferenceSensitive") != null,
		)
	}
	
	private fun ParserResult.parseGenericTypeDeclaration(): BitGenericTypeDeclaration =
		DefaultBitGenericTypeDeclaration(
			members["name"].single().text,
			members["variance"].singleOrNull()?.parseGenericTypeVariance(),
			members["superType"].singleOrNull()?.parseTypeReference()
		)
	
	private fun ParserResult.parseGenericTypeVariance(): BitGenericVariance =
		when (text)
		{
			"in" -> inVariance
			"out" -> outVariance
			else -> TODO()
		}
	
	private fun ParserResult.parseTypeReference(): BitTypeReference =
		when (option?.name)
		{
			"DirectTypeReference" -> parseDirectTypeReference()
			"StarTypeReference" -> parseStarTypeReference()
			else -> throw IllegalArgumentException("Invalid option for a type reference: '${option?.name}'.")
		}
	
	private fun ParserResult.parseDirectTypeReference(): BitDirectTypeReference =
		DefaultBitDirectTypeReference(
			members["name"].single().parseName(),
			members["generics"].map { it.parseTypeReference() },
			members["optional"].any()
		)
	
	private fun parseStarTypeReference(): BitStarTypeReference =
		DefaultBitStarTypeReference()
	
	private fun ParserResult.parseName(): BitName =
		DefaultBitName(
			members["parts"].map { it.text }
		)
	
	private fun ParserResult.parseValue(): BitValue =
		DefaultBitValue(
			members["name"].single().text,
			members["arguments"].map { it.parseExpression() }
		)
	
	private fun ParserResult.parseProperty(): BitProperty
	{
		val annotations = members["annotations"].map { it.parseAnnotation() }
		return DefaultBitProperty(
			members["name"].single().text,
			members["type"].single().parseTypeReference(),
			members["defaultValue"].singleOrNull()?.parseExpression(),
			members["index"].singleOrNull()?.text?.parseInteger(),
			annotations,
			when (members["propertyType"].single().text)
			{
				"property" -> property
				"value" -> value
				else -> TODO()
			},
			annotations.findSingleByName("Transient") != null,
			annotations.findSingleByName("Serialized") != null,
			annotations.findSingleByName("Override") != null,
		)
	}
}
