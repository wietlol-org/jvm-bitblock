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
import java.util.stream.Stream

object SequenceSerializer : ModelSerializer<Sequence<Any>, Sequence<Any>>
{
	private const val endOfObject = 0
	
	override val modelId: UUID = UUID.fromString("58d936e1-e3dc-4b2c-a4b4-c8e992fbe4c5")
	@Suppress("UNCHECKED_CAST")
	override val dataClass: Class<Sequence<Any>> = Stream::class.java as Class<Sequence<Any>>
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Sequence<Any>)
	{
		entity.forEach {
			schema.serialize(serializationContext, stream, it)
		}
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Sequence<Any> =
		generateSequence { stream.readUnsignedVarInt() }
			.takeWhile { it != endOfObject }
			.map { schema.deserialize(deserializationContext, stream, it) }
}
