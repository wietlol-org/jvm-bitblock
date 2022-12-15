package me.wietlol.bitblock.core

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey
import me.wietlol.bitblock.core.registry.LocalModelRegistry
import me.wietlol.bitblock.core.serialization.serializers.BitDynamicObjectSerializer
import me.wietlol.bitblock.core.serialization.serializers.BooleanSerializer
import me.wietlol.bitblock.core.serialization.serializers.ByteSerializer
import me.wietlol.bitblock.core.serialization.serializers.CharacterSerializer
import me.wietlol.bitblock.core.serialization.serializers.DoubleSerializer
import me.wietlol.bitblock.core.serialization.serializers.FloatSerializer
import me.wietlol.bitblock.core.serialization.serializers.IntegerSerializer
import me.wietlol.bitblock.core.serialization.serializers.ListSerializer
import me.wietlol.bitblock.core.serialization.serializers.LongSerializer
import me.wietlol.bitblock.core.serialization.serializers.MapSerializer
import me.wietlol.bitblock.core.serialization.serializers.SequenceSerializer
import me.wietlol.bitblock.core.serialization.serializers.SetSerializer
import me.wietlol.bitblock.core.serialization.serializers.ShortSerializer
import me.wietlol.bitblock.core.serialization.serializers.StringSerializer
import me.wietlol.bitblock.core.serialization.serializers.UuidSerializer
import me.wietlol.bitblock.core.serialization.serializers.VarSIntSerializer
import me.wietlol.bitblock.core.serialization.serializers.VarSLongSerializer
import me.wietlol.bitblock.core.serialization.serializers.VarUIntSerializer
import me.wietlol.bitblock.core.serialization.serializers.VarULongSerializer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

object BitBlockBase : RegistrySet
{
	private const val module = "Base"
	private const val owner = "BitBlock"
	private const val baseVersion = "1.0"
	
	override val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>> = mapOf(
		CommonModelRegistryKey("Character", module, owner, baseVersion) to CharacterSerializer,
		CommonModelRegistryKey("String", module, owner, baseVersion) to StringSerializer(StandardCharsets.UTF_8),
		CommonModelRegistryKey("Boolean", module, owner, baseVersion) to BooleanSerializer,
		CommonModelRegistryKey("Byte", module, owner, baseVersion) to ByteSerializer,
		CommonModelRegistryKey("Short", module, owner, baseVersion) to ShortSerializer,
		CommonModelRegistryKey("Integer", module, owner, baseVersion) to IntegerSerializer,
		CommonModelRegistryKey("Long", module, owner, baseVersion) to LongSerializer,
		CommonModelRegistryKey("VarSInt", module, owner, baseVersion) to VarSIntSerializer,
		CommonModelRegistryKey("VarUInt", module, owner, baseVersion) to VarUIntSerializer,
		CommonModelRegistryKey("VarSLong", module, owner, baseVersion) to VarSLongSerializer,
		CommonModelRegistryKey("VarULong", module, owner, baseVersion) to VarULongSerializer,
		CommonModelRegistryKey("Float", module, owner, baseVersion) to FloatSerializer,
		CommonModelRegistryKey("Double", module, owner, baseVersion) to DoubleSerializer,
		CommonModelRegistryKey("List", module, owner, baseVersion) to ListSerializer,
		CommonModelRegistryKey("Map", module, owner, baseVersion) to MapSerializer,
		CommonModelRegistryKey("Uuid", module, owner, baseVersion) to UuidSerializer,
		CommonModelRegistryKey("Dynamic", module, owner, baseVersion) to BitDynamicObjectSerializer,
		CommonModelRegistryKey("Sequence", module, owner, baseVersion) to SequenceSerializer,
		CommonModelRegistryKey("Set", module, owner, baseVersion) to SetSerializer,
	)
		.plus(
			Charset.availableCharsets()
				.map { CommonModelRegistryKey("String:${it.key}", module, owner, baseVersion) to StringSerializer(it.value) }
		)
	
	val modelRegistry: ModelRegistry = LocalModelRegistry()
		.also { initialize(it) }
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: modelRegistry)::set)
}
