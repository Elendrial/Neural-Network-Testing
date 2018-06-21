package me.hii488.control;

import me.hii488.network.Network;

public class NetworkBuilder {
	
	private int discriminatorInputLength = 1;
	private int discriminatorOutputs = 1;
	private int inputNoiseSize = 5;
	private int[] discriminatorHidden = new int[] {};
	private int[] generatorHidden = new int[] {};
	
	public NetworkBuilder() {}
	public NetworkBuilder(int noiseAmount, int[] generatorHidden, int generatorOutputs, int[] discriminatorHidden, int discriminatorOutputs) {
		setGeneratorInputNoiseAmount(noiseAmount);
		setGeneratorHiddenLayers(generatorHidden);
		setGeneratorOutputSize(generatorOutputs);
		setDiscriminatorHiddenLayers(discriminatorHidden);
		setDiscriminatorOutputs(discriminatorOutputs);
	}
	
	public void setGeneratorOutputSize(int i) {
		discriminatorInputLength = i;
	}
	
	public void setGeneratorInputNoiseAmount(int i) {
		inputNoiseSize = i;
	}
	
	public void setDiscriminatorOutputs(int i) {
		discriminatorOutputs = i;
	}
	
	public void setDiscriminatorHiddenLayers(int[] i) {
		discriminatorHidden = i;
	}
	
	public void setGeneratorHiddenLayers(int[] i) {
		generatorHidden = i;
	}
	
	public Network buildDiscriminator() {
		return new Network(discriminatorInputLength, discriminatorHidden, discriminatorOutputs);
	}
	
	public Network buildGenerator() {
		return new Network(inputNoiseSize, generatorHidden, discriminatorInputLength);
	}
	
	
	
}
