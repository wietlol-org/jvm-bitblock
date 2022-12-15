package me.wietlol.bitblock.api.registry

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class MissingModelRegistryEntryException(
	val key: ModelRegistryKey,
	cause: Throwable? = null
) : RuntimeException("This registry has no serializer with the key '$key'.", cause)
