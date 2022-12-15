package me.wietlol.bitblock.api.serialization

@Suppress("unused")
interface DeserializationContext
{
	var currentObjectTypeId: Int
	
	fun registerBitReference(key: Int, reference: Any)
	
	fun loadBitReference(key: Int): Any?
	
	fun addSerializationNode()
	
	fun <T> referenceNode(offset: Int, type: Class<T>): T
	
	fun removeSerializationNode(reference: Any)
}
