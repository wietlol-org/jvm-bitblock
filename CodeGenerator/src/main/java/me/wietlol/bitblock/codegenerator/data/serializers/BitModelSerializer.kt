// hash: #5b315e6e
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
import me.wietlol.bitblock.codegenerator.data.builders.BitModelBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitModel
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitModelSerializer : ModelSerializer<BitModel, BitModel>
{
	private val endOfObject: Int
		= 0
	
	private val nameIndex: Int
		= 1
	
	private val genericsIndex: Int
		= 2
	
	private val extendsIndex: Int
		= 3
	
	private val valuesIndex: Int
		= 4
	
	private val propertiesIndex: Int
		= 5
	
	private val annotationsIndex: Int
		= 6
	
	private val typeIndex: Int
		= 7
	
	private val mutableVariantIndex: Int
		= 8
	
	private val isReferenceSensitiveIndex: Int
		= 9
	
	override val modelId: UUID
		get() = BitModel.serializationKey
	
	override val dataClass: Class<BitModel>
		get() = BitModel::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitModel)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(genericsIndex)
		schema.serialize(serializationContext, stream, entity.generics)
		
		stream.writeUnsignedVarInt(extendsIndex)
		schema.serialize(serializationContext, stream, entity.extends)
		
		stream.writeUnsignedVarInt(valuesIndex)
		schema.serialize(serializationContext, stream, entity.values)
		
		stream.writeUnsignedVarInt(propertiesIndex)
		schema.serialize(serializationContext, stream, entity.properties)
		
		stream.writeUnsignedVarInt(annotationsIndex)
		schema.serialize(serializationContext, stream, entity.annotations)
		
		stream.writeUnsignedVarInt(typeIndex)
		schema.serialize(serializationContext, stream, entity.type)
		
		entity.mutableVariant?.also {
			stream.writeUnsignedVarInt(mutableVariantIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(isReferenceSensitiveIndex)
		schema.serialize(serializationContext, stream, entity.isReferenceSensitive)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitModel
	{
		var name: String? = null
		var generics: MutableList<BitGenericTypeDeclaration>? = mutableListOf()
		var extends: MutableList<BitTypeReference>? = mutableListOf()
		var values: MutableList<BitValue>? = mutableListOf()
		var properties: MutableList<BitProperty>? = mutableListOf()
		var annotations: MutableList<BitAnnotation>? = mutableListOf()
		var type: BitModelType? = null
		var mutableVariant: String? = null
		var isReferenceSensitive: Boolean? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitModel(
					name!!,
					generics!!.toMutableList(),
					extends!!.toMutableList(),
					values!!.toMutableList(),
					properties!!.toMutableList(),
					annotations!!.toMutableList(),
					type!!,
					mutableVariant,
					isReferenceSensitive!!,
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				genericsIndex -> generics = schema.deserialize(deserializationContext, stream)
				extendsIndex -> extends = schema.deserialize(deserializationContext, stream)
				valuesIndex -> values = schema.deserialize(deserializationContext, stream)
				propertiesIndex -> properties = schema.deserialize(deserializationContext, stream)
				annotationsIndex -> annotations = schema.deserialize(deserializationContext, stream)
				typeIndex -> type = schema.deserialize(deserializationContext, stream)
				mutableVariantIndex -> mutableVariant = schema.deserialize(deserializationContext, stream)
				isReferenceSensitiveIndex -> isReferenceSensitive = schema.deserialize(deserializationContext, stream)
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