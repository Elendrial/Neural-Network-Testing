package me.hii488.tests;

import java.lang.reflect.Field;

import me.hii488.network.Layer;
import me.hii488.network.Network;

public class CloneStructureTest {
	
	public static boolean test() {
		Network n = new Network(2, new int[] {3,2}, 5);
		Network n2 = n.cloneStructure();
		Network n3 = new Network(1, new int[] {3,5,2}, 5);
		boolean asdfn = checkNetworks(n, n2) && !checkNetworks(n, n3);
		return asdfn;
	}
	
	private static boolean checkNetworks(Network n, Network n2) {
		try {
			boolean similar = true;
			Field f = Network.class.getDeclaredField("layers");

			f.setAccessible(true);
			
			Layer[] a = ((Layer[])f.get(n)), b = ((Layer[])f.get(n2));
			
			for(int i = 0; i < a.length && similar; i++) {
				for(int j = 0; j < a[i].nodes.length && similar; j++) {
					if(a[i].nodes[j].weights.length != b[i].nodes[j].weights.length) similar = false;
				}
			}
			
			return similar;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			System.err.println("Can't change access modifiers");
			e.printStackTrace();
			return false;
		}
	}
	
}
