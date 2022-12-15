package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.core.serialization.types.VarSLong
import me.wietlol.utils.common.streams.readSignedVarLong
import me.wietlol.utils.common.streams.writeSignedVarLong
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object VarSLongSerializer : ModelSerializer<Long, VarSLong>
{
	override val modelId: UUID = UUID.fromString("46ef134d-ac8f-4ed7-9d4f-d9f0ea54b49b")
	override val dataClass: Class<VarSLong> = VarSLong::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Long) =
		stream.writeSignedVarLong(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Long =
		stream.readSignedVarLong()
}
