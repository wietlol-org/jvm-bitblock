package me.wietlol.bitblock.core.registry

import me.wietlol.bitblock.api.registry.CollisionInModelRegistryException
import me.wietlol.bitblock.api.registry.MissingModelRegistryEntryException
import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.serialization.ModelSerializer

class LocalModelRegistry : ModelRegistry
{
	private val registry: MutableMap<ModelRegistryKey, ModelSerializer<*, *>> = HashMap()
	
	override fun containsKey(key: ModelRegistryKey): Boolean =
		registry.containsKey(key)
	
	override operator fun set(key: ModelRegistryKey, serializer: ModelSerializer<*, *>): Boolean =
		when (registry[key])
		{
			null ->
			{
				registry[key] = serializer
				true
			}
			serializer ->
				false
			else ->
				throw CollisionInModelRegistryException(key)
		}
	
	override operator fun get(key: ModelRegistryKey): ModelSerializer<*, *> =
		registry[key]
			?: throw MissingModelRegistryEntryException(key)
}
