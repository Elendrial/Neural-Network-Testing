package me.hii488.network;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class Network implements Serializable {
	private static final long serialVersionUID = 2307104666063622823L;
	public static Random random = new Random();
	
	protected Layer[] layers;
	
	protected Network() {}
	
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
			System.err.println("Failed to load network");
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
	
}
