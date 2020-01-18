package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.Main;
import Model.Player;
import Model.Question;
import Model.SysData;


/**
 * The {@code SnakeGame} class is responsible for handling much of the game's logic.
 *
 */
public class SnakeGame extends JFrame{//JFrame
		
	/**
	 * The Serial Version UID.
	 */
	private static final long serialVersionUID = 6678292058307426314L;

	/**
	 * The number of milliseconds that should pass between each frame.
	 */
	private static final long FRAME_TIME = 1000L / 50L;
	
	/**
	 * The minimum length of the snake. This allows the snake to grow
	 * right when the game starts, so that we're not just a head moving
	 * around on the board.
	 */
	private static final int MIN_SNAKE_LENGTH = 2;//5
	
	/**
	 * The maximum number of directions that we can have polled in the
	 * direction list.
	 */
	private static final int MAX_DIRECTIONS = 3;
	
	/**
	 * The BoardPanel instance.
	 */
	private BoardPanel board;
	
	/**
	 * The SidePanel instance.
	 */
	private SidePanel side;
	
	/**
	 * The random number generator (used for spawning fruits).
	 */
	private Random random;
	
	/**
	 * The Clock instance for handling the game logic.
	 */
	private Clock logicTimer;
	
	/**
	 * Whether or not we're running a new game.
	 */
	private boolean isNewGame;
		
	/**
	 * Whether or not the game is over.
	 */
	private boolean isGameOver;
	
	/**	
	 * Whether or not the game is paused.
	 */
	private boolean isPaused;
	private boolean isQuestion;
	
	/**
	 * The list that contains the points for the snake.
	 */
	private LinkedList<Point> snake;
	
	/**
	 * The list that contains the queued directions.
	 */
	private LinkedList<Direction> directions;
	
	/**
	 * The current score.
	 */
	private int score;
	
	/**
	 * each player start with a 3 lifes {rat can give one life, while hitting wall/body/traps can get a life}
	 */
	private int lifes;

	/**
	 * Timers
	 */
	private Date Banana_timer;
	private Date Apple_timer;
	private Date Mouse_timer;
	/**
	 * mouse real time place {for moving it}
	 */
	private int mouse_x;
	private int mouse_y;
	private Date mouse_move;
	/**
	 * The number of fruits that we've eaten.
	 */
	private int fruitsEaten;
	
	/**
	 * The number of points that the next fruit will award us.
	 */
	private int nextFruitScore;
	
	 Timer timer;
	 private Player player;
	/**
	 * Creates a new SnakeGame instance. Creates a new window,
	 * and sets up the controller input.
	 */
	public SnakeGame(Player p) {
		super("Snake Remake");
		this.player=p;
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		/*
		 * Initialize the game's panels and add them to the window.
		 */
		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);
	    
		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);
		this.setJMenuBar(createMenuBar());
		

		
		/*
		 *thi Adds a new key listener to the frame to process input. 
		 */
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {

				/*
				 * If the game is not paused, and the game is not over...
				 * 
				 * Ensure that the direction list is not full, and that the most
				 * recent direction is adjacent to North before adding the
				 * direction to the list.
				 */
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.South && last != Direction.North) {
								directions.addLast(Direction.North);
							}
						}
					}
					break;

				/*
				 * If the game is not paused, and the game is not over...
				 * 
				 * Ensure that the direction list is not full, and that the most
				 * recent direction is adjacent to South before adding the
				 * direction to the list.
				 */	
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.North && last != Direction.South) {
								directions.addLast(Direction.South);
							}
						}
					}
					break;
				
				/*
				 * If the game is not paused, and the game is not over...
				 * 
				 * Ensure that the direction list is not full, and that the most
				 * recent direction is adjacent to West before adding the
				 * direction to the list.
				 */						
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.East && last != Direction.West) {
								directions.addLast(Direction.West);
							}
						}
					}
					break;
			
				/*
				 * If the game is not paused, and the game is not over...
				 * 
				 * Ensure that the direction list is not full, and that the most
				 * recent direction is adjacent to East before adding the
				 * direction to the list.
				 */		
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if(!isPaused && !isGameOver) {
						if(directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if(last != Direction.West && last != Direction.East) {
								directions.addLast(Direction.East);
							}
						}
					}
					break;
				
				/*
				 * If the game is not over, toggle the paused flag and update
				 * the logicTimer's pause flag accordingly.
				 */
				case KeyEvent.VK_P:
					if(!isGameOver) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
						
					}
					break;
				
				/*
				 * Reset the game if one is not currently in progress.
				 */
				case KeyEvent.VK_ENTER:
					if(isNewGame || isGameOver) {
						resetGame();
					}
					break;
					
				case KeyEvent.VK_ESCAPE:
		             MainFrame mf = new MainFrame(Main.file);
		             mf.setVisible(true);
		             SnakeGame.this.dispose();
					
				}
			}
			
		});
		
		
		
		/*
		 * Resize the window to the appropriate size, center it on the
		 * screen and display it.
		 */
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	/**
	 * Starts the game running.
	 */
	public void startGame() {
		/*
		 * Initialize everything we're going to be using.
		 */
		this.random = new Random();
		this.snake = new LinkedList<>();
		this.directions = new LinkedList<>();
		this.logicTimer = new Clock(9.0f);
		this.isNewGame = true;
		this.Banana_timer=null;
		this.Apple_timer=null;
		this.Mouse_timer=null;
		
		//Set the timer to paused initially.
		logicTimer.setPaused(true);

		/*
		 * This is the game loop. It will update and render the game and will
		 * continue to run until the game window is closed.
		 */
		while(true) {
			//Get the current frame's start time.
			long start = System.nanoTime();
			
			//Update the logic timer.
			logicTimer.update();
			
			/*
			 * If a cycle has elapsed on the logic timer, then update the game.
			 */
			if(logicTimer.hasElapsedCycle()) {
				updateGame();
			}
			
			//Repaint the board and side panel with the new content.
			board.repaint();
			side.repaint();
			
			/*
			 * Calculate the delta time between since the start of the frame
			 * and sleep for the excess time to cap the frame rate. While not
			 * incredibly accurate, it is sufficient for our purposes.
			 */
			long delta = (System.nanoTime() - start) / 1000000L;
			if(delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Updates the game's logic.
	 */
	private void updateGame() {
		/*
		 * Gets the type of tile that the head of the snake collided with. If 
		 * the snake hit a wall, SnakeBody will be returned, as both conditions
		 * are handled identically.
		 */
		TileType collision = updateSnake();
	
		
		/*
		 * Here we handle the different possible collisions.
		 * 
		 * Fruit: If we collided with a fruit, we increment the number of
		 * fruits that we've eaten, update the score, and spawn a new fruit.
		 * 
		 * SnakeBody: If we collided with our tail (or a wall), we flag that
		 * the game is over and pause the game.
		 * 
		 * If no collision occurred, we simply decrement the number of points
		 * that the next fruit will give us if it's high enough. This adds a
		 * bit of skill to the game as collecting fruits more quickly will
		 * yield a higher score.
		 */
		if(collision == TileType.Fruit) {
			fruitsEaten++;
			score += 10;
			//Initiate a AppleTimer to 0
			//spawnFruit();
			Apple_timer=new Date();
		} else if(collision == TileType.SnakeBody) {//if the snake collision with its body
			if(lifes==1) {
			lifes=0;
			isGameOver = true;
			logicTimer.setPaused(true);
			//The game is finished so we update the player score
			player.setScore(score);
			Main.usdc.FinishedGame(player);
			}
			else {
				lifes--;
				loseLife();

			}
			}
			 else if(collision == TileType.Banana) {
				 fruitsEaten++;
					score += 15;
					//Initiate a BananaTimer to 0
					Banana_timer=new Date();
					//spawnFruit1();
		} else if(collision == TileType.Pear) {
			 fruitsEaten++;
				score += 20;
				spawnpear();

	}else if(collision == TileType.MOUSE) {
		 fruitsEaten++;
			score += 30;
			if(lifes!=3)//eating a mouse bring you one life
			  lifes++;
			//after one minute display another
			//Initiate a RatTimer to 0
			Mouse_timer=new Date();
}
	else if(collision == TileType.Easy_Ques) {
		 questionView("1");
		isQuestion=true;
		spawnEasy_Ques();
		
}
	else if(collision == TileType.Medium_Ques) {
		 questionView("2");
		isQuestion=true;
		spawnMedium_Ques();
		
}
	else if(collision == TileType.Hard_Ques) {
		 questionView("3");
		isQuestion=true;
		spawnHard_Ques();
		
}
			 else if(nextFruitScore > 10) {
			nextFruitScore--;
		}
		/**
		 * moving the mouse
		 */
		if(Mouse_timer==null) {// mouse timer till us that there is a mouse in the board 
			// in case the mouse in the game 
			Date right_now=new Date();
			if(((right_now.getTime()-mouse_move.getTime())/1000)>=5) {// every 5 sec move the mouse
			board.setTile(mouse_x, mouse_y, null);
			spawnmouse();
			mouse_move=new Date();}
		}
		/**
		 * check timers
		 */
		Date right_now=new Date();
		if(Banana_timer!=null && ((right_now.getTime()-Banana_timer.getTime())/1000)>=10) {
			spawnFruit1();
			Banana_timer=null;
		}
		if(Apple_timer!=null && ((right_now.getTime()-Apple_timer.getTime())/1000)>=5) {
			spawnFruit();
			Apple_timer=null;
		}
		if(Mouse_timer!=null && ((right_now.getTime()-Mouse_timer.getTime())/1000)>=60) {
			spawnmouse();
			Mouse_timer=null;
			mouse_move=new Date();
		}
	}
	
/**
 * this method display it to the user a question according to the level 
 * @param level
 */
	public void questionView(String level) {
		Question q = ViewLogic.getInstance().popRandomQuestion(level);
		if(!isGameOver) {
			isPaused = !isPaused;
			logicTimer.setPaused(isPaused);}
		decorateQuestion(this,q);

		
	
	}
	/**
	 * This method display a question to the user 
	 * @param jf
	 */
	public void decorateQuestion(SnakeGame jf,Question q) {
		JPanel pan=new JPanel();
		CustomDialog jd=new CustomDialog(jf);
		pan.setOpaque(true);
		pan.setBackground(Color.WHITE);
		pan.setLayout(null);
		int reducePoint=q.getQl().getReducePts();
		int winPoints=q.getQl().getAddPts();
		
		String raw = "<html><head></head><body><div>" +
				""+"<h><FONT style=\"font-weight: bold; font-size: 10px;\"> "+q.getText()+"</FONT></h>" +
				"<br>" + "</div></body></html>";
		JLabel questionTitle = new JLabel(raw);
		questionTitle.setSize(500, 100);
        questionTitle.setLocation(0, 0);
        //question.setFont(new Font("Serif", Font.PLAIN, 14));
        //The answers will be in group of radio buttons
        ButtonGroup AnswersGroup =new ButtonGroup();
        JRadioButton ans1 =new JRadioButton(q.getOptionsList().get(0));
        ans1.setBounds(30, 70, 500, 20);
        ans1.setBackground(Color.WHITE);
        JRadioButton ans2 =new JRadioButton(q.getOptionsList().get(1));
        ans2.setBounds(30, 120, 500, 20);
        ans2.setBackground(Color.WHITE);
        JRadioButton ans3 =new JRadioButton(q.getOptionsList().get(2));
        ans3.setBounds(30, 170, 500, 20);
        ans3.setBackground(Color.WHITE);
        JRadioButton ans4 =new JRadioButton(q.getOptionsList().get(3));
        ans4.setBounds(30, 220, 500, 20);
        ans4.setBackground(Color.WHITE);
        AnswersGroup.add(ans1);
        AnswersGroup.add(ans2);
        AnswersGroup.add(ans3);
        AnswersGroup.add(ans4);
        AnswersGroup.clearSelection();
        pan.add(ans1);
        pan.add(ans2);
        pan.add(ans3);
        pan.add(ans4); 
        pan.add(questionTitle); 

        
        Font checkBoxFont = new Font("Arial",Font.BOLD,18);
        //BTN 
        JButton correctBTN = new JButton("Enter Answer");
        correctBTN.setFont(checkBoxFont);
        correctBTN.setSize(200,60);
        correctBTN.setLocation(150, 250);
        correctBTN.addActionListener(new ActionListener() {   
            public void actionPerformed(ActionEvent e) 
            {   
                String status = ""; 
  
                if (ans1.isSelected()) { 
                	if(q.getAnswer().equals("1"))
                		status="You Are right";
  
                	else status = "You'r answer is wrong"; 
                } 
  
                else if (ans2.isSelected()) { 
  
                	if(q.getAnswer().equals("2"))
                		status="You Are right";
  
                	else status = "You'r answer is wrong";
                	} 
                else if(ans3.isSelected()) { 
                	if(q.getAnswer().equals("3"))
                		status="You Are right";
  
                	else status = "You'r answer is wrong"; 
                }else if(ans4.isSelected()) {
                	if(q.getAnswer().equals("4"))
                		status="You Are right";
  
                	else status = "You'r answer is wrong"; 
                	
                }else status="Must Answer the question";
                if(status.equals("You Are right")) {
                	//We will add to his score
                	score+=winPoints;
                }else if(status.equals("You'r answer is wrong")) {
                	//We will reduce from his score 
                	if(score+reducePoint>=0)
                	  score+=reducePoint;
                	else score =0;
                }
  
                JOptionPane.showMessageDialog(SnakeGame.this,status); 
                if(status.equals("You'r answer is wrong") || status.equals("You Are right"))
                     jd.dispose();
            } 
        });
        pan.add(correctBTN); 

   
        
   
        jd.cretaUI2(pan,500,400); //350 250  

	}
	
/**
 * if the player lose a life it reset the board and the snake but not his score	
 */
 private void loseLife() {
	 /**
	 * Create the head at the center of the board.
	 */
	Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

	/*
	 * Clear the snake list and add the head.
	 */
	snake.clear();
	snake.add(head);
	
	/*
	 * Clear the board and add the head.
	 */
	board.clearBoard();
	board.setTile(head, TileType.SnakeHead);
	
	/*
	 * Clear the directions and add north as the
	 * default direction.
	 */
	directions.clear();
	directions.add(Direction.North);
	
	/*
	 * Reset the logic timer & object timers.
	 */
	logicTimer.reset();
	this.Apple_timer=null;
	this.Banana_timer=null;
	this.Mouse_timer=null;	

	/*
	 * Spawn a new fruit.
	 */
	spawnFruit();//Apple
	spawnFruit1();//banana
	spawnpear();//pear
	spawnmouse();//rat
	mouse_move=new Date();
	/**
	 * Three questions should display at the beginning of the game
	 */
	spawnEasy_Ques();
	spawnMedium_Ques();
	spawnHard_Ques();
 }
	
	
	
	
	/**
	 * Updates the snake's position and size.
	 * @return Tile tile that the head moved into.
	 */
	private TileType updateSnake() {

		/*
		 * Here we peek at the next direction rather than polling it. While
		 * not game breaking, polling the direction here causes a small bug
		 * where the snake's direction will change after a game over (though
		 * it will not move).
		 */
		Direction direction = directions.peekFirst();
				
		/*
		 * Here we calculate the new point that the snake's head will be at
		 * after the update.
		 */		
		Point head = new Point(snake.peekFirst());
		switch(direction) {
		case North:
			head.y--;
			break;
			
		case South:
			head.y++;
			break;
			
		case West:
			head.x--;
			break;
			
		case East:
			head.x++;
			break;
		}
		
		/*
		 * If the snake has moved out of bounds ('hit' a wall), we can just
		 * return that it's collided with itself, as both cases are handled
		 * identically.
		 */
		if(head.x < 0 || head.x >= BoardPanel.COL_COUNT || head.y < 0 || head.y >= BoardPanel.ROW_COUNT) {
			return TileType.SnakeBody; //Pretend we collided with our body.
		}
		
		/*
		 * Here we get the tile that was located at the new head position and
		 * remove the tail from of the snake and the board if the snake is
		 * long enough, and the tile it moved onto is not a fruit.
		 * 
		 * If the tail was removed, we need to retrieve the old tile again
		 * incase the tile we hit was the tail piece that was just removed
		 * to prevent a false game over.
		 */
		TileType old = board.getTile(head.x, head.y);
		if(old != TileType.Fruit &&old != TileType.Banana && old != TileType.Pear&&old != TileType.MOUSE && snake.size() > MIN_SNAKE_LENGTH) {
			Point tail = snake.removeLast();
			board.setTile(tail, null);
			old = board.getTile(head.x, head.y);
		}
		
		/*
		 * Update the snake's position on the board if we didn't collide with
		 * our tail:
		 * 
		 * 1. Set the old head position to a body tile.
		 * 2. Add the new head to the snake.
		 * 3. Set the new head position to a head tile.
		 * 
		 * If more than one direction is in the queue, poll it to read new
		 * input.
		 */
		if(old != TileType.SnakeBody) {
			int m=1;
			int i;
			if(old!=null&&old.equals(TileType.MOUSE))
				m=2;
			for(i=0;i<m;i++) {
			board.setTile(snake.peekFirst(), TileType.SnakeBody);
			snake.push(head);
			board.setTile(head, TileType.SnakeHead);}
			if(directions.size() > 1) {
				directions.poll();
			}
		}
				
		return old;
	}
	
	/**
	 * Resets the game's variables to their default states and starts a new game.
	 */
	private void resetGame() {
		/*
		 * Reset the score statistics. (Note that nextFruitPoints is reset in
		 * the spawnFruit function later on).
		 */
		this.score = 0;
		this.fruitsEaten = 0;
		this.lifes=3;
		
		/*
		 * Reset both the new game and game over flags.
		 */
		this.isNewGame = false;
		this.isGameOver = false;
		this.Apple_timer=null;
		this.Banana_timer=null;
		this.Mouse_timer=null;
		
		/*
		 * Create the head at the center of the board.
		 */
		Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

		/*
		 * Clear the snake list and add the head.
		 */
		snake.clear();
		snake.add(head);
		
		/*
		 * Clear the board and add the head.
		 */
		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);
		
		/*
		 * Clear the directions and add north as the
		 * default direction.
		 */
		directions.clear();
		directions.add(Direction.North);
		
		/*
		 * Reset the logic timer.
		 */
		logicTimer.reset();
		
		/*
		 * Spawn a new fruit.
		 */
		spawnFruit();//Apple
		spawnFruit1();//banana
		spawnpear();//pear
		spawnmouse();//rat
		mouse_move=new Date();
		/**
		 * Three questions should display at the beginning of the game
		 */
		spawnEasy_Ques();
		spawnMedium_Ques();
		spawnHard_Ques();
	}
	
	/**
	 * Gets the flag that indicates whether or not we're playing a new game.
	 * @return The new game flag.
	 */
	public boolean isNewGame() {
		return isNewGame;
	}
	
	/**
	 * Gets the flag that indicates whether or not the game is over.
	 * @return The game over flag.
	 */
	public boolean isGameOver() {
		return isGameOver;
	}
	
	/**
	 * Gets the flag that indicates whether or not the game is paused.
	 * @return The paused flag.
	 */
	public boolean isPaused() {
		return isPaused;
	}
	
	
	public int getLifes() {
		return lifes;
	}


	/**
	 * Spawns a new Apple fruit onto the board.
	 */
	private void spawnFruit() {
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;

		/*
		 * Get a random index based on the number of free spaces left on the board.
		 */
		int index = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
/*
		 * While we could just as easily choose a random index on the board
		 * and check it if it's free until we find an empty one, that method
		 * tends to hang if the snake becomes very large.
		 * 
		 * This method simply loops through until it finds the nth free index
		 * and selects uses that. This means that the game will be able to
		 * locate an index at a relatively constant rate regardless of the
		 * size of the snake.
		 */
		int freeFound = -1;
		for(int x = 0; x < BoardPanel.COL_COUNT; x++) {
			for(int y = 0; y < BoardPanel.ROW_COUNT; y++) {
				TileType type = board.getTile(x, y);
				if(type == null || type == TileType.Fruit ) {
					if(++freeFound == index) {
						board.setTile(x, y, TileType.Fruit);
						break;
					}
				}
			}
		}
	
}
	
/**
 * Spawns a new banana fruit onto the board.	
 */
	
	private void spawnFruit1() {
		int freeFound1 = -1;
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;
	int index1 = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
	for(int x1 = 0; x1 < BoardPanel.COL_COUNT; x1++) {
		for(int y1 = 0; y1 < BoardPanel.ROW_COUNT; y1++) {
			TileType type = board.getTile(x1, y1);
			if(type == null || type == TileType.Banana) {
				if(++freeFound1 == index1) {
					board.setTile(x1, y1, TileType.Banana);
					break;
				}
			}
		}
	}
	}
	private void spawnpear() {
		int freeFound1 = -1;
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;
	int index1 = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
	for(int x1 = 0; x1 < BoardPanel.COL_COUNT; x1++) {
		for(int y1 = 0; y1 < BoardPanel.ROW_COUNT; y1++) {
			TileType type = board.getTile(x1, y1);
			if(type == null || type == TileType.Pear) {
				if(++freeFound1 == index1) {
					board.setTile(x1, y1, TileType.Pear);
					break;
				}
			}
		}
	}
	}
	
	/**
	 * Spawns a new rat onto the board.
	 */
	private void spawnmouse() {
		int freeFound1 = -1;
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;
	int index1 = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
	for(int x1 = 0; x1 < BoardPanel.COL_COUNT; x1++) {
		for(int y1 = 0; y1 < BoardPanel.ROW_COUNT; y1++) {
			TileType type = board.getTile(x1, y1);
			if(x1!=mouse_x && y1!=mouse_y) {
			if(type == null || type == TileType.MOUSE) {
				if(++freeFound1 == index1) {
					board.setTile(x1, y1, TileType.MOUSE);
					mouse_x=x1;
					mouse_y=y1;
					break;
				}
			}
		}
		}
	}
	}
	
	/**
	 * Spawns a new easy question onto the board.
	 */
	private void spawnEasy_Ques() {
		int freeFound1 = -1;
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;
	int index1 = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
	for(int x1 = 0; x1 < BoardPanel.COL_COUNT; x1++) {
		for(int y1 = 0; y1 < BoardPanel.ROW_COUNT; y1++) {
			TileType type = board.getTile(x1, y1);
			if(type == null || type == TileType.MOUSE) {
				if(++freeFound1 == index1) {
					board.setTile(x1, y1, TileType.Easy_Ques);
					break;
				}
			}
		}
	}
	}
	
	/**
	 * Spawns a new medium question onto the board.
	 */
	
	private void spawnMedium_Ques() {
		int freeFound1 = -1;
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;
	int index1 = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
	for(int x1 = 0; x1 < BoardPanel.COL_COUNT; x1++) {
		for(int y1 = 0; y1 < BoardPanel.ROW_COUNT; y1++) {
			TileType type = board.getTile(x1, y1);
			if(type == null || type == TileType.MOUSE) {
				if(++freeFound1 == index1) {
					board.setTile(x1, y1, TileType.Medium_Ques);
					break;
				}
			}
		}
	}
	}
	
	/**
	 * Spawns a new hard question onto the board.
	 */
	private void spawnHard_Ques() {
		int freeFound1 = -1;
		//Reset the score for this fruit to 100.
		this.nextFruitScore = 100;
	int index1 = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());
	for(int x1 = 0; x1 < BoardPanel.COL_COUNT; x1++) {
		for(int y1 = 0; y1 < BoardPanel.ROW_COUNT; y1++) {
			TileType type = board.getTile(x1, y1);
			if(type == null || type == TileType.MOUSE) {
				if(++freeFound1 == index1) {
					board.setTile(x1, y1, TileType.Hard_Ques);
					break;
				}
			}
		}
	}
	}
	
//    public static void function(String[] args) {
//    	
//        SwingUtilities.invokeLater(() -> {
//            JFrame f = new JFrame();
//            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            f.setTitle("Snake");
//            f.setResizable(false);
////            SnakeGame sk = new SnakeGame();
////            sk.startGame();
//           f.add(new SnakeGame(), BorderLayout.CENTER);
//            f.pack();
//            f.setLocationRelativeTo(null);
//            f.setVisible(true);
//        });
//    }
	/**
	 * Gets the current score.
	 * @return The score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Gets the number of fruits eaten.
	 * @return The fruits eaten.
	 */
	public int getFruitsEaten() {
		return fruitsEaten;
	}
	
	/**
	 * Gets the next fruit score.
	 * @return The next fruit score.
	 */
	public int getNextFruitScore() {
		return nextFruitScore;
	}
	
	/**
	 * Gets the current direction of the snake.
	 * @return The current direction.
	 */
	public Direction getDirection() {
		return directions.peek();
	}
	
	public String getPlayerName() {
		return this.player.getName();
	}
	
	
	///
	JMenuBar createMenuBar() {
	       JMenuBar menuBar = new JMenuBar();
	          JMenu menu = new JMenu("HOME");
	       JMenuItem menuItem = new JMenuItem("HOME");
	       menuItem.setMnemonic(KeyEvent.VK_N);
	       menuItem.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	             MainFrame mf = new MainFrame(Main.file);
	             mf.setVisible(true);
	             SnakeGame.this.dispose();
	           }
	       }); 
	       menu.add(menuItem);
	       menuBar.add(menu);
	       return menuBar;
	   }



}
