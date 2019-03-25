package me.hii488.network;

import java.io.Serializable;

import me.hii488.network.nodeActivations.Activation;

public class Layer implements Serializable{
	
	private static final long serialVersionUID = 3563307408734079388L;
	public Node[] nodes;
	
	public Layer(int nodesInLayer, int previousConnections, Activation act) {
		nodes = new Node[nodesInLayer];
		
		for(int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(previousConnections, act);
		}
	}
	
	public Layer(Node[] nodes) {
		this.nodes = nodes;
	}
	
	public double[] getOutput(double[] input) {
		double[] output = new double[nodes.length];
		for(int i = 0; i < output.length; i++) {
			output[i] = nodes[i].getOutput(input);
		}
		
		return output;
	}
	
	public void randomise() {
		for(Node n : nodes) n.randomise();
	}
}
