package me.hii488.network.nodeActivations;

public interface Activation {
	
	public double function(double d);
	public double functionPrime(double d);
	
	public default String getName() {
		return "";
	}
	
}
