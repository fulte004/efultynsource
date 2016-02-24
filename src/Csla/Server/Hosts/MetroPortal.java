package Csla.Server.Hosts;
import Csla.Server.Hosts.MetroChannel.CreateRequest;
import Csla.Server.Hosts.MetroChannel.DeleteRequest;
import Csla.Server.Hosts.MetroChannel.FetchRequest;
import Csla.Server.Hosts.MetroChannel.UpdateRequest;
import Csla.Server.Hosts.MetroChannel.MetroResponse;

/**
 * Exposes server-side DataPortal functionality through WCF.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:49 PM
 */
public class MetroPortal implements IMetroPortal {


	/**
	 * Create a new business object.
	 * 
	 * @param request    The request parameter object.
	 */
	public MetroResponse create(CreateRequest request){
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		Object result;
		try
		{
			result = portal.create(request.getObjectType(), request.getCriteria(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return new MetroResponse(result);
	}

	/**
	 * Delete a business Object.
	 * 
	 * @param request    The request parameter Object.
	 */
	public MetroResponse delete(DeleteRequest request){
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		Object result;
		try
		{
			result = portal.delete(request.getCriteria(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return new MetroResponse(result);
	}

	/**
	 * Get an existing business Object.
	 * 
	 * @param request    The request parameter Object.
	 */
	public MetroResponse fetch(FetchRequest request){
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		Object result;
		try
		{
			result = portal.fetch(request.getObjectType(), request.getCriteria(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return new MetroResponse(result);
	}

	/**
	 * Update a business Object.
	 * 
	 * @param request    The request parameter Object.
	 */
	public MetroResponse update(UpdateRequest request){
		Csla.Server.DataPortal portal = new Csla.Server.DataPortal();
		Object result;
		try
		{
			result = portal.update(request.getObject(), request.getContext());
		}
		catch (Exception ex)
		{
			result = ex;
		}
		return new MetroResponse(result);
	}

}