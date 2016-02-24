package Csla.Server.Hosts;
import Csla.Server.Hosts.MetroChannel.CreateRequest;
import Csla.Server.Hosts.MetroChannel.DeleteRequest;
import Csla.Server.Hosts.MetroChannel.FetchRequest;
import Csla.Server.Hosts.MetroChannel.UpdateRequest;
import Csla.Server.Hosts.MetroChannel.MetroResponse;

/**
 * Defines the service contract for the WCF data portal.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface IMetroPortal {

	/**
	 * Create a new business object.
	 * 
	 * @param request    The request parameter object.
	 */
	public MetroResponse create(CreateRequest request);

	/**
	 * Delete a business object.
	 * 
	 * @param request    The request parameter object.
	 */
	public MetroResponse delete(DeleteRequest request);

	/**
	 * Get an existing business object.
	 * 
	 * @param request    The request parameter object.
	 */
	public MetroResponse fetch(FetchRequest request);

	/**
	 * Update a business object.
	 * 
	 * @param request    The request parameter object.
	 */
	public MetroResponse update(UpdateRequest request);

}