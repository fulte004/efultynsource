package Csla.Server.Hosts;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import Csla.Server.DataPortal;
import Csla.Server.DataPortalContext;

/**
 * Exposes server-side DataPortal functionality through Web Services (asmx).
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:50 PM
 */
public class WebServicePortal {

	/**
	 * Request message for creating a new business object.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:50 PM
	 */
	public class CreateRequest {

		private DataPortalContext _context;
		private Object _criteria;
		private Class<?> _objectType;

		/**
		 * Data portal context from client.
		 */
		public DataPortalContext getContext(){
			return _context; 
		}
		public void setContext(DataPortalContext value){
			_context = value; 
		}

		/**
		 * Criteria Object describing business Object.
		 */
		public Object getCriteria(){
			return _criteria; 
		}
		public void setCriteria(Object value){
			_criteria = value; 
		}

		/**
		 * Type of business Object to create.
		 */
		public Class<?> getObjectType(){
			return _objectType; 
		}
		public void setObjectType(Class<?> value){
			_objectType = value; 
		}

	}

	/**
	 * Request message for retrieving an existing business Object.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:50 PM
	 */
	public class FetchRequest {

		private DataPortalContext _context;
		private Object _criteria;
		private Class<?> _objectType;

		/**
		 * Data portal context from client.
		 */
		public DataPortalContext getContext(){
			return _context; 
		}
		public void setContext(DataPortalContext value){
			_context = value; 
		}

		/**
		 * Criteria Object describing business Object.
		 */
		public Object getCriteria(){
			{ return _criteria; }
		}
		public void setCriteria(Object value){
			{ _criteria = value; }
		}

		/**
		 * Type of business Object to create.
		 */
		public Class<?> getObjectType(){
			{ return _objectType; }
		}
		public void setObjectType(Class<?> value){
			{ _objectType = value; }
		}

	}

	/**
	 * Request message for updating a business Object.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:50 PM
	 */
	public class UpdateRequest {

		private DataPortalContext _context;
		private Object _object;

		/**
		 * Data portal context from client.
		 */
		public DataPortalContext getContext(){
			return _context; 
		}
		public void setContext(DataPortalContext value){
			_context = value; 
		}

		/**
		 * Business Object to be updated.
		 */
		public Object getObject(){
			return _object; 
		}
		public void setObject(Object value){
			_object = value; 
		}

	}

	/**
	 * Request message for deleting a business Object.
	 * 
	 * 
	 * @author Eric
	 * @version 1.0
	 * @created 21-Dec-2009 7:10:50 PM
	 */
	public class DeleteRequest {

		private DataPortalContext _context;
		private Object _criteria;

		/**
		 * Data portal context from client.
		 */
		public DataPortalContext getContext(){
			return _context; 
		}
		public void setContext(DataPortalContext value){
			_context = value; 
		}

		/**
		 * Criteria Object describing business Object.
		 */
		public Object getCriteria(){
			return _criteria; 
		}
		public void setCriteria(Object value){
			_criteria = value; 
		}

	}

	/**
	 * Create a new business Object.
	 * 
	 *          @returns Byte stream containing resulting Object data.
	 * 
	 * @param requestData    Byte stream containing <see cref="CreateRequest" />.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public byte[] create(byte[] requestData) throws IOException, ClassNotFoundException{
		CreateRequest request = (CreateRequest)deserialize(requestData);

		DataPortal portal = new DataPortal();
		Object result;
		try
		{
			result = portal.create(request.getObjectType(), request.getCriteria(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return Serialize(result);
	}

	/**
	 * Delete a business Object.
	 * 
	 *          @returns Byte stream containing resulting Object data.
	 * 
	 * @param requestData    Byte stream containing <see cref="DeleteRequest" />.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public byte[] delete(byte[] requestData) throws IOException, ClassNotFoundException{
		DeleteRequest request = (DeleteRequest)deserialize(requestData);

		DataPortal portal = new DataPortal();
		Object result;
		try
		{
			result = portal.delete(request.getCriteria(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return Serialize(result);
	}

	/**
	 * 
	 * @param obj
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private static Object deserialize(byte[] obj) throws IOException, ClassNotFoundException{
		if (obj != null)
		{
			ByteArrayInputStream bis = new ByteArrayInputStream(obj);
			ObjectInputStream in = new ObjectInputStream(bis);
			return in.readObject();
//			MemoryStream buffer = new MemoryStream(obj);
//			{
//				BinaryFormatter formatter = new BinaryFormatter();
//				return formatter.Deserialize(buffer);
//			}
		}
		return null;
	}

	/**
	 * Get an existing business Object.
	 * 
	 *          @returns Byte stream containing resulting Object data.
	 * 
	 * @param requestData    Byte stream containing <see cref="FetchRequest" />.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public byte[] fetch(byte[] requestData) throws IOException, ClassNotFoundException{
		FetchRequest request = (FetchRequest)deserialize(requestData);

		DataPortal portal = new DataPortal();
		Object result;
		try
		{
			result = portal.fetch(request.getObjectType(), request.getCriteria(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return Serialize(result);
	}

	/**
	 * 
	 * @param obj
	 * @throws IOException 
	 */
	private static byte[] Serialize(Object obj) throws IOException{
		if (obj != null)
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			out.close();
			return bos.toByteArray();
		}
		return null;
	}

	/**
	 * Update a business Object.
	 * 
	 *          @returns Byte stream containing resulting Object data.
	 * 
	 * @param requestData    Byte stream containing <see cref="UpdateRequest" />.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public byte[] update(byte[] requestData) throws IOException, ClassNotFoundException{
		UpdateRequest request = (UpdateRequest)deserialize(requestData);

		DataPortal portal = new DataPortal();
		Object result;
		try
		{
			result = portal.update(request.getObject(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return Serialize(result);
	}

}