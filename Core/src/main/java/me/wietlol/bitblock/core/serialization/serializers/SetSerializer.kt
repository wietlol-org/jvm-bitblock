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

object SetSerializer : ModelSerializer<Set<Any>, Set<*>>
{
	override val modelId: UUID = UUID.fromString("38bf63c7-1ccb-45a9-8d9e-2939935b20f8")
	override val dataClass: Class<Set<*>> = Set::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Set<Any>)
	{
		stream.writeUnsignedVarInt(entity.size)
		entity.forEach {
			schema.serialize(serializationContext, stream, it)
		}
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema) =
		generateSequence { 0 }
			.take(stream.readUnsignedVarInt())
			.map { schema.deserialize<Any>(deserializationContext, stream) }
			.filterNotNull()
			.toSet()
}
