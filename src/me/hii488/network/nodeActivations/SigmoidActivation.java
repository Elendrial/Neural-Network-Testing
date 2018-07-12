package me.hii488.network.nodeActivations;

public class SigmoidActivation implements Activation {

	@Override
	public double function(double d) {
		return 1/(1 + Math.pow(Math.E, -d));
	}

	@Override
	public double functionPrime(double d) {
		double s = function(d);
		return s * (1-s);
	}
	
	public String getName() {
		return "sigmoid";
	}

}
