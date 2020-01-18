/**package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Controller.GameController;
import Controller.Main;
import Controller.QuestionsController;
import Controller.UpdateSystemDataController;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.SysData;

public class junitTesting {

	private static Main main;
	private static GameController GC;
	private static QuestionsController QC;
	private static Game g;
	private static Player p;
	private static Question q;
	private static SysData sys;
	private static UpdateSystemDataController usdc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		main = new Main();
		sys= SysData.getInstance();
		main.systemData=sys;
		usdc=UpdateSystemDataController.getInstance();
		 g = new Game();
		 p = new Player("shada", "123123");
		GC = new GameController(g);
		q= new  Question(200, 0, "Ques1", "Hard");
		q.addOption("Answer1");
		q.addOption("Answer2");
		q.addOption("Answer3");
		q.addOption("Answer4");
		q.addAnswer(1);
		sys.getQuestions().add(q);
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	main = null;
	GC = null;
		
	}

	
	// First test
	@Test
	public void testProgrammerSaveCodeSegmentAddQuestion() {
		//suppose to succeed
		boolean res = GC.addNewQuestion(q);
		assertEquals("testing program to add new Q", true, res);
		//suppose to fail because id is already exist
		boolean res2 = GC.addNewQuestion(q);
		assertEquals("testing program to add existed Q", false, res2);
		
	}
	
	// Second test 
	@Test
	public void NulluserTest() {
		//suppose to fail because this player is null
		boolean res2 = GC.connectNewPlayer(new Player(null, "456456"));
		assertEquals("testing userTest", false, res2);
	} 
	
	// Third test 
	@Test
	public void userTest() {
		//suppose to fail because this player is null
		boolean res2 = GC.connectNewPlayer(new Player("shada", "456456"));
		assertEquals("testing userTest", false, res2);
	} 
	
	//Forth test 
	@Test
	public void testShowQuestionDetails() {
		//suppose to return the same object details
		String  id=String.valueOf(q.getId());
		Question res2 = usdc.getSpecificQuestion(id);
		assertEquals("testing testShowQuestionDetails", q, res2);
	}
	}	
}**/