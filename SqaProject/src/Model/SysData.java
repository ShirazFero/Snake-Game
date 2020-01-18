package Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Controller.Main;

/**
 * This class handle the reading/saving questions from the JSON file.
 *
 */
@SuppressWarnings("serial")
public class SysData implements java.io.Serializable{
	
	private static SysData instance;
	/**Stores all players*/
	private ArrayList<Player> players;
	/**Stores all Questions*/
	private ArrayList<Question> questions;
	
	private SysData() {
		players=new ArrayList<Player>();
		questions = new ArrayList<Question>();
        questions=loadQuestionFiles();
	}
	
	/**
	 ** The method creates this class's instance & provides access to it, by returning a reference (singleton).
	 ** @return reference to this class's only instance (singleton).
	 **/
	public static SysData getInstance() {
		if(instance == null) {
			instance = new SysData();
			return instance;
		} else {
			return instance;
		}
	}

	/***************************************** Getters & Setters *************************************/
	
	/**
	 ** @return the users.
	 **/
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 ** @return the questions
	 **/
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(ArrayList<Question>  qe) {
		this.questions=qe;
	}
	
	/***************************************** Updating The SYSDATA *****************************************/

/**
 * adding a player    
 * @param p
 * @return
 */
    public boolean addPlayer(Player p) {
    	players.add(p);
    	writeObject(Main.file, this);
    	return true;
    }
    
   /**
    * Adding a new question to the system 
    * @param q
    * @return
    */
    public boolean addQuestion(Question q) {//JSON
    	if(questions.contains(q))
    		return false;
    	else {
    		questions.add(q);
    		saveQuestions(queList());
    		return true;
    	}
    	
    }
    /**
     * this method update exist question
     * @param q
     * @return
     */
    public boolean updateAnExistingQuestion(Question q) {
    	int right_index=-1;
    	for(int i=0;i<questions.size();i++) {
    		Question temp=questions.get(i);
    		if(temp.getText().equals(q.getText()))
    			right_index=i;
    	}
    	if(right_index!=-1) {
    		questions.remove(right_index);
    		questions.add(q);
    		saveQuestions(queList());//JSON
    		return true;
    	}else return false;
    		
 
    	
    }
    /**
     * to remove question from db
     * @param q
     * @return
     */
    public boolean removeQuestion(Question q) {
    	if(questions.contains(q)) {
    		questions.remove(q);
    		saveQuestions(queList());
    		return true;
    	}else return false;
    	
    }
    
	/**Map- stores the questions for each level**/
    public Map<String, List<Question>> queList(){
		Map M = new HashMap<String, List<Question>>();
		ArrayList<Question> A = new ArrayList<Question>();
		ArrayList<Question> B = new ArrayList<Question>();
		ArrayList<Question> C = new ArrayList<Question>();
		for (Question q : questions) {
			if (q.getLevel().equals("1")) {
				A.add(q);
			}
			if (q.getLevel().equals("2")) {
				B.add(q);
			}
			if (q.getLevel().equals("3")) {
				C.add(q);
			}

		}
		M.put("1", A);
		M.put("2", B);
		M.put("3", C);
		saveQuestions(M);
		return M;
    }
    ///~~~~~ Methods for Writing/Reading to JSON File
    
    public ArrayList loadQuestionFiles() {
		this.questions.clear();
		 
		JSONParser p = new JSONParser();
		try {
			File f=new File("Questions.json");
			Object obj = p.parse(new FileReader(f.getAbsolutePath()));
			JSONObject jObject = (JSONObject) obj;
			JSONArray Qs = (JSONArray) jObject.get("questions");
			for (Object Q : Qs) {
				jObject = (JSONObject) Q;
				String Question1 = (String) jObject.get("question");
				JSONArray answers = ((JSONArray) jObject.get("answers"));
				String team = (String) jObject.get("team");
				String CorrAns = (String) jObject.get("correct_ans");
				String Level = (String) jObject.get("level");
				ArrayList<String> Ans = new ArrayList<String>();
				Object[] tempA = answers.toArray();
				for (Object tm : tempA) {
					Ans.add((String) tm);
				}
				Question ques = new Question(Question1, Ans, CorrAns, Level, team);
				this.questions.add(ques);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}
    
	/*This method save the question to JSON file*/
	@SuppressWarnings("unchecked")
	public void saveQuestions(Map<String, List<Question>> questions) {//
		JSONArray JSONquestions = new JSONArray();
		JSONObject toWrite = new JSONObject();
		for (Map.Entry<String, List<Question>> list : questions.entrySet()) {
			if (list.getValue() == null)
				continue;
			for (Question q :list.getValue() ) {//
				JSONObject jo = new JSONObject();
				JSONArray ans = new JSONArray();
				for (String a : q.getOptionsList()) {
				     	ans.add(a);
				}
				jo.put("correct_ans", q.getAnswer());
				jo.put("level", q.getLevel());
				jo.put("team", q.getTeam());
				jo.put("question", q.getText());
				jo.put("answers", ans);
				JSONquestions.add(jo);
				

			}
			toWrite.put("questions", JSONquestions);

		}
		        try (FileWriter file = new FileWriter("Questions.json")) {
		 
        file.write(toWrite.toJSONString());
        file.flush();

    } catch (IOException e) {
        e.printStackTrace();
    }
}  
    
    
    
    ///~~~~~~~~~~~~ Methods for writing/reading ser file

    /**
     * To write to the db 
     * @param fileName
     * @param _database
     */
    public void writeObject(String fileName ,SysData _database) {
    	ObjectOutputStream output=null;
    	try {
    		output= new ObjectOutputStream(new FileOutputStream(fileName));
    		output.writeObject(_database);
    		output.close();
    	} catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }

}
