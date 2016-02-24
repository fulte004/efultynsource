package Csla.Core;

import java.io.IOException;

/**
 * Defines the methods required to participate in n-level undo within the CSLA .
 * NET framework.
 * 
 *      @remark This interface is used by Csla.Core.UndoableBase to initiate begin,
 * cancel and apply edit operations.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface UndoableObject {

	/**
	 * Accepts any changes made to the object since the last state copy was made.
	 * 
	 *        @remark The most recent state copy is removed from the state stack and
	 * discarded, thus committing any changes made to the object's state.
	 * 
	 * @param parentEditLevel    Parent object's edit level.
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void acceptChanges(int parentEditLevel) throws UndoException, IllegalArgumentException, IllegalAccessException;

	/**
	 * Copies the state of the object and places the copy onto the state stack.
	 * 
	 * @param parentEditLevel    Parent object's edit level.
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	public void copyState(int parentEditLevel) throws UndoException, IllegalArgumentException, IllegalAccessException, IOException;

	/**
	 * Restores the object's state to the most recently copied values from the state
	 * stack.
	 * 
	 *        @remark Restores the state of the object to its previous value by taking
	 * the data out of the stack and restoring it into the fields of the object.
	 * 
	 * @param parentEditLevel    Parent object's edit level.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws UndoException 
	 * @throws IOException 
	 */
	public void undoChanges(int parentEditLevel) throws IllegalArgumentException, IllegalAccessException, UndoException, IOException;

}