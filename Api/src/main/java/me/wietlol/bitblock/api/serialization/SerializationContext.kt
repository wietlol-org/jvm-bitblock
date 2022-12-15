package me.wietlol.bitblock.api.serialization

@Suppress("unused")
interface SerializationContext
{
	fun registerBitReference(reference: Any): RegisterResult
	
	data class RegisterResult(
		val key: Int,
		val isNewlyRegistered: Boolean
	)
	
	fun addSerializationNode(reference: Any): Int?
	
	fun removeSerializationNode()
}
