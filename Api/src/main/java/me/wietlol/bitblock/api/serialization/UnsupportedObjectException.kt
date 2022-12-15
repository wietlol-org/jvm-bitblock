package me.wietlol.bitblock.api.serialization

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class UnsupportedObjectException(
	val value: Any,
	cause: Throwable? = null
) : SchemaException("Unsupported object for serialization of type '${value.javaClass}'.", cause)
