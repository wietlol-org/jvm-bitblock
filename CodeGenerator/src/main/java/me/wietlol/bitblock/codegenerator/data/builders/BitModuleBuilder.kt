// hash: #ea6fdc00
// @formatter:off
package me.wietlol.bitblock.codegenerator.data.builders

import me.wietlol.bitblock.codegenerator.data.models.*
import me.wietlol.bitblock.codegenerator.data.models.DefaultBitModule

// @formatter:on
// @tomplot:customCode:start:f5k3GB
// @tomplot:customCode:end
// @formatter:off


class BitModuleBuilder
{
	var module: String?
		= null
	
	var owner: String?
		= null
	
	var version: String?
		= null
	
	var apiRootPackage: String?
		= null
	
	var implRootPackage: String?
		= null
	
	var builderPackage: String?
		= null
	
	var apiModelPackage: String?
		= null
	
	var implModelPackage: String?
		= null
	
	var serializerPackage: String?
		= null
	
	var annotations: MutableList<BitAnnotation>?
		= mutableListOf()
	
	var models: MutableList<BitModel>?
		= mutableListOf()
	
	var relativeRoot: String?
		= null
	
	var relativeApiRoot: String?
		= null
	
	var relativeImplRoot: String?
		= null
	
	var useExistingModels: Boolean?
		= null
	
	var builderFacade: String?
		= null
	
	var mutableModelNameFormat: String?
		= null
	
	var implementationNameFormat: String?
		= null
	
	fun build(): BitModule =
		DefaultBitModule(
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
	
	// @formatter:on
	// @tomplot:customCode:start:0ETUWm
	// @tomplot:customCode:end
	// @formatter:off
}
// @formatter:on
