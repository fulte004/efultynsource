package Csla.Core;

import java.io.IOException;

/**
 * Define the common methods used by the UI to interact with n-level undo.
 * 
 *      @remark This interface is designed to help UI framework developers
 * interact with editable business objects. The CSLA .NET editable base classes
 * already implement this interface and the required n-level undo behaviors.
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:37 PM
 */
public interface SupportUndo {

	/**
	 * Commits the current edit process.
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	public void applyEdit() throws IllegalArgumentException, UndoException, IllegalAccessException;

	/**
	 * Starts a nested edit on the object.
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws UndoException 
	 * @throws IllegalArgumentException 
	 */
	public void beginEdit() throws IllegalArgumentException, UndoException, IllegalAccessException, IOException;

	/**
	 * Cancels the current edit process, restoring the object's state to its previous
	 * values.
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	public void cancelEdit() throws IllegalArgumentException, IllegalAccessException, UndoException, IOException;

}