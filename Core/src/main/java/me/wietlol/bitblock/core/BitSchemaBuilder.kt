package me.wietlol.bitblock.core

import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.BitKeyResolver
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.ImmutableSchema
import java.io.File
import java.io.InputStream

object BitSchemaBuilder
{
	fun buildSchema(file: File, registries: List<RegistrySet>)
	{
		buildSchema(
			file,
			registries
				.map { it.modelSerializers }
				.reduce(Map<out ModelRegistryKey, ModelSerializer<*, *>>::plus)
		)
	}
	
	fun buildSchema(file: File, serializers: Map<out ModelRegistryKey, ModelSerializer<*, *>>)
	{
		val reservedTypeIds = 65
		file
			.printWriter()
			.use { writer ->
				with(writer) {
					BitBlockBase.modelSerializers.keys
						.filter { it.name.contains(":").not() }
						.map { it.toKeyString() }
						.forEachIndexed { index, it -> println("${index + 1}:$it") }
					serializers.keys
						.filter { it.name.contains(":").not() }
						.map { it.toKeyString() }
						.forEachIndexed { index, it -> println("${index + reservedTypeIds}:$it") }
				}
			}
	}
	
	fun buildSchema(resource: InputStream, registries: Collection<RegistrySet>, keyResolvers: Collection<BitKeyResolver> = emptyList()): Schema =
		LocalModelRegistry()
			.also { registry ->
				registries.forEach { it.initialize(registry) }
			}
			.let { ImmutableSchema(resource, it, keyResolvers = keyResolvers) }
}

