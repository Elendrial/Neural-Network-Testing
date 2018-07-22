package me.hii488.control.trainers;

import me.hii488.control.data.DataController;
import me.hii488.control.trainers.costfunctions.Cost;

public abstract class Trainer {
	
	public abstract void train(int iterations, int batchSize);
	public abstract double test(int number);

	public double learningRate;
	public DataController data;
	public Cost cost;
	
	public void setLearningRate(double lr) {
		learningRate = lr;
	}
	
	public double getLearningRate() {
		return learningRate;
	}

	public void setDataController(DataController c) {
		data = c;
	}

	public DataController getDataController() {
		return data;
	}
	
	public void setCostFunction(Cost c) {
		cost = c;
	}
	
	public Cost getCostFunction() {
		return cost;
	}
	
}
