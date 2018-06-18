package me.hii488.network.specialisations;

import me.hii488.network.Network;

public class GeneratorNetwork extends Network{
	
	private static final long serialVersionUID = -7776515361074256054L;

	public GeneratorNetwork(int inputs, int[] hiddenLayers, int outputs) {
		super(inputs, hiddenLayers, outputs);
	}
	
	public double[] generateOutput() {
		double[] noise = new double[super.getInputNumber()];
		
		for(int i = 0; i < noise.length; i++) {
			noise[i] = random.nextDouble();
		}
		
		return getOutput(noise);
	}
	
	public int getInputNumber() {
		return 0;
	}
	
}
