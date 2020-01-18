
package Controller;

import Model.Question;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionsController {
    
    @SuppressWarnings("unused")
    /** Variables **/
    private static QuestionsController qcData;
    private ArrayList<Question> questions;
    public HashMap<String,Question> questionsInHashMap; 
    private HashMap<String,Question> hardQuestions;
    private HashMap<String,Question> mediumQuestion;
    private HashMap<String,Question> easyQuestions;
  
    /** Constructor **/
    private QuestionsController() {
    	questions = Main.systemData.getQuestions();
        hardQuestions = new HashMap<String,Question>();
        mediumQuestion = new HashMap<String,Question>();
        easyQuestions = new HashMap<String,Question>();
        questionsInHashMap = new HashMap<String,Question>();
        
        for(Question q : questions) {
        	questionsInHashMap.put(q.getText(), q);
        }

        classifyQuestions();
    }
    
    public static QuestionsController getInstance() {
    	if(qcData == null) {
            qcData = new QuestionsController();
        }
        
        return qcData;
    }
 /**
  * this method classify the questions to level categories   
  */
    private void classifyQuestions() {
    	
    	hardQuestions=new HashMap<String,Question>();
        mediumQuestion=new HashMap<String,Question>();
        easyQuestions=new HashMap<String,Question>();
        
        if(questions != null) {
           for(Question e :questions) {
               if(e.getLevel().equals("1"))
            	   easyQuestions.put(e.getText(), e);
               else if(e.getLevel().equals("2"))
            	   mediumQuestion.put(e.getText(), e); 
               else hardQuestions.put(e.getText(), e); 
           }  
        }
    }

	
	/**
	 * This method edits/updates an existing question
	 * @param q
	 * @return true if question was edited
	 */
	public boolean updateAnExistingQuestion(Question questionToUpdate ) {
		boolean result=Main.systemData.updateAnExistingQuestion(questionToUpdate);
		if(result) {
			questions=Main.systemData.getQuestions();
			classifyQuestions();
			return true;
		}
		return false;
	} 
        	
      
    /**
     * This method removes a question and returns true for success, false otherwise.
     * @param q
     * @return
     */
	public boolean removeQuestion(Question q) {
		boolean result= Main.systemData.removeQuestion(q);
		if(result) {
			questions=Main.systemData.getQuestions();
			classifyQuestions();
		}
		return result;

	}
	
/**
 * sending the data to sys data to add it 	
 * @param q
 * @return
 */
	public boolean addQuesitonToDB(Question q) {
		boolean result= Main.systemData.addQuestion(q);
		if(result) {
			questions=Main.systemData.getQuestions();
			classifyQuestions();

		}
		return result;
	} 
	/**
	 * return the whole question with this id
	 * @param id
	 * @return
	 */
	public Question getSpecificQuestion(String id) {
		Question right_quest=null;
		for(Question temp: questions) {
			if(temp.getText().equals(id))
				return temp;
		}
		return right_quest;
		
	}
	/**
	 * return all the id for the questions
	 * @return
	 */
    public ArrayList<String> getAllQuestions() {
    	ArrayList<String> questionsIDS = new ArrayList<String>();
    	
    	for(Question q : questions) {
    		questionsIDS.add(q.getText());
    	}
    	return questionsIDS;
    }
	
    /** get the Question level **/
	public HashMap<String,Question> getEasyQuestions() {
		return easyQuestions;
	}
	
	public HashMap<String,Question> getMedQuestions() {
		return mediumQuestion;
	}
	
	public HashMap<String,Question> getHardQuestions() {
		return hardQuestions;
	}

}
