package Csla;

/**
 * Adds transactional capability when adding a new item to a collection.
 *
 */
public interface CancelAddNew {
 
	/**
	 * Discards a pending new item from the collection.
	 * 
	 * @param itemIndex The index of the item that was previously added to the collection.
	 */
    public void cancelNew(int itemIndex);

    /**
     * Commits a pending new item to the collection.
     * 
     * @param itemIndex The index of the item that was previously added to the collection.
     */
    public void endNew(int itemIndex);
}
