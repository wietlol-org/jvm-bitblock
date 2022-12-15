// hash: #f92894d9
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
import me.wietlol.bitblock.codegenerator.data.builders.BitStringExpressionBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitStringExpression
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitStringExpressionSerializer : ModelSerializer<BitStringExpression, BitStringExpression>
{
	private val endOfObject: Int
		= 0
	
	private val valueIndex: Int
		= 1
	
	override val modelId: UUID
		get() = BitStringExpression.serializationKey
	
	override val dataClass: Class<BitStringExpression>
		get() = BitStringExpression::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitStringExpression)
	{
		stream.writeUnsignedVarInt(valueIndex)
		schema.serialize(serializationContext, stream, entity.value)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitStringExpression
	{
		var value: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitStringExpression(
					value!!,
				)
				valueIndex -> value = schema.deserialize(deserializationContext, stream)
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