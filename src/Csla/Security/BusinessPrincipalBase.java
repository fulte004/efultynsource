package Csla.Security;
import java.security.KeyStore;
import java.security.Principal;
/**
 * Base class from which custom principal objects should inherit to operate
 * properly with the data portal.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:27 PM
 */
public class BusinessPrincipalBase implements Principal {

	private KeyStore _identity;

	/**
	 * Creates an instance of the object.
	 * 
	 * @param identity    Identity object for the user.
	 */
	protected BusinessPrincipalBase(KeyStore identity){
		_identity = identity;
	}

	/**
	 * Returns the user's identity object.
	 */
	public KeyStore getIdentity(){
		 return _identity; 
	}

	/**
	 * Returns a value indicating whether the user is in a given role.
	 * 
	 * @param role    Name of the role.
	 */
	public boolean isInRole(String role){
		// TODO Add code to isInRole method
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}