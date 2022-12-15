package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readString
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeString
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*

class StringSerializer(
	val charset: Charset
) : ModelSerializer<String, String>
{
	override val modelId: UUID = Companion.modelId
	override val dataClass: Class<String> = String::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: String)
	{
		val (key, isNewlyRegistered) = serializationContext.registerBitReference(entity)
		stream.writeUnsignedVarInt(key)
		if (isNewlyRegistered.not())
			return
		
		stream.writeString(entity, charset)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): String
	{
		val referenceKey = stream.readUnsignedVarInt()
		val reference = deserializationContext.loadBitReference(referenceKey)
		if (reference != null)
			return reference as String
		
		return stream.readString(charset).also {
			deserializationContext.registerBitReference(referenceKey, it)
		}
	}
	
	companion object
	{
		val modelId: UUID = UUID.fromString("0c482f5b-edc6-4393-89b8-7a1b3b4c6702")
	}
}
