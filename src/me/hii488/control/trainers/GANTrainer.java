package me.hii488.control.trainers;

import me.hii488.control.data.DataController;
import me.hii488.network.Network;
import me.hii488.network.specialisations.GeneratorNetwork;

public class GANTrainer extends Trainer{
	
	public GeneratorNetwork generator;
	public Network discriminator;
	
	public GANTrainer(GeneratorNetwork g, Network d, DataController da, double learningRate) {
		generator = g;
		discriminator = d;
		data = da;

		this.learningRate = learningRate;
	}
	
	public void train(int iterations, int batchSize) {
		trainDiscriminator(iterations, batchSize);
		trainGenerator(iterations, batchSize);
	}
	
	public void trainDiscriminator(int iterations, int batchSize) {
		Network deltaDiscrim = discriminator.cloneStructure();

		double[][] actualOutputs = new double[batchSize][discriminator.getOutputNumber()];
		double[][] expectedOutputs = new double[batchSize][discriminator.getOutputNumber()];
		
		while(iterations-- > 0) {
		
			deltaDiscrim.setToWeightsValue(0);
			deltaDiscrim.setBiases(0);
			
			for(int i = 0; i < batchSize/2; i++) {
				actualOutputs[i] = discriminator.getOutput(generator.generateOutput());
				// Don't need to set expected outputs, as they are initialised to all 0's, which is what we need here.
				backpropOnDiscrim(expectedOutputs[i], actualOutputs[i], deltaDiscrim);
			}
			
			for(int i = batchSize/2; i < batchSize; i++) {
				actualOutputs[i] = discriminator.getOutput(data.getNextTrainingInput());
				expectedOutputs[i] = data.getCurrentExpectedOutput();
				backpropOnDiscrim(expectedOutputs[i], actualOutputs[i], deltaDiscrim);
			}
			
			deltaDiscrim.scaleNetwork(-(learningRate/batchSize));
			
			discriminator.addNetwork(deltaDiscrim);
		}
	}
	
	private void trainGenerator(int iterations, int batchSize) {
		double[][] actualOutputs = new double[batchSize][discriminator.getOutputNumber()];
		double[][] expectedOutputs = new double[batchSize][discriminator.getOutputNumber()];
		
		Network deltaGenerator = generator.cloneStructure();
		
		while(iterations-- > 0) {
			deltaGenerator.setToWeightsValue(0);
			deltaGenerator.setBiases(0);
			
			for(int i = 0; i < batchSize; i++) {
				actualOutputs[i] = discriminator.getOutput(generator.generateOutput());
				// Don't need to set expected outputs, as they are initialised to all 0's, which is what we need here.
				backpropOnGen(expectedOutputs[i], actualOutputs[i], deltaGenerator);
			}
			
			deltaGenerator.scaleNetwork(-(learningRate/batchSize));
			
			generator.addNetwork(deltaGenerator);
			
		}
	}
	
	public void backpropOnDiscrim(double[] expectedOut, double[] actualOut, Network deltaDiscrim) {
		
	}
	
	public void backpropOnGen(double[] expectedOut, double[] actualOut, Network deltaGenerator) {
		// This might be a tad tricky... Going to have to work out how you differentiate a neural network...
	}

	@Override
	public double test(int number) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
