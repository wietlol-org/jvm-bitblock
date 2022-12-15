package me.wietlol.bitblock.api.registry

import me.wietlol.bitblock.api.serialization.ModelSerializer

@Suppress("unused")
interface ModelRegistry
{
	@Throws(CollisionInModelRegistryException::class)
	operator fun set(key: ModelRegistryKey, serializer: ModelSerializer<*, *>): Boolean
	
	@Throws(MissingModelRegistryEntryException::class)
	operator fun get(key: ModelRegistryKey): ModelSerializer<*, *>
	
	fun containsKey(key: ModelRegistryKey): Boolean
}
