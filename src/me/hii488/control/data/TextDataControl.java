package me.hii488.control.data;

public class TextDataControl implements DataController{

	private int inputLength;
	private String trainingPath;
	private String testingPath;
	private char startChar;
	
	private int position = 0;
	
	public TextDataControl(String trainingPath, int inputLength) {
		this.trainingPath = trainingPath;
		this.inputLength = inputLength;
	}
	
	public TextDataControl(String trainingPath, String testingPath, int inputLength, char startingChar) {
		this.trainingPath = trainingPath;
		this.testingPath = testingPath;
		this.inputLength = inputLength;
		this.startChar = startingChar;
	}
	
	@Override
	public double[] getNextTrainingInput() {
		//TODO
		return null;
	}

	@Override
	public double[] getNextTestingInput() {
		if(testingPath == null) return getNextTrainingInput();
		return null;
	}
	
	@Override
	public void setInputSize(int i) {
		inputLength = i;
	}
	
	public void changeTrainingPath(String s) {
		trainingPath = s;
	}
	
	public void changeTestingPath(String s) {
		testingPath = s;
	}
	
	public void setStartChar(char c) {
		startChar = c;
	}

}
