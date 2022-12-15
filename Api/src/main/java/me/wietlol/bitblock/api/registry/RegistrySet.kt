package me.wietlol.bitblock.api.registry

import me.wietlol.bitblock.api.serialization.ModelSerializer

@Suppress("unused")
interface RegistrySet
{
	val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>>
	
	fun initialize(registry: ModelRegistry? = null)
}
