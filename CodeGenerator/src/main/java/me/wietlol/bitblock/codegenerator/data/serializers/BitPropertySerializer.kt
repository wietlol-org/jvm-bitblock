// hash: #8e5c8987
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
import me.wietlol.bitblock.codegenerator.data.builders.BitPropertyBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitProperty
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitPropertySerializer : ModelSerializer<BitProperty, BitProperty>
{
	private val endOfObject: Int
		= 0
	
	private val nameIndex: Int
		= 1
	
	private val typeIndex: Int
		= 2
	
	private val defaultValueIndex: Int
		= 3
	
	private val indexIndex: Int
		= 4
	
	private val annotationsIndex: Int
		= 5
	
	private val propertyTypeIndex: Int
		= 6
	
	private val isTransientIndex: Int
		= 7
	
	private val isSerializedIndex: Int
		= 8
	
	private val isOverrideIndex: Int
		= 9
	
	override val modelId: UUID
		get() = BitProperty.serializationKey
	
	override val dataClass: Class<BitProperty>
		get() = BitProperty::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitProperty)
	{
		stream.writeUnsignedVarInt(nameIndex)
		schema.serialize(serializationContext, stream, entity.name)
		
		stream.writeUnsignedVarInt(typeIndex)
		schema.serialize(serializationContext, stream, entity.type)
		
		entity.defaultValue?.also {
			stream.writeUnsignedVarInt(defaultValueIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.index?.also {
			stream.writeUnsignedVarInt(indexIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(annotationsIndex)
		schema.serialize(serializationContext, stream, entity.annotations)
		
		stream.writeUnsignedVarInt(propertyTypeIndex)
		schema.serialize(serializationContext, stream, entity.propertyType)
		
		stream.writeUnsignedVarInt(isTransientIndex)
		schema.serialize(serializationContext, stream, entity.isTransient)
		
		stream.writeUnsignedVarInt(isSerializedIndex)
		schema.serialize(serializationContext, stream, entity.isSerialized)
		
		stream.writeUnsignedVarInt(isOverrideIndex)
		schema.serialize(serializationContext, stream, entity.isOverride)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitProperty
	{
		var name: String? = null
		var type: BitTypeReference? = null
		var defaultValue: BitExpression? = null
		var index: Int? = null
		var annotations: MutableList<BitAnnotation>? = mutableListOf()
		var propertyType: BitPropertyType? = null
		var isTransient: Boolean? = null
		var isSerialized: Boolean? = null
		var isOverride: Boolean? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitProperty(
					name!!,
					type!!,
					defaultValue,
					index,
					annotations!!.toMutableList(),
					propertyType!!,
					isTransient!!,
					isSerialized!!,
					isOverride!!,
				)
				nameIndex -> name = schema.deserialize(deserializationContext, stream)
				typeIndex -> type = schema.deserialize(deserializationContext, stream)
				defaultValueIndex -> defaultValue = schema.deserialize(deserializationContext, stream)
				indexIndex -> index = schema.deserialize(deserializationContext, stream)
				annotationsIndex -> annotations = schema.deserialize(deserializationContext, stream)
				propertyTypeIndex -> propertyType = schema.deserialize(deserializationContext, stream)
				isTransientIndex -> isTransient = schema.deserialize(deserializationContext, stream)
				isSerializedIndex -> isSerialized = schema.deserialize(deserializationContext, stream)
				isOverrideIndex -> isOverride = schema.deserialize(deserializationContext, stream)
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
