// data: serializationKey:cdc57aa3-d7d7-4b6e-8d5a-a4d18ac8ba19
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


interface BitMemberExpression : BitSerializable, BitExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("cdc57aa3-d7d7-4b6e-8d5a-a4d18ac8ba19")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val type: String
		get() = "memberExpression"
	
	val qualifier: BitExpression
	
	val memberName: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitMemberExpression) return false
		
		if (type != other.type) return false
		if (qualifier != other.qualifier) return false
		if (memberName != other.memberName) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(type)
			.with(qualifier)
			.with(memberName)
	
	override fun toJson(): String =
		"""{"type":${type.toJsonString()},"qualifier":${qualifier.toJsonString()},"memberName":${memberName.toJsonString()}}"""
	
	override fun duplicate(): BitMemberExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
