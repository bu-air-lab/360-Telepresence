package newMDP;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

import static newMDP.frameDomain.*;

public class frameStateModel implements FullStateModel{

	protected double [][] transitionProbs;

	public frameStateModel() {
		this.transitionProbs = new double[2][2];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				double p = i != j ? 0.1 : 0.9;
				transitionProbs[i][j] = p;
			}
		}
	}

	protected int actionDir(Action a){
		int adir = -1;
		if(a.actionName().equals(ACTION_LEFT)){
			adir = 0;
		}
		else if(a.actionName().equals(ACTION_RIGHT)){
			adir = 1;
		}
		else if(a.actionName().equals(ACTION_CHECK)){
			adir = 2;
		}
		return adir;
	}

	protected Integer [] moveResult(Integer [] curFrame, int direction) {
		
		int carry = 0;
		Integer [] newFrame = new Integer[8];
		if(direction == 0){
			carry = curFrame[7];
			for(int i = 7; i > 0; i--) {
				newFrame[i] = curFrame[i-1];
			}
			newFrame[0] = carry;
		}
		else if(direction == 1){
			carry = curFrame[0];
			for(int i = 0; i < 7; i++) {
				newFrame[i] = curFrame[i+1];
			}
			newFrame[7] = carry;
		}
		else if(direction == 2){
			newFrame = curFrame;
			frameDomain.isChecking = true;
		}
		
		return newFrame;
	}

	@Override
	public State sample(State s, Action a) {

		s = s.copy();
		frameState gs = (frameState)s;
		Integer [] curFrame = gs.frame;

		int adir = actionDir(a);
		
		if(adir == 2) {
			Integer [] newFrame = this.moveResult(curFrame, adir);
			gs.frame = newFrame;
			return gs;
		}else {
			int dir = -1;
			if(adir == 0) {
				if(Math.random() < .1) {
					dir = 1;
				}else {
					dir = 0;
				}
			}else if(adir == 1) {
				if(Math.random() < .1) {
					dir = 0;
				}else {
					dir = 1;
				}
			}
			/* FROM TUTORIAL, NOT SURE IF WORKS THOUGH
			//sample direction with random roll
			double r = Math.random();
			double sumProb = 0.;
			int dir = 0;
			for(int i = 0; i < 2; i++){
				sumProb += this.transitionProbs[adir][i];
				if(r < sumProb){
					dir = i;
					break; //found direction
				}
			}
			*/

			//get resulting position
			Integer [] newFrame = this.moveResult(curFrame, dir);

			//set the new position
			gs.frame = newFrame;

			//return the state we just modified
			return gs;
		}
	}

	@Override
	public List<StateTransitionProb> stateTransitions(State s, Action a) {

		//get agent current position
		frameState gs = (frameState)s;

		Integer [] curFrame = gs.frame;

		int adir = actionDir(a);

		List<StateTransitionProb> tps = new ArrayList<StateTransitionProb>(3);
		StateTransitionProb noChange = null;
		for(int i = 0; i < 3; i++){

			Integer [] newFrame = this.moveResult(curFrame, i);
			if(newFrame != curFrame){
				//new possible outcome
				frameState ns = gs.copy();
				ns.frame = newFrame;

				//create transition probability object and add to our list of outcomes
				tps.add(new StateTransitionProb(ns, this.transitionProbs[adir][i]));
			}
			else{
				//this direction didn't lead anywhere new
				//if there are existing possible directions
				//that wouldn't lead anywhere, aggregate with them
				if(noChange != null){
					noChange.p += this.transitionProbs[adir][i];
				}
				else{
					//otherwise create this new state and transition
					noChange = new StateTransitionProb(s.copy(), this.transitionProbs[adir][i]);
					tps.add(noChange);
				}
			}

		}

		return tps;
	}

}