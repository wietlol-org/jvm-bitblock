package me.wietlol.bitblock.api.registry

@Suppress("unused")
interface ModelRegistryKey
{
	val name: CharSequence
	val module: CharSequence
	val owner: CharSequence
	val version: CharSequence
	
	fun toKeyString(): CharSequence
}
