package me.wietlol.bitblock.api.serialization

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class MissingSchemaEntryException(
	val key: Any,
	val entityClass: Class<*>,
	cause: Throwable? = null
) : SchemaException("Missing schema entry for type '$entityClass' and key '$key'.", cause)
