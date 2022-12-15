// hash: #7047f1a0
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitIntegerExpression

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitIntegerExpressionBuilder
{
	var value: Int?
		= null
	
	fun build(): BitIntegerExpression =
		DefaultBitIntegerExpression(
			value!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on