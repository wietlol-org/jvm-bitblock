package me.wietlol.bitblock.api.serialization

import java.util.*

@Suppress("unused")
interface BitKeyResolver
{
	fun findKey(model: Any): UUID?
}
