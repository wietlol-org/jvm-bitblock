// hash: #6af8e43b
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
import me.wietlol.bitblock.codegenerator.data.builders.BitNameBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitName
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// Generated by BitBlock version 1.0.0

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off

object BitNameSerializer : ModelSerializer<BitName, BitName>
{
	private val endOfObject: Int = 0
	
	private val partsIndex: Int = 1
	
	override val modelId: UUID
		get() = BitName.serializationKey
	
	override val dataClass: Class<BitName>
		get() = BitName::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitName)
	{
		stream.writeUnsignedVarInt(partsIndex)
		schema.serialize(serializationContext, stream, entity.parts)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitName
	{
		var parts: MutableList<String>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitName(
					parts!!.toMutableList(),
				)
				partsIndex -> parts = schema.deserialize(deserializationContext, stream)
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
