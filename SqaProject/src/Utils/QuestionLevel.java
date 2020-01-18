package Utils;


/** This class represents the diffculty levels of the Questions **/
public enum QuestionLevel {
	
	EASY (1, -10, "White"),
	MEDIUM (2, -20, "Yellow"),
	HARD (3, -30, "Red");
	
	private final int addPts;
	private final int reducePts;
	private final String color;
	
	/** Constructor **/
	QuestionLevel(int addPts, int reducePts, String color){
		this.addPts = addPts;
		this.reducePts = reducePts;
		this.color = color;
	}

	/********************* Getters and Setters ****************/
	public int getAddPts() {
		return addPts;
	}

	public int getReducePts() {
		return reducePts;
	}

	public String getColor() {
		return color;
	}
	
}
