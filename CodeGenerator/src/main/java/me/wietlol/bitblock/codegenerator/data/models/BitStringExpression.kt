// hash: #667a6fae
// data: serializationKey:d294929d-ea33-4a7c-b265-399a4172d94e
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


interface BitStringExpression : BitSerializable, BitLiteralExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("d294929d-ea33-4a7c-b265-399a4172d94e")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val type: String
		get() = "stringExpression"
	
	override val value: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitStringExpression) return false
		
		if (type != other.type) return false
		if (value != other.value) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(type)
			.with(value)
	
	override fun toJson(): String =
		"""{"type":${type.toJsonString()},"value":${value.toJsonString()}}"""
	
	override fun duplicate(): BitStringExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on