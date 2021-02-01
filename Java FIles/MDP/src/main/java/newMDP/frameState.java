package newMDP;

import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;

import static newMDP.frameDomain.*;;


public class frameState implements MutableState{

	private final static List<Object> keys = Arrays.<Object>asList(VAR_FRAME, VAR_FRAME_w0, VAR_FRAME_w1, VAR_FRAME_w2, VAR_FRAME_w3, VAR_FRAME_w4, VAR_FRAME_w5, VAR_FRAME_w6, VAR_FRAME_w7);
	
	public Integer [] frame;

	public frameState() {
	}

	public frameState(Integer[] frame) {
		this.frame = frame;
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if(variableKey.equals(VAR_FRAME)){
			this.frame = (Integer[]) value;
		}else if(variableKey.equals(VAR_FRAME_w0)) {
			this.frame[0] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w1)) {
			this.frame[1] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w2)) {
			this.frame[2] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w3)) {
			this.frame[3] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w4)) {
			this.frame[4] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w5)) {
			this.frame[5] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w6)) {
			this.frame[6] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else if(variableKey.equals(VAR_FRAME_w7)) {
			this.frame[7] = (Integer) StateUtilities.stringOrNumber(value).intValue();
		}else{
			throw new UnknownKeyException(variableKey);
		}
		return this;
	}

	@Override
	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {
		if(variableKey.equals(VAR_FRAME)){
			return frame;
		}else if(variableKey.equals(VAR_FRAME_w0)) {
			return frame[0];
		}else if(variableKey.equals(VAR_FRAME_w1)) {
			return frame[1];
		}else if(variableKey.equals(VAR_FRAME_w2)) {
			return frame[2];
		}else if(variableKey.equals(VAR_FRAME_w3)) {
			return frame[3];
		}else if(variableKey.equals(VAR_FRAME_w4)) {
			return frame[4];
		}else if(variableKey.equals(VAR_FRAME_w5)) {
			return frame[5];
		}else if(variableKey.equals(VAR_FRAME_w6)) {
			return frame[6];
		}else if(variableKey.equals(VAR_FRAME_w7)) {
			return frame[7];
		}
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public frameState copy() {
		return new frameState(frame);
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}
	
	public String toPrintString() {
		return "[" + frame[0].toString() + ", " + frame[1].toString() + ", " + frame[2].toString() + ", " + frame[3].toString() + ", " + frame[4].toString() + ", " + frame[5].toString() + ", " + frame[6].toString() + ", " + frame[7].toString() + "]";
	}
}