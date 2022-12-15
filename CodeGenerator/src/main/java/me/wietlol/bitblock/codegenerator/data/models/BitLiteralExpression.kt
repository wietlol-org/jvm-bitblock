// hash: #8e27aee9
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.utils.common.Jsonable

// @formatter:on
// @tomplot:customCode:start:WTcTph
// @tomplot:customCode:end
// @formatter:off


interface BitLiteralExpression : BitSerializable, BitExpression, Jsonable
{
	val value: Any
	
	override fun duplicate(): BitLiteralExpression
	
	// @formatter:on
	// @tomplot:customCode:start:0MOZ71
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on