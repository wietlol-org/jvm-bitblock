package me.wietlol.bitblock.codegenerator.generators

data class CodeGeneratorOptions(
	val confirmationDialog: ConfirmationDialog,
	val logger: (String) -> Unit,
	val internalBuilders: Boolean,
	val hasInlinedValues: Boolean,
)
