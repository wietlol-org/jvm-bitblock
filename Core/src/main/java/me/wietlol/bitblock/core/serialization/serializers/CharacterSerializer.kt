package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readChar
import me.wietlol.utils.common.streams.writeChar
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object CharacterSerializer : ModelSerializer<Char, Char>
{
	override val modelId: UUID = UUID.fromString("a2bff66f-21ad-40b6-8c9b-2987375dabeb")
	override val dataClass: Class<Char> = Char::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Char) =
		stream.writeChar(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Char =
		stream.readChar()
}
