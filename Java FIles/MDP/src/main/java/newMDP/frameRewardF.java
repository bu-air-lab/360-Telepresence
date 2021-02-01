package newMDP;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

import static newMDP.frameDomain.*;

public class frameRewardF implements RewardFunction {

	public frameRewardF(){}

	@Override
	public double reward(State s, Action a, State sprime) {

		Integer [] frame = (Integer []) s.get(VAR_FRAME);
		Integer [] nextFrame = (Integer []) sprime.get(VAR_FRAME);

		//are they at goal location?
		if(frameDomain.isChecking){
			if(frame[0] >= 2){
				return 250.;
			}else{
				return -500.;
			}
		}else{
			if(nextFrame[0] % 2 == 1){
				return -50.;
			}
		}

		return -3.;
	}


}