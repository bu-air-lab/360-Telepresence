package turnSuggestions;

import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.auxiliary.StateReachability;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.ValueFunctionVisualizerGUI;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.common.ArrowActionGlyph;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.common.LandmarkColorBlendInterpolation;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.common.PolicyGlyphPainter2D;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.common.StateValuePainter2D;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
import burlap.behavior.singleagent.planning.Planner;
import burlap.behavior.singleagent.planning.deterministic.DeterministicPlanner;
import burlap.behavior.singleagent.planning.deterministic.informed.Heuristic;
import burlap.behavior.singleagent.planning.deterministic.informed.astar.AStar;
import burlap.behavior.singleagent.planning.deterministic.uninformed.bfs.BFS;
import burlap.behavior.singleagent.planning.deterministic.uninformed.dfs.DFS;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.behavior.valuefunction.QProvider;
import burlap.behavior.valuefunction.QValue;
import burlap.behavior.valuefunction.ValueFunction;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldTerminalFunction;
import burlap.domain.singleagent.gridworld.GridWorldVisualizer;
import burlap.domain.singleagent.gridworld.state.GridAgent;
import burlap.domain.singleagent.gridworld.state.GridLocation;
import burlap.domain.singleagent.gridworld.state.GridWorldState;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.auxiliary.stateconditiontest.TFGoalCondition;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.vardomain.VariableDomain;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.common.GoalBasedRF;
import burlap.mdp.singleagent.common.VisualActionObserver;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;


public class BasicBehavior {

	circleView cvdg;
	SADomain domain;
	RewardFunction rf;
	TerminalFunction tf;
	StateConditionTest goalCondition;
	viewState initialState;
	HashableStateFactory hashingFactory;
	SimulatedEnvironment env;
	getState getS;


	public static void main(String[] args) throws IOException {

		/*
		File dir = new File("/home/jack/eclipse-workspace/MDP/output/");

		for (File file: dir.listFiles()) {
			file.delete();
		}
		*/

		String mapString = ""; //Will contain the input from the file



		getState getCurrState = new getState();
		mapString = getCurrState.readServerFile();
		System.out.println("Server read Successful:"+mapString);


		//Reads in map from the file
		try {
		      File input = new File("inputMap.txt");
		      Scanner scanner = new Scanner(input);
		      while (scanner.hasNextLine()) {
		        mapString = scanner.nextLine();
		      }
		      scanner.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Unable to find the file.");
		      e.printStackTrace();
		      System.exit(0);
		}

		//Parsing file from string
		String[] mapAndStart = mapString.split(" ", 0);

		String[] mapNums = mapAndStart[0].split(",", 0);

		int[] inputMap = new int[8]; //Holds the map array

		int startPos = Integer.parseInt(mapAndStart[1]); //Holds the position of the view

		int goalPos = 0; //Holds the position of the object of interest

		for(int i = 0; i < 8; i++) {
			inputMap[i] = Integer.parseInt(mapNums[i]);
			if(inputMap[i] == 2) {
				goalPos = i;
			}
		}

		BasicBehavior view = new BasicBehavior(inputMap, startPos, goalPos);
		String outputPath = "output/"; //Results Directory

		//view.valueIterationExample(outputPath); //Value Iteration
		//view.BFSExample(outputPath); //Breath First Search
		view.QLearningExample(outputPath); //Q-Learning


		//view.visualize(outputPath);
	}

	public BasicBehavior(int[] map, int initPos, int goalPos) {

		getS = new getState();
		cvdg = new circleView(map, goalPos);
		tf = new circleTerminalf(cvdg.goalx);
		cvdg.setTf(tf);
		rf = new circleRewardf(cvdg.goalx);
		cvdg.setRf(rf);
		goalCondition = new TFGoalCondition(tf);
		domain = cvdg.generateDomain();
		initialState = new viewState(initPos);
		hashingFactory = new SimpleHashableStateFactory();

		env = new SimulatedEnvironment(domain, initialState);
	}

	public void visualize(String outputPath) {
		Visualizer v = new Visualizer(cvdg.getStateRenderLayer());
		new EpisodeSequenceVisualizer(v, domain, outputPath);
	}


	public void valueIterationExample(String outputPath) {

		Planner planner = new ValueIteration(domain, 0.99, hashingFactory, 0.001, 100);
		Policy p = planner.planFromState(initialState);

		PolicyUtils.rollout(p, initialState, domain.getModel()).write(outputPath + "vi");
	}

	public void BFSExample(String outputPath){

		DeterministicPlanner planner = new BFS(domain, goalCondition, hashingFactory);
		Policy p = planner.planFromState(initialState);
		PolicyUtils.rollout(p, initialState, domain.getModel()).write(outputPath + "bfs");

	}

	public void QLearningExample(String outputPath){

		QLearning agent = new QLearning(domain, 0.99, hashingFactory, 0., 1.);
		String current_action_set = "";
		int episodes = 250000;

		updateAction updateCurrAction = new updateAction();


		/*
		Episode best = null;
		double bestReward = -1.0;
		int num = -1;
		*/

		//run learning for 50 episodes
		for(int i = 1; i < episodes+1; i++){
			Episode e = agent.runLearningEpisode(env);

			/*
			double totalReward = 0;

			for(Double d : e.rewardSequence) {
				System.out.print(d + ", ");
				totalReward += d;
			}


			if(i % 250 == 0 || i == 1) {
				e.write(outputPath + "ql_" + i + "_" + totalReward);
			};

			if(i == 1 || totalReward >= bestReward){
				best = e;
				bestReward = totalReward;
				num = i;
			}

			System.out.println("\n" + i + ": " + e.maxTimeStep());
			*/

			//reset environment for next learning episode
			env.resetEnvironment();
		}

		//best.write(outputPath + "ql_BEST_" + num);

		try {
		      File output = new File("QTable.txt");
		      output.createNewFile();
		} catch (IOException e) {
		      System.out.println("Unable to create output file.");
		      e.printStackTrace();
		}

		try {
		    FileWriter writer = new FileWriter("QTable.txt", false);
		    for(int i = 0; i < 8; i++) {
		    	List<QValue> qList = agent.qValues(new viewState(i));
		    	QValue bestqV = null;
		    	double bestq = Double.NEGATIVE_INFINITY;
				for(QValue q : qList) {
					if(q.q > bestq){
						bestqV = q;
						bestq = q.q;
					}
				}
				current_action_set = current_action_set + bestqV.s.toString() + " " + bestqV.a.toString() + " " + bestqV.q + "\n";
				writer.write(bestqV.s.toString() + " " + bestqV.a.toString() + " " + bestqV.q + "\n");
		    }
		    writer.close();
		    System.out.println(current_action_set);
				updateCurrAction.updateServerFile(current_action_set);
		    System.out.println("Successfully wrote QTable.txt");
		} catch (IOException e) {
		    System.out.println("Failed to write QTable.txt.");
		    e.printStackTrace();
		}

		//agent.writeQTable("QTable"); //Writes a Q-table to the output
	}

}
