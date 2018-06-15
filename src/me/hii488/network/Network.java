package me.hii488.network;

import java.util.Random;

public class Network {
	
	public static Random random = new Random();
	
	private Layer[] layers;
	
	public Network(int inputs, int[] hiddenLayers, int outputs) {
		layers = new Layer[hiddenLayers.length+1];
		
		if(hiddenLayers.length > 0) {
			layers[0] = new Layer(hiddenLayers[0], inputs);
		
			for(int i = 1; i < layers.length-1; i++) {
				layers[i] = new Layer(hiddenLayers[i], hiddenLayers[i-1]);
			}
			
			layers[layers.length - 1] = new Layer(outputs, hiddenLayers[hiddenLayers.length-1]);
		}
		
		else {
			layers[0] = new Layer(outputs, inputs);
		}
	}
	
	public double[] getOutput(double[] input) {
		double[] output = layers[0].getOutput(input);
		
		for(int i = 1; i < layers.length; i++) {
			output = layers[i].getOutput(output);
		}
		
		return output;
	}
	
	public void randomise() {
		for(Layer l : layers) l.randomise();
	}
	
}
