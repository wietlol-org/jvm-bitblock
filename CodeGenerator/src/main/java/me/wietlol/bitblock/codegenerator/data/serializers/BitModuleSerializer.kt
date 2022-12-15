// hash: #9e1837c4
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.serializers

import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import me.wietlol.bitblock.api.serialization.DeserializationContext
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.api.serialization.Schema
import me.wietlol.bitblock.api.serialization.SerializationContext
import me.wietlol.bitblock.api.serialization.deserialize
import me.wietlol.bitblock.codegenerator.data.builders.BitModuleBuilder
import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.BitModule
import me.wietlol.utils.common.streams.readUnsignedVarInt
import me.wietlol.utils.common.streams.writeUnsignedVarInt

// @formatter:on
// @tomplot:customCode:start:70v0f9
// @tomplot:customCode:end
// @formatter:off


object BitModuleSerializer : ModelSerializer<BitModule, BitModule>
{
	private val endOfObject: Int
		= 0
	
	private val moduleIndex: Int
		= 1
	
	private val ownerIndex: Int
		= 2
	
	private val versionIndex: Int
		= 3
	
	private val apiRootPackageIndex: Int
		= 4
	
	private val implRootPackageIndex: Int
		= 5
	
	private val builderPackageIndex: Int
		= 6
	
	private val apiModelPackageIndex: Int
		= 7
	
	private val implModelPackageIndex: Int
		= 8
	
	private val serializerPackageIndex: Int
		= 9
	
	private val annotationsIndex: Int
		= 10
	
	private val modelsIndex: Int
		= 11
	
	private val relativeRootIndex: Int
		= 12
	
	private val relativeApiRootIndex: Int
		= 13
	
	private val relativeImplRootIndex: Int
		= 14
	
	private val useExistingModelsIndex: Int
		= 15
	
	private val builderFacadeIndex: Int
		= 16
	
	private val mutableModelNameFormatIndex: Int
		= 17
	
	private val implementationNameFormatIndex: Int
		= 18
	
	override val modelId: UUID
		get() = BitModule.serializationKey
	
	override val dataClass: Class<BitModule>
		get() = BitModule::class.java
	
	override fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: BitModule)
	{
		stream.writeUnsignedVarInt(moduleIndex)
		schema.serialize(serializationContext, stream, entity.module)
		
		stream.writeUnsignedVarInt(ownerIndex)
		schema.serialize(serializationContext, stream, entity.owner)
		
		stream.writeUnsignedVarInt(versionIndex)
		schema.serialize(serializationContext, stream, entity.version)
		
		stream.writeUnsignedVarInt(apiRootPackageIndex)
		schema.serialize(serializationContext, stream, entity.apiRootPackage)
		
		stream.writeUnsignedVarInt(implRootPackageIndex)
		schema.serialize(serializationContext, stream, entity.implRootPackage)
		
		stream.writeUnsignedVarInt(builderPackageIndex)
		schema.serialize(serializationContext, stream, entity.builderPackage)
		
		stream.writeUnsignedVarInt(apiModelPackageIndex)
		schema.serialize(serializationContext, stream, entity.apiModelPackage)
		
		stream.writeUnsignedVarInt(implModelPackageIndex)
		schema.serialize(serializationContext, stream, entity.implModelPackage)
		
		stream.writeUnsignedVarInt(serializerPackageIndex)
		schema.serialize(serializationContext, stream, entity.serializerPackage)
		
		stream.writeUnsignedVarInt(annotationsIndex)
		schema.serialize(serializationContext, stream, entity.annotations)
		
		stream.writeUnsignedVarInt(modelsIndex)
		schema.serialize(serializationContext, stream, entity.models)
		
		entity.relativeRoot?.also {
			stream.writeUnsignedVarInt(relativeRootIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.relativeApiRoot?.also {
			stream.writeUnsignedVarInt(relativeApiRootIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		entity.relativeImplRoot?.also {
			stream.writeUnsignedVarInt(relativeImplRootIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(useExistingModelsIndex)
		schema.serialize(serializationContext, stream, entity.useExistingModels)
		
		entity.builderFacade?.also {
			stream.writeUnsignedVarInt(builderFacadeIndex)
			schema.serialize(serializationContext, stream, it)
		}
		
		stream.writeUnsignedVarInt(mutableModelNameFormatIndex)
		schema.serialize(serializationContext, stream, entity.mutableModelNameFormat)
		
		stream.writeUnsignedVarInt(implementationNameFormatIndex)
		schema.serialize(serializationContext, stream, entity.implementationNameFormat)
		
		stream.writeUnsignedVarInt(endOfObject)
	}
	
	override fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): BitModule
	{
		var module: String? = null
		var owner: String? = null
		var version: String? = null
		var apiRootPackage: String? = null
		var implRootPackage: String? = null
		var builderPackage: String? = null
		var apiModelPackage: String? = null
		var implModelPackage: String? = null
		var serializerPackage: String? = null
		var annotations: MutableList<BitAnnotation>? = mutableListOf()
		var models: MutableList<BitModel>? = mutableListOf()
		var relativeRoot: String? = null
		var relativeApiRoot: String? = null
		var relativeImplRoot: String? = null
		var useExistingModels: Boolean? = null
		var builderFacade: String? = null
		var mutableModelNameFormat: String? = null
		var implementationNameFormat: String? = null
		
		while (true)
		{
			when (stream.readUnsignedVarInt())
			{
				endOfObject -> return DefaultBitModule(
					module!!,
					owner!!,
					version!!,
					apiRootPackage!!,
					implRootPackage!!,
					builderPackage!!,
					apiModelPackage!!,
					implModelPackage!!,
					serializerPackage!!,
					annotations!!.toMutableList(),
					models!!.toMutableList(),
					relativeRoot,
					relativeApiRoot,
					relativeImplRoot,
					useExistingModels!!,
					builderFacade,
					mutableModelNameFormat!!,
					implementationNameFormat!!,
				)
				moduleIndex -> module = schema.deserialize(deserializationContext, stream)
				ownerIndex -> owner = schema.deserialize(deserializationContext, stream)
				versionIndex -> version = schema.deserialize(deserializationContext, stream)
				apiRootPackageIndex -> apiRootPackage = schema.deserialize(deserializationContext, stream)
				implRootPackageIndex -> implRootPackage = schema.deserialize(deserializationContext, stream)
				builderPackageIndex -> builderPackage = schema.deserialize(deserializationContext, stream)
				apiModelPackageIndex -> apiModelPackage = schema.deserialize(deserializationContext, stream)
				implModelPackageIndex -> implModelPackage = schema.deserialize(deserializationContext, stream)
				serializerPackageIndex -> serializerPackage = schema.deserialize(deserializationContext, stream)
				annotationsIndex -> annotations = schema.deserialize(deserializationContext, stream)
				modelsIndex -> models = schema.deserialize(deserializationContext, stream)
				relativeRootIndex -> relativeRoot = schema.deserialize(deserializationContext, stream)
				relativeApiRootIndex -> relativeApiRoot = schema.deserialize(deserializationContext, stream)
				relativeImplRootIndex -> relativeImplRoot = schema.deserialize(deserializationContext, stream)
				useExistingModelsIndex -> useExistingModels = schema.deserialize(deserializationContext, stream)
				builderFacadeIndex -> builderFacade = schema.deserialize(deserializationContext, stream)
				mutableModelNameFormatIndex -> mutableModelNameFormat = schema.deserialize(deserializationContext, stream)
				implementationNameFormatIndex -> implementationNameFormat = schema.deserialize(deserializationContext, stream)
				else -> schema.deserialize<Any>(deserializationContext, stream)
			}
		}
	}
	
	// @formatter:on
	// @tomplot:customCode:start:5CFs54
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
