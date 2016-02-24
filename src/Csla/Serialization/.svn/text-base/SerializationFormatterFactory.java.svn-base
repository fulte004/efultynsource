package Csla.Serialization;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

/**
 * Factory used to create the appropriate serialization formatter object based on
 * the application configuration.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:44 PM
 */
public class SerializationFormatterFactory {

	public SerializationFormatterFactory(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Creates a serialization formatter object.
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	public static SerializationFormatter GetFormatter() throws InvalidPropertiesFormatException, IOException{
		//		#if NET20
		//		              return new BinaryFormatterWrapper();
		//		        #else
//		if (ApplicationContext.getSerializationFormatter() == ApplicationContext.SerializationFormatters.BINARY_FORMATTER)
			return new BinaryFormatterWrapper();
//		else
//			return new NetDataContractSerializerWrapper();
		//		        #endif
	}

}