package me.wietlol.bitblock.codegenerator

import me.wietlol.bitblock.codegenerator.generators.kotlin.BitModuleKotlinGenerator
import me.wietlol.utils.common.surroundWith
import java.io.File

object Main
{
	/*
		todo:
			- performance benchmarks on larger data (no behavior change)
			- create executable so models can be generated via the command line (no behavior change)
			- documentation
		
		wont do:
			- null serialization
			- generics saved? not by fully qualified type name, perhaps by a list of known types... known contracts/blueprints and base set
		
		to test:
			- versioning revision
	*/
	
	@JvmStatic
	fun main(args: Array<String>)
	{
		val supportedLanguages = mapOf(
			"kotlin" to { BitModuleKotlinGenerator() },
			"kt" to { BitModuleKotlinGenerator() },
		)
		
		val supportedLanguagesString = supportedLanguages
			.keys
			.joinToString(", ") { it.surroundWith("'") }
			.surroundWith("supported languages: [", "]")
		
		val language = args.getOrNull(0)
		val bitmoduleFilePath = args.getOrNull(1)
		
		if (language == null)
		{
			System.err.println("Missing language argument. cli pattern: 'java -jar bitblock.jar language path', $supportedLanguagesString")
			return
		}
		
		val generator = supportedLanguages[language.lowercase()]
		
		if (generator == null)
		{
			System.err.println(
				"Invalid option for language '$language', $supportedLanguagesString"
			)
			return
		}
		
		if (bitmoduleFilePath == null)
		{
			System.err.println("Missing bitmodule path argument. cli pattern: 'java -jar bitblock.jar language path'")
			return
		}
		
		val bitModuleFile = File(bitmoduleFilePath).absoluteFile
		
		if (bitModuleFile.exists().not())
		{
			System.err.println("Cannot find the bitmodule file at ${bitModuleFile.absolutePath}")
			return
		}
		
		if (bitModuleFile.isFile.not())
		{
			System.err.println("Bitmodule file is not a file, at ${bitModuleFile.absolutePath}")
			return
		}
		
		println("processing ${bitModuleFile.absolutePath}")
		BitModuleProcessor.processBitModule(
			bitModuleFile,
			generator()
		)
		
		println("done")
	}
}
