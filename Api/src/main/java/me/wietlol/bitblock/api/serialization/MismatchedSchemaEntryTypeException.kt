package me.wietlol.bitblock.api.serialization

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class MismatchedSchemaEntryTypeException(
	val modelClass: Class<*>,
	val valueClass: Class<*>,
	cause: Throwable? = null
) : SchemaException("Mismatched classes from model: '$modelClass' to value: '$valueClass'.", cause)
