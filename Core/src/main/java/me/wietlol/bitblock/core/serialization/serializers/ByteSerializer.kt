package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readByte
import me.wietlol.utils.common.streams.writeByte
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object ByteSerializer : ModelSerializer<Byte, Byte>
{
	override val modelId: UUID = UUID.fromString("da1cd32e-3920-4b14-b2db-26fccbe63ebf")
	override val dataClass: Class<Byte> = Byte::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Byte) =
		stream.writeByte(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Byte =
		stream.readByte()
}
