package Model;


@SuppressWarnings("serial")
public class Player implements java.io.Serializable {

	private int totalScore;

	private String user_name;

	public Player(String userName) {
		
		this.user_name = userName;
		this.totalScore = 0;
	}

	/********** Getters and Setters ********/

	public int getTotalScore() {
		return totalScore;
	}

	public void setScore(int Score) {
		this.totalScore = Score;
	}
	
	public String getName() {
		return user_name;
	}

}
