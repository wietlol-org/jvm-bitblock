// hash: #54718a75
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
import me.wietlol.bitblock.codegenerator.data.builders.BitDirectTypeReferenceBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitDirectTypeReference
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitDirectTypeReferenceSerializer : ModelSerializer<BitDirectTypeReference, BitDirectTypeReference>
{
	private val endOfObject: Int
		= 0
	
	private val typeNameIndex: Int
		= 1
	
	private val genericsIndex: Int
		= 2
	
	private val isOptionalIndex: Int
		= 3
	
	override val modelId: UUID
		get() = BitDirectTypeReference.serializationKey
	
	override val dataClass: Class<BitDirectTypeReference>
		get() = BitDirectTypeReference::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitDirectTypeReference)
	{
		stream.writeUnsignedVarInt(typeNameIndex)
		schema.serialize(serializationContext, stream, entity.typeName)
		
		stream.writeUnsignedVarInt(genericsIndex)
		schema.serialize(serializationContext, stream, entity.generics)
		
		stream.writeUnsignedVarInt(isOptionalIndex)
		schema.serialize(serializationContext, stream, entity.isOptional)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitDirectTypeReference
	{
		var typeName: BitName? = null
		var generics: MutableList<BitTypeReference>? = mutableListOf()
		var isOptional: Boolean? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitDirectTypeReference(
					typeName!!,
					generics!!.toMutableList(),
					isOptional!!,
				)
				typeNameIndex -> typeName = schema.deserialize(deserializationContext, stream)
				genericsIndex -> generics = schema.deserialize(deserializationContext, stream)
				isOptionalIndex -> isOptional = schema.deserialize(deserializationContext, stream)
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
