package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.core.serialization.types.VarUInt
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object VarUIntSerializer : ModelSerializer<Int, VarUInt>
{
	override val modelId: UUID = UUID.fromString("9b7c13c5-5bcb-4e07-957f-da074cde8763")
	override val dataClass: Class<VarUInt> = VarUInt::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Int) =
		stream.writeUnsignedVarInt(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Int =
		stream.readUnsignedVarInt()
}
