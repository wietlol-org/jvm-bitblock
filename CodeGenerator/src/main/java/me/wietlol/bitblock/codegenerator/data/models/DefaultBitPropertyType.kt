// hash: #61c257e5
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitPropertyType(
	override val id: Int,
	override val name: String,
) : BitPropertyType
{
	companion object
	{
		val property: DefaultBitPropertyType
			= DefaultBitPropertyType(1, "property")
		
		val value: DefaultBitPropertyType
			= DefaultBitPropertyType(2, "value")
	}
	
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitPropertyType =
		copy(
			id = id,
			name = name,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
