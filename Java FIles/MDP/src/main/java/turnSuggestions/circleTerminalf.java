package turnSuggestions;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;
import turnSuggestions.circleView;

public class circleTerminalf implements TerminalFunction {

	int goalX;

	public circleTerminalf(int goalX){
		this.goalX = goalX;
	}

	public boolean isTerminal(State s) {

		//are they at goal location?
		if(circleView.isChecking == true){
			
			int ax = (Integer)s.get(circleView.VAR_X);
			
			if(ax == goalX) {
				circleView.isChecking = false;
				return true;
			}
			
			circleView.isChecking = false;
			return false;

		}

		circleView.isChecking = false;
		return false;
	}
	
}
