// data: serializationKey:c8e79b42-6ab4-4b4d-ae46-0fa7ce68c297
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


interface BitGenericTypeDeclaration : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("c8e79b42-6ab4-4b4d-ae46-0fa7ce68c297")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val variance: BitGenericVariance?
	
	val superType: BitTypeReference?
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitGenericTypeDeclaration) return false
		
		if (name != other.name) return false
		if (variance != other.variance) return false
		if (superType != other.superType) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(variance)
			.with(superType)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"variance":${variance.toJsonString()},"superType":${superType.toJsonString()}}"""
	
	override fun duplicate(): BitGenericTypeDeclaration
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on