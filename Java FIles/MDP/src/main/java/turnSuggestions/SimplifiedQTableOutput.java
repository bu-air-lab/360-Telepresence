package turnSuggestions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.valuefunction.QValue;
import burlap.mdp.singleagent.SADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import newMDP.FrameGenerator;
import newMDP.frameDomain;
import newMDP.frameState;

public class SimplifiedQTableOutput {

	frameDomain frameDom;
	SADomain domain;
	HashableStateFactory hashingFactory;
	FrameGenerator frameGen;
	
	public static void main(String[] args) {
		
		SimplifiedQTableOutput q = new SimplifiedQTableOutput();		
		
		try {
			File output = new File("SimplifiedQTable.txt");
		    output.createNewFile();
			FileWriter writer = new FileWriter("SimplifiedQTable.txt", false);
			
			QLearning agent = new QLearning(q.domain, .99, q.hashingFactory, 0., 1.);
			agent.loadQTable("output/QTable.txt");
			
			ArrayList<Integer []> arrs = new ArrayList<Integer []>();
			ArrayList<frameState> states = new ArrayList<frameState>();
			
			for(int i = 0; i < 256; i++) {
				Integer[] baseArr = new Integer[8];
				for(int j = 0; j < 8; j++) {
					baseArr[j] = 0;
				}
				int x = i;
				int counter = 0;
				while(x > 0) {
					baseArr[counter++] = x % 2;
					x = x / 2;
				}
				for(int j = 0; j < 8; j++) {
					Integer [] newArr = baseArr.clone();
					newArr[j] += 2;
					arrs.add(newArr);
					for(int k = 0; k < 8; k++) {
						System.out.print(newArr[k] + " ");
					}
					System.out.println("\n");
				}
			}
			
			System.out.println(arrs.size());
			
			for(Integer [] a : arrs) {
				states.add(new frameState(a));
			}
						
			for(frameState state : states) {				
				List<QValue> qList = agent.qValues(state);
			    QValue bestqV = null;
			    double bestq = Double.NEGATIVE_INFINITY;
				for(QValue val : qList) {
					if(val.q > bestq){
						bestqV = val;
						bestq = val.q;
					}
				}
				writer.write(((frameState) bestqV.s).toPrintString() + " " + bestqV.a.toString() + " " + bestqV.q + "\n");
			}
			writer.close();
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SimplifiedQTableOutput() {
		frameDom = new frameDomain();
		domain = frameDom.generateDomain();
		hashingFactory = new SimpleHashableStateFactory();
		frameGen = new FrameGenerator();
	}

}
