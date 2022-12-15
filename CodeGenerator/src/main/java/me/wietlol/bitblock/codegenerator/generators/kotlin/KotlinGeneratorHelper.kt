package me.wietlol.bitblock.codegenerator.generators.kotlin

import me.wietlol.bitblock.codegenerator.data.models.BitDirectTypeReference
import me.wietlol.bitblock.codegenerator.data.models.BitExpression
import me.wietlol.bitblock.codegenerator.data.models.BitGenericTypeDeclaration
import me.wietlol.bitblock.codegenerator.data.models.BitIntegerExpression
import me.wietlol.bitblock.codegenerator.data.models.BitMemberExpression
import me.wietlol.bitblock.codegenerator.data.models.BitModel
import me.wietlol.bitblock.codegenerator.data.models.BitModule
import me.wietlol.bitblock.codegenerator.data.models.BitNameExpression
import me.wietlol.bitblock.codegenerator.data.models.BitProperty
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitPropertyType.Companion.value
import me.wietlol.bitblock.codegenerator.data.models.BitStarTypeReference
import me.wietlol.bitblock.codegenerator.data.models.BitStringExpression
import me.wietlol.bitblock.codegenerator.data.models.BitTypeReference
import me.wietlol.bitblock.codegenerator.utils.BitModuleHelper
import me.wietlol.tomplot.core.writers.TomplotStringBuilder
import me.wietlol.tomplot.kotlin.builders.KotlinElementBuilder
import me.wietlol.tomplot.kotlin.builders.ktDirectGenericTypeArgument
import me.wietlol.tomplot.kotlin.builders.ktDirectTypeReference
import me.wietlol.tomplot.kotlin.builders.ktGenericTypeDeclaration
import me.wietlol.tomplot.kotlin.builders.ktImport
import me.wietlol.tomplot.kotlin.builders.ktStarGenericTypeArgument
import me.wietlol.tomplot.kotlin.data.models.KtDirectGenericTypeArgument
import me.wietlol.tomplot.kotlin.data.models.KtDirectTypeReference
import me.wietlol.tomplot.kotlin.data.models.KtGenericTypeArgument
import me.wietlol.tomplot.kotlin.data.models.KtGenericTypeDeclaration
import me.wietlol.tomplot.kotlin.data.models.KtStarGenericTypeArgument
import me.wietlol.tomplot.kotlin.data.models.KtTypeReference
import me.wietlol.utils.common.cast
import me.wietlol.utils.common.toJsonString

interface KotlinGeneratorHelper : BitModuleHelper
{
	fun BitModel.asReference(): KtDirectTypeReference =
		ktDirectTypeReference(
			name,
			generics.map {
				ktDirectGenericTypeArgument(
					it.name,
					emptyList()
				)
			},
			false
		)
	
	fun BitModel.isMutable(): Boolean =
		annotations.any { it.name.stringEquals("BitBlockMutable") }
	
	fun convertGenericTypeArgument(typeReference: BitTypeReference): KtGenericTypeArgument =
		when (typeReference)
		{
			is BitDirectTypeReference -> ktDirectGenericTypeArgument(convertTypeReference(typeReference))
			is BitStarTypeReference -> ktStarGenericTypeArgument()
			else -> TODO()
		}
	
	fun convertTypeReference(typeReference: BitTypeReference): KtTypeReference =
		when (typeReference)
		{
			is BitDirectTypeReference -> convertTypeReference(typeReference)
			is BitStarTypeReference -> TODO("not supported")
			else -> TODO()
		}
	
	fun convertTypeReference(typeReference: BitDirectTypeReference): KtTypeReference =
		ktDirectTypeReference(
			typeReference.typeName.parts,
			typeReference.generics.map {
				convertGenericTypeArgument(it)
			},
			typeReference.isOptional
		)
	
	companion object
	{
		private val keywords = setOf("package", "class", "interface", "object")
	}
	
	fun escapeName(name: String): String =
		if (keywords.contains(name))
			"`$name`"
		else
			name
	
	fun generateDuplicateCall(property: BitProperty): String =
		"${escapeName(property.name)} = ${escapeName(property.name)}${generateDuplicateCall(property.type)}"
	
	fun generateDuplicateCall(typeReference: BitTypeReference): String
	{
		val accessor = if (typeReference.isOptional) "?." else "."
		return when
		{
			typeReference.isMap() ->
			{
				val head = "${accessor}map { it.key${generateDuplicateCall(typeReference.generics.first())} to it.value${generateDuplicateCall(typeReference.generics[1])} }${accessor}"
				if (typeReference.isNativelyImmutable())
					"$head${toImmutableCollectionFor(typeReference)}"
				else
					"$head${toMutableCollectionFor(typeReference)}"
			}
			typeReference.isCollective() ->
			{
				val head = "${accessor}map { it${generateDuplicateCall(typeReference.generics.first())} }${accessor}"
				if (typeReference.isNativelyImmutable())
					"$head${toImmutableCollectionFor(typeReference)}"
				else
					"$head${toMutableCollectionFor(typeReference)}"
			}
			typeReference.isNativelyImmutable() -> ""
			else -> "${accessor}duplicate()"
		}
	}
	
	fun BitTypeReference.isNativelyImmutable(): Boolean =
		when (fullName)
		{
			"Any",
			"Anything",
			"Byte",
			"Short",
			"Int",
			"Integer",
			"Long",
			"Float",
			"Double",
			"UUID",
			"Boolean",
			"Character",
			"String" -> true
			else -> fullName.startsWith("Mutable")
		}
	
	fun BitTypeReference.isCollective(): Boolean =
		when (fullName)
		{
			"List",
			"Set",
			"Collection" -> true
			else -> false
		}
	
	fun BitTypeReference.isMap(): Boolean =
		when (fullName)
		{
			"Map" -> true
			else -> false
		}
	
	fun BitGenericTypeDeclaration.toKotlin(bitModule: BitModule, isMutable: Boolean = false, includeVariance: Boolean = true): KtGenericTypeDeclaration =
		ktGenericTypeDeclaration(
			name,
			variance?.takeIf { includeVariance }?.code,
			superType?.toKotlin(bitModule, false)
		)
	
	fun BitTypeReference.toKotlin(bitModule: BitModule, isMutable: Boolean = false): KtTypeReference
	{
		val typeName = when (fullName)
		{
			"Anything" -> "Any"
			"Integer" -> "Int"
			"Character" -> "Char"
			"String" -> "String"
			"List" -> if (isMutable) "MutableList" else "List"
			"Set" -> if (isMutable) "MutableSet" else "Set"
			"Map" -> if (isMutable) "MutableMap" else "Map"
			"Collection" -> if (isMutable) "MutableCollection" else "Collection"
			else ->
				if (isMutable)
				{
					val referencedModel = bitModule.models.singleOrNull { it.name == fullName }
					val mutableName = referencedModel?.mutableVariant?.takeIf { it.isNotBlank() }
					
					when
					{
						mutableName != null -> mutableName
						referencedModel?.mutableVariant == "" -> bitModule.mutableModelNameFormat.replace("%", referencedModel.name)
						else -> fullName
					}
				}
				else
					fullName
		}
		
		return ktDirectTypeReference(
			typeName,
			generics.map { it.toKotlinGenericArgument(bitModule, isMutable) },
			isOptional
		)
	}
	
	fun BitTypeReference.toKotlinGenericArgument(bitModule: BitModule, isMutable: Boolean = false): KtGenericTypeArgument =
		when (this)
		{
			is BitDirectTypeReference -> ktDirectGenericTypeArgument(toKotlin(bitModule, isMutable))
			is BitStarTypeReference -> ktStarGenericTypeArgument()
			else -> TODO()
		}
	
	fun newCollectionFor(type: BitTypeReference): String =
		when (type.fullName)
		{
			"List" -> "mutableListOf()"
			"Set" -> "mutableSetOf()"
			"Map" -> "mutableMapOf()"
			else -> "mutableListOf()"
		}
	
	fun toImmutableCollectionFor(type: BitTypeReference): String =
		when (type.fullName)
		{
			"List" -> "toList()"
			"Set" -> "toSet()"
			"Map" -> "toMap()"
			else -> "toList()"
		}
	
	fun toMutableCollectionFor(type: BitTypeReference): String =
		when (type.fullName)
		{
			"List", "MutableList" -> "toMutableList()"
			"Set", "MutableSet" -> "toMutableSet()"
			"Map", "MutableMap" -> "toMap(HashMap())"
			else -> "toMutableList()"
		}
	
	fun BitExpression.toKotlin(): String =
		when (this)
		{
			is BitIntegerExpression -> value.toJsonString()
			is BitStringExpression -> value.toJsonString()
			is BitNameExpression -> name
			is BitMemberExpression -> qualifier.toKotlin() + "." + memberName
			else -> throw IllegalStateException("weird ass shit")
		}
	
	fun BitModel.getExtendedProperties(bitModule: BitModule): List<BitProperty> =
		listOf(this)
			.plus(getExtendedBlueprints(bitModule))
			.toList()
			.flatMap { it.properties }
			.distinctBy { it.name }
	
	fun BitModel.getExtendedNoValueProperties(bitModule: BitModule): List<BitProperty> =
		listOf(this)
			.plus(getExtendedBlueprints(bitModule))
			.toList()
			.flatMap { it.properties }
			.filter { it.propertyType != value }
			.distinctBy { it.name }
	
	fun BitModel.getInheritedProperties(bitModule: BitModule): List<BitProperty> =
		extends
			.asSequence()
			.mapNotNull { ex -> bitModule.models.singleOrNull { it.name == ex.fullName } }
			.toList()
			.flatMap { it.properties.plus(it.getInheritedProperties(bitModule)) }
	
	fun BitModel.getExtendedBlueprints(model: BitModule): List<BitModel> =
		extends
			.mapNotNull { ex -> model.models.singleOrNull { it.name == ex.fullName } }
			.flatMap { listOf(it).plus(it.getExtendedBlueprints(model)) }
	
	val BitTypeReference.isOptional: Boolean
		get() = when (this)
		{
			is BitDirectTypeReference -> isOptional
			is BitStarTypeReference -> true
			else -> TODO()
		}
	
	val BitTypeReference.generics: List<BitTypeReference>
		get() = when (this)
		{
			is BitDirectTypeReference -> generics
			is BitStarTypeReference -> emptyList()
			else -> TODO()
		}
	
	val BitTypeReference.fullName: String
		get() = when (this)
		{
			is BitDirectTypeReference -> typeName.parts.joinToString(".")
			is BitStarTypeReference -> "*"
			else -> TODO()
		}
	
	fun KotlinElementBuilder<*>.addImportsFromAnnotations(bitModule: BitModule)
	{
		bitModule.annotations
			.plus(bitModule.models.flatMap { it.annotations })
			.asSequence()
			.filter { it.name.stringEquals("Import") }
			.map { it.arguments.single().cast<BitStringExpression>().value }
			.forEach { ktImport(it) }
	}
	
	fun KotlinElementBuilder<*>.addImportsFromAnnotations(bitModule: BitModule, bitModel: BitModel)
	{
		bitModule.annotations
			.plus(bitModel.annotations)
			.asSequence()
			.filter { it.name.stringEquals("Import") }
			.map { it.arguments.single().cast<BitStringExpression>().value }
			.forEach { ktImport(it) }
	}
	
	fun TomplotStringBuilder.appendTypeReference(type: KtTypeReference)
	{
		type as KtDirectTypeReference
		append(type.path.joinToString("."))
		if (type.genericTypeArguments.isNotEmpty())
		{
			append("<")
			appendJoin(type.genericTypeArguments, ", ") {
				appendGenericTypeArgument(it)
			}
			append(">")
		}
		if (type.isNullable)
			append("?")
	}
	
	fun TomplotStringBuilder.appendGenericTypeArgument(arg: KtGenericTypeArgument) = when (arg)
	{
		is KtStarGenericTypeArgument -> append("*")
		is KtDirectGenericTypeArgument -> appendTypeReference(arg.typeReference)
		else -> TODO()
	}
}
