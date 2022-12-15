// hash: #2920641e
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitGenericVariance(
	override val id: Int,
	override val code: String,
) : BitGenericVariance
{
	companion object
	{
		val inVariance: DefaultBitGenericVariance
			= DefaultBitGenericVariance(1, "in")
		
		val outVariance: DefaultBitGenericVariance
			= DefaultBitGenericVariance(2, "out")
	}
	
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitGenericVariance =
		copy(
			id = id,
			code = code,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
