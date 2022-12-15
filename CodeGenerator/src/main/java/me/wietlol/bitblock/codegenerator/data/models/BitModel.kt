// data: serializationKey:c465db35-dda9-4f6e-bcf8-1250d56e21fe
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


interface BitModel : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("c465db35-dda9-4f6e-bcf8-1250d56e21fe")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val generics: List<BitGenericTypeDeclaration>
	
	val extends: List<BitTypeReference>
	
	val values: List<BitValue>
	
	val properties: List<BitProperty>
	
	val annotations: List<BitAnnotation>
	
	val type: BitModelType
	
	val mutableVariant: String?
	
	val isReferenceSensitive: Boolean
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitModel) return false
		
		if (name != other.name) return false
		if (generics != other.generics) return false
		if (extends != other.extends) return false
		if (values != other.values) return false
		if (properties != other.properties) return false
		if (annotations != other.annotations) return false
		if (type != other.type) return false
		if (mutableVariant != other.mutableVariant) return false
		if (isReferenceSensitive != other.isReferenceSensitive) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(generics)
			.with(extends)
			.with(values)
			.with(properties)
			.with(annotations)
			.with(type)
			.with(mutableVariant)
			.with(isReferenceSensitive)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"generics":${generics.toJsonString()},"extends":${extends.toJsonString()},"values":${values.toJsonString()},"properties":${properties.toJsonString()},"annotations":${annotations.toJsonString()},"type":${type.toJsonString()},"mutableVariant":${mutableVariant.toJsonString()},"isReferenceSensitive":${isReferenceSensitive.toJsonString()}}"""
	
	override fun duplicate(): BitModel
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
