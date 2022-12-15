// hash: #f46c042d
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitMemberExpression

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitMemberExpressionBuilder
{
	var qualifier: BitExpression?
		= null
	
	var memberName: String?
		= null
	
	fun build(): BitMemberExpression =
		DefaultBitMemberExpression(
			qualifier!!,
			memberName!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
