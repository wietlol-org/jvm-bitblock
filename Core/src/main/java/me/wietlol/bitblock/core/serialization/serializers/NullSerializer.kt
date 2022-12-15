package me.wietlol.bitblock.core.serialization.serializers

import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.core.serialization.types.Null
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@Deprecated("Removed because it should not be used.", level = DeprecationLevel.HIDDEN)
object NullSerializer : ModelSerializer<Null, Null>
{
	override val modelId: UUID = UUID.fromString("7755cefc-c035-4ebf-b011-5142da3afce7")
	override val dataClass: Class<Null> = Null::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: Null)
	{
		// nothing written
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): Null =
		Null
}
