package me.wietlol.bitblock.core.serialization

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.serialization.BitKeyResolver
import me.wietlol.bitblock.api.serialization.BitSerializable
import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.MismatchedSchemaEntryTypeException
import me.wietlol.bitblock.api.serialization.MissingSchemaEntryException
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SchemaEntry
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.UnsupportedObjectException
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.bitblock.core.serialization.CollisionInSchemaEntriesException.RegistryType.DATA_CLASS_REGISTRY
import me.wietlol.bitblock.core.serialization.CollisionInSchemaEntriesException.RegistryType.KEY_REGISTRY
import me.wietlol.bitblock.core.serialization.CollisionInSchemaEntriesException.RegistryType.MODEL_CLASS_REGISTRY
import me.wietlol.bitblock.core.serialization.serializers.BooleanSerializer
import me.wietlol.bitblock.core.serialization.serializers.DoubleSerializer
import me.wietlol.bitblock.core.serialization.serializers.BitDynamicObjectSerializer
import me.wietlol.bitblock.core.serialization.serializers.FloatSerializer
import me.wietlol.bitblock.core.serialization.serializers.IntegerSerializer
import me.wietlol.bitblock.core.serialization.serializers.ListSerializer
import me.wietlol.bitblock.core.serialization.serializers.LongSerializer
import me.wietlol.bitblock.core.serialization.serializers.MapSerializer
import me.wietlol.bitblock.core.serialization.serializers.SetSerializer
import me.wietlol.bitblock.core.serialization.serializers.StringSerializer
import me.wietlol.bitblock.core.serialization.serializers.UuidSerializer
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class ImmutableSchema : Schema
{
	private val modelIdRegistry: MutableMap<UUID, SchemaEntry<*, *>>
	private val dataClassRegistry: MutableMap<Class<*>, SchemaEntry<*, *>>
	private val entryKeyRegistry: MutableMap<Int, SchemaEntry<*, *>>
	private val rawKeys: Map<Class<out Any>, UUID>
	private val keyResolvers: List<BitKeyResolver>
	
	private val unknownEntryKey: Int = 0
	private val referenceEntryKey: Int = 1
	private val reservedIndices: Int = 8
	
	constructor(
		stream: InputStream,
		modelRegistry: ModelRegistry = BitBlockBase.modelRegistry,
		rawKeys: Map<Class<out Any>, UUID> = baseRawKeys,
		keyResolvers: Collection<BitKeyResolver> = emptyList(),
	)
		: this(stream.readBytes(), modelRegistry, rawKeys, keyResolvers)
	
	constructor(
		file: File,
		modelRegistry: ModelRegistry = BitBlockBase.modelRegistry,
		rawKeys: Map<Class<out Any>, UUID> = baseRawKeys,
		keyResolvers: Collection<BitKeyResolver> = emptyList(),
	)
		: this(file.readBytes(), modelRegistry, rawKeys, keyResolvers)
	
	constructor(
		bytes: ByteArray,
		modelRegistry: ModelRegistry = BitBlockBase.modelRegistry,
		rawKeys: Map<Class<out Any>, UUID> = baseRawKeys,
		keyResolvers: Collection<BitKeyResolver> = emptyList(),
	)
		: this(String(bytes), modelRegistry, rawKeys, keyResolvers)
	
	constructor(
		text: String,
		modelRegistry: ModelRegistry = BitBlockBase.modelRegistry,
		rawKeys: Map<Class<out Any>, UUID> = baseRawKeys,
		keyResolvers: Collection<BitKeyResolver> = emptyList(),
	)
		: this(extractModelSerializers(text, modelRegistry), rawKeys, keyResolvers)
	
	constructor(
		serializers: Map<Int, ModelSerializer<*, *>>,
		rawKeys: Map<Class<out Any>, UUID> = baseRawKeys,
		keyResolvers: Collection<BitKeyResolver> = emptyList(),
	)
	{
		val size: Int = serializers.size
		modelIdRegistry = HashMap(size)
		dataClassRegistry = HashMap(size)
		entryKeyRegistry = HashMap(size)
		
		serializers.forEach { addEntry(it.key, it.value) }
		
		this.rawKeys = rawKeys
		this.keyResolvers = keyResolvers + BaseKeyResolver
	}
	
	private fun addEntry(id: Int, serializer: ModelSerializer<*, *>) =
		addEntry(CommonSchemaEntry(id, serializer))
	
	private fun addEntry(entry: SchemaEntry<*, *>)
	{
		val key: Int = entry.entryKey
		if (entryKeyRegistry.containsKey(key))
			throw CollisionInSchemaEntriesException(entry, KEY_REGISTRY)
		val dataClass = entry.modelSerializer.dataClass
		if (dataClassRegistry.containsKey(dataClass))
			throw CollisionInSchemaEntriesException(entry, DATA_CLASS_REGISTRY)
		val modelId = entry.modelSerializer.modelId
		if (modelIdRegistry.containsKey(modelId))
			throw CollisionInSchemaEntriesException(entry, MODEL_CLASS_REGISTRY)
		
		entryKeyRegistry[key] = entry
		dataClassRegistry[dataClass] = entry
		modelIdRegistry[modelId] = entry
	}
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : Any> serialize(serializationContext: SerializationContext, stream: OutputStream, entity: T)
	{
		val referenceOffset = serializationContext.addSerializationNode(entity)
		
		if (referenceOffset != null)
		{
			println("value is reference, appending VarInt($referenceEntryKey), appending VarInt($referenceOffset")
			stream.writeUnsignedVarInt(referenceEntryKey)
			stream.writeUnsignedVarInt(referenceOffset)
		}
		else
		{
			println("value is ${entity.javaClass.simpleName}")
			val key: UUID = findSerialKey(entity)
			val entry: SchemaEntry<T, *> = modelIdRegistry[key] as SchemaEntry<T, *>?
				?: throw MissingSchemaEntryException(key, entity.javaClass)
			
			if (entity is BitDynamicObject)
				writeHeader(stream, entity.typeId)
			else
				writeHeader(stream, entry.entryKey)
			serialize(serializationContext, stream, entity, entry)
			
			serializationContext.removeSerializationNode()
		}
	}
	
	override fun <T : Any> serialize(serializationContext: SerializationContext, stream: OutputStream, entity: T, entry: SchemaEntry<T, *>)
	{
		entry.modelSerializer.serialize(serializationContext, stream, this, entity)
	}
	
	private fun writeHeader(stream: OutputStream, key: Int)
	{
		println("writing header, appending VarInt(${key + reservedIndices})")
		stream.writeUnsignedVarInt(key + reservedIndices)
	}
	
	override fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, modelClass: Class<T>): T?
	{
		deserializationContext.addSerializationNode()
		
		val entryKey: Int = stream.readUnsignedVarInt()
		if (entryKey == unknownEntryKey)
			return null

		if (entryKey == referenceEntryKey)
		{
			val offset = stream.readUnsignedVarInt()
			return deserializationContext.referenceNode(offset, modelClass)
		}
		
		return deserialize(deserializationContext, stream, entryKey - reservedIndices, modelClass)
			.also { deserializationContext.removeSerializationNode(it) }
	}
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, key: Int, modelClass: Class<T>): T
	{
		deserializationContext.currentObjectTypeId = key
		try
		{
			return (entryKeyRegistry[key] as SchemaEntry<T, *>?)
				?.let { deserialize(deserializationContext, stream, it, modelClass) }
				?: deserialize(deserializationContext, stream, BitDynamicObjectSerializer, BitDynamicObject::class.java) as T?
				?: throw MissingSchemaEntryException(key, modelClass)
		}
		finally
		{
			deserializationContext.currentObjectTypeId = 0
		}
	}
	
	override fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, modelSerializer: ModelSerializer<T, *>, modelClass: Class<T>): T
	{
		val value: T = modelSerializer.deserialize(deserializationContext, stream, this)
		
		if (!modelClass.isInstance(value))
			throw MismatchedSchemaEntryTypeException(modelClass, value.javaClass)
		
		return value
	}
	
	override fun createSerializationContext(): SerializationContext =
		HashMapSerializationContext()
	
	override fun createDeserializationContext(): DeserializationContext =
		HashMapDeserializationContext()
	
	private fun findSerialKey(model: Any): UUID
	{
		if (model is BitSerializable)
			return model.serializationKey
		
		val rawKey: UUID? = rawKeys[model.javaClass]
		if (rawKey != null)
			return rawKey
		
		val resolvedKey = keyResolvers
			.asSequence()
			.map { it.findKey(model) }
			.filterNotNull()
			.firstOrNull()
		if (resolvedKey != null)
			return resolvedKey
		
		throw UnsupportedObjectException(model)
	}
	
	companion object
	{
		private fun extractModelSerializers(text: String, modelRegistry: ModelRegistry): Map<Int, ModelSerializer<*, *>> =
			text
				.lines()
				.asSequence()
				.filter { it.isNotEmpty() }
				.map { it.split(":", limit = 2) }
				.map { Pair(it[0].toInt(), modelRegistry[CommonModelRegistryKey(it[1])]) }
				.toMap()
		
		val baseRawKeys: Map<Class<out Any>, UUID> = hashMapOf(
			String::class.java to StringSerializer.modelId,
			java.lang.String::class.java to StringSerializer.modelId,
			Int::class.java to IntegerSerializer.modelId,
			Integer::class.java to IntegerSerializer.modelId,
			Long::class.java to LongSerializer.modelId,
			java.lang.Long::class.java to LongSerializer.modelId,
			Float::class.java to FloatSerializer.modelId,
			java.lang.Float::class.java to FloatSerializer.modelId,
			Double::class.java to DoubleSerializer.modelId,
			java.lang.Double::class.java to DoubleSerializer.modelId,
			Boolean::class.java to BooleanSerializer.modelId,
			java.lang.Boolean::class.java to BooleanSerializer.modelId,
			UUID::class.java to UuidSerializer.modelId
			// todo add more as needed
		)
		
		object BaseKeyResolver : BitKeyResolver
		{
			override fun findKey(model: Any): UUID? =
				when (model)
				{
					is Map<*, *> -> MapSerializer.modelId
					is List<*> -> ListSerializer.modelId
					is Set<*> -> SetSerializer.modelId
					else -> null
				}
		}
	}
}
