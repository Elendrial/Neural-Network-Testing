package me.hii488.control;

import me.hii488.control.data.DataController;
import me.hii488.network.Network;
import me.hii488.network.specialisations.GeneratorNetwork;

public class Trainer {
	
	public GeneratorNetwork generator;
	public Network discriminator;
	public DataController data;
	public double learningRate;
	
	public Trainer(GeneratorNetwork g, Network d, DataController da, double learningRate) {
		generator = g;
		discriminator = d;
		data = da;
	}
	
	public void train(int iterations, int batchSize) {
		double[][] actualOutputs = new double[batchSize][discriminator.getOutputNumber()];
		double[][] expectedOutputs = new double[batchSize][discriminator.getOutputNumber()];
		
		Network deltaDiscrim = discriminator.cloneStructure();
		Network deltaGenerator = generator.cloneStructure();
		
		while(iterations-- > 0) {
			
			deltaDiscrim.setToWeightsValue(0);
			deltaDiscrim.setBiases(0);
			deltaGenerator.setToWeightsValue(0);
			deltaGenerator.setBiases(0);
			
			for(int i = 0; i < batchSize/2; i++) {
				actualOutputs[i] = discriminator.getOutput(generator.generateOutput());
				// Don't need to set expected outputs, as they are initialised to all 0's, which is what we need here.
				backprop(expectedOutputs[i], actualOutputs[i], deltaDiscrim, deltaGenerator);
			}
			
			for(int i = batchSize/2; i < batchSize; i++) {
				actualOutputs[i] = discriminator.getOutput(data.getNextTrainingInput());
				expectedOutputs[i] = data.getCurrentExpectedOutput();
				backprop(expectedOutputs[i], actualOutputs[i], deltaDiscrim, deltaGenerator);
			}
			
			deltaDiscrim.scaleNetwork(-(learningRate/batchSize));
			deltaGenerator.scaleNetwork(-(learningRate/batchSize));
			
			discriminator.addNetwork(deltaDiscrim);
			generator.addNetwork(deltaGenerator);
			
		}
	}
	
	public void backprop(double[] expectedOut, double[] actualOut, Network deltaDiscrim, Network deltaGen) {
		
	}
	
	public void setLearningRate(double lr) {
		learningRate = lr;
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	
}
