// hash: #bc53d5cc
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.models

import me.wietlol.bitblock.codegenerator.data.models.*

// @formatter:on
// @tomplot:customCode:start:B8CiSn
// @tomplot:customCode:end
// @formatter:off


data class DefaultBitProperty(
	override val name: String,
	override val type: BitTypeReference,
	override val defaultValue: BitExpression?,
	override val index: Int?,
	override val annotations: List<BitAnnotation>,
	override val propertyType: BitPropertyType,
	override val isTransient: Boolean,
	override val isSerialized: Boolean,
	override val isOverride: Boolean,
) : BitProperty
{
	override fun equals(other: Any?): Boolean =
		isEqualTo(other)
	
	override fun hashCode(): Int =
		computeHashCode()
	
	override fun toString(): String =
		toJson()
	
	override fun duplicate(): DefaultBitProperty =
		copy(
			name = name,
			type = type.duplicate(),
			defaultValue = defaultValue?.duplicate(),
			index = index,
			annotations = annotations.map { it.duplicate() }.toMutableList(),
			propertyType = propertyType.duplicate(),
			isTransient = isTransient,
			isSerialized = isSerialized,
			isOverride = isOverride,
		)
	
	// @formatter:on
	// @tomplot:customCode:start:fIpaBB
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
