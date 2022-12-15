package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readInt
import me.wietlol.utils.common.streams.writeInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object IntegerSerializer : ModelSerializer<Int, Int>
{
	override val modelId: UUID = UUID.fromString("a76a9fe0-7a54-4acf-9934-62ecfaf9221c")
	override val dataClass: Class<Int> = Int::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Int) =
		stream.writeInt(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Int =
		stream.readInt()
}
