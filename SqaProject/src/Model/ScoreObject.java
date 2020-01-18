package Model;

public class ScoreObject {
	
	/** How much parts this object adds to the snake length **/
	private int partsToAdd;
	
	/** How much points this object adds to the player **/
	private int points;

	/** How many seconds to wait before showing another object on the screen**/
	private int secondsDelay;
	
	/********************* Getters and Setters ****************/
	public int getPartsToAdd() {
		return partsToAdd;
	}

	public void setPartsToAdd(int partsToAdd) {
		this.partsToAdd = partsToAdd;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getSecondsDelay() {
		return secondsDelay;
	}

	public void setSecondsDelay(int secondsDelay) {
		this.secondsDelay = secondsDelay;
	}
	
	/********************* HashCode and Equals ****************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + partsToAdd;
		result = prime * result + points;
		result = prime * result + secondsDelay;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreObject other = (ScoreObject) obj;
		if (partsToAdd != other.partsToAdd)
			return false;
		if (points != other.points)
			return false;
		if (secondsDelay != other.secondsDelay)
			return false;
		return true;
	}
}
