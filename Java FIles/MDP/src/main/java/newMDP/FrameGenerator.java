package newMDP;

import burlap.mdp.auxiliary.StateGenerator;
import burlap.mdp.core.state.State;

public class FrameGenerator implements StateGenerator{

	public State generateState() {
		Integer [] frame = frameDomain.generateFrame();
		
		return new frameState(frame);
	}
}
