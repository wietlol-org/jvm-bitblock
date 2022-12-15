package me.wietlol.bitblock.codegenerator.generators.kotlin

import me.wietlol.bitblock.codegenerator.data.models.BitModel
import me.wietlol.bitblock.codegenerator.data.models.BitModule
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitAnnotation
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitDirectTypeReference
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModel
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModule
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitName

object MutabilityProcessor : KotlinGeneratorHelper
{
	fun addMutableBlueprints(bitModule: BitModule): BitModule
	{
		val models = bitModule.models
			.flatMap { it.withMutableBlueprint(bitModule) }
		
		return DefaultBitModule(
			bitModule.module,
			bitModule.owner,
			bitModule.version,
			bitModule.apiRootPackage,
			bitModule.implRootPackage,
			bitModule.builderPackage,
			bitModule.apiModelPackage,
			bitModule.implModelPackage,
			bitModule.serializerPackage,
			bitModule.annotations,
			models,
			bitModule.relativeRoot,
			bitModule.relativeApiRoot,
			bitModule.relativeImplRoot,
			bitModule.useExistingModels,
			bitModule.builderFacade,
			bitModule.mutableModelNameFormat,
			bitModule.implementationNameFormat,
		)
	}
	
	private fun BitModel.withMutableBlueprint(bitModule: BitModule): List<BitModel>
	{
		if (mutableVariant == null)
			return listOf(this)
		
		fun buildReferenceOf(name: String) =
			DefaultBitDirectTypeReference(DefaultBitName(listOf(name)), emptyList(), false)
		
		val mutableName = mutableVariant?.takeIf { it.isNotBlank() } ?: bitModule.mutableModelNameFormat.replace("%", name)
		
		val mutableBlueprint = DefaultBitModel(
			mutableName,
			generics,
			extends
				.map { reference ->
					
					val referencedModel = bitModule.models.singleOrNull { it.name == reference.fullName }
					val isMutable = referencedModel?.mutableVariant != null
					val mutableReferenceName = referencedModel?.mutableVariant?.takeIf { it.isNotBlank() }

					when
					{
						referencedModel == null -> reference
						mutableReferenceName != null -> buildReferenceOf(mutableReferenceName)
						isMutable -> buildReferenceOf(bitModule.mutableModelNameFormat.replace("%", referencedModel.name))
						else -> buildReferenceOf(referencedModel.name)
					}
				}
				.plus(buildReferenceOf(name)),
			values,
			properties,
			annotations.plus(DefaultBitAnnotation(
				DefaultBitName(listOf("BitBlockMutable")),
				emptyList()
			)),
			type,
			null,
			isReferenceSensitive,
		)
		
		return listOf(this, mutableBlueprint)
	}
}
