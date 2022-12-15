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

object ListSerializer : ModelSerializer<List<Any>, List<*>>
{
	override val modelId: UUID = UUID.fromString("40289a4f-7cb9-4922-a9f6-c33d5f705cd4")
	override val dataClass: Class<List<*>> = List::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: List<Any>)
	{
		println("writing size, appending VarInt(${entity.size})")
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
			.toList()
}

