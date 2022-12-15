package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.core.serialization.types.VarSInt
import me.wietlol.utils.common.streams.readSignedVarInt
import me.wietlol.utils.common.streams.writeSignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object VarSIntSerializer : ModelSerializer<Int, VarSInt>
{
	override val modelId: UUID = UUID.fromString("f6f679ec-ab41-4a37-8c99-58ff15cbbe40")
	override val dataClass: Class<VarSInt> = VarSInt::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Int) =
		stream.writeSignedVarInt(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Int =
		stream.readSignedVarInt()
}
