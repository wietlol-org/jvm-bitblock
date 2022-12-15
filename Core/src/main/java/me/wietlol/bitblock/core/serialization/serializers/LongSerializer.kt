package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readLong
import me.wietlol.utils.common.streams.writeLong
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object LongSerializer : ModelSerializer<Long, Long>
{
	override val modelId: UUID = UUID.fromString("cba42155-deeb-43a2-9bb6-bf82f6f3f4f1")
	override val dataClass: Class<Long> = Long::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Long) =
		stream.writeLong(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Long =
		stream.readLong()
}
