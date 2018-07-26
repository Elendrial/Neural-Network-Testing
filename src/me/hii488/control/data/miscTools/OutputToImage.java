package me.hii488.control.data.miscTools;

import java.awt.image.BufferedImage;

import me.hii488.network.nodeActivations.Activation;

public class OutputToImage {
	
	public static BufferedImage convertToBlackWhiteImage(Activation activationType, double[] output) {
		if((int) Math.sqrt(output.length) != (double) Math.sqrt(output.length)) throw new RuntimeException("Output length must be a square number.");
		BufferedImage i = new BufferedImage((int) Math.sqrt(output.length), (int) Math.sqrt(output.length), BufferedImage.TYPE_INT_RGB);
		
		int d;
		double scale = 256/(activationType.getUpperLimit() - activationType.getLowerLimit());
		for(int y = 0; y < Math.sqrt(output.length); y++) {
			for(int x = 0; x < Math.sqrt(output.length); x++) {
				d = (int) ((output[(int) (x + y * Math.sqrt(output.length))] - activationType.getLowerLimit()) * scale);
				i.setRGB(x, y, (d << 16) + (d << 8) + d);
			}
		}
		
		return i;
	}
	
}
