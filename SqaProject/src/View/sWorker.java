package View;

import javax.swing.SwingWorker;

import Model.Player;

public class sWorker extends SwingWorker {
	String Player;
	public sWorker(String s) {
		Player=s;
		
	}

	@Override
	protected Object doInBackground() throws Exception {
		SnakeGame sg=new SnakeGame(new Player(Player));
		sg.startGame();
		Thread.sleep(1000);
		return null;
	}

}
