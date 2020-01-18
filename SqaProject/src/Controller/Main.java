/**
 * The Main Class that launch the whole Game
 * systemData- instance of the SysData object that contains the current data in the game
 * gc-instance of game Controller object that control the whole game
 * file- we used serialization file to store data, the file that contains all the data
 *  SnakeGameDB.ser
 */

package Controller;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


import Model.SysData;
import View.ViewLogic;
import View.SnakeGame;
/** Main Class of the project **/
public class Main {
	
	/** Variables **/
	public static SysData systemData ;
	public static QuestionsController qc;
	public static String file = "SnakeGameDB.ser";
	public static ViewLogic vl=ViewLogic.getInstance();
	public static UpdateSystemDataController usdc;
       
	public static void main(String[] args) {
		
		ObjectInputStream input = null;
		/**
		 * reading data from the SnakeGameDB.ser file and store it locally 
		 * in sysData(in Model Package) to use it while we launch the Game
		 */
		try {
			input=new ObjectInputStream(new FileInputStream(file));
			systemData=(SysData)input.readObject();
		} catch(Exception e) {
			systemData=SysData.getInstance();
		}
		
		if(systemData == null) {
			systemData=SysData.getInstance();
		}
		qc=QuestionsController.getInstance();
		usdc=UpdateSystemDataController.getInstance();
		
		/****launch the Main Screen {GUI}****/
		vl.LaunchGUI(file);

	}
}
