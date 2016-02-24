package Csla.DataPortalClient;
import java.lang.reflect.Type;

import Csla.Server.DataPortalResult;
import Csla.Server.DataPortalContext;
import Csla.Server.Hosts.IMetroPortal;
import Csla.Server.Hosts.MetroChannel.CreateRequest;
import Csla.Server.Hosts.MetroChannel.DeleteRequest;
import Csla.Server.Hosts.MetroChannel.FetchRequest;
import Csla.Server.Hosts.MetroChannel.MetroResponse;
import Csla.Server.Hosts.MetroChannel.UpdateRequest;

/**
 * Implements a data portal proxy to relay data portal calls to a remote
 * application server by using WCF.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:49 PM
 */
public class MetroProxy implements IDataPortalProxy {

	private String _endPoint = "WcfDataPortal";

	/**
	 * Called by <see cref="DataPortal" /> to create a new business object.
	 * 
	 * @param objectType    Type of business object to create.
	 * @param criteria    Criteria object describing business object.
	 * @param context    <see cref="Server.DataPortalContext" /> object passed to the
	 * server.
	 */
	public DataPortalResult create(Class<?> objectType, Object criteria, DataPortalContext context){
		ChannelFactory<IMetroPortal> cf = new ChannelFactory<IMetroPortal>(_endPoint);
		IMetroPortal svr = cf.CreateChannel();
		MetroResponse response = null;
		try
		{
			response =
				svr.create(new CreateRequest(objectType, criteria, context));
			if (cf != null)
				cf.Close();
		}
		catch(Exception ex)
		{
			cf.Abort();
			throw ex;
		}

		Object result = response.getResult();
		if (result instanceof Exception)
			throw (Exception)result;
		return (DataPortalResult)result;
	}

	/**
	 * Called by <see cref="DataPortal" /> to delete a business Object.
	 * 
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 */
	public DataPortalResult delete(Object criteria, DataPortalContext context){
		ChannelFactory<IMetroPortal> cf = new ChannelFactory<IMetroPortal>(_endPoint);
		IMetroPortal svr = cf.CreateChannel();
		MetroResponse response = null;
		try
		{
			response =
				svr.delete(new DeleteRequest(criteria, context));
			cf.Close();
		}
		catch(Exception ex)
		{
			cf.Abort();
			throw ex;
		}
		Object result = response.getResult();
		if (result instanceof Exception)
			throw (Exception)result;
		return (DataPortalResult)result;
	}

	/**
	 * Gets or sets the WCF endpoint used to contact the server.
	 * 
	 *        @remark The default value is WcfDataPortal.
	 */
	protected String getEndPoint(){
		return _endPoint;
	}
	protected void setEndPoint(String value){
		_endPoint = value;
	}
	/**
	 * Called by <see cref="DataPortal" /> to load an existing business Object.
	 * 
	 * @param objectType    Type of business Object to create.
	 * @param criteria    Criteria Object describing business Object.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 */
	public DataPortalResult fetch(Class<?> objectType, Object criteria, DataPortalContext context){
		ChannelFactory<IMetroPortal> cf = new ChannelFactory<IMetroPortal>(_endPoint);
		IMetroPortal svr = cf.CreateChannel();
		MetroResponse response = null;
		try
		{
			response =
				svr.fetch(new FetchRequest(objectType, criteria, context));
			if (cf != null)
				cf.Close();
		}
		catch(Exception ex)
		{
			cf.Abort();
			throw ex;
		}

		Object result = response.getResult();
		if (result instanceof Exception)
			throw (Exception)result;
		return (DataPortalResult)result;
	}

	/**
	 * Gets a value indicating whether the data portal is hosted on a remote server.
	 */
	public boolean isServerRemote(){
		return true;
	}

	/**
	 * Called by <see cref="DataPortal" /> to update a business Object.
	 * 
	 * @param obj    The business Object to update.
	 * @param context    <see cref="Server.DataPortalContext" /> Object passed to the
	 * server.
	 */
	public DataPortalResult update(Object obj, DataPortalContext context){
		ChannelFactory<IMetroPortal> cf = new ChannelFactory<IMetroPortal>(_endPoint);
		IMetroPortal svr = cf.CreateChannel();
		MetroResponse response = null;
		try
		{
			response =
				svr.update(new Csla.Server.Hosts.MetroChannel.UpdateRequest(obj, context));
			if (cf != null)
				cf.Close();
		}
		catch(Exception ex)
		{
			cf.Abort();
			throw ex;
		}
		Object result = response.getResult();
		if (result instanceof Exception)
			throw (Exception)result;
		return (DataPortalResult)result;
	}

}