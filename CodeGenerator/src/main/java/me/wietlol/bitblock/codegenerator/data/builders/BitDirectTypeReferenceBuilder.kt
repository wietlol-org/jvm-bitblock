// hash: #928ef2fe
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitDirectTypeReference

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitDirectTypeReferenceBuilder
{
	var typeName: BitName?
		= null
	
	var generics: MutableList<BitTypeReference>?
		= mutableListOf()
	
	var isOptional: Boolean?
		= null
	
	fun build(): BitDirectTypeReference =
		DefaultBitDirectTypeReference(
			typeName!!,
			generics!!.toMutableList(),
			isOptional!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on