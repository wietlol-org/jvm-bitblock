package me.wietlol.bitblock.core.serialization

import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.SerializationContext.RegisterResult

class HashMapSerializationContext : SerializationContext
{
	private val bitReferences: MutableMap<Any, Int> = HashMap()
	
	override fun registerBitReference(reference: Any): RegisterResult
	{
		val key = bitReferences[reference]
		
		return if (key != null)
			RegisterResult(key, false)
		else
		{
			val newKey = bitReferences.size
			bitReferences[reference] = newKey
			RegisterResult(newKey, true)
		}
	}
	
	private val referenceStack: MutableList<Any> = ArrayList()
	
	override fun addSerializationNode(reference: Any): Int?
	{
		(referenceStack.size - 1 downTo 0).forEachIndexed { index, it ->
			if (reference === referenceStack[it])
				return index
		}
		
		referenceStack.add(reference)
		return null
	}
	
	override fun removeSerializationNode()
	{
		referenceStack.removeLast()
	}
}
