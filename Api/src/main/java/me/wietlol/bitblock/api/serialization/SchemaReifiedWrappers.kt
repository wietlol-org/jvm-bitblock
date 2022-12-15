package me.wietlol.bitblock.api.serialization

import java.io.InputStream
import java.nio.ByteBuffer

inline fun <reified T : Any> Schema.deserialize(bytes: ByteArray): T? =
	deserialize(bytes, T::class.java)

inline fun <reified T : Any> Schema.deserialize(bytes: ByteArray, key: Int): T =
	deserialize(bytes, key, T::class.java)

inline fun <reified T : Any> Schema.deserialize(bytes: ByteArray, entry: SchemaEntry<T, *>): T =
	deserialize(bytes, entry, T::class.java)

inline fun <reified T : Any> Schema.deserialize(buffer: ByteBuffer): T? =
	deserialize(buffer, T::class.java)

inline fun <reified T : Any> Schema.deserialize(buffer: ByteBuffer, key: Int): T =
	deserialize(buffer, key, T::class.java)

inline fun <reified T : Any> Schema.deserialize(buffer: ByteBuffer, entry: SchemaEntry<T, *>): T =
	deserialize(buffer, entry, T::class.java)

inline fun <reified T : Any> Schema.deserialize(stream: InputStream): T? =
	deserialize(stream, T::class.java)

inline fun <reified T : Any> Schema.deserialize(stream: InputStream, key: Int): T =
	deserialize(stream, key, T::class.java)

inline fun <reified T : Any> Schema.deserialize(stream: InputStream, entry: SchemaEntry<T, *>): T =
	deserialize(stream, entry, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, bytes: ByteArray): T? =
	deserialize(deserializationContext, bytes, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, bytes: ByteArray, key: Int): T =
	deserialize(deserializationContext, bytes, key, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, bytes: ByteArray, entry: SchemaEntry<T, *>): T =
	deserialize(deserializationContext, bytes, entry, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, buffer: ByteBuffer): T? =
	deserialize(deserializationContext, buffer, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, buffer: ByteBuffer, key: Int): T =
	deserialize(deserializationContext, buffer, key, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, buffer: ByteBuffer, entry: SchemaEntry<T, *>): T =
	deserialize(deserializationContext, buffer, entry, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, stream: InputStream): T? =
	deserialize(deserializationContext, stream, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, stream: InputStream, key: Int): T =
	deserialize(deserializationContext, stream, key, T::class.java)

inline fun <reified T : Any> Schema.deserialize(deserializationContext: DeserializationContext, stream: InputStream, entry: SchemaEntry<T, *>): T =
	deserialize(deserializationContext, stream, entry, T::class.java)
