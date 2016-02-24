package Csla.WebServiceHost;

import java.util.EventObject;

/**
 * <remarks/>
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:29 PM
 */
public class CreateCompletedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1537170731028403052L;
	private Object results[];

	/**
	 * 
	 * @param results
	 * @param exception
	 * @param cancelled
	 * @param userState
	 */
	protected CreateCompletedEvent(Object source, Object[] results, Exception exception, boolean cancelled, Object userState){
		super(source);
		this.results = results;
	}

	/**
	 * <remarks/>
	 */
	public byte[] getResult(){
		{
			//		                        this.RaiseExceptionIfNecessary();
			return ((byte[])(this.results[0]));
		}
	}

}