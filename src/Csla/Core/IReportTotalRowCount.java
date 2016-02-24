package Csla.Core;

/**
 * Implement this interface in a collection to report a total row count to
 *      <see cref="Csla.Web.CslaDataSource"/>, where that row count is different
 * from the collection's normal Count property value.
 * 
 *      @remark This interface is used to provide paging support for web data
 * binding through
 *      <see cref="Csla.Web.CslaDataSource"/>. You should implement this interface
 * in your business collection class, along with windowed data loading, to provide
 * efficient paging support.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface IReportTotalRowCount {

	/**
	 * The total number of rows of available data.
	 */
	public int TotalRowCount();

}