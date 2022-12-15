// hash: #146f9311
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
import me.wietlol.bitblock.codegenerator.data.builders.BitMemberExpressionBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitMemberExpression
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitMemberExpressionSerializer : ModelSerializer<BitMemberExpression, BitMemberExpression>
{
	private val endOfObject: Int
		= 0
	
	private val qualifierIndex: Int
		= 1
	
	private val memberNameIndex: Int
		= 2
	
	override val modelId: UUID
		get() = BitMemberExpression.serializationKey
	
	override val dataClass: Class<BitMemberExpression>
		get() = BitMemberExpression::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitMemberExpression)
	{
		stream.writeUnsignedVarInt(qualifierIndex)
		schema.serialize(serializationContext, stream, entity.qualifier)
		
		stream.writeUnsignedVarInt(memberNameIndex)
		schema.serialize(serializationContext, stream, entity.memberName)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitMemberExpression
	{
		var qualifier: BitExpression? = null
		var memberName: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitMemberExpression(
					qualifier!!,
					memberName!!,
				)
				qualifierIndex -> qualifier = schema.deserialize(deserializationContext, stream)
				memberNameIndex -> memberName = schema.deserialize(deserializationContext, stream)
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