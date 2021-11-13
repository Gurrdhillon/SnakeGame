import javax.swing.*;

public class GameFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GameFrame(){
		
		//Create an instance of Panel class
		GamePanel panel = new GamePanel();
		
		//add Panel to the game frame
		this.add(panel);
		this.setTitle("Serpent Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//I haven't added setResizable Function()...., if something breaks, would add it later
		this.pack();   //The pack method sizes the frame so that all its contents are at or above their preferred sizes.
		this.setVisible(true);
		this.setLocationRelativeTo(null); //To make the GUI appear in middle of computer screen
		
		
		
		//Continue......
	}

}
