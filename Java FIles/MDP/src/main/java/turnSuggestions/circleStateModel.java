package turnSuggestions;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;

public class circleStateModel implements FullStateModel{
	
	protected double [][] transitionProbs;

	
	public List<StateTransitionProb> stateTransitions(State s, Action a) {

		//get agent current position
		viewState gs = (viewState)s;
		int curX = gs.x;
		int adir = actionDir(a);

		List<StateTransitionProb> tps = new ArrayList<StateTransitionProb>(4);
		StateTransitionProb noChange = null;
		for(int i = 0; i < 3; i++){

			int newPos = this.moveResult(curX, i);
			if(newPos != curX){
				//new possible outcome
				viewState ns = gs.copy();
				ns.x = newPos;

				//create transition probability object and add to our list of outcomes
				tps.add(new StateTransitionProb(ns, this.transitionProbs[adir][i]));
			} else {
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

	public State sample(State s, Action a) {
		s = s.copy();
		viewState gs = (viewState)s;
		int curX = gs.x;

		int adir = actionDir(a);
		
		if(adir == 2) {
			int newPos = this.moveResult(curX, adir);
			gs.x = newPos;
			return gs;
		}else {
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
	
			//get resulting position
			int newPos = this.moveResult(curX, dir);
	
			//set the new position
			gs.x = newPos;
	
			//return the state we just modified
			return gs;
		}
	}

	public circleStateModel() {
		this.transitionProbs = new double[4][4];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				double p = i != j ? 0.1 : 0.9;
				transitionProbs[i][j] = p;
			}
		}
	}
	
	protected int actionDir(Action a){
		int adir = -1;
		
		if(a.actionName().equals(circleView.ACTION_LEFT)){
			adir = 0;
		}else if(a.actionName().equals(circleView.ACTION_RIGHT)){
			adir = 1;
		}else if(a.actionName().equals(circleView.ACTION_CHECK)) {
			adir = 2;
		}
		
		return adir;
	}
	
	protected int moveResult(int curX, int direction){

		//first get change in x and y from direction using 0: north; 1: south; 2:east; 3: west
		int xdelta = 0;
		if(direction == 0){
			xdelta = -1;
		}
		else if(direction == 1){
			xdelta = 1;
		}else if(direction == 2) {
			circleView.isChecking = true;
		}

		int nx = Math.floorMod((curX + xdelta), 8);

		return nx;
	}
	
	
}
