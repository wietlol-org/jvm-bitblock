// hash: #69843530
// @formatter:off
package me.wietlol.bitblock.codegenerator.data

import me.wietlol.bitblock.api.registry.ModelRegistry
import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.bitblock.api.registry.RegistrySet
import me.wietlol.bitblock.api.serialization.ModelSerializer
import me.wietlol.bitblock.codegenerator.data.serializers.*
import me.wietlol.bitblock.core.BitBlockBase
import me.wietlol.bitblock.core.registry.CommonModelRegistryKey

object BitModule : RegistrySet
{
	override val modelSerializers: Map<ModelRegistryKey, ModelSerializer<*, *>>
		= createModelSerializers()
	
	private fun createModelSerializers(): Map<ModelRegistryKey, ModelSerializer<*, *>> =
		mapOf(
			CommonModelRegistryKey("BitModule", "BitModule", "Wietlol", "1.0") to BitModuleSerializer,
			CommonModelRegistryKey("BitAnnotation", "BitModule", "Wietlol", "1.0") to BitAnnotationSerializer,
			CommonModelRegistryKey("BitName", "BitModule", "Wietlol", "1.0") to BitNameSerializer,
			CommonModelRegistryKey("BitModelType", "BitModule", "Wietlol", "1.0") to BitModelTypeSerializer,
			CommonModelRegistryKey("BitModel", "BitModule", "Wietlol", "1.0") to BitModelSerializer,
			CommonModelRegistryKey("BitPropertyType", "BitModule", "Wietlol", "1.0") to BitPropertyTypeSerializer,
			CommonModelRegistryKey("BitGenericTypeDeclaration", "BitModule", "Wietlol", "1.0") to BitGenericTypeDeclarationSerializer,
			CommonModelRegistryKey("BitGenericVariance", "BitModule", "Wietlol", "1.0") to BitGenericVarianceSerializer,
			CommonModelRegistryKey("BitProperty", "BitModule", "Wietlol", "1.0") to BitPropertySerializer,
			CommonModelRegistryKey("BitDirectTypeReference", "BitModule", "Wietlol", "1.0") to BitDirectTypeReferenceSerializer,
			CommonModelRegistryKey("BitStarTypeReference", "BitModule", "Wietlol", "1.0") to BitStarTypeReferenceSerializer,
			CommonModelRegistryKey("BitValue", "BitModule", "Wietlol", "1.0") to BitValueSerializer,
			CommonModelRegistryKey("BitIntegerExpression", "BitModule", "Wietlol", "1.0") to BitIntegerExpressionSerializer,
			CommonModelRegistryKey("BitStringExpression", "BitModule", "Wietlol", "1.0") to BitStringExpressionSerializer,
			CommonModelRegistryKey("BitMemberExpression", "BitModule", "Wietlol", "1.0") to BitMemberExpressionSerializer,
			CommonModelRegistryKey("BitNameExpression", "BitModule", "Wietlol", "1.0") to BitNameExpressionSerializer,
		)
	
	override fun initialize(registry: ModelRegistry?) =
		modelSerializers.forEach((registry ?: BitBlockBase.modelRegistry)::set)
}
// @formatter:on
