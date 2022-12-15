package me.wietlol.bitblock.api.registry

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate")
class CollisionInModelRegistryException(
	val key: ModelRegistryKey,
	cause: Throwable? = null
) : RuntimeException("A model serializer already exists with the key '$key'.", cause)
