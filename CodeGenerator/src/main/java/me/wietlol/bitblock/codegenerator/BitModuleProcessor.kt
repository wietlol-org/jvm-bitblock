package me.wietlol.bitblock.codegenerator

import me.wietlol.bitblock.codegenerator.data.models.BitModule
import me.wietlol.bitblock.codegenerator.data.models.BitStringExpression
import me.wietlol.bitblock.codegenerator.generators.CodeGenerator
import me.wietlol.bitblock.codegenerator.generators.CodeGeneratorOptions
import me.wietlol.bitblock.codegenerator.generators.consoleConfirmationDialog
import me.wietlol.bitblock.codegenerator.parser.BitModuleParser
import java.io.File

object BitModuleProcessor
{
	fun processBitModule(file: File, codeGenerator: CodeGenerator)
	{
		val bitModule: BitModule = BitModuleParser().parseBitModule(file)
		
		generateSourceCode(bitModule, file.parentFile, codeGenerator)
	}
	
	private fun generateSourceCode(bitModule: BitModule, folder: File, codeGenerator: CodeGenerator)
	{
		codeGenerator.generateCode(bitModule, folder, createOptions(bitModule))
	}
	
	private fun createOptions(bitModule: BitModule): CodeGeneratorOptions =
		CodeGeneratorOptions(
			consoleConfirmationDialog(),
			::println,
			true,
			bitModule.annotations.any {
				it.name.parts.single() == "InlinedValues"
					&& it.arguments.singleOrNull()?.let { it as BitStringExpression }?.value != "false"
		    },
		)
}
