package me.hii488.control;

import me.hii488.control.data.DataController;
import me.hii488.network.Network;
import me.hii488.network.specialisations.GeneratorNetwork;

public class Trainer {
	
	public GeneratorNetwork generator;
	public Network discriminator;
	public DataController data;
	
	public Trainer(GeneratorNetwork g, Network d, DataController da) {
		generator = g;
		discriminator = d;
		data = da;
	}
	
	public void train(int iterations, int iterationSize) {
		double[][] descOutputs = new double[iterationSize][discriminator.getOutputNumber()];
		
		while(iterations-- > 0) {
			
			for(int i = 0; i < iterationSize/2; i++) {
				descOutputs[i] = discriminator.getOutput(generator.generateOutput());
			}
			
			for(int i = iterationSize/2; i < iterationSize; i++) {
				descOutputs[i] = discriminator.getOutput(data.getNextTrainingInput());
			}
			
		}
	}
	
}
