package me.wietlol.bitblock.core.serialization

import me.wietlol.bitblock.api.serialization.DeserializationContext
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class HashMapDeserializationContext : DeserializationContext
{
	override var currentObjectTypeId: Int = 0
	
	private val bitReferences: MutableMap<Int, Any> = HashMap()
	
	override fun registerBitReference(key: Int, reference: Any)
	{
		bitReferences[key] = reference
	}
	
	override fun loadBitReference(key: Int): Any? =
		bitReferences[key]
	
	private val proxyStack: MutableList<Any?> = ArrayList()
	
	override fun addSerializationNode()
	{
		proxyStack.add(null)
	}
	
	override fun <T> referenceNode(offset: Int, type: Class<T>): T
	{
		proxyStack.removeLast()
		
		val index = proxyStack.size - 1 - offset
		val instance = proxyStack[index]
			?: createProxy(type).also { proxyStack[index] = it }
		
		@Suppress("UNCHECKED_CAST")
		return instance as T
	}
	
	private fun createProxy(type: Class<*>): Any =
		Proxy.newProxyInstance(type.classLoader, arrayOf(type), PassThroughInvocationHandler())
	
	override fun removeSerializationNode(reference: Any)
	{
		val proxy = proxyStack.removeLast()
		if (proxy != null)
		{
			val handler = Proxy.getInvocationHandler(proxy)
			handler as PassThroughInvocationHandler
			handler.instance = reference
		}
	}
	
	private class PassThroughInvocationHandler : InvocationHandler
	{
		lateinit var instance: Any
		
		override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any =
			if (args == null)
				method.invoke(instance)
			else
				method.invoke(instance, *args)
	}
}
