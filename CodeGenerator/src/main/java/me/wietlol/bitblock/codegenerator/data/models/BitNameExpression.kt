// hash: #f37d7aa7
// data: serializationKey:b1d1428b-6862-469a-9650-04596333f7e8
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


interface BitNameExpression : BitSerializable, BitExpression, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("b1d1428b-6862-469a-9650-04596333f7e8")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	override val type: String
		get() = "nameExpression"
	
	val name: String
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitNameExpression) return false
		
		if (type != other.type) return false
		if (name != other.name) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(type)
			.with(name)
	
	override fun toJson(): String =
		"""{"type":${type.toJsonString()},"name":${name.toJsonString()}}"""
	
	override fun duplicate(): BitNameExpression
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
