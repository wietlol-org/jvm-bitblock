// hash: #b45ad04f
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
import me.wietlol.bitblock.codegenerator.data.builders.BitValueBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitValue
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitValueSerializer : ModelSerializer<BitValue, BitValue>
{
	private val endOfObject: Int
		= 0
	
	private val nameIndex: Int
		= 1
	
	private val argumentsIndex: Int
		= 2
	
	override val modelId: UUID
		get() = BitValue.serializationKey
	
	override val dataClass: Class<BitValue>
		get() = BitValue::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitValue)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(argumentsIndex)
		schema.serialize(serializationContext, stream, entity.arguments)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitValue
	{
		var name: String? = null
		var arguments: MutableList<BitExpression>? = mutableListOf()
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitValue(
					name!!,
					arguments!!.toMutableList(),
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				argumentsIndex -> arguments = schema.deserialize(deserializationContext, stream)
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