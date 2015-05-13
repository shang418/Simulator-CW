import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.JTextArea;

public class InstructionsScreen {
	private JFrame instructFrame;
	private JPanel instructPanel;
	private JPanel buttonPanel;
	
	private JButton returnButton;
	
	public InstructionsScreen(){
		instructgui();
	}
	
	public void instructgui(){
		instructFrame = new JFrame("Instructions");
		instructFrame.setVisible(true);
		instructFrame.setSize(800,800);
		instructFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instructFrame.setLocationRelativeTo(null);
		instructFrame.getContentPane().setBackground(Color.WHITE);
		
		instructPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		instructPanel.setBackground(Color.WHITE);
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		returnButton = new JButton("Return to Main Screen");
		
		//Clicking the return button will close window and open main screen
		returnButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				instructFrame.dispose();
				new WelcomeScreen();
			}
		});
		instructFrame.getContentPane().add(instructPanel, "North");
		instructFrame.getContentPane().add(buttonPanel);
		
		instructPanel.add(new JTextArea(content));
		buttonPanel.add(returnButton);
	}
	
	static String content = "Welcome to Seek 'N' Destroy, a game\n" +
			"that challenees players to defend the home airspace\n" +
			"from an enemy aircraft. Armed with missles and your\n" +
			"own UAV, there is hope to destroy the enemy aircraft\n"+
			"before it reaches it's target.\n" +
			"When the game starts the enemy aircraft will immediatly\n" +
			"begin moving towards the target (which is clearly marked)\n" +
			"Using your keyboard you can control your own UAV to seek\n" +
			"and destroy the target by hitting it with 3 missiles\n" +
			"Controls are shown below:\n"+
			"Space Bar -- shoot missile\n" +
			"'A' -- move left\n" +
			"'D' -- move right\n" +
			"'W' -- move up\n" +
			"'S' -- move down\n" +
			"'I' -- move counter-clockwise\n" +
			"'O' -- move clock-wise\n"+ "\n" +
			"The player's avatar is blue while the enemy avatar is red.\n" +
			"Good Luck!!!";
	
	public static void main(String[] args){
		new InstructionsScreen();
	}

}
