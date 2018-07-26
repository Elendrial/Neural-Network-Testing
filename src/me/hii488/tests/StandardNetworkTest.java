package me.hii488.tests;

import me.hii488.control.data.DataController;
import me.hii488.control.trainers.StandardTrainer;
import me.hii488.control.trainers.Trainer;
import me.hii488.control.trainers.costfunctions.QuadraticCost;
import me.hii488.network.Network;
import me.hii488.network.nodeActivations.SigmoidActivation;

public class StandardNetworkTest {
	
	public static boolean simpleTest() {
		Network net = new Network(10, new int[] {16, 8}, 1, new SigmoidActivation());
		net.randomise();
		
		DataController data = new DataController() {
			int inputs = 10;
			double expectedOut;
			
			@Override
			public double[] getNextTrainingInput() {
				double[] d = new double[inputs];
				for(int i = 0; i < inputs; i++) {
					d[i] = Network.random.nextInt(1);
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
		return doTheTests(t);
	}
	
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
					d[i] = Network.random.nextInt(1);
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
		t.train(1000, 30);
		System.out.println(t.test(1000));
		t.train(1000, 30);
		System.out.println(t.test(1000));
		
		return t.test(60) > 0.9;
	}
	
}
