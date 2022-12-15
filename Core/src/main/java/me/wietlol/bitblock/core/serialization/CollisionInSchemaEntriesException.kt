package me.wietlol.bitblock.core.serialization

import me.wietlol.bitblock.api.serialization.SchemaEntry

@Suppress("CanBeParameter")
class CollisionInSchemaEntriesException(
	val entry: SchemaEntry<*, *>,
	val registryType: RegistryType,
	cause: Throwable? = null
) : RuntimeException("Adding the entry '$entry' collided with an existing entry in the schema registry '$registryType'.", cause)
{
	enum class RegistryType
	{
		KEY_REGISTRY,
		DATA_CLASS_REGISTRY,
		MODEL_CLASS_REGISTRY,;
	}
}
