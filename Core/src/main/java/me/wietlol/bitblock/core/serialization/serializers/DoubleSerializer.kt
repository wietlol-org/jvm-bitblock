package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readDouble
import me.wietlol.utils.common.streams.writeDouble
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object DoubleSerializer : ModelSerializer<Double, Double>
{
	override val modelId: UUID = UUID.fromString("3d018f44-a817-4d68-86f2-ebfdd9fa189f")
	override val dataClass: Class<Double> = Double::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Double) =
		stream.writeDouble(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Double =
		stream.readDouble()
}
