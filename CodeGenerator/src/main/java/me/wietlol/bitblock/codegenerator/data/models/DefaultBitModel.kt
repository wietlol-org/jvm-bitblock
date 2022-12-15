// hash: #6e9c07ea
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitModel(
	override val name: String,
	override val generics: List<BitGenericTypeDeclaration>,
	override val extends: List<BitTypeReference>,
	override val values: List<BitValue>,
	override val properties: List<BitProperty>,
	override val annotations: List<BitAnnotation>,
	override val type: BitModelType,
	override val mutableVariant: String?,
	override val isReferenceSensitive: Boolean,
) : BitModel
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitModel =
		copy(
			name = name,
			generics = generics.map { it.duplicate() }.toMutableList(),
			extends = extends.map { it.duplicate() }.toMutableList(),
			values = values.map { it.duplicate() }.toMutableList(),
			properties = properties.map { it.duplicate() }.toMutableList(),
			annotations = annotations.map { it.duplicate() }.toMutableList(),
			type = type.duplicate(),
			mutableVariant = mutableVariant,
			isReferenceSensitive = isReferenceSensitive,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on