package me.hii488.tests;

import java.util.Arrays;

import me.hii488.network.Network;

public class SaveLoadTest {
	
	public static boolean test() {
		Network n = new Network(3, new int[] {}, 1);
		double[] input = {0.5, 0.5, 0.5};
		
		n.randomise();
		double[] output = n.getOutput(input);
		
		n.saveNetwork("d:/temp/saveloadTest.net");
		
		n = null;
		System.gc();
		
		Network n2 = Network.loadNetwork("d:/temp/saveloadTest.net");
		return Arrays.equals(n2.getOutput(input), output);
	}
	
}
