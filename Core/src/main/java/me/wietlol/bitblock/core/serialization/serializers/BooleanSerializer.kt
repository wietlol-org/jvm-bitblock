package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readBoolean
import me.wietlol.utils.common.streams.writeBoolean
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object BooleanSerializer : ModelSerializer<Boolean, Boolean>
{
	override val modelId: UUID = UUID.fromString("438e0e60-9b78-46cb-b3a7-2f4ba7ffdd37")
	override val dataClass: Class<Boolean> = Boolean::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Boolean) =
		stream.writeBoolean(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Boolean =
		stream.readBoolean()
}
