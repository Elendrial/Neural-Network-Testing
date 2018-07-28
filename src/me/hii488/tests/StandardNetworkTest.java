package me.hii488.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import me.hii488.control.data.DataController;
import me.hii488.control.data.miscTools.OutputToImage;
import me.hii488.control.trainers.StandardTrainer;
import me.hii488.control.trainers.Trainer;
import me.hii488.control.trainers.costfunctions.QuadraticCost;
import me.hii488.network.Network;
import me.hii488.network.nodeActivations.SigmoidActivation;

public class StandardNetworkTest {
	
	// Test if it can check whether there are 5+ inputs=1 or not.
	public static boolean simpleTest() {
		Network net = new Network(10, new int[] {50}, 1, new SigmoidActivation());
		net.randomise();
		
		DataController data = new DataController() {
			int inputs = 10;
			double expectedOut;
			
			@Override
			public double[] getNextTrainingInput() {
				double[] d = new double[inputs];
				for(int i = 0; i < inputs; i++) {
					d[i] = Network.random.nextInt(2);
					expectedOut += d[i];
				}
				
				if(expectedOut > 5) expectedOut = 1;
				else expectedOut = 0;
				
				return d;
			}

			@Override
			public double[] getNextTestingInput() {
				return getNextTrainingInput();
			}

			@Override
			public double[] getCurrentExpectedOutput() {
				return new double[] {expectedOut};
			}

			@Override
			public void setInputSize(int i) {
				inputs = i;
			}
			
		};
		
		Trainer t = new StandardTrainer(net, data, new QuadraticCost(), 1);
		doTheTests(t);
		doLotsOfTests(t);
		return doTheTests(t);
	}
	
	// Test if it can count up number of inputs = 1
	public static boolean simpleTest2() {
		Network net = new Network(10, new int[] {16, 8}, 10, new SigmoidActivation());
		net.randomise();
		
		DataController data = new DataController() {
			int inputs = 10;
			double expectedOut;
			
			@Override
			public double[] getNextTrainingInput() {
				double[] d = new double[inputs];
				for(int i = 0; i < inputs; i++) {
					d[i] = Network.random.nextInt(2);
					expectedOut += d[i];
				}
				
				return d;
			}

			@Override
			public double[] getNextTestingInput() {
				return getNextTrainingInput();
			}

			@Override
			public double[] getCurrentExpectedOutput() {
				double[] d = new double[10]; // there are 10 outputs
				for(int i = 0; i < d.length; i++) {
					if(i==expectedOut) d[i] = 1;
					else d[i] = 0;
				}
				return d;
			}

			@Override
			public void setInputSize(int i) {
				inputs = i;
			}
			
		};
		
		Trainer t = new StandardTrainer(net, data, new QuadraticCost(), 1);
		return doTheTests(t);
	}
	
	// Can it reflect images?
	public static boolean imageTest() {
		Network net = new Network(16, new int[] {}, 64, new SigmoidActivation());
		net.randomise();
		
		
		
		DataController data = new DataController() {
			double[] input = new double[16];
			
			public double[] getCurrentTrainingInput() {
				return input;
			}
			
			@Override
			public double[] getNextTrainingInput() {
				for(int i = 0; i < 16; i++)
					input[i] = Network.random.nextInt(2);
				
				return input;
			}

			@Override
			public double[] getNextTestingInput() {
				return getNextTrainingInput();
			}

			@Override
			public double[] getCurrentExpectedOutput() {
				double[] d = new double[64]; // there are 10 outputs
				
				for(int x = 0; x < 8; x++) {
					for(int y = 0; y < 8; y++) {
						if(x < 4 && y < 4)	     d[x+8*y] = input[x + 4 * y];
						else if(x >= 4 && y < 4) d[x+8*y] = input[4 * y + (7 - x)];
						else if(x < 4 && y >= 4) d[x+8*y] = input[x + 4 * (7-y)];
						else if(x >= 4 && y >= 4)d[x+8*y] = input[4 * (7-y) + 7 - x];
					}
				}
				
				return d;
			}

			@Override
			public void setInputSize(int i) {}
			
		};
		
		
		
		Trainer t = new StandardTrainer(net, data, new QuadraticCost(), 0.5);
		doTheTests(t);
		for(int i = 0; i < 100; i++) doLotsOfTests(t);
		boolean output = doTheTests(t);
		
		try {
		    // retrieve image
			double[] netOut = net.getOutput(data.getNextTestingInput());
		    BufferedImage outputIm = OutputToImage.convertToBlackWhiteImage(net.getLayers()[0].nodes[0].activation, netOut);
		    File outputfile = new File("output.png");
		    ImageIO.write(outputIm, "png", outputfile);
		    
		    BufferedImage inputIm = OutputToImage.convertToBlackWhiteImage(net.getLayers()[0].nodes[0].activation, data.getCurrentTrainingInput());
		    File outputfile2 = new File("input.png");
		    ImageIO.write(inputIm, "png", outputfile2);
		    
		    BufferedImage expectedOut = OutputToImage.convertToBlackWhiteImage(net.getLayers()[0].nodes[0].activation, data.getCurrentExpectedOutput());
		    File expoutputfile = new File("expectedOutput.png");
		    ImageIO.write(expectedOut, "png", expoutputfile);
		    
		    System.out.println(Arrays.toString(data.getCurrentTrainingInput()));
		    System.out.println(Arrays.toString(data.getCurrentExpectedOutput()));
		    System.out.println(Arrays.toString(netOut));
		    
		} catch (IOException e) {
			
		}
		
		return output;
	}
	
	private static boolean doTheTests(Trainer t) {
		System.out.println(t.test(100));
		t.train(100, 30);
		System.out.println(t.test(1000));
		t.train(100, 30);
		System.out.println(t.test(1000));
		t.train(100, 30);
		System.out.println(t.test(1000));
		t.train(100, 30);
		System.out.println(t.test(1000));
	//	t.train(1000, 30);
	//	System.out.println(t.test(1000));
	//	t.train(1000, 30);
	//	System.out.println(t.test(1000));
		
		return t.test(60) > 0.9;
	}
	
	private static void doLotsOfTests(Trainer t) {
		t.train(1000, 50);
		System.out.println(t.test(1000));
		t.train(1000, 50);
		System.out.println(t.test(1000));
		t.train(1000, 50);
		System.out.println(t.test(1000));
		t.train(1000, 50);
		System.out.println(t.test(1000));
	}
	
}
