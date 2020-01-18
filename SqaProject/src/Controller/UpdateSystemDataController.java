package Controller;

import Model.Player;
import Model.SysData;
import java.util.ArrayList;



public class UpdateSystemDataController {
	
	private static UpdateSystemDataController update;
    public static SysData systemData;
    
    private UpdateSystemDataController() {
    	systemData = Main.systemData;

    }
    
    public static UpdateSystemDataController getInstance() {
    	if(update == null) {
            update = new UpdateSystemDataController();
        }
        return update;
    }
    
    public ArrayList<Player> getAllPlayers() {

    	return Main.systemData.getPlayers();
    }
	/**
	 * this method is called from ViewLogic after a Game is finished
	 */
	public void FinishedGame(Player p) {
		// after the game is finished we will save the player in sysData
		Main.systemData.addPlayer(p);
		
		
	}
}
