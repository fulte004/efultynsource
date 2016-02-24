package Csla.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eric
 * @version 1.0
 * @param <IRuleMethod>
 * @created 21-Dec-2009 7:10:43 PM
 */
public class RulesList {

	private ArrayList<String> _dependantProperties;
	private List<IRuleMethod> _list = new ArrayList<IRuleMethod>();
	private boolean _sorted;

	/**
	 * 
	 * @param item
	 */
	public void add(IRuleMethod item){
		_list.add(item);
		        _sorted = false;
	}

	/**
	 * 
	 * @param create
	 */
	public List<String> getDependancyList(boolean create){
		if (_dependantProperties == null && create)
		          _dependantProperties = new ArrayList<String>();
		        return _dependantProperties;
	}

	/**
	 * 
	 * @param applySort
	 */
	public List<IRuleMethod> getList(boolean applySort){
		if (applySort && !_sorted)
		        {
		          synchronized (this)
		          {
		            if (applySort && !_sorted)
		            {
		              Collections.sort(_list);
		              _sorted = true;
		            }
		          }
		        }
		        return _list;
	}

}