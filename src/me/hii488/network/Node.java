package me.hii488.network;

public class Node {
	
	public double[] weights;
	public double bias;
	public double lastOutput;
	
	public Node(int prevConnections) {
		weights = new double[prevConnections];
		
		for(int i = 0; i < weights.length; i++)
			weights[i] = 0;
		
		bias = 0;
	}
	
	public void randomise() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = Network.random.nextGaussian();
		}
	}

	public double getOutput(double[] input) {
		double temp = bias; 
		
		for(int i = 0; i < input.length; i++){
			temp += input[i] * weights[i];
		}
		
		// Sigmoid function
		lastOutput = 1/(1 + Math.pow(Math.E, -temp));
		return lastOutput;
	}
	
}
