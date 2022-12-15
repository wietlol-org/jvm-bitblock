// data: serializationKey:a02735e1-b9b5-41ea-81f1-dcfda23dd403
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


interface BitProperty : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("a02735e1-b9b5-41ea-81f1-dcfda23dd403")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val type: BitTypeReference
	
	val defaultValue: BitExpression?
	
	val index: Int?
	
	val annotations: List<BitAnnotation>
	
	val propertyType: BitPropertyType
	
	val isTransient: Boolean
	
	val isSerialized: Boolean
	
	val isOverride: Boolean
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitProperty) return false
		
		if (name != other.name) return false
		if (type != other.type) return false
		if (defaultValue != other.defaultValue) return false
		if (index != other.index) return false
		if (annotations != other.annotations) return false
		if (propertyType != other.propertyType) return false
		if (isTransient != other.isTransient) return false
		if (isSerialized != other.isSerialized) return false
		if (isOverride != other.isOverride) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(type)
			.with(defaultValue)
			.with(index)
			.with(annotations)
			.with(propertyType)
			.with(isTransient)
			.with(isSerialized)
			.with(isOverride)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"type":${type.toJsonString()},"defaultValue":${defaultValue.toJsonString()},"index":${index.toJsonString()},"annotations":${annotations.toJsonString()},"propertyType":${propertyType.toJsonString()},"isTransient":${isTransient.toJsonString()},"isSerialized":${isSerialized.toJsonString()},"isOverride":${isOverride.toJsonString()}}"""
	
	override fun duplicate(): BitProperty
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
