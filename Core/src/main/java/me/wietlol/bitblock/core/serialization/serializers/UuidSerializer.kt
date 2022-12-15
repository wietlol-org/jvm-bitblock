package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.utils.common.streams.readUuid
import me.wietlol.utils.common.streams.writeUuid
import java.io.InputStream
import java.io.OutputStream
import java.util.*

object UuidSerializer : ModelSerializer<UUID, UUID>
{
	override val modelId: UUID = UUID.fromString("86b58ca7-69f1-4992-a284-de54d475aeb9")
	override val dataClass: Class<UUID> = UUID::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: UUID) =
		stream.writeUuid(entity)
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): UUID =
		stream.readUuid()
}
