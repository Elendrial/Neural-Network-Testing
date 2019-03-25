package me.hii488.control.trainers;

import me.hii488.control.data.DataController;
import me.hii488.control.trainers.costfunctions.Cost;
import me.hii488.network.Layer;
import me.hii488.network.Network;

public class StandardTrainer extends Trainer {
	
	public Network network;
	
	public StandardTrainer(Network n, DataController da, Cost c, double learningRate) {
		network = n;
		data = da;
		cost = c;
		
		this.learningRate = learningRate;
	}
	
	public void train(int iterations, int batchSize) {
		Network deltaNet = network.cloneStructure();
		
		double[][] actualOutputs = new double[batchSize][network.getOutputNumber()];
		double[][] expectedOutputs = new double[batchSize][network.getOutputNumber()];
		
		while(iterations-- >= 0) {
			
			deltaNet.setToWeightsValue(0);
			deltaNet.setBiases(0);
			
			for(int i = 0; i < batchSize; i++) {
				actualOutputs[i] = network.getOutput(data.getNextTrainingInput());
				expectedOutputs[i] = data.getCurrentExpectedOutput();
				deltaNet.addNetwork(backprop(expectedOutputs[i], actualOutputs[i]));
			}
			
			deltaNet.scaleNetwork(-(learningRate/batchSize));
			
			network.addNetwork(deltaNet);
		}
	}
	
	@Override
	public double test(int number) {
		double accuracy = 0, tempacc, num = number;
		double[] actualOutput = new double[network.getOutputNumber()];
		double[] expectedOutput = new double[network.getOutputNumber()];
		
		while(number-- > 0) {
			actualOutput = network.getOutput(data.getNextTestingInput());
			expectedOutput = data.getCurrentExpectedOutput();
			
			// TODO: Probably change this to be cost or something? Not sure, will need to check, for the moment this works, should always work, who knows?
			tempacc = 0;
			for(int i = 0; i < network.getOutputNumber(); i++)
				tempacc += 1 - Math.abs(expectedOutput[i] - actualOutput[i]);
			
			accuracy += tempacc/network.getOutputNumber();
		}
		
		return accuracy/num;
	}
	
	private Network backprop(double[] expectedOut, double[] actualOut) {
		Network n = network.cloneStructure();
		Layer[] layers = n.getLayers();
		
		for(int i = 0; i < layers.length; i++) {
			for(int j = 0; j < layers[i].nodes.length; j++) {
				layers[i].nodes[j].weightedInput = network.getLayers()[i].nodes[j].weightedInput;
			}
		}
		
		int lastLayer = layers.length-1;
		
		// Work out the output error
		for(int i = 0; i < layers[lastLayer].nodes.length; i++) {
			// NB: Using last layer's bias as the error here as bias error is the same as the node's error. This line is essentially (BP1) from chap2 of that book.
			layers[lastLayer].nodes[i].bias = cost.costPrime(actualOut[i], expectedOut[i]) * layers[lastLayer].nodes[i].activation.functionPrime(actualOut[i]);
		}
		
		// Backpropagate this error, using eq^n 45 and BP4 at http://neuralnetworksanddeeplearning.com/chap2.html
		double nodeError, activationPrime, activation;
		for(int i = lastLayer-1; i >= 0; i--) {
			for(int j = 0; j < layers[i].nodes.length; j++) {
				// Node/Bias Error:
				nodeError = 0;
				
				activationPrime = layers[i].nodes[j].activation.functionPrime(layers[i].nodes[j].weightedInput);
				for(int k = 0; k < layers[i+1].nodes.length; k++)
					nodeError += network.getLayers()[i+1].nodes[k].weights[j] * layers[i+1].nodes[k].bias * activationPrime;
				
				layers[i].nodes[j].bias = nodeError;
				
				
				// Weight Error:
				activation = layers[i].nodes[j].activation.function(layers[i].nodes[j].weightedInput);
				for(int k = 0; k < layers[i+1].nodes.length; k++)
					layers[i+1].nodes[k].weights[j] = activation * layers[i+1].nodes[k].bias;
				
			}
		}
		
		return n;
	}
	
}
