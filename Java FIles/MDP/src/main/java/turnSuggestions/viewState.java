package turnSuggestions;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.Arrays;
import java.util.List;

import static turnSuggestions.circleView.VAR_X;

public class viewState implements MutableState{
	
	public int x;
	private final static List<Object> keys = Arrays.<Object>asList(VAR_X);
	
	public viewState() {}
	
	public viewState(int x) {
		this.x = x;
	}

	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_X)) {
			this.x = StateUtilities.stringOrNumber(value).intValue();
		}else {
			throw new UnknownKeyException(variableKey);
		}
		return this;
	}

	public List<Object> variableKeys() {
		return keys;
	}

	public Object get(Object variableKey) {
		if(variableKey.equals(VAR_X)) {
			return x;
		}else {
			throw new UnknownKeyException(variableKey);
		}
	}

	@DeepCopyState
	public viewState copy() {
		return new viewState(x);
	}
	
	public String toString() {
		return "" + x;
	}

}
