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
		
		// TODO: Setup an identical set of networks with the weights/biases being the proposed change
		// Starting as all 0's, this is updated with proposed changes for each output test
		// newWeight = originalWeight - (learnRate/batchSize) * weightChange
		// Same for biases
		
		while(iterations-- > 0) {
			
			for(int i = 0; i < batchSize/2; i++) {
				actualOutputs[i] = discriminator.getOutput(generator.generateOutput());
			}
			
			for(int i = batchSize/2; i < batchSize; i++) {
				actualOutputs[i] = discriminator.getOutput(data.getNextTrainingInput());
				expectedOutputs[i] = data.getCurrentExpectedOutput();
			}
			
			
		}
	}
	
	public void setLearningRate(double lr) {
		learningRate = lr;
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	
}
