package me.wietlol.bitblock.api.serialization

import java.util.*

@Suppress("unused")
interface BitSerializable
{
	val serializationKey: UUID
	
	fun duplicate(): BitSerializable
}
