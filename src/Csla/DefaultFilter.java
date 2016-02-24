package Csla;

/**
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:33 PM
 */
public class DefaultFilter implements FilterProviderListener{
	/**
	 * 
	 * @param item
	 * @param filter
	 */

	@Override
	public boolean onFilterProviderInvoked(Object item, Object filter) {
		boolean result = false;
		if (item != null && filter != null)
			result = item.toString().contains(filter.toString());
		return result;
	}

}