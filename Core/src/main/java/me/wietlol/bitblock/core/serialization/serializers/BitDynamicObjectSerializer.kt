package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.core.serialization.BitDynamicObject
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object BitDynamicObjectSerializer : ModelSerializer<BitDynamicObject, BitDynamicObject>
{
	override val modelId: UUID = UUID.fromString("debbec70-3372-4862-84b6-9ecce0460f42")
	override val dataClass: Class<BitDynamicObject> = BitDynamicObject::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitDynamicObject)
	{
		entity.forEach { (key, value) ->
			stream.writeUnsignedVarInt(key)
			schema.serialize(serializationContext, stream, value)
		}
		
		stream.writeUnsignedVarInt(0)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitDynamicObject
	{
		val typeId = deserializationContext.currentObjectTypeId
		return generateSequence { 0 }
			.map { stream.readUnsignedVarInt() }
			.takeWhile { it != 0 }
			.map { it to schema.deserialize<Any>(deserializationContext, stream)!! }
			.toMap(BitDynamicObject(typeId))
	}
}
