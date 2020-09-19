package turnSuggestions;

import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.RewardFunction;

public class circleRewardf implements RewardFunction {

	int goalX;

	public circleRewardf(int goalX){
		this.goalX = goalX;
	}

	public double reward(State s, Action a, State sprime) {
		
		int ax = (Integer)s.get(circleView.VAR_X);
		
		int axx = (Integer)s.get(circleView.VAR_X);
		
		int axNext = (Integer)sprime.get(circleView.VAR_X);

		//are they at goal location?
//		if(ax == this.goalX){ //Goal amount = 3 move max * 4 cluttered squares + 2 extra moves * 4 cluttered = 20 reward
//			double probVal = Math.random();
//			if(mapVal % 2 == 1) {
//				if(mapVal == 7) {
//					if(probVal < .1) {
//						circleView.missedObject = true;
//						return 0;
//					}else {
//						return 20;
//					}
//				}else if(mapVal == 5) {
//					if(probVal < .43) {
//						circleView.missedObject = true;
//						return 0;
//					}else {
//						return 20;
//					}
//				}else if(mapVal == 3) {
//					if(probVal < .76) {
//						circleView.missedObject = true;
//						return 0;
//					}else {
//						return 20;
//					}
//				}
//			}else {
//				if(mapVal == 6){
//					return 20;	
//				}else if(mapVal == 4) {
//					if(probVal < .33) {
//						circleView.missedObject = true;
//						return 0;
//					}else {
//						return 20;
//					}
//				}else if(mapVal == 2) {
//					if(probVal < .66) {
//						circleView.missedObject = true;
//						return 0;
//					}else {
//						return 20;
//					}
//				}
//			}
//		}else if(circleView.map[ax] == 1) { //Are they going to a cluttered area?
//			return -4.;
//		}else if (circleView.isResigning) {
//			return -10;
//		}
		
		if(circleView.isChecking) {
			if (axx == goalX) {
				return 50.;
			}else {
				return -50.;
			}
		}else if(circleView.map[axNext] % 2 == 1) {
			return -15.;
		}
		
		return -3.;
	}


}
