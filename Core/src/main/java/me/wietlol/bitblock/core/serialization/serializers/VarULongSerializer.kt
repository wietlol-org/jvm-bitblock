package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.core.serialization.types.VarULong
import me.wietlol.utils.common.streams.readUnsignedVarLong
import me.wietlol.utils.common.streams.writeUnsignedVarLong
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object VarULongSerializer : ModelSerializer<Long, VarULong>
{
	override val modelId: UUID = UUID.fromString("45b67b8e-2adf-4e79-ab60-5abf358e8571")
	override val dataClass: Class<VarULong> = VarULong::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Long) =
		stream.writeUnsignedVarLong(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Long =
		stream.readUnsignedVarLong()
}
