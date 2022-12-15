package me.wietlol.bitblock.api.serialization

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class InvalidModelFieldException(
	val fieldName: String,
	cause: Throwable? = null
) : SchemaException("Invalid field in model: '$fieldName'.", cause)
