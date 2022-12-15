package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readFloat
import me.wietlol.utils.common.streams.writeFloat
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object FloatSerializer : ModelSerializer<Float, Float>
{
	override val modelId: UUID = UUID.fromString("f3f3ae15-ed62-4d46-be97-795d4dc494c5")
	override val dataClass: Class<Float> = Float::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Float) =
		stream.writeFloat(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Float =
		stream.readFloat()
}
