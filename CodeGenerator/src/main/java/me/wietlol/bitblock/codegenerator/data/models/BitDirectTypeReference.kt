// hash: #6a6e402c
// data: serializationKey:3cfa44be-648d-4e0e-b043-d631e2599545
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


interface BitDirectTypeReference : BitSerializable, BitTypeReference, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("3cfa44be-648d-4e0e-b043-d631e2599545")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val typeName: BitName
	
	val generics: List<BitTypeReference>
	
	val isOptional: Boolean
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitDirectTypeReference) return false
		
		if (typeName != other.typeName) return false
		if (generics != other.generics) return false
		if (isOptional != other.isOptional) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(typeName)
			.with(generics)
			.with(isOptional)
	
	override fun toJson(): String =
		"""{"typeName":${typeName.toJsonString()},"generics":${generics.toJsonString()},"isOptional":${isOptional.toJsonString()}}"""
	
	override fun duplicate(): BitDirectTypeReference
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
