package Csla;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

public abstract class EventSource<E extends EventObject, L extends EventListener> {
	protected ArrayList<L> _listeners;

	public abstract void addListener(L l);
	
	public abstract void removeListener(L l);
	
	public abstract void fireEvent(E e);

}
