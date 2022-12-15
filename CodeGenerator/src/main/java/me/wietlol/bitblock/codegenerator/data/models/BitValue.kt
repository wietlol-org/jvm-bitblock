// data: serializationKey:7cd429f3-f3f9-4d30-985b-e9a1906f2bdb
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


interface BitValue : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("7cd429f3-f3f9-4d30-985b-e9a1906f2bdb")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val name: String
	
	val arguments: List<BitExpression>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitValue) return false
		
		if (name != other.name) return false
		if (arguments != other.arguments) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(name)
			.with(arguments)
	
	override fun toJson(): String =
		"""{"name":${name.toJsonString()},"arguments":${arguments.toJsonString()}}"""
	
	override fun duplicate(): BitValue
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
