package newMDP;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.shell.visual.VisualExplorer;
import burlap.visualizer.StatePainter;
import burlap.visualizer.StateRenderLayer;
import burlap.visualizer.Visualizer;



public class frameDomain implements DomainGenerator {

	public static final String VAR_FRAME = "frame";
	public static final String VAR_FRAME_w0 = "frame_w0";
	public static final String VAR_FRAME_w1 = "frame_w1";
	public static final String VAR_FRAME_w2 = "frame_w2";
	public static final String VAR_FRAME_w3 = "frame_w3";
	public static final String VAR_FRAME_w4 = "frame_w4";
	public static final String VAR_FRAME_w5 = "frame_w5";
	public static final String VAR_FRAME_w6 = "frame_w6";
	public static final String VAR_FRAME_w7 = "frame_w7";

	public static final String ACTION_LEFT = "left";
	public static final String ACTION_RIGHT = "right";
	public static final String ACTION_CHECK = "check";

	public static boolean isChecking = false;

	protected RewardFunction rf;
	protected TerminalFunction tf;

	public static void main(String [] args){

		frameDomain gen = new frameDomain();
		SADomain domain = gen.generateDomain();
		State initialState = new frameState(generateFrame()); //(int)Math.floor(Math.random() * 8));
		SimulatedEnvironment env = new SimulatedEnvironment(domain, initialState);

		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("a", ACTION_LEFT, "");
		exp.addKeyAction("d", ACTION_RIGHT, "");
		exp.addKeyAction("c", ACTION_CHECK, "");

		exp.initGUI();

	}
	
	public SADomain generateDomain() {
		
		SADomain domain = new SADomain();

		domain.addActionTypes(
				new UniversalActionType(ACTION_LEFT),
				new UniversalActionType(ACTION_RIGHT),
				new UniversalActionType(ACTION_CHECK)
		);

		frameStateModel fsmodel = new frameStateModel();
		RewardFunction rf = new frameRewardF();
		TerminalFunction tf = new frameTerminalF();

		domain.setModel(new FactoredModel( fsmodel, rf, tf));

		return domain;
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

	public static Integer [] generateFrame(){
		
		Integer [] retArr = new Integer[8];
		for(int i = 0; i < retArr.length; i++) {
			retArr[i] = (Math.random() < 0.5 ? 1 : 0);
		}

		retArr[(int) Math.floor(Math.random() * 8)] += 2;

		return retArr;
	}

	public class AgentPainter implements StatePainter {

		@Override
		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(Color.BLUE);

			//set up floats for the width and height of our domain
			float fWidth = ((Integer []) s.get("frame")).length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;

			//left coordinate of cell on our canvas
			float rx = 0;

			//top coordinate of cell on our canvas
			//coordinate system adjustment because the java canvas
			//origin is in the top left instead of the bottom right
			float ry = cHeight - (height*4);

			//paint the rectangle
			g2.fill(new RoundRectangle2D.Float(rx, ry, width, height, 63, 63));
		}
	}

	public class GoalPainter implements StatePainter {


		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(new Color(0, 255, 0, 255));

			//set up floats for the width and height of our domain
			float fWidth = ((Integer []) s.get("frame")).length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;
			

			for(int i = 0; i < ((Integer []) s.get("frame")).length; i++){
				if((((Integer []) s.get("frame")))[i] >= 2){
					//left coordinate of cell on our canvas
					float rx = i*width;

					//top coordinate of cell on our canvas
					//coordinate system adjustment because the java canvas
					//origin is in the top left instead of the bottom right
					float ry = cHeight - (height*4);

					//paint the rectangle
					g2.fill(new Ellipse2D.Float(rx, ry, width, height));
				}
			}
		}
	}

	public class SquarePainter implements StatePainter {


		public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

			//agent will be filled in gray
			g2.setColor(Color.BLACK);

			//set up floats for the width and height of our domain
			float fWidth = ((Integer []) s.get("frame")).length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;
			
			for(int i = 0; i < ((Integer []) s.get("frame")).length; i++) {
				if((((Integer []) s.get("frame")))[i] == 0) {
					//left coordinate of cell on our canvas
					float rx = i*width;
	
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

			//walls will be filled in black
			g2.setColor(new Color(255, 128, 128, 64));

			//set up floats for the width and height of our domain
			float fWidth = ((Integer []) s.get("frame")).length;
			float fHeight = fWidth;

			//determine the width of a single cell on our canvas
			//such that the whole map can be painted
			float width = cWidth / fWidth;
			float height = cHeight / fHeight;
			
			for(int i = 0; i < ((Integer []) s.get("frame")).length; i++) {

				//is there a wall here?
				if((((Integer []) s.get("frame")))[i] % 2 == 1){

					//left coordinate of cell on our canvas
					float rx = i*width;

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
		
		rl.addStatePainter(new frameDomain.SquarePainter());
		rl.addStatePainter(new frameDomain.ClutterPainter());
		rl.addStatePainter(new frameDomain.AgentPainter());
		rl.addStatePainter(new frameDomain.GoalPainter());
		
		
		return rl;
	}

	public Visualizer getVisualizer(){
		return new Visualizer(this.getStateRenderLayer());
	}
}
