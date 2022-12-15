// hash: #9617bf5c
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitDirectTypeReference(
	override val typeName: BitName,
	override val generics: List<BitTypeReference>,
	override val isOptional: Boolean,
) : BitDirectTypeReference
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitDirectTypeReference =
		copy(
			typeName = typeName.duplicate(),
			generics = generics.map { it.duplicate() }.toMutableList(),
			isOptional = isOptional,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
