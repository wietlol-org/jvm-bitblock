package me.wietlol.bitblock.core.serialization

import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.SchemaEntry

class CommonSchemaEntry<T : Any, C : Any>(
	override val entryKey: Int,
	override val modelSerializer: ModelSerializer<T, C>
) : SchemaEntry<T, C>
