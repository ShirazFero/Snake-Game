package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.Main;
import Model.Player;
import Model.Question;

/**
 * This class can communicate with controller package to displayed data to the user
 * in the UI
 *
 */
public class ViewLogic {
	private static ViewLogic instance;
    private final ImageIcon iconRED = new ImageIcon(this.getClass().getResource("/questionIcon.png"));
    ImageIcon successICONN = new ImageIcon(this.getClass().getResource("/images/correct_icon.png"));
    ImageIcon falseICONN = new ImageIcon(this.getClass().getResource("/images/false_icon.png"));
	

	/**
	 ** The method creates this class's instance & provides access to it, by returning a reference (singleton).
	 ** @return reference to this class's only instance (singleton).
	 **/
	public static ViewLogic getInstance() {
		if(instance == null) {
			instance = new ViewLogic();
			return instance;
		} else {
			return instance;
		}
	}
	/**
	 * this method launch the Main ckFrame Screen and display it to the user
	 * @param file
	 */
	public void LaunchGame(String name) {
		
//	    // prompt the user to enter their name

		if(name !=null)
			startAGame(name);


	}
	
	public void LaunchGUI(String file) {
		MainFrame mf=new MainFrame(file);
		mf.setVisible(true);
		
	}
	
	/**
	 * initiate a game for a specific player
	 * @param userName
	 * @return
	 */
	
	public void startAGame(String userName) {
		Player pl=new Player(userName);
		new sWorker(userName).execute();
		

		



	}
	/**
	 * return 10 higher players (if  exist 10) 
	 * @return
	 */
	public ArrayList<Player> scoreBoardPlayers(){
    	ArrayList<Player> playersSorted =new ArrayList<Player>();
    	playersSorted=Main.usdc.getAllPlayers();

    	if(playersSorted.size()>0){
    		Collections.sort(playersSorted, new Comparator<Player>() {
    			@Override public int compare(Player p1, Player p2) {
    				return p2.getTotalScore() - p1.getTotalScore(); // Descending
    			}
    		});
    	}
    	ArrayList<Player> returnResult =new ArrayList<Player>();
    	if(playersSorted.size()<=10)// return all the players{they are less than 10}
    		return playersSorted;
    	else {
    		//return the 10 players who have the highest score
    		for(int i=0;i<=9;i++)
    			returnResult.add(playersSorted.get(i));
    	}
    	
    	return returnResult;
		
	}
	
	//~~ Methods Relates to Questions like adding/removing/editing/get for {AddnewQuestionFrame/Admin Frame}
	public boolean addQuestion(Question q) {
		return Main.qc.addQuesitonToDB(q);
	}
	public boolean removeQuestion(Question q) {
		return Main.qc.removeQuestion(q);
	}
	
	public Question getSpecificQuestion(String Q_id) {
		Question q=null;
		// call this method from controller 
		q=Main.qc.getSpecificQuestion(Q_id);
		return  q;
	}
	public boolean updateAnExistingQuestion(Question q) {
		return Main.qc.updateAnExistingQuestion(q);
				
		
	}
	public ArrayList<String> getAllQuestions(){// to fill combobox in Admin Frame
		return Main.qc.getAllQuestions();
	}
	/**
	 * Get from Question controller all the questions that match this level
	 * @param level
	 * @return
	 */
	public ArrayList<Question> getQuestionsByLevel(String level){
		ArrayList<Question> returnedList=new ArrayList<Question>();
		if(level.equals("1")) {// return easy question
			HashMap<String,Question> qs=Main.qc.getEasyQuestions();
			for(String s:qs.keySet()) {
				returnedList.add(qs.get(s));
			}
			
		}else if(level.equals("2")) {//medium question
			HashMap<String,Question> qs=Main.qc.getMedQuestions();
			for(String s:qs.keySet()) {
				returnedList.add(qs.get(s));
			}
		}else if (level.equals("3")) {//hard question
			HashMap<String,Question> qs=Main.qc.getHardQuestions();
			for(String s:qs.keySet()) {
				returnedList.add(qs.get(s));
			}
		}
		return returnedList;
	}
	public boolean addNewUser(String usernameTT, String passwordTT, String picturePath) {
		return true;//Main.usdc.addNewUser(usernameTT, passwordTT, picturePath);
		
	}
	/**
	 * To decorate the Question on the Admin Frame when the user click the Show Details button
	 * @param q
	 * @param jf
	 */
	public void decorateQuestion(Question q,JFrame jf){
		HashMap<String,JCheckBox> checkBoxes = new HashMap<String,JCheckBox>(); 
		String raw = "<html><head></head><body><div>" +
				""+"<h><FONT style=\"font-weight: bold; font-size: 12px;\"> "+q.getText()+"<br>"+"Level: "+q.getLevel()+"</FONT></h>" +
				"<br>" + "</div></body></html>";
		JPanel pan=new JPanel();
		pan.setOpaque(true);
		pan.setBackground(Color.WHITE);
		pan.setLayout(null);
		JLabel questionTitle = new JLabel(raw);
		questionTitle.setSize(800, 80);
        questionTitle.setLocation(100, 20);
        pan.add(questionTitle); 
        JLabel label = new JLabel(iconRED, JLabel.CENTER);
        label.setSize(64, 64);
        label.setLocation(20, 10);
        pan.add(label);
        
        @SuppressWarnings("unused")
		int width = 0, height = 80;
        Font checkBoxFont = new Font("David",Font.BOLD,16);
        
        for(int i=0;i<q.getOptionsList().size();i++) {
        	String ans=String.valueOf(i+1);
        	height += 40;
        	JCheckBox box = new JCheckBox();
        	JLabel jl = new JLabel(q.getOptionsList().get(i));
        	if(ans.equals(q.getAnswer()))
        		jl.setForeground(Color.GREEN);
        	jl.setSize(800, 30);
        	jl.setFont(checkBoxFont);
        	box.setFont(checkBoxFont);
        	box.setSize(20, 20);
        	box.setLocation(50, height);
        	jl.setLocation(50+30, height-5);
        	pan.add(box);pan.add(jl);
        	checkBoxes.put(jl.getText(), box);
        }
        
        CustomDialog jd=new CustomDialog(jf);
        jd.cretaUI(pan);
    }
	/**
	 * This method used in Snake Game to pop up a question to the user 
	 * level="Hard" OR level="Med" OR level="Easy"
	 * @param level
	 * @return
	 */
	public Question popRandomQuestion(String level) {
		Random rand = new Random();
		ArrayList<Question> r=getQuestionsByLevel(level);
		int rand_index = rand.nextInt(r.size());
		Question q=r.get(rand_index);
		return q;
		
	}
	/**
	 * This method decorate the options in addNewQuestion Frame
	 * @param jf
	 */
	public void decorateOption(AddnewQuestionFrame jf) {
		String[] optionAndStatus = new String[2];
		JPanel pan=new JPanel();
		pan.setOpaque(true);
		pan.setBackground(Color.WHITE);
		pan.setLayout(null);
		
		String raw = "<html><head></head><body><div>" +
				""+"<h><FONT style=\"font-weight: bold; font-size: 16px;\"> "+"Enter new Option"+"</FONT></h>" +
				"<br>" + "</div></body></html>";
		JLabel questionTitle = new JLabel(raw);
		questionTitle.setSize(250, 50);
        questionTitle.setLocation(70, 10);
        JTextField tf;
        tf = new JTextField();
        tf.setSize(250, 50);
        tf.setLocation(40, 60);
        pan.add(tf);
        pan.add(questionTitle); 
   
        Font checkBoxFont = new Font("Arial",Font.BOLD,18);
        JButton correctBTN = new JButton("Correct");
        correctBTN.setFont(checkBoxFont);
        correctBTN.setSize(100,60);
        correctBTN.setLocation(70, 130);
        pan.add(correctBTN); 
   
        JButton incorrectBTN = new JButton("False");
        incorrectBTN.setSize(100,60);
        incorrectBTN.setLocation(190, 130);
        incorrectBTN.setFont(checkBoxFont);
        pan.add(incorrectBTN); 
   
        CustomDialog jd=new CustomDialog(jf);
   
        jd.cretaUI2(pan,350,250);   
        correctBTN.addActionListener( new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if( tf.getText().equals("")) {
        			JOptionPane.showMessageDialog(null, "Please insert an option.");
        		} else if(jf.optionsT.keySet().size()<8) {
        			optionAndStatus[0] = tf.getText();
        			optionAndStatus[1] = "Correct";
        			jf.setDetails(optionAndStatus);
        			jf.updateTextArea();
        			jd.dispose();
        		} else {
        			JOptionPane.showMessageDialog(null, "Reached Maximum Limit of options (8)");
        		}
        	}    
        });
        
        incorrectBTN.addActionListener( new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if( tf.getText().equals("")) {
        			JOptionPane.showMessageDialog(null, "Please insert an option.");
        		} else if(jf.optionsT.keySet().size()<8) {
        			optionAndStatus[0] = tf.getText();
        			optionAndStatus[1] = "False";
        			jf.setDetails(optionAndStatus);
        			jf.updateTextArea();
        			jd.dispose(); 
        		} else {
        			JOptionPane.showMessageDialog(null, "Reached Maximum Limit of options (8)");
        		}
        	}
        });
	}
	
	//~~ Method to add User 



}
