package me.wietlol.bitblock.codegenerator.generators

import me.wietlol.bitblock.codegenerator.data.models.BitModule
import java.io.File

interface CodeGenerator
{
	fun generateCode(module: BitModule, root: File, options: CodeGeneratorOptions)
}
