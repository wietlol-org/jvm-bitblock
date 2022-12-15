// hash: #e6c13198
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitValue

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitValueBuilder
{
	var name: String?
		= null
	
	var arguments: MutableList<BitExpression>?
		= mutableListOf()
	
	fun build(): BitValue =
		DefaultBitValue(
			name!!,
			arguments!!.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
