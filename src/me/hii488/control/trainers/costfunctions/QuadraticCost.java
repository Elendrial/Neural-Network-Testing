package me.hii488.control.trainers.costfunctions;

public class QuadraticCost implements Cost{
	
	@Override
	public double cost(double[] actualOutput, double[] expectedOutput) {
		double output = 0;
		for(int i = 0; i < actualOutput.length; i++) 
			output += Math.pow(expectedOutput[i]-actualOutput[i], 2);
		
		return output/2;
	}
	
	@Override
	public double costPrime(double output, double expectedOutput) {
		return output - expectedOutput;
	}
	
}
