package newMDP;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;

public class qLearning {

	frameDomain frameDom;
	SADomain domain;
	RewardFunction rf;
	TerminalFunction tf;
	StateConditionTest goalCondition;
	FrameGenerator frameGen;
	HashableStateFactory hashingFactory;
	SimulatedEnvironment env;
	
	
	public static void main(String[] args) {
	
		qLearning learner = new qLearning();
		String outputPath = "output/"; //directory to record results
	
		//we will call planning and learning algorithms here
		learner.QLearningSolver(outputPath);
	
		//run the visualizer
		//qLearning.visualize(outputPath);
	
	}
	
	public qLearning(){
		frameDom = new frameDomain();
		tf = new frameTerminalF();
		frameDom.setTf(tf);
		goalCondition = new TFGoalCondition(tf);
		domain = frameDom.generateDomain();

		frameGen = new FrameGenerator();
		hashingFactory = new SimpleHashableStateFactory();

		env = new SimulatedEnvironment(domain, frameGen);
	}

	public void visualize(String outputPath){
		Visualizer v = frameDom.getVisualizer();
		new EpisodeSequenceVisualizer(v, domain, outputPath);
	}

	public void QLearningSolver(String outputPath){
		
		QLearning agent = new QLearning(domain, .99, hashingFactory, 0., 1.);

		//run learning for 50 episodes
		for(int i = 1; i <= 163840000; i++){
			//Run agent on new frame
			Episode e = agent.runLearningEpisode(env);
			
			/*if(i % 500 == 0 || i == 1) {
				double totalReward = 0;
				e.write(outputPath + "ql_" + i);
				for(Double actionReward : e.rewardSequence) {
					totalReward += actionReward;
				}
				System.out.println(i + ": " + totalReward);
			}*/
			
			//generate new frame for next learning episode
			env.resetEnvironment();
		}
		
		agent.writeQTable(outputPath + "QTable.txt");
		System.out.println("Done!");
	}	
	
}