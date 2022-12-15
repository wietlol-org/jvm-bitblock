package me.wietlol.bitblock.api.serialization

abstract class SchemaException(
	message: String,
	cause: Throwable? = null,
) : Exception(message, cause)
