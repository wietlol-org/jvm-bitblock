// hash: #47189543
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitAnnotation

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitAnnotationBuilder
{
	var name: BitName?
		= null
	
	var arguments: MutableList<BitExpression>?
		= mutableListOf()
	
	fun build(): BitAnnotation =
		DefaultBitAnnotation(
			name!!,
			arguments!!.toMutableList(),
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
