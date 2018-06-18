package me.hii488.tests;

import java.lang.reflect.Field;
import java.util.Arrays;

import me.hii488.network.Layer;
import me.hii488.network.Network;
import me.hii488.network.specialisations.GeneratorNetwork;

public class SaveLoadTest {
	
		Network n = new Network(3, new int[] {}, 1);
	public static boolean roughTest() {
		double[] input = {0.5, 0.5, 0.5};
		
		n.randomise();
		double[] output = n.getOutput(input);
		
		n.saveNetwork("d:/temp/saveloadTest.net");
		
		n = null;
		System.gc();
		
		Network n2 = Network.loadNetwork("d:/temp/saveloadTest.net");
		return Arrays.equals(n2.getOutput(input), output);
	}
	
	// I could have just changed 'layers' and 'nodes' to be public, buuuut I didn't want to
	public static boolean exactTest() {
		Network n = new Network(3, new int[] {}, 1);
		
		n.randomise();
		n.saveNetwork("d:/temp/saveloadTest.net");
		
		Network n2 = Network.loadNetwork("d:/temp/saveloadTest.net");
		
		try {
			boolean different = false;
			Field f = Network.class.getDeclaredField("layers");

			f.setAccessible(true);
			
			Layer[] a = ((Layer[])f.get(n)), b = ((Layer[])f.get(n2));
			
			for(int i = 0; i < a.length && !different; i++) {
				for(int j = 0; j < a[i].nodes.length && !different; j++) {
					if(!Arrays.equals(a[i].nodes[j].weights, b[i].nodes[j].weights)) different = true;
				}
			}
			
			return !different;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println("Can't change access modifiers");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean specialisedNetworkTest() {
		GeneratorNetwork n = new GeneratorNetwork(3, new int[] {16*16, 16*16,8*8,4*4, 2*2}, 1);
		double[] input = {0.5, 0.5, 0.5};
		
		n.randomise();
		double[] output = n.getOutput(input);
		
		n.saveNetwork("d:/temp/saveloadTest.net");
		
		n = null;
		System.gc();
		
		Network n2 = Network.loadNetwork("d:/temp/saveloadTest.net");
		return Arrays.equals(n2.getOutput(input), output) && (n2 instanceof GeneratorNetwork);
	}
	
}
