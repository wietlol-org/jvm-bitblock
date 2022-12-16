package me.wietlol.bitblock.codegenerator

import me.wietlol.bitblock.codegenerator.data.BitModule
import me.wietlol.bitblock.codegenerator.generators.kotlin.BitModuleKotlinGenerator
import me.wietlol.bitblock.core.BitSchemaBuilder
import org.junit.Test
import java.io.File

class BitBlockManager
{
	@Test
	fun processBitModule()
	{
		BitModuleProcessor.processBitModule(
			File("src/main/resources/me/wietlol/bitblock/codegenerator/BitModule.bitmodule"),
			BitModuleKotlinGenerator()
		)
	}
	
	@Test
	fun generateBitSchema()
	{
		BitSchemaBuilder.buildSchema(
			File("src/main/resources/me/wietlol/bitblock/codegenerator/BitModule.bitschema"),
			BitModule.modelSerializers
		)
	}
}
