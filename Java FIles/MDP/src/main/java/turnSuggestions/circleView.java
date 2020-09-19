package turnSuggestions;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import burlap.shell.visual.VisualExplorer;
import burlap.visualizer.StatePainter;
import burlap.visualizer.StateRenderLayer;
import burlap.visualizer.Visualizer;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class circleView implements DomainGenerator {

	public static final String VAR_X = "x";

	public static final String ACTION_LEFT = "left";
	public static final String ACTION_RIGHT = "right";
	public static final String ACTION_CHECK = "check";
	
	public static boolean isChecking = false;
	
	protected int goalx = -1;
	
	protected RewardFunction rf;
	protected TerminalFunction tf;
	
	protected static int [] map;
	
	public static void main(String [] args){

		circleView gen = new circleView();
		int goalGen = (int) Math.floor(Math.random()*8);
		map[goalGen] = ((((int) Math.floor(Math.random()*3))+1) * 2) + map[goalGen];
		gen.setGoalLocation(goalGen);
		SADomain domain = gen.generateDomain();
		int initPos = (int) Math.floor(Math.random()*8);
		while(initPos == goalGen) {
			initPos = (int) Math.floor(Math.random()*8);
		}
		State initialState = new viewState(initPos);
		SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);

		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("d", ACTION_LEFT, "");
		exp.addKeyAction("a", ACTION_RIGHT, "");
		exp.addKeyAction("q", ACTION_CHECK, "");

		exp.initGUI();

	}
	
	public circleView() {
		
	}
	
	public circleView(int goalx) {
		map = generateMap();
		map[goalx] = ((((int) Math.floor(Math.random()*3))+1) * 2) + map[goalx];
		this.setGoalLocation(goalx);
	}
	
	public circleView(int[] mapInfo, int goal) {
		map = mapInfo;
		this.setGoalLocation(goal);
	}
	
	public void setGoalLocation(int goalx){
		this.goalx = goalx;
	}
	
	public SADomain generateDomain() {
		
		SADomain domain = new SADomain();

		domain.addActionTypes(
				new UniversalActionType(ACTION_LEFT),
				new UniversalActionType(ACTION_RIGHT),
				new UniversalActionType(ACTION_CHECK));

		circleStateModel smodel = new circleStateModel();
		RewardFunction rf = new circleRewardf(this.goalx);
		TerminalFunction tf = new circleTerminalf(this.goalx);

		domain.setModel(new FactoredModel(smodel, rf, tf));

		return domain;
	}
	
	public static int[] generateMap(){
		
		int[] retArr = new int[8];
		for(int i = 0; i < retArr.length; i++) {
			retArr[i] = (Math.random() < 0.125 ? 1 : 0);
		}
		return retArr;
	
	}
	
	public RewardFunction getRf() {
		return rf;
	}

	public void setRf(RewardFunction rf) {
		this.rf = rf;
	}

	public TerminalFunction getTf() {
		return tf;
	}

	public void setTf(TerminalFunction tf) {
		this.tf = tf;
	}

	public class AgentPainter implements StatePainter {


		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(Color.GRAY);

			//set up floats for the width and height of our domain
			float fWidth = circleView.map.length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;

			int ax = (Integer)s.get(VAR_X);

			//left coordinate of cell on our canvas
			float rx = ax*width;

			//top coordinate of cell on our canvas
			//coordinate system adjustment because the java canvas
			//origin is in the top left instead of the bottom right
			float ry = cHeight - (height*4);

			//paint the rectangle
			g2.fill(new Ellipse2D.Float(rx, ry, width, height));


		}



	}
	
	public class GoalPainter implements StatePainter {


		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(new Color(0, 255, 0, 164));

			//set up floats for the width and height of our domain
			float fWidth = circleView.map.length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;

			int ax = (Integer)goalx;

			//left coordinate of cell on our canvas
			float rx = ax*width;

			//top coordinate of cell on our canvas
			//coordinate system adjustment because the java canvas
			//origin is in the top left instead of the bottom right
			float ry = cHeight - (height*4);

			//paint the rectangle
			g2.fill(new RoundRectangle2D.Float(rx, ry, width, height, 63, 63));


		}



	}
	
	public class SquarePainter implements StatePainter {


		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(Color.BLACK);

			//set up floats for the width and height of our domain
			float fWidth = circleView.map.length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;

			
			for(int i = 0; i < map.length; i++) {
				if(map[i] == 0 && i != goalx) {
					int ax = i;
	
					//left coordinate of cell on our canvas
					float rx = ax*width;
	
					//top coordinate of cell on our canvas
					//coordinate system adjustment because the java canvas
					//origin is in the top left instead of the bottom right
					float ry = cHeight - (height*4);
	
					//paint the rectangle
					g2.draw(new Rectangle2D.Float(rx, ry, width, height));
				}
			}
		}
	}
	
	public class ClutterPainter implements StatePainter {


		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(new Color(255, 128, 128, 64));

			//set up floats for the width and height of our domain
			float fWidth = circleView.map.length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;

			
			for(int i = 0; i < map.length; i++) {
				if(map[i] == 1) {
					int ax = i;
	
					//left coordinate of cell on our canvas
					float rx = ax*width;
	
					//top coordinate of cell on our canvas
					//coordinate system adjustment because the java canvas
					//origin is in the top left instead of the bottom right
					float ry = cHeight - (height*4);
	
					//paint the rectangle
					g2.fill(new Rectangle2D.Float(rx, ry, width, height));
				}
			}
		}
	}
	
	public StateRenderLayer getStateRenderLayer(){
		StateRenderLayer rl = new StateRenderLayer();
		
		rl.addStatePainter(new circleView.SquarePainter());
		rl.addStatePainter(new circleView.ClutterPainter());
		rl.addStatePainter(new circleView.AgentPainter());
		rl.addStatePainter(new circleView.GoalPainter());
		
		return rl;
	}

	public Visualizer getVisualizer(){
		return new Visualizer(this.getStateRenderLayer());
	}
}