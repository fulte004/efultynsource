package Csla.Security;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import Csla.Configuration;

/**
 * Provides a cache for a limited number of principal objects at the AppDomain
 * level.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:40 PM
 */
public class PrincipalCache {

	private static List<Principal> _cache = new ArrayList<Principal>();
	private static int _maxCacheSize;

	/**
	 * Adds a principal to the cache.
	 * 
	 * @param principal    IPrincipal object to be added.
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	public static void addPrincipal(Principal principal) throws InvalidPropertiesFormatException, IOException{
		synchronized (_cache)
		{
			if (!_cache.contains(principal))
			{
				_cache.add(principal);
				if (_cache.size() > getMaxCacheSize())
					_cache.remove(0);
			}
		}
	}

	/**
	 * Clears the cache.
	 */
	public static void clear(){
		synchronized (_cache) {
			_cache.clear();
		}
	}

	/**
	 * Gets a principal from the cache based on the identity name. If no match is
	 * found null is returned.
	 * 
	 * @param name    The identity name associated with the principal.
	 */
	public static Principal getPrincipal(String name){
		synchronized (_cache)
		{
			for (Principal item : _cache)
				if (item.getName() == name)
					return item;
			return null;
		}
	}

	private static int getMaxCacheSize() throws InvalidPropertiesFormatException, IOException{


		if (_maxCacheSize == 0)
		{
			String tmp = Configuration.getSetting("CslaPrincipalCacheSize");
			if (tmp.isEmpty())
				_maxCacheSize = 10;
			else
				_maxCacheSize = Integer.parseInt(tmp);
		}
		return _maxCacheSize;

	}

}