package me.wietlol.bitblock.codegenerator.generators

typealias ConfirmationDialog = (ConfirmationRequest) -> Boolean

fun literalConfirmationDialog(value: Boolean): ConfirmationDialog =
	{ value }

fun consoleConfirmationDialog(): ConfirmationDialog =
	::consoleConfirm

private fun consoleConfirm(confirmationRequest: ConfirmationRequest): Boolean
{
	println(confirmationRequest.text)
	println("do you want to apply these changes? (Y / N)")
	
	while (true)
	{
		val answer = readLine()!!
		
		when (answer.toLowerCase())
		{
			"y" -> return true
			"n" -> return false
			else -> {
				println("that is not a valid answer, either answer with Y or N")
			}
		}
	}
}
