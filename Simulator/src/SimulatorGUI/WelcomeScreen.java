package SimulatorGUI;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class WelcomeScreen extends JPanel implements ActionListener {
	private JButton instructButton;
	private JButton quitButton;
	private JButton playButton;
	
	private JLabel welcomeTitle;
	
	private JFrame welcomeFrame;
	private JPanel welcomePanelupper;
	private JPanel welcomePanellower;
	
	static final int WIDTH = 1400;
	static final int HEIGHT = 800;

	
	public WelcomeScreen(){
		gui();
	}
	
	public void gui(){
		welcomeFrame = new JFrame("Main Screen");
		welcomeFrame.setVisible(true);
		welcomeFrame.setSize(WIDTH,HEIGHT);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//welcomeFrame.setLocationRelativeTo(null);
		welcomeFrame.getContentPane().setBackground(Color.PINK);
		
		welcomePanelupper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		welcomePanelupper.setBackground(Color.PINK);
		welcomePanellower = new JPanel(new GridBagLayout());
		welcomePanellower.setBackground(Color.PINK);
		
		instructButton = new JButton("Instructions");
		quitButton = new JButton("Quit");
		playButton = new JButton("Play");
		
		welcomeTitle = new JLabel("Seek 'N' Destroy");
		welcomeTitle.setFont(new Font("Helvetica",Font.BOLD, 60));
		
		//Make game go to Instructions Page when button is clicked
		instructButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				welcomeFrame.dispose();
				new InstructionsScreen();
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
				welcomeFrame.dispose();
				new DisplayServer();
			}
		});
		
		welcomePanelupper.add(welcomeTitle);
		welcomePanellower.add(instructButton);
		welcomePanellower.add(quitButton);
		welcomePanellower.add(playButton);
		
		welcomeFrame.getContentPane().add(welcomePanelupper, "North");
		welcomeFrame.getContentPane().add(welcomePanellower);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		new WelcomeScreen();
	}
	

}
