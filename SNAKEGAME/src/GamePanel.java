import java.awt.Graphics;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;



//This GamePanel class would extends JPanel class and Implements ActionListener interface
public class GamePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Screenwidth and Screenheight for game area
	static final int WIDTH = 500;
	static final int HEIGHT = 500;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (WIDTH*HEIGHT)/UNIT_SIZE;
	static final int DELAY = 150;
	
	//Arrays for holding body parts of snake, we would have two arrays, one for x coordinates and another for y coordinates
	final int x[] = new int[GAME_UNITS];   //Hold all x coordinates including head of snake
	final int y[] = new int[GAME_UNITS];   //Hold y coordinates including head of snake
	
	int bodyparts = 1; //Initial body parts of the snake
	int foodEaten;    //Number of foods eaten by snake
	int foodX;        //X coordinates of food appearing on screen
	int foodY;        //Y coordinate of food apppearing on screen
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		
		random = new Random();
		
		//setPreferredSize is a function of JPanel for setting size of the Panel, its arguement is object of Dimension
		//Dimesnion class constructor takes width and Height as arguement
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.gray);
		this.setFocusable(true);   //It is true by default, so setting it to true does not change anything
		this.addKeyListener(new myKeyAdapter());
		startGame();
		
	}
	
	public void startGame() {
		newFood();
		running = true;
		// This is a swing Timer instance, (first arguemny specifies the number of milisecond to pause bw events, 
		//second argument is an action listener, which the constructor registers with the timer.
		timer = new Timer(DELAY, this);  
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			
			//This for loop is for showing grid
			
//			for(int i = 0; i < HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);   // drawline(startingcoordinate_x, startingcoordinate_y, endingcoordinate_x, endigcoordinate_Y);
//				g.drawLine(0, i*UNIT_SIZE, WIDTH, i*UNIT_SIZE);
//			}
			
			g.setColor(Color.black);
			g.fillOval(foodX, foodY, UNIT_SIZE,UNIT_SIZE); //Fill  Oval to make the food appear in ovel shape at coordinate(X,Y) and next two arguemnts are width nd height of foodItem
//			g.fillRect(40, 0, UNIT_SIZE,UNIT_SIZE);
			
			for(int i = 0; i < bodyparts; i++) {
				g.setColor(Color.black);
				g.fillRect(x[i], y[i], UNIT_SIZE,UNIT_SIZE);
			}
		} else {
			gameOver(g);
		}
	}
	
	//This method will generate new food, whenver this method is called wiith given coordinates
	//We will get random coordinates by using random function
	public void newFood() {
		foodX = random.nextInt(WIDTH/UNIT_SIZE)*UNIT_SIZE;
		foodY = random.nextInt(HEIGHT/UNIT_SIZE)*(UNIT_SIZE);
		
	}
	
	//This method will help move the snake
	public void move() {
		
		for (int i = bodyparts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'R' :
			x[0] = x[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkFood() {
		
		if((x[0] == foodX && y[0] == foodY)) {
			bodyparts++;
			foodEaten++;
			newFood();
		}
		
	}
	
	public void checkCollision() {
		
		for (int i = bodyparts; i > 0; i--) {
			//check if head of snake collide with body parts
			if(x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
			
			//Check if snake head collide with border
			//check left border
			if(x[0] < 0) {
				running = false;	
			}
			
			//check right border
			if(x[0] > WIDTH) {
				running = false;
			}
			
			//Check upper border
			if(y[0] < 0) {
				running = false;
			}
			
			if(y[0] > HEIGHT) {
				running = false;
			}
			
			if(!running) {
				timer.stop();
			}
		
	}
	
	public void gameOver(Graphics g) {
		
//		g.setColor(Color.black);
//		g.setFont(new Font("Ink Free", Font.BOLD, 40));
//		FontMetrics metrics = getFontMetrics(g.getFont());
//		g.drawString("Game Over",( WIDTH - metrics.stringWidth("Game Over"))/2, HEIGHT/2);
		
		g.setColor(Color.black);
		g.setFont(new Font("Simlple", Font.PLAIN, 20));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Your Score: " + foodEaten, (WIDTH - metrics1.stringWidth("Score:  " + foodEaten))/2 - 5, HEIGHT/2);
	}
	

	//Do not understand it completely:: Seee explanation at the very bottom
	//This method is being called by timer objct after specified time miiseconds.
	@Override
	public void actionPerformed(ActionEvent o) {
		// TODO Auto-generated method stub
		
		if(running) {
			move();
			checkFood();
			checkCollision();
		}
		
		repaint();
	
	}
	
	//Inter class
	//keyAdapter is a class which implements Keylistener interface, and used to get keyboard input
	//Here we are getting keyboard input
	public class myKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
			     
			
		}
		
	}

}



/*How functionn are being called and how I am getting the grid
i) When I launch the program paint component will be called automatically once
ii) Timer object (as its second arguemet is instance of our GamePanel class which is extending ActionListener and first is duration of recall), would call
ActionPerfomed function after every (first arguememt ) ms. 
iii) In the ActionPerformed, we are checking if it running then we move the snake and check for food and collision
iv) if it is not running, then we simply repaint, whichh would print endofgame screen
v) ActionListeer is getting called by time object and ActionLsitener is calling repaint() function.


Initially the paintComponent() function is getting, and x coordinate of array are 0 and 'y' coordinates are also 0.
After the first call, after 75ms, timer calls the actionPerformed() Function, this function call move() funcion, 

now move() function change the O th index to right by UNIT_SiZE and  and other Index get whatevery their previous index has. x[i] = x[i-1], 

Now in the next call we would have pressed a key and direction would have changed, then for loop will do the changes for the body and 
switch will do the chanegs for the head, unless we press another key, it will be keep going in the same direcion.

*/
