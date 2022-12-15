package me.wietlol.bitblock.core.registry

@Suppress("CanBeParameter")
class InvalidModelRegistryKeyFormatException(
	val keyString: String,
	cause: Throwable? = null
) : RuntimeException("ModelRegistryKey '$keyString' does not have exactly 4 values.", cause)
