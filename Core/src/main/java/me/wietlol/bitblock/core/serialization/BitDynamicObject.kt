package me.wietlol.bitblock.core.serialization

import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.bitblock.core.serialization.serializers.BitDynamicObjectSerializer
import java.util.*
import kotlin.collections.HashMap

class BitDynamicObject(
	val typeId: Int,
	data: Map<Int, Any>? = null
) : HashMap<Int, Any>(data), BitSerializable
{
	override val serializationKey: UUID
		get() = BitDynamicObjectSerializer.modelId
	
	override fun duplicate(): BitDynamicObject =
		BitDynamicObject(typeId, this)
	
	override fun toString(): String =
		"$typeId:${super.toString()}"
}
