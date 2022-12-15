package me.wietlol.bitblock.codegenerator.utils

import me.wietlol.bitblock.codegenerator.data.models.BitAnnotation
import me.wietlol.bitblock.codegenerator.data.models.BitLiteralExpression
import me.wietlol.bitblock.codegenerator.data.models.BitName
import me.wietlol.utils.common.cast

interface BitModuleHelper
{
	fun List<BitAnnotation>.findSingleByName(name: String): BitAnnotation? =
		singleOrNull { it.name.stringEquals(name) }
	
	fun <T : Any> BitAnnotation.findValue(): T? =
		findValue(0)
	
	@Suppress("UNCHECKED_CAST")
	fun <T : Any> BitAnnotation.findValue(index: Int): T? =
		arguments.getOrNull(index)?.cast<BitLiteralExpression>()?.value as T?
	
	fun <T> BitAnnotation.getValue(): T =
		getValue(0)
	
	@Suppress("UNCHECKED_CAST")
	fun <T> BitAnnotation.getValue(index: Int): T =
		arguments[index].cast<BitLiteralExpression>().value as T
	
	fun BitName.stringEquals(name: String): Boolean =
		parts.joinToString(".") == name
	
	fun BitName.stringNotEquals(name: String): Boolean =
		stringEquals(name).not()
}
