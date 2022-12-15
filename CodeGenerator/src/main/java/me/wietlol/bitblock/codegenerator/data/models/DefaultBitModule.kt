// hash: #4e322015
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitModule(
	override val module: String,
	override val owner: String,
	override val version: String,
	override val apiRootPackage: String,
	override val implRootPackage: String,
	override val builderPackage: String,
	override val apiModelPackage: String,
	override val implModelPackage: String,
	override val serializerPackage: String,
	override val annotations: List<BitAnnotation>,
	override val models: List<BitModel>,
	override val relativeRoot: String?,
	override val relativeApiRoot: String?,
	override val relativeImplRoot: String?,
	override val useExistingModels: Boolean,
	override val builderFacade: String?,
	override val mutableModelNameFormat: String,
	override val implementationNameFormat: String,
) : BitModule
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitModule =
		copy(
			module = module,
			owner = owner,
			version = version,
			apiRootPackage = apiRootPackage,
			implRootPackage = implRootPackage,
			builderPackage = builderPackage,
			apiModelPackage = apiModelPackage,
			implModelPackage = implModelPackage,
			serializerPackage = serializerPackage,
			annotations = annotations.map { it.duplicate() }.toMutableList(),
			models = models.map { it.duplicate() }.toMutableList(),
			relativeRoot = relativeRoot,
			relativeApiRoot = relativeApiRoot,
			relativeImplRoot = relativeImplRoot,
			useExistingModels = useExistingModels,
			builderFacade = builderFacade,
			mutableModelNameFormat = mutableModelNameFormat,
			implementationNameFormat = implementationNameFormat,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on