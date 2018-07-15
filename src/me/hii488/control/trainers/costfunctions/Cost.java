package me.hii488.control.trainers.costfunctions;

public interface Cost {
	
	public double cost(double[] actualOutput, double[] expectedOutput);
	public double costPrime(double output, double expectedOutput); // This costPrime is only interested in a specific neuron AFAIK
	
}
