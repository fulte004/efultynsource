package Csla.Web;

/**
 * Argument object used in the SelectObject event.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:44 PM
 */
public class SelectObjectArgs extends EventArgs {

	private object _businessObject;
	private int _maximumRows;
	private bool _retrieveTotalRowCount;
	private ListSortDirection _sortDirection;
	private string _sortExpression;
	private string _sortProperty;
	private int _startRowIndex;

	public SelectObjectArgs(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the object, initializing it with values from data
	 * binding.
	 * 
	 * @param args    Values provided from data binding.
	 */
	public SelectObjectArgs(System.Web.UI.DataSourceSelectArguments args){
		_startRowIndex = args.StartRowIndex;
		        _maximumRows = args.MaximumRows;
		        _retrieveTotalRowCount = args.RetrieveTotalRowCount;
		        
		        _sortExpression = args.SortExpression;
		        if (!(string.IsNullOrEmpty(_sortExpression)))
		        {
		          if (_sortExpression.Length >= 5 &&
		            _sortExpression.Substring(_sortExpression.Length - 5) == " DESC")
		          {
		            _sortProperty = _sortExpression.Substring(0, _sortExpression.Length - 5);
		            _sortDirection = ListSortDirection.Descending;
		        
		          }
		          else
		          {
		            _sortProperty = args.SortExpression;
		            _sortDirection = ListSortDirection.Ascending;
		          }
		        }
	}

	/**
	 * Get or set a reference to the business object that is created and populated by
	 * the SelectObject event handler in the web page.
	 * 
	 *        @value A reference to a CSLA .NET business object.
	 */
	public object BusinessObject(){
		get { return _businessObject; }
		        set { _businessObject = value; }
	}

	/**
	 * Gets the maximum number of rows that should be returned as a result of this
	 * query. For paged collections, this is the page size.
	 */
	public int MaximumRows(){
		get
		              {
		                return _maximumRows;
		              }
	}

	/**
	 * Gets a value indicating whether the query should return the total row count
	 * through the
	 *        <see cref="Csla.Core.IReportTotalRowCount"/> interface.
	 */
	public bool RetrieveTotalRowCount(){
		get
		              {
		                return _retrieveTotalRowCount;
		              }
	}

	/**
	 * Gets the sort direction for the sort if only one property/column name is
	 * specified.
	 * 
	 *        @remark If multiple properties/columns are specified for the sort, you
	 * must parse the value from
	 *        <see cref="SortExpression"/> to find all the property names and sort
	 * directions for the sort.
	 */
	public ListSortDirection SortDirection(){
		get
		              {
		                return _sortDirection;
		              }
	}

	/**
	 * Gets the sort expression that should be used to sort the data being returned to
	 * the data source control.
	 */
	public string SortExpression(){
		get
		              {
		                return _sortExpression;
		              }
	}

	/**
	 * Gets the property name for the sort if only one property/column name is
	 * specified.
	 * 
	 *        @remark If multiple properties/columns are specified for the sort, you
	 * must parse the value from
	 *        <see cref="SortExpression"/> to find all the property names and sort
	 * directions for the sort.
	 */
	public string SortProperty(){
		get
		              {
		                return _sortProperty;
		              }
	}

	/**
	 * Gets the index for the first row that will be displayed. This should be the
	 * first row in the resulting collection set into the
	 *        <see cref="BusinessObject"/> property.
	 */
	public int StartRowIndex(){
		get
		              {
		                return _startRowIndex;
		              }
	}

}