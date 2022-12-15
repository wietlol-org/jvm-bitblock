// hash: #a887024d
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
import me.wietlol.bitblock.codegenerator.data.builders.BitGenericTypeDeclarationBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitGenericTypeDeclaration
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitGenericTypeDeclarationSerializer : ModelSerializer<BitGenericTypeDeclaration, BitGenericTypeDeclaration>
{
	private val endOfObject: Int
		= 0
	
	private val nameIndex: Int
		= 1
	
	private val varianceIndex: Int
		= 2
	
	private val superTypeIndex: Int
		= 3
	
	override val modelId: UUID
		get() = BitGenericTypeDeclaration.serializationKey
	
	override val dataClass: Class<BitGenericTypeDeclaration>
		get() = BitGenericTypeDeclaration::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitGenericTypeDeclaration)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		entity.variance?.also {
			stream.writeUnsignedVarInt(varianceIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.superType?.also {
			stream.writeUnsignedVarInt(superTypeIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitGenericTypeDeclaration
	{
		var name: String? = null
		var variance: BitGenericVariance? = null
		var superType: BitTypeReference? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitGenericTypeDeclaration(
					name!!,
					variance,
					superType,
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				varianceIndex -> variance = schema.deserialize(deserializationContext, stream)
				superTypeIndex -> superType = schema.deserialize(deserializationContext, stream)
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
