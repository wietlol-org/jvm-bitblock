// hash: #41a51a9b
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitGenericTypeDeclaration

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitGenericTypeDeclarationBuilder
{
	var name: String?
		= null
	
	var variance: BitGenericVariance?
		= null
	
	var superType: BitTypeReference?
		= null
	
	fun build(): BitGenericTypeDeclaration =
		DefaultBitGenericTypeDeclaration(
			name!!,
			variance,
			superType,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
