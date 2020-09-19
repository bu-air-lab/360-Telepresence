package turnSuggestions;

import burlap.mdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;

public class viewTFGoalCondition extends TFGoalCondition {

	int goalx;
	
	public viewTFGoalCondition(TerminalFunction tf, int goalx) {
		super(tf);
		this.goalx = goalx;
	}

	public boolean satisfies(viewState a) {
		return tf.isTerminal(a) && a.x == goalx;
	}
}
