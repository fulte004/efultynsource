package Csla.Server.Hosts.MetroChannel;

/**
 * Specify that WCF should serialize objects in a .NET specific manner to as to
 * preserve complex object references and to be able to deserialize the graph into
 * the same type as the original objets.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:48 PM
 */
public class UseNetDataContractAttribute extends Attribute implements IOperationBehavior {

	public UseNetDataContractAttribute(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Not implemented.
	 * 
	 * @param description    Not implemented.
	 * @param parameters    Not implemented.
	 */
	public void AddBindingParameters(OperationDescription description, BindingParameterCollection parameters){

	}

	/**
	 * Apply the client behavior by requiring the use of the NetDataContractSerializer.
	 * 
	 * 
	 * @param description    Operation description.
	 * @param proxy    Client operation object.
	 */
	public void ApplyClientBehavior(OperationDescription description, System.ServiceModel.Dispatcher.ClientOperation proxy){
		ReplaceDataContractSerializerOperationBehavior(description);
	}

	/**
	 * Apply the dispatch behavior by requiring the use of the
	 * NetDataContractSerializer.
	 * 
	 * @param description    Operation description.
	 * @param dispatch    Dispatch operation object.
	 */
	public void ApplyDispatchBehavior(OperationDescription description, System.ServiceModel.Dispatcher.DispatchOperation dispatch){
		ReplaceDataContractSerializerOperationBehavior(description);
	}

	/**
	 * 
	 * @param description
	 */
	private static void ReplaceDataContractSerializerOperationBehavior(OperationDescription description){
		DataContractSerializerOperationBehavior dcsOperationBehavior = description.Behaviors.Find<DataContractSerializerOperationBehavior>();
		            
		            if (dcsOperationBehavior != null)
		            {
		              description.Behaviors.Remove(dcsOperationBehavior);
		              description.Behaviors.Add(new NetDataContractOperationBehavior(description));
		            }
	}

	/**
	 * Not implemented.
	 * 
	 * @param description    Not implemented.
	 */
	public void Validate(OperationDescription description){

	}

}