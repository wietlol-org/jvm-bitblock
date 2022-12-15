// hash: #ebd0859c
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModel

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitModelBuilder
{
	var name: String?
		= null
	
	var generics: MutableList<BitGenericTypeDeclaration>?
		= mutableListOf()
	
	var extends: MutableList<BitTypeReference>?
		= mutableListOf()
	
	var values: MutableList<BitValue>?
		= mutableListOf()
	
	var properties: MutableList<BitProperty>?
		= mutableListOf()
	
	var annotations: MutableList<BitAnnotation>?
		= mutableListOf()
	
	var type: BitModelType?
		= null
	
	var mutableVariant: String?
		= null
	
	var isReferenceSensitive: Boolean?
		= null
	
	fun build(): BitModel =
		DefaultBitModel(
			name!!,
			generics!!.toMutableList(),
			extends!!.toMutableList(),
			values!!.toMutableList(),
			properties!!.toMutableList(),
			annotations!!.toMutableList(),
			type!!,
			mutableVariant,
			isReferenceSensitive!!,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
