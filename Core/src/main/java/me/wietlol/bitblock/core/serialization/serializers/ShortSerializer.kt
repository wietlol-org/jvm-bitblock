package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readShort
import me.wietlol.utils.common.streams.writeShort
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object ShortSerializer : ModelSerializer<Short, Short>
{
	override val modelId: UUID = UUID.fromString("f1074584-f3cb-40ae-90c9-86855049e4d2")
	override val dataClass: Class<Short> = Short::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Short) =
		stream.writeShort(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Short =
		stream.readShort()
}
