package me.hii488.network.nodeActivations;

public class TanhActivation implements Activation {

	@Override
	public double function(double d) {
		return Math.tanh(d);
	}

	@Override
	public double functionPrime(double d) {
		return 1/(Math.pow(Math.cosh(d), 2));
	}
	
	public String getName() {
		return "tanh";
	}

}
