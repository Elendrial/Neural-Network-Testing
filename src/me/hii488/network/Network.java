package me.hii488.network;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import me.hii488.network.nodeActivations.Activation;

public class Network implements Serializable {
	private static final long serialVersionUID = 2307104666063622823L;
	public static Random random = new Random();
	
	protected Layer[] layers;
	
	protected Network() {}
	
	public Network(int inputs, int[] hiddenLayers, int outputs, Activation act) {
		layers = new Layer[hiddenLayers.length+1];
		
		if(hiddenLayers.length > 0) {
			layers[0] = new Layer(hiddenLayers[0], inputs, act);
		
			for(int i = 1; i < layers.length-1; i++) {
				layers[i] = new Layer(hiddenLayers[i], hiddenLayers[i-1], act);
			}
			
			layers[layers.length - 1] = new Layer(outputs, hiddenLayers[hiddenLayers.length-1], act);
		}
		
		else {
			layers[0] = new Layer(outputs, inputs, act);
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
	
	public void setToWeightsValue(double d) {
		for(Layer l : layers) for(Node n : l.nodes) Arrays.setAll(n.weights, a -> d);
	}
	
	public void setBiases(double d) {
		for(Layer l : layers) for(Node n : l.nodes) n.bias = d;
	}
	
	public void saveNetwork(String path) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			System.err.println("Failed to save network:");
			e.printStackTrace();
		}
	}
	
	public static Network loadNetwork(String path) {
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object result = in.readObject();
			in.close();
			fileIn.close();
			return (Network) result;
		}
		catch(Exception e){
			System.err.println("Failed to load network:");
			e.printStackTrace();
			
			return new Network();
		}
	}
	
	public int getInputNumber() {
		return layers[0].nodes[0].weights.length;
	}
	
	public int getOutputNumber() {
		return layers[layers.length-1].nodes.length;
	}
	
	public Network cloneStructure(){
		int[] layerSizes = new int[layers.length-1];
		for(int i = 0; i < layers.length - 1; i++) {
			layerSizes[i] = layers[i].nodes.length;
		}
		return new Network(layers[0].nodes[0].weights.length, layerSizes, layers[layers.length-1].nodes.length, layers[0].nodes[0].activation);
	}
	
	public void addNetwork(Network n) {
		if(!haveSimilarStructure(this, n)) throw new RuntimeException("Networks must have similar structure to add them.");
		for(int i = 0; i < layers.length; i++) {
			for(int j = 0; j < layers[i].nodes.length; j++) {
				
				layers[i].nodes[j].bias += n.layers[i].nodes[j].bias;
				for(int k = 0; k < layers[i].nodes[j].weights.length; k++)
					layers[i].nodes[j].weights[k] += n.layers[i].nodes[j].weights[k];
				
			}
		}
	}
	
	public void averageNetwork(Network n) {
		if(!haveSimilarStructure(this, n)) throw new RuntimeException("Networks must have similar structure to add them.");
		for(int i = 0; i < layers.length; i++) {
			for(int j = 0; j < layers[i].nodes.length; j++) {
				
				layers[i].nodes[j].bias = (layers[i].nodes[j].bias + n.layers[i].nodes[j].bias)/2;
				for(int k = 0; k < layers[i].nodes[j].weights.length; k++)
					layers[i].nodes[j].weights[k] = (layers[i].nodes[j].weights[k] + n.layers[i].nodes[j].weights[k])/2;
				
			}
		}
	}
	
	public void scaleNetwork(double d) {
		for(int i = 0; i < layers.length; i++) {
			for(int j = 0; j < layers[i].nodes.length; j++) {
				
				layers[i].nodes[j].bias *= d;
				for(int k = 0; k < layers[i].nodes[j].weights.length; k++)
					layers[i].nodes[j].weights[k] *= d;
				
			}
		}
	}
	
	
	public static boolean haveSimilarStructure(Network a, Network b) {
		if(a.layers.length != b.layers.length) return false;
		for(int i = 0; i < a.layers.length; i++) {
			if(a.layers[i].nodes.length != b.layers[i].nodes.length) return false;
			for(int j = 0; j < a.layers[i].nodes.length; j++) {
				if(a.layers[i].nodes[j].weights.length != b.layers[i].nodes[j].weights.length) return false;
			}
		}
		return true;
	}
	
	public Layer[] getLayers() {
		return layers;
	}
}
