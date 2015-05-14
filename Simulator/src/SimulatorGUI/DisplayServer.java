package SimulatorGUI;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import Avatars.*;
// import custom map
import MapClass.Map;

public class DisplayServer extends JPanel implements KeyListener {
	private Map myMap; 
	protected double gvX [], gvY[], gvTheta[];
	protected int numVehicles = 0;
	protected int shapeX[], shapeY[];
	protected JFrame frame;
	protected NumberFormat format = new DecimalFormat("#####.##");
	PlayerUAV player; 
	EnemyUAV enemy; 

	public DisplayServer () {
		// generate random number of obstacles on the map
		Random rand=new Random();
		int num=rand.nextInt(20);
		System.out.println(num);
		myMap=new Map(num);
		this.enemy=new EnemyUAV(new double[] {0,0,0},0,0);
		this.player = new PlayerUAV();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGraphics();
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed");
		// Directional controls based off key events for player uav
		if (e.getKeyCode() == KeyEvent.VK_A){
			player.setPosition(new double[]{player.getPosition()[0] - 5, player.getPosition()[1], player.getPosition()[2]});
			System.out.println("Pressed A");
		}
		if (e.getKeyCode() == KeyEvent.VK_D){
			player.setPosition(new double[]{player.getPosition()[0] + 5, player.getPosition()[1], player.getPosition()[2]});
			System.out.println("Pressed D");
		}
		if (e.getKeyCode() == KeyEvent.VK_W){
			player.setPosition(new double[]{player.getPosition()[0], player.getPosition()[1]-5, player.getPosition()[2]});
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			player.setPosition(new double[]{player.getPosition()[0], player.getPosition()[1]+5, player.getPosition()[2]});
		}
		if (e.getKeyCode() == KeyEvent.VK_I){
			player.setPosition(new double[]{player.getPosition()[0], player.getPosition()[1], player.getPosition()[2]+Math.PI/4});
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			player.setPosition(new double[]{player.getPosition()[0], player.getPosition()[1], player.getPosition()[2]-Math.PI/4});
		}
		// Missle Fire Event
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			//run missile class
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// not used
	}

	


	public void startGraphics()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("16.35 Display: Seek 'N' Destroy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		// container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setLayout(new BorderLayout());
		this.setOpaque(false);
		setFocusable(true);
		setMinimumSize(new Dimension(640,500));
		setSize(new Dimension(640,500));
		setPreferredSize(new Dimension(640,500));
		addKeyListener(this);
		container.add(myMap, BorderLayout.WEST);

		//  myMap.repaint();
		myMap.add(this);
		setVisible(true);
		frame.pack();
		// frame.setResizable(false);
		frame.setVisible(true);    
	}

	

	

	public void keyTyped(KeyEvent e)
	{
		switch (e.getKeyChar()) {
		case 'q':
			System.exit(0);
		case 'Q':
			System.exit(0);
		}
	}

	protected synchronized void drawUAVs(Graphics g) {
		g.setColor(Color.black);

		// This chunk of code just translate and rotates the shape.

		g.drawImage(this.enemy.getImage(), (int) (this.enemy.getPosition()[0]),(int) (this.enemy.getPosition()[1]), null);
		g.drawImage(this.player.getImage(), (int) (this.player.getPosition()[0]),(int) (this.player.getPosition()[1]), null);  
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //paints the background and image

		g.setColor(Color.white);
		drawUAVs(g);
	}




}