package Model;

import java.io.Serializable;
import java.util.ArrayList;

import Utils.QuestionLevel;


/**
 * This class represents a question in the game.
 */
public class Question implements Serializable {
	
	/**Serializable serial*/
	private static final long serialVersionUID = 1L;
	
	/*============================================ Attributes & Constructors ============================================*/

	
	/**the Question's text description*/
	private String text;
	
	/**the Question's level:  0=easy 1=medium 3=hard*/
	private String level;

	
	/**the Question's options*/
	private ArrayList<String> options;//4 options
	
	/**the Question's answers*/
	private String right_Answer;//{between 1 to 4}
	private String team;
	private QuestionLevel ql;
	
	/**
	 * Full Constructor
	 * 
	 * @param text
	 * @param level
	 * @param options
	 * @param right_Answer
	 * @param level2
	 * @param team
	 */
	
	public Question(String text, ArrayList<String> options, String right_Answer, String level,
			String team) {
		super();
		this.text = text;
		this.level = level;
		setQl(level);
		this.options = options;
		this.right_Answer = right_Answer;
		this.team = team;
	}

	/**
	 * partial constructor
	 * @param id
	 */
	public Question(String text) {
		this.text = text;
		this.options = new ArrayList<String>();
	}
	
	/*============================================ Getters & Setters ============================================*/


public void setQl(String level) {
	if(level.equals("1"))
		ql=QuestionLevel.EASY;
	else if(level.equals("2"))
		ql=QuestionLevel.MEDIUM;
	else ql=QuestionLevel.HARD;
}
public QuestionLevel getQl() {
	return ql;
}
	/**
	 * 1 = easy, 2 = medium, 3 = hard
	 * @return Question's level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * set Question's level
	 * @param level
	 */
	public void setLevel(String level) {
		this.level=level;
	}

	/**
	 * @return Question's text description
	 */
	public String getText() {
		return text;
	}

	/**
	 * set Question's text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return Question's options array list
	 */
	public ArrayList<String> getOptionsList() {
		return options;
	}

	/**
	 * @return Question's answersIndex array list
	 */
	public String  getAnswer() {
		return right_Answer;
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	/*============================================ Class ~ Methods ============================================*/
	


	/**
	 * @return Question's number of options
	 */
	public int getNumOfOptions() {
		return options.size();
	}


	/**
	 * add new option to the Question's 'options' array list.
	 * IF the option is not null and
	 * IF option not already exist
	 * @param option
	 * @return true or false
	 */
	public boolean addOption(String option){
		if(option!=null){
			if(!options.contains(option)){
				options.add(option);
				return true;
			}
		}
		// option is null OR option already exist
		return false;
	}


	/**
	 * remove an option from the 'options' array list by given index
	 * IF the given index is in range
	 * @param optionIndex
	 * @return true or false
	 */
	public boolean removeOptionByIndex(int optionIndex){
		if(optionIndex>=0 && optionIndex<options.size()){
				options.remove(optionIndex);
				return true;
		}
		return false;
	}
	
	/**
	 * remove an option from the 'options' array list by given optionString
	 * IF the optionString is exist in the 'options' array list
	 * @param option
	 * @return true or false
	 */
	public boolean removeOptionByText(String option){
		
		if(options.contains(option)){
			options.remove(option);
			return true;
		}
		return false;

	}
	
	/**
	 * by giving an optionString - return the index in the 'options' array list.
	 * IF the optionString exist >> Otherwise return (-1)
	 * @param optionText
	 * @return index of given optionString
	 */
	public int getOptionIndex(String optionString){
		
		return options.indexOf(optionString);
		
	}


	
	/*============================================ Equals & toString ============================================*/
	@Override
	public String toString() {
		return "Question [text=" + text + ", level=" + level + ", options=" + options + ", right_Answer=" + right_Answer
				+ ", team=" + team + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Question other = (Question) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}
