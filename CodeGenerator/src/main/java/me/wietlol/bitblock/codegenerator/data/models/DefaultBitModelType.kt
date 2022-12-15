// hash: #bf439d8f
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitModelType(
	override val id: Int,
	override val name: String,
) : BitModelType
{
	companion object
	{
		val blueprint: DefaultBitModelType
			= DefaultBitModelType(1, "blueprint")
		
		val contract: DefaultBitModelType
			= DefaultBitModelType(2, "contract")
	}
	
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitModelType =
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
