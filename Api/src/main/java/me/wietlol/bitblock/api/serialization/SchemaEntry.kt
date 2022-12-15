package me.wietlol.bitblock.api.serialization

@Suppress("unused")
interface SchemaEntry<T : Any, C : Any>
{
	val entryKey: Int
	
	val modelSerializer: ModelSerializer<T, C>
}
