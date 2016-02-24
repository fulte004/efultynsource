package Csla.Serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Wraps the <see cref="BinaryFormatter"/> in the
 *      <see cref="ISerializationFormatter"/> interface so it can be used in a
 * standardized manner.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:24 PM
 */
public class BinaryFormatterWrapper implements SerializationFormatter {

//	private BinaryFormatter _formatter = new BinaryFormatter();


	/**
	 * Converts a serialization stream into an object graph.
	 * 
	 *        @returns A deserialized object graph.
	 * 
	 * @param serializationStream    Byte stream containing the serialized data.
	 */
	@Override
	public Object deserialize(ObjectInputStream serializationStream){
	//	return _formatter.Deserialize(serializationStream);
		
        Object obj = null;
        try {
            // Make an input stream from the byte array and read
            // a copy of the object back in.
            obj = serializationStream.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
	}

	/**
	 * Gets a reference to the underlying
	 *        <see cref="BinaryFormatter"/> Object.
	 */
//	public BinaryFormatter getFormatter(){
//		                return _formatter;
//	}

	/**
	 * Converts an Object graph into a byte stream.
	 * 
	 * @param serializationStream    Stream that will contain the the serialized data.
	 * @param graph    Object graph to be serialized.
	 */
	@Override
	public void serialize(ObjectOutputStream serializationStream, Object graph){
		//_formatter.Serialize(serializationStream, graph);
		
        try {
            // Write the object out to a byte array
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            serializationStream.writeObject(graph);
            serializationStream.flush();
            serializationStream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
	}

}