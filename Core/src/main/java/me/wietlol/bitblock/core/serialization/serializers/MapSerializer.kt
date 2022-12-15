package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object MapSerializer : ModelSerializer<Map<Any, Any>, Map<*, *>>
{
	override val modelId: UUID = UUID.fromString("864f21ff-f9d7-47ab-a3f6-e14f56fa4c0f")
	override val dataClass: Class<Map<*, *>> = Map::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Map<Any, Any>)
	{
		stream.writeUnsignedVarInt(entity.size)
		entity.forEach {
			schema.serialize(serializationContext, stream, it.key)
			schema.serialize(serializationContext, stream, it.value)
		}
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Map<Any, Any> =
		generateSequence { 0 }
			.take(stream.readUnsignedVarInt())
			.map { schema.deserialize<Any>(deserializationContext, stream)!! to schema.deserialize<Any>(deserializationContext, stream)!! }
			.toMap()
}
