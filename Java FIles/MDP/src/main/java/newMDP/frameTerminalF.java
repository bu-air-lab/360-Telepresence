package newMDP;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;

import static newMDP.frameDomain.*;

public class frameTerminalF implements TerminalFunction {


	public frameTerminalF(){}

	@Override
	public boolean isTerminal(State s) {

		if(frameDomain.isChecking){

			Integer [] frame = (Integer []) s.get(VAR_FRAME);

			if(frame[0] >= 2){
				frameDomain.isChecking = false;
				return true;
			}
		}
		
		return false;
	}

}