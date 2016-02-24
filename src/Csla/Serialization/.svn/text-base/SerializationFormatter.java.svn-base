package Csla.Serialization;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Defines an object that can serialize and deserialize object graphs.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface SerializationFormatter {

	/**
	 * Converts a serialization stream into an object graph.
	 * 
	 *        @returns A deserialized object graph.
	 * 
	 * @param serializationStream    Byte stream containing the serialized data.
	 */
	public Object deserialize(ObjectInputStream serializationStream);

	/**
	 * Converts an object graph into a byte stream.
	 * 
	 * @param serializationStream    Stream that will contain the the serialized data.
	 * @param graph    Object graph to be serialized.
	 */
	public void serialize(ObjectOutputStream serializationStream, Object graph);

}