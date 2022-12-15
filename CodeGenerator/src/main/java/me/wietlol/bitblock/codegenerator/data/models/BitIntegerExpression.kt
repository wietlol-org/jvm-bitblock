// hash: #7d69697f
// data: serializationKey:f7fd06e7-f824-49c6-ad83-793ebcd5c6ff
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


interface BitIntegerExpression : BitSerializable, BitLiteralExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("f7fd06e7-f824-49c6-ad83-793ebcd5c6ff")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val type: String
		get() = "integerExpression"
	
	override val value: Int
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitIntegerExpression) return false
		
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
	
	override fun duplicate(): BitIntegerExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
