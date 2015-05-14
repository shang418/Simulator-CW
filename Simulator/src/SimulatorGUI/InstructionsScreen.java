package SimulatorGUI; 
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.JTextArea;

public class InstructionsScreen {
	private JFrame instructFrame;
	private JPanel instructPanel;
	private JPanel buttonPanel;
	
	private JLabel balloonLabel;
	private JLabel player_avatar;
	private JLabel enemy_avatar;
	
	private JButton returnButton;
	
	static final int WIDTH = 1000;
	static final int HEIGHT = 600;
	
	public InstructionsScreen(){
		instructgui();
	}
	
	public void instructgui(){
		instructFrame = new JFrame("Instructions");
		instructFrame.setVisible(true);
		instructFrame.setSize(WIDTH,HEIGHT);
		instructFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//instructFrame.setLocationRelativeTo(null);
		instructFrame.getContentPane().setBackground(Color.WHITE);
		
		instructPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		instructPanel.setBackground(Color.WHITE);
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(Color.ORANGE);
		
		balloonLabel = new JLabel();
		//player_avatar = new JLabel();
		//enemy_avatar = new JLabel();
		
		balloonLabel.setIcon(new ImageIcon("Images/GameScreen/Obstacles/balloon.png"));
		//player_avatar.setIcon(new ImageIcon("Images/Avatars/PlayerBlue.png"));
		//enemy_avatar.setIcon(new ImageIcon("Images/Avatars/EnemyRed.png"));
		
		instructPanel.add(balloonLabel);
		//instructPanel.add(player_avatar);
		//instructPanel.add(enemy_avatar);
		
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
		
		
		JTextArea jtext = new JTextArea(content);
		jtext.setFont(new Font("Helvetica", Font.PLAIN, 15));
		
		instructPanel.add(jtext);
		buttonPanel.add(returnButton);
		
	}
	
	static String content = "Welcome to Seek 'N' Destroy, a game\n" +
			"that challenges players to defend the home airspace\n" +
			"from an enemy aircraft. Armed with missles and your\n" +
			"own UAV, there is hope to destroy the enemy aircraft\n"+
			"before it reaches it's target.\n" + "\n" +
			"When the game starts the enemy aircraft will immediatly\n" +
			"begin moving towards the target (which is clearly marked)\n" +
			"Using your keyboard you can control your own UAV to seek\n" +
			"and destroy the target by hitting it with 3 missiles.\n" +
			"Controls are shown below:\n"+
			"Space Bar -- shoot missile\n" +
			"'A' -- move left\n" +
			"'D' -- move right\n" +
			"'W' -- move up\n" +
			"'S' -- move down\n" +
			"'I' -- move counter-clockwise\n" +
			"'O' -- move clock-wise\n"+ "\n" +
			"You will need to avoid obstacles (hot air balloons) along the way\n" +
			"or else you will chrash and end the game." +
			"The player's avatar is blue while the enemy avatar is red.\n" +
			"Good Luck!!!";
	
	public static void main(String[] args){
		new InstructionsScreen();
	}

}
