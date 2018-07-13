package me.hii488.network;

import java.io.Serializable;

import me.hii488.network.nodeActivations.Activation;

public class Node implements Serializable{
	
	private static final long serialVersionUID = 397101734162241004L;
	
	public Activation activation;
	public double[] weights;
	public double bias;
	public double weightedInput;
	
	public Node(int prevConnections, Activation act) {
		weights = new double[prevConnections];
		activation = act;
		bias = 0;
		
		for(int i = 0; i < weights.length; i++)
			weights[i] = 0;
		
	}
	
	public void randomise() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = Network.random.nextGaussian();
		}
	}

	public double getOutput(double[] input) {
		weightedInput = bias; 
		
		for(int i = 0; i < input.length; i++){
			weightedInput += input[i] * weights[i];
		}
		
		return activation.function(weightedInput);
	}
	
}
