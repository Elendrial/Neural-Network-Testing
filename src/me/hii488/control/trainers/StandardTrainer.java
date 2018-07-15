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

	private Network backprop(double[] expectedOut, double[] actualOut) {
		Network n = network.cloneStructure();
		Layer[] layers = n.getLayers();
		
		int lastLayer = layers.length-1;
		
		// Work out the output error
		for(int i = 0; i < layers[lastLayer].nodes.length; i++) {
			// NB: Using last layer's bias as the error here as bias error is the same as the node's error. This line is essentially (BP1) from chap2 of that book.
			layers[lastLayer].nodes[i].bias = cost.costPrime(actualOut[i], expectedOut[i]) * layers[lastLayer].nodes[i].activation.functionPrime(actualOut[i]);
		}
		
		// Backpropagate this error
		for(int i = lastLayer-1; i >= 0; i--) {
			
		}
		
		
		return n;
	}
	
}
