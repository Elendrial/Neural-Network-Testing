package me.hii488.control.data;

public interface DataController {
	
	public double[] getNextTrainingInput();
	public double[] getNextTestingInput();
	public double[] getCurrentExpectedOutput();
	public default double[] getCurrentTrainingInput() {return null;};
	public void setInputSize(int i);
	
}
