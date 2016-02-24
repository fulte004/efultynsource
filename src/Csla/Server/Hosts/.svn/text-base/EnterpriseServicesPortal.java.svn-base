package Csla.Server.Hosts;
import Csla.Server.DataPortalResult;
import Csla.Server.DataPortalContext;
import Csla.Server.IDataPortalServer;

/**
 * Exposes server-side DataPortal functionality through Enterprise Services.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:34 PM
 */
public abstract class EnterpriseServicesPortal extends ServicedComponent implements IDataPortalServer {



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Set up event handler to deal with serialization issue as discussed in Chapter 4.
	 */
	private static EnterpriseServicesPortal(){
		SerializationWorkaround();
	}

	/**
	 * Create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public DataPortalResult Create(Type objectType, object criteria, DataPortalContext context){
		Server.DataPortal portal = new Server.DataPortal();
		          return portal.Create(objectType, criteria, context);
	}

	/**
	 * Delete a business object.
	 * 
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public DataPortalResult Delete(object criteria, DataPortalContext context){
		Server.DataPortal portal = new Server.DataPortal();
		          return portal.Delete(criteria, context);
	}

	/**
	 * Get an existing business object.
	 * 
	 * @param objectType    Type of business object to retrieve.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public DataPortalResult Fetch(Type objectType, object criteria, DataPortalContext context){
		Server.DataPortal portal = new Server.DataPortal();
		          return portal.Fetch(objectType, criteria, context);
	}

	/**
	 * 
	 * @param sender
	 * @param args
	 */
	private static Assembly ResolveEventHandler(object sender, ResolveEventArgs args){
		// get a list of all the assemblies loaded in our appdomain
		          Assembly[] list = AppDomain.CurrentDomain.GetAssemblies();
		          
		          // search the list to find the assembly that was not found automatically
		          // and return the assembly from the list
		          
		          foreach (Assembly asm in list)
		            if (asm.FullName == args.Name)
		              return asm;
		          
		          // if the assembly wasn't already in the appdomain, then try to load it.
		          return Assembly.Load(args.Name);
	}

	private static void SerializationWorkaround(){
		// hook up the AssemblyResolve
		          // event so deep serialization works properly
		          // this is a workaround for a bug in the .NET runtime
		          AppDomain currentDomain = AppDomain.CurrentDomain;
		          
		          currentDomain.AssemblyResolve += 
		            new ResolveEventHandler(ResolveEventHandler);
	}

	/**
	 * Update a business object.
	 * 
	 * @param obj    Business object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public DataPortalResult Update(object obj, DataPortalContext context){
		Server.DataPortal portal = new Server.DataPortal();
		          return portal.Update(obj, context);
	}

}