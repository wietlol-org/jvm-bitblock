package me.wietlol.bitblock.api.serialization

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class MissingBitReferenceException(
	val key: Int,
	cause: Throwable? = null
) : SchemaException("Missing reference value for key '$key'.", cause)
