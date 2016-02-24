package Csla.Core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Stack;

import Csla.NotUndoable;
import Csla.Properties.Resources;
import Csla.Serialization.SerializationFormatter;
import Csla.Serialization.SerializationFormatterFactory;
import Csla.Core.UndoableObject;

/**
 * Implements n-level undo capabilities as described in Chapters 2 and 3.
 * 
 * 
 * @author Eric
 * @version 1.0
 * @created 21-Dec-2009 7:10:47 PM
 */
public abstract class UndoableBase extends BindableBase implements UndoableObject {

	private boolean _bindingEdit;
	/**
	 * keep a stack of Object state values.
	 */
	private Stack<byte[]> _stateStack = new Stack<byte[]>();



	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Creates an instance of the Object.
	 */
	protected UndoableBase(){

	}

	/**
	 * Accepts any changes made to the Object since the last state copy was made.
	 * 
	 *        @remark The most recent state copy is removed from the state stack and
	 * discarded, thus committing any changes made to the Object's state.
	 * 
	 * @param parentEditLevel
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void acceptChanges(int parentEditLevel) throws UndoException, IllegalArgumentException, IllegalAccessException{
		if (this.getEditLevel() - 1 < parentEditLevel)
		          throw new UndoException(String.format(Resources.getEditLevelMismatchException(), "AcceptChanges"));
		        
		        if (getEditLevel() > 0)
		        {
		          _stateStack.pop();
		          Class<?> currentType = this.getClass();
		          Field[] fields;
		        
		          do
		          {
		            // get the list of fields in this type
		            fields = currentType.getFields();
		            for (Field field : fields)
		            {
		              // make sure we process only our variables
		              if (field.getDeclaringClass() == currentType)
		              {
		                // see if the field is undoable or not
		                if (!notUndoableField(field))
		                {
		                  // the field is undoable so see if it is a child Object
		                  if (UndoableObject.class.isAssignableFrom(field.getType()))
		                  {
		                    Object value = field.get(this);
		                    // make sure the variable has a value
		                    if (value != null)
		                    {
		                      // it is a child Object so cascade the call
		                      if (!_bindingEdit)
		                        ((UndoableObject)value).acceptChanges(this.getEditLevel());
		                    }
		                  }
		                }
		              }
		            }
		            currentType = currentType.getSuperclass();
		          } while (currentType.equals(UndoableBase.class));
		        }
		        acceptChangesComplete();
	}

	/**
	 * This method is invoked after the AcceptChanges operation is complete.
	 */
	protected void acceptChangesComplete(){

	}

	/**
	 * Gets or sets a value indicating whether n-level undo was invoked through
	 * IEditableObject. FOR INTERNAL CSLA .NET USE ONLY!
	 */
	protected boolean getBindingEdit(){
		          return _bindingEdit;
	}
	protected void setBindingEdit(boolean value){
		          _bindingEdit = value;
	}

	/**
	 * Copies the state of the Object and places the copy onto the state stack.
	 * 
	 * @param parentEditLevel
	 * @throws UndoException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	public void copyState(int parentEditLevel) throws UndoException, IllegalArgumentException, IllegalAccessException, IOException{
		Class<?> currentType = this.getClass();
		        HashMap<String, Object> state = new HashMap<String, Object>();
		        Field[] fields;
		        
		        if (this.getEditLevel() + 1 > parentEditLevel)
		          throw new UndoException(String.format(Resources.getEditLevelMismatchException(), "CopyState"));
		        
		        do
		        {
		          // get the list of fields in this type
		          fields = currentType.getFields();
		        
		          for (Field field : fields)
		          {
		            // make sure we process only our variables
		            if (field.getDeclaringClass() == currentType)
		            {
		              // see if this field is marked as not undoable
		              if (!notUndoableField(field))
		              {
		                // the field is undoable, so it needs to be processed.
		                Object value = field.get(this);
		        
		                if (UndoableObject.class.isAssignableFrom(field.getType()))
		                {
		                  // make sure the variable has a value
		                  if (value == null)
		                  {
		                	  // variable has no value - store that fact
		                    state.put(getFieldName(field), null);
		                  }
		                  else
		                  {
		                    // this is a child Object, cascade the call
		                    if (!_bindingEdit)
		                      ((UndoableObject)value).copyState(this.getEditLevel() + 1);
		                  }
		                }
		                else
		                {
		                  // this is a normal field, simply trap the value
		                  state.put(getFieldName(field), value);
		                }
		              }
		            }
		          }
		          currentType = currentType.getSuperclass();
		        } while (!currentType.equals(UndoableBase.class));
		        
		        // serialize the state and stack it
		        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		        ObjectOutputStream outBuffer = new ObjectOutputStream(buffer);
		        {
		          SerializationFormatter formatter =
		            SerializationFormatterFactory.GetFormatter();
		          formatter.serialize(outBuffer, state);
		          _stateStack.push(buffer.toByteArray());
		          buffer.close();
		        }
		        copyStateComplete();
	}

	/**
	 * This method is invoked after the CopyState operation is complete.
	 */
	protected void copyStateComplete(){

	}

	/**
	 * Returns the current edit level of the Object.
	 */
	protected int getEditLevel(){
		  return _stateStack.size(); 
	}

	/**
	 * 
	 * @param field
	 */
	protected static String getFieldName(Field field){
		return field.getDeclaringClass().getName() + "!" + field.getName();
	}

	/**
	 * 
	 * @param field
	 */
	protected static boolean notUndoableField(Field field){
		return field.getClass().isAnnotationPresent(NotUndoable.class);
	}

	/**
	 * Restores the Object's state to the most recently copied values from the state
	 * stack.
	 * 
	 *        @remark Restores the state of the Object to its previous value by taking
	 * the data out of the stack and restoring it into the fields of the Object.
	 * 
	 * @param parentEditLevel
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws UndoException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void undoChanges(int parentEditLevel) throws IllegalArgumentException, IllegalAccessException, UndoException, IOException{
		// if we are a child Object we might be asked to
		        // undo below the level of stacked states,
		        // so just do nothing in that case
		        if (getEditLevel() > 0)
		        {
		          if (this.getEditLevel() - 1 < parentEditLevel)
		            throw new UndoException(String.format(Resources.getEditLevelMismatchException(), "UndoChanges"));
		        
		          HashMap<String, Object> state;
		          ByteArrayInputStream buffer = new ByteArrayInputStream(_stateStack.pop());
		          ObjectInputStream inBuffer = new ObjectInputStream(buffer);
		          {
		            buffer.mark(0);
		            SerializationFormatter formatter =
		              SerializationFormatterFactory.GetFormatter();
		            state = (HashMap<String, Object>)formatter.deserialize(inBuffer);
		          }
		        
		          Class<?> currentType = this.getClass();
		          Field[] fields;
		        
		          do
		          {
		            // get the list of fields in this type
		            fields = currentType.getFields();
		            for (Field field : fields)
		            {
		              // make sure we process only our variables
		              if (field.getDeclaringClass() == currentType)
		              {
		                // see if the field is undoable or not
		                if (!notUndoableField(field))
		                {
		                  // the field is undoable, so restore its value
		                  Object value = field.get(this);
		        
		                  if (UndoableObject.class.isAssignableFrom(field.getClass()))
		                  {
		                    // this is a child Object
		                    // see if the previous value was empty
		                    if (state.containsKey(getFieldName(field)))
		                    {
		                      // previous value was empty - restore to empty
		                      field.set(this, null);
		                    }
		                    else
		                    {
		                      // make sure the variable has a value
		                      if (value != null)
		                      {
		                        // this is a child Object, cascade the call.
		                        if (!_bindingEdit)
		                          ((UndoableObject)value).undoChanges(this.getEditLevel());
		                      }
		                    }
		                  }
		                  else
		                  {
		                    // this is a regular field, restore its value
		                    field.set(this, state.get(getFieldName(field)));
		                  }
		                }
		              }
		            }
		            currentType = currentType.getSuperclass();
		          } while (currentType != UndoableBase.class);
		        }
		        undoChangesComplete();
	}

	/**
	 * This method is invoked after the UndoChanges operation is complete.
	 */
	protected void undoChangesComplete(){

	}

}