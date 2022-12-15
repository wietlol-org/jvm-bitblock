// data: serializationKey:088d1973-1faa-4486-a0f6-5175fbbf6ad8
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


interface BitName : BitSerializable, Jsonable
{
	companion object
	{
		val serializationKey: UUID
			get() = UUID.fromString("088d1973-1faa-4486-a0f6-5175fbbf6ad8")
	}
	
	override val serializationKey: UUID
		get() = Companion.serializationKey
	
	val parts: List<String>
	
	fun isEqualTo(other: Any?): Boolean
	{
		if (this === other) return true
		if (other == null) return false
		if (other !is BitName) return false
		
		if (parts != other.parts) return false
		
		return true
	}
	
	fun computeHashCode(): Int =
		emptyHashCode
			.with(parts)
	
	override fun toJson(): String =
		"""{"parts":${parts.toJsonString()}}"""
	
	override fun duplicate(): BitName
	
	// @formatter:on
	// @tomplot:customCode:start:32T3K8
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
