package Csla.Server.Hosts.MetroChannel;

/**
 * Override the DataContract serialization behavior to use the <see
 * cref="NetDataContractSerializer"/>.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:39 PM
 */
public class NetDataContractOperationBehavior extends DataContractSerializerOperationBehavior {

	/**
	 * Create new instance of object.
	 * 
	 * @param operation    Operation description.
	 */
	public NetDataContractOperationBehavior(OperationDescription operation){

	}

	/**
	 * Create new instance of object.
	 * 
	 * @param operation    Operation description.
	 * @param dataContractFormatAttribute    Data contract attribute object.
	 */
	public NetDataContractOperationBehavior(OperationDescription operation, DataContractFormatAttribute dataContractFormatAttribute){

	}

	/**
	 * Overrided CreateSerializer to return an XmlObjectSerializer which is capable of
	 * preserving the object references.
	 * 
	 * @param type
	 * @param name
	 * @param ns
	 * @param knownTypes
	 */
	public XmlObjectSerializer CreateSerializer(Type type, string name, string ns, IList<Type> knownTypes){
		return new NetDataContractSerializer(name, ns);
	}

	/**
	 * Overrided CreateSerializer to return an XmlObjectSerializer which is capable of
	 * preserving the object references.
	 * 
	 * @param type
	 * @param name
	 * @param ns
	 * @param knownTypes
	 */
	public XmlObjectSerializer CreateSerializer(Type type, XmlDictionaryString name, XmlDictionaryString ns, IList<Type> knownTypes){
		return new NetDataContractSerializer(name, ns);
	}

}