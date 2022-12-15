package me.wietlol.bitblock.core.registry

import me.wietlol.bitblock.api.registry.ModelRegistryKey
import me.wietlol.utils.common.emptyHashCode
import me.wietlol.utils.common.with
import java.text.MessageFormat
import java.util.regex.Pattern

class CommonModelRegistryKey : ModelRegistryKey
{
	override val name: CharSequence
	override val module: CharSequence
	override val owner: CharSequence
	override val version: CharSequence
	
	constructor(name: CharSequence, module: CharSequence, owner: CharSequence, version: CharSequence)
	{
		this.name = name.toString()
		this.module = module.toString()
		this.owner = owner.toString()
		this.version = version.toString()
	}
	
	constructor(keyString: CharSequence)
	{
		val str = keyString.toString()
		val values: List<String> = str.split(PARSE_REGEX)
		
		if (values.size != 4)
			throw InvalidModelRegistryKeyFormatException(str)
		
		name = decode(values[0])
		module = decode(values[1])
		owner = decode(values[2])
		version = decode(values[3])
	}
	
	override fun toKeyString(): CharSequence =
		MessageFormat.format(
			TO_KEY_STRING_PATTERN,
			encode(name),
			encode(module),
			encode(owner),
			encode(version))
	
	override fun equals(other: Any?): Boolean
	{
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as CommonModelRegistryKey
		
		if (name != other.name) return false
		if (module != other.module) return false
		if (owner != other.owner) return false
		if (version != other.version) return false
		
		return true
	}
	
	override fun hashCode(): Int =
		emptyHashCode
			.with(name)
			.with(module)
			.with(owner)
			.with(version)
	
	override fun toString(): String =
		MessageFormat.format(
			TO_STRING_PATTERN,
			name,
			module,
			owner,
			version
		)
	
	companion object
	{
		private val PARSE_REGEX: Regex by lazy { "(?<!\\|)\\|".toRegex() }
		private const val TO_KEY_STRING_PATTERN = "{0}|{1}|{2}|{3}"
		private const val TO_STRING_PATTERN = "CommonModelRegistryKey(name=''{0}'', module=''{1}'', owner=''{2}'', version=''{3}'')"
		
		private val ENCODE_PATTERN = Pattern.compile("\\|")
		private fun encode(input: CharSequence): String =
			ENCODE_PATTERN.matcher(input).replaceAll("||")
		
		private val DECODE_PATTERN = Pattern.compile("\\|\\|")
		private fun decode(input: CharSequence): String =
			DECODE_PATTERN.matcher(input).replaceAll("|")
	}
}
