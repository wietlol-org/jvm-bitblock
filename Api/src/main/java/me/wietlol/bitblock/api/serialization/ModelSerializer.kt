package me.wietlol.bitblock.api.serialization

import java.io.InputStream
import java.io.OutputStream
import java.util.*

@Suppress("unused")
interface ModelSerializer<M : Any, D : Any>
{
	/**
	 * @return the id of the runtime model.
	 */
	val modelId: UUID
	
	/**
	 * @return the class of the serialized data.
	 */
	val dataClass: Class<D>
	
	/**
	 * @param stream the output stream that the entity must be written to.
	 * @param schema the schema being used to find ModelSerializers for fields of the object.
	 * @param entity the object that must be serialized.
	 */
	fun serialize(serializationContext: SerializationContext, stream: OutputStream, schema: Schema, entity: M)
	
	/**
	 * @param stream the input stream that contains the data.
	 * @param schema the schema being used to find ModelSerializers for fields of the object.
	 * @return the newly generated object from the input stream.
	 */
	fun deserialize(deserializationContext: DeserializationContext, stream: InputStream, schema: Schema): M
}
