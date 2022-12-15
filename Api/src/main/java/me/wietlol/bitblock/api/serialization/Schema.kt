package me.wietlol.bitblock.api.serialization

import me.wietlol.utils.common.streams.ByteBufferBackedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer

@Suppress("unused")
interface Schema
{
	/*
		object:     [header] [properties]
		header:     [serializationKey]
		serialKey:  (unsigned var int) (>=1)
		properties: [property]+ [0]
		property:   [key] [value]
		key:        (unsigned var int) (>=1)
		value:      [object]
					| [length] [object]{length}
					| [0]
		length:     (unsigned int) (>=0)
		
		serializeObject public
		deserializeObject public
		serializeProperties internal
		serialize internal
	 */
	
	/**
	 * Serializes an entity to a byte array.
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(entity: T): ByteArray =
		serialize(createSerializationContext(), entity)
	
	/**
	 * Serializes an entity to a byte array.
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(entity: T, entry: SchemaEntry<T, *>): ByteArray =
		serialize(createSerializationContext(), entity, entry)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serializeToBuffer(entity: T): ByteBuffer =
		serializeToBuffer(createSerializationContext(), entity)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serializeToBuffer(entity: T, entry: SchemaEntry<T, *>): ByteBuffer =
		serializeToBuffer(createSerializationContext(), entity, entry)
	
	/**
	 * Serializes an entity with its header to a binary stream.
	 *
	 * Output: \[object]
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(stream: OutputStream, entity: T) =
		serialize(createSerializationContext(), stream, entity)
	
	/**
	 * Serializes an entity to a binary stream.
	 *
	 * Output: \[properties]
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(stream: OutputStream, entity: T, entry: SchemaEntry<T, *>) =
		serialize(createSerializationContext(), stream, entity, entry)
	
	/**
	 * Serializes an entity to a byte array.
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(serializationContext: SerializationContext, entity: T): ByteArray =
		ByteArrayOutputStream().use {
			serialize(serializationContext, it, entity)
			it.toByteArray()
		}
	
	/**
	 * Serializes an entity to a byte array.
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(serializationContext: SerializationContext, entity: T, entry: SchemaEntry<T, *>): ByteArray =
		ByteArrayOutputStream()
			.use {
				serialize(serializationContext, it, entity, entry)
				it.toByteArray()
			}
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serializeToBuffer(serializationContext: SerializationContext, entity: T): ByteBuffer =
		serialize(serializationContext, entity).let { ByteBuffer.wrap(it) }
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serializeToBuffer(serializationContext: SerializationContext, entity: T, entry: SchemaEntry<T, *>): ByteBuffer =
		serialize(serializationContext, entity, entry).let { ByteBuffer.wrap(it) }
	
	/**
	 * Serializes an entity with its header to a binary stream.
	 *
	 * Output: \[object]
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(serializationContext: SerializationContext, stream: OutputStream, entity: T)
	
	/**
	 * Serializes an entity to a binary stream.
	 *
	 * Output: \[properties]
	 */
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, UnsupportedObjectException::class, IOException::class)
	fun <T : Any> serialize(serializationContext: SerializationContext, stream: OutputStream, entity: T, entry: SchemaEntry<T, *>)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(data: ByteArray, modelClass: Class<T>): T? =
		deserialize(createDeserializationContext(), data, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(data: ByteArray, entry: SchemaEntry<T, *>, modelClass: Class<T>): T =
		deserialize(createDeserializationContext(), data, entry, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(data: ByteArray, key: Int, modelClass: Class<T>): T =
		deserialize(createDeserializationContext(), data, key, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(data: ByteBuffer, modelClass: Class<T>): T? =
		deserialize(createDeserializationContext(), data, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(data: ByteBuffer, entry: SchemaEntry<T, *>, modelClass: Class<T>): T =
		deserialize(createDeserializationContext(), data, entry, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(data: ByteBuffer, key: Int, modelClass: Class<T>): T =
		deserialize(createDeserializationContext(), data, key, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(stream: InputStream, modelClass: Class<T>): T? =
		deserialize(createDeserializationContext(), stream, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(stream: InputStream, entry: SchemaEntry<T, *>, modelClass: Class<T>): T =
		deserialize(createDeserializationContext(), stream, entry, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(stream: InputStream, key: Int, modelClass: Class<T>): T =
		deserialize(createDeserializationContext(), stream, key, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, data: ByteArray, modelClass: Class<T>): T? =
		ByteArrayInputStream(data).use { deserialize(deserializationContext, it, modelClass) }
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, data: ByteArray, entry: SchemaEntry<T, *>, modelClass: Class<T>): T =
		ByteArrayInputStream(data).use { inputStream -> deserialize(deserializationContext, inputStream, entry, modelClass) }
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, data: ByteArray, key: Int, modelClass: Class<T>): T =
		ByteArrayInputStream(data).use { deserialize(deserializationContext, it, key, modelClass) }
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, data: ByteBuffer, modelClass: Class<T>): T? =
		deserialize(deserializationContext, ByteBufferBackedInputStream(data), modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, data: ByteBuffer, entry: SchemaEntry<T, *>, modelClass: Class<T>): T =
		deserialize(deserializationContext, ByteBufferBackedInputStream(data), entry, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, data: ByteBuffer, key: Int, modelClass: Class<T>): T =
		deserialize(deserializationContext, ByteBufferBackedInputStream(data), key, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, modelClass: Class<T>): T?
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, entry: SchemaEntry<T, *>, modelClass: Class<T>): T =
		deserialize(deserializationContext, stream, entry.modelSerializer, modelClass)
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, modelSerializer: ModelSerializer<T, *>, modelClass: Class<T>): T
	
	@Throws(MismatchedSchemaEntryTypeException::class, MissingSchemaEntryException::class, InvalidModelFieldException::class, MissingBitReferenceException::class, IOException::class)
	fun <T : Any> deserialize(deserializationContext: DeserializationContext, stream: InputStream, key: Int, modelClass: Class<T>): T
	
	fun createSerializationContext(): SerializationContext
	fun createDeserializationContext(): DeserializationContext
}
