// hash: #d40920ea
// @formatter:off

package me.wietlol.bitblock.codegenerator.data.serializers

import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.codegenerator.data.builders.BitGenericVarianceBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitGenericVariance
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object BitGenericVarianceSerializer : ModelSerializer<BitGenericVariance, BitGenericVariance>
{
	private val endOfObject: Int = 0
	
	private val idIndex: Int = 1
	
	private val codeIndex: Int = 2
	
	override val modelId: UUID
		get() = BitGenericVariance.serializationKey
	
	override val dataClass: Class<BitGenericVariance>
		get() = BitGenericVariance::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitGenericVariance)
	{
		stream.writeUnsignedVarInt(idIndex)
		schema.serialize(serializationContext, stream, entity.id)
		
		stream.writeUnsignedVarInt(codeIndex)
		schema.serialize(serializationContext, stream, entity.code)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitGenericVariance
	{
		var id: Int? = null
		var code: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitGenericVariance(
					id!!,
					code!!,
				)
				idIndex -> id = schema.deserialize(deserializationContext, stream)
				codeIndex -> code = schema.deserialize(deserializationContext, stream)
				else -> schema.deserialize<Any>(deserializationContext, stream)
			}
		}
	}
	
	// @formatter:on
	// @tomplot:customCode:start:5CFs54
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
