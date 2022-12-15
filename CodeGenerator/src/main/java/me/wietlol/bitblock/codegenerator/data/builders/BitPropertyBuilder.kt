// hash: #4ed0abdd
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitProperty

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitPropertyBuilder
{
	var name: String?
		= null
	
	var type: BitTypeReference?
		= null
	
	var defaultValue: BitExpression?
		= null
	
	var index: Int?
		= null
	
	var annotations: MutableList<BitAnnotation>?
		= mutableListOf()
	
	var propertyType: BitPropertyType?
		= null
	
	var isTransient: Boolean?
		= null
	
	var isSerialized: Boolean?
		= null
	
	var isOverride: Boolean?
		= null
	
	fun build(): BitProperty =
		DefaultBitProperty(
			name!!,
			type!!,
			defaultValue,
			index,
			annotations!!.toMutableList(),
			propertyType!!,
			isTransient!!,
			isSerialized!!,
			isOverride!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
