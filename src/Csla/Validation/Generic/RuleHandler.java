package Csla.Validation.Generic;

import java.util.EventListener;

import Csla.Validation.RuleArgs;


public interface RuleHandler<T, R extends RuleArgs> extends EventListener
 {
	public boolean invoke(T target, R args);
	public String getRuleName();
	public Csla.Validation.RuleHandler getMethod();
}
