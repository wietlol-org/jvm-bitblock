// data: serializationKey:2ac65e19-8ba5-4cce-af71-8824b9d33388
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import java.util.UUID
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.toJsonString
import me.wietlol.utils.common.with

// @formatter:on
// @tomplot:customCode:start:gAeCSq
// @tomplot:customCode:end
// @formatter:off


interface BitModule : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("2ac65e19-8ba5-4cce-af71-8824b9d33388")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val module: String
	
	val owner: String
	
	val version: String
	
	val apiRootPackage: String
	
	val implRootPackage: String
	
	val builderPackage: String
	
	val apiModelPackage: String
	
	val implModelPackage: String
	
	val serializerPackage: String
	
	val annotations: List<BitAnnotation>
	
	val models: List<BitModel>
	
	val relativeRoot: String?
	
	val relativeApiRoot: String?
	
	val relativeImplRoot: String?
	
	val useExistingModels: Boolean
	
	val builderFacade: String?
	
	val mutableModelNameFormat: String
	
	val implementationNameFormat: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitModule) return false
		
		if (module != other.module) return false
		if (owner != other.owner) return false
		if (version != other.version) return false
		if (apiRootPackage != other.apiRootPackage) return false
		if (implRootPackage != other.implRootPackage) return false
		if (builderPackage != other.builderPackage) return false
		if (apiModelPackage != other.apiModelPackage) return false
		if (implModelPackage != other.implModelPackage) return false
		if (serializerPackage != other.serializerPackage) return false
		if (annotations != other.annotations) return false
		if (models != other.models) return false
		if (relativeRoot != other.relativeRoot) return false
		if (relativeApiRoot != other.relativeApiRoot) return false
		if (relativeImplRoot != other.relativeImplRoot) return false
		if (useExistingModels != other.useExistingModels) return false
		if (builderFacade != other.builderFacade) return false
		if (mutableModelNameFormat != other.mutableModelNameFormat) return false
		if (implementationNameFormat != other.implementationNameFormat) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(module)
			.with(owner)
			.with(version)
			.with(apiRootPackage)
			.with(implRootPackage)
			.with(builderPackage)
			.with(apiModelPackage)
			.with(implModelPackage)
			.with(serializerPackage)
			.with(annotations)
			.with(models)
			.with(relativeRoot)
			.with(relativeApiRoot)
			.with(relativeImplRoot)
			.with(useExistingModels)
			.with(builderFacade)
			.with(mutableModelNameFormat)
			.with(implementationNameFormat)
	
	override fun toJson(): String =
		"""{"module":${module.toJsonString()},"owner":${owner.toJsonString()},"version":${version.toJsonString()},"apiRootPackage":${apiRootPackage.toJsonString()},"implRootPackage":${implRootPackage.toJsonString()},"builderPackage":${builderPackage.toJsonString()},"apiModelPackage":${apiModelPackage.toJsonString()},"implModelPackage":${implModelPackage.toJsonString()},"serializerPackage":${serializerPackage.toJsonString()},"annotations":${annotations.toJsonString()},"models":${models.toJsonString()},"relativeRoot":${relativeRoot.toJsonString()},"relativeApiRoot":${relativeApiRoot.toJsonString()},"relativeImplRoot":${relativeImplRoot.toJsonString()},"useExistingModels":${useExistingModels.toJsonString()},"builderFacade":${builderFacade.toJsonString()},"mutableModelNameFormat":${mutableModelNameFormat.toJsonString()},"implementationNameFormat":${implementationNameFormat.toJsonString()}}"""
	
	override fun duplicate(): BitModule
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
