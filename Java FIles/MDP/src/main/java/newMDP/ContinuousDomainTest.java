package newMDP;

import burlap.behavior.functionapproximation.dense.DenseCrossProductFeatures;
import burlap.behavior.functionapproximation.dense.NormalizedVariableFeatures;
import burlap.behavior.functionapproximation.dense.fourier.FourierBasis;
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.learning.lspi.LSPI;
import burlap.behavior.singleagent.learning.lspi.SARSCollector;
import burlap.behavior.singleagent.learning.lspi.SARSData;
import burlap.mdp.auxiliary.StateGenerator;
import burlap.mdp.core.state.vardomain.VariableDomain;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.visualizer.Visualizer;

public class ContinuousDomainTest {

	public static void main(String [] args){
		frameLSPIFB();
	}

	public static void frameLSPIFB(){
		frameDomain frameDom = new frameDomain();
		SADomain domain = frameDom.generateDomain();
		StateGenerator rFrameGen = new FrameGenerator();
		SARSCollector collector = new SARSCollector.UniformRandomSARSCollector(domain);
		SARSData dataset = collector.collectNInstances(rFrameGen, domain.getModel(), 5000, 20, null);
		
		NormalizedVariableFeatures inputFeatures = new NormalizedVariableFeatures()
				.variableDomain("wedge", new VariableDomain(0, 7));
		
		FourierBasis fb = new FourierBasis(inputFeatures, 4);
		
		LSPI lspi = new LSPI(domain, 0.99, new DenseCrossProductFeatures(fb, 3), dataset);
		Policy p = lspi.runPolicyIteration(30, 1e-6);

		SimulatedEnvironment env = new SimulatedEnvironment(domain, rFrameGen);

		for(int i = 0; i < 5; i++){
			System.out.println("Rollout " + i);
			
			PolicyUtils.rollout(p, env);
			env.resetEnvironment();
		}

		System.out.println("Finished");
	}

}
