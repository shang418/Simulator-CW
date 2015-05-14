package SimulatorGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverScreen extends JPanel implements ActionListener {
	private JButton instructButton;
	private JButton quitButton;
	private JButton playButton;
	
	private JLabel gameOverTitle;
	
	private JFrame overFrame;
	private JPanel panelUpper;
	private JPanel panelLower;
	
	static final int WIDTH = 1000;
	static final int HEIGHT = 600;

	
	public GameOverScreen(){
		gui();
	}
	
	public void gui(){
		overFrame = new JFrame("Game Over");
		overFrame.setVisible(true);
		overFrame.setSize(WIDTH,HEIGHT);
		overFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		overFrame.getContentPane().setBackground(Color.PINK);
		
		panelUpper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelUpper.setBackground(Color.BLACK);
		panelLower = new JPanel(new GridBagLayout());
		panelLower.setBackground(Color.BLACK);
		
		playButton = new JButton("Restart");
		quitButton = new JButton("Quit");
		
		
		gameOverTitle = new JLabel("Game Over");
		gameOverTitle.setForeground(Color.WHITE);
		gameOverTitle.setFont(new Font("Helvetica",Font.BOLD, 80));
		
		//Make game go to play Page when button is clicked
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				overFrame.dispose();
				new DisplayServer();
			}
		});
		
		//Make game quit when quit button is clicked
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				overFrame.dispose();
				new DisplayServer();
			}
		});
		
		panelUpper.add(gameOverTitle);
		panelLower.add(quitButton);
		panelLower.add(playButton);
		
		overFrame.getContentPane().add(panelUpper, "North");
		overFrame.getContentPane().add(panelLower);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		new GameOverScreen();
	}
	

}


