// hash: #ef8c5439
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitGenericVariance

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitGenericVarianceBuilder
{
	var id: Int?
		= null
	
	var code: String?
		= null
	
	fun build(): BitGenericVariance =
		DefaultBitGenericVariance(
			id!!,
			code!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
