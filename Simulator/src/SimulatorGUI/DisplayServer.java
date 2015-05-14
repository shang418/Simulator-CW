package SimulatorGUI;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import Avatars.*;
// import custom map
import MapClass.Map;
import MapClass.Obstacle;
import MotionPlanning.Node;

public class DisplayServer extends JPanel implements KeyListener {
	private Map myMap; 
	protected int numVehicles = 0;
	protected JFrame frame;
	protected NumberFormat format = new DecimalFormat("#####.##");
	PlayerUAV player; 
	EnemyUAV enemy; 
	Missile missile;
	final int MAX_MISSILE_NUM=10;
	private double count=0;
	private boolean isfired;
	ArrayList<Missile> missile_list;
	private double initial_x, initial_y, initial_theta;
	
	

	public DisplayServer () {
		// generate random number of obstacles on the map
		//Random rand=new Random();
		//int num=rand.nextInt(20);
		//System.out.println(num);
		int num=1;
		myMap=new Map(num);
		this.enemy=new EnemyUAV(new double[] {100,100,0},0,0);
		this.player = new PlayerUAV();
		this.missile_list=new ArrayList<Missile>();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGraphics();
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
		double ny=player.getPosition()[1];
		double nx=player.getPosition()[0];
		double ntheta=player.getPosition()[2];
		// Directional controls based off key events for player uav
		if (e.getKeyCode() == KeyEvent.VK_A){
			if(!(myMap.getCspace().violatesCSpace(new Node ((int)(nx-5), (int)ny)))){
				
				nx -= 5;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_D){
			if(!(myMap.getCspace().violatesCSpace(new Node ((int)(nx+5), (int)(ny))))){	
				nx += 5;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W){
			if(!(myMap.getCspace().violatesCSpace(new Node ((int)(nx), (int)(ny-5))))){	
				ny -= 5;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			if(!(myMap.getCspace().violatesCSpace(new Node ((int)nx, (int)(ny+5))))){	
				ny += 5;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_I){
			player.setPosition(new double[]{player.getPosition()[0], player.getPosition()[1], player.getPosition()[2]});
			//count = 0.1;
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			player.setPosition(new double[]{player.getPosition()[0], player.getPosition()[1], player.getPosition()[2]});
			//count = -0.1;
		}
		player.setPosition(new double[]{nx,ny,ntheta});
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			if(this.missile_list.size()<MAX_MISSILE_NUM){
			System.out.println("Shots fired");
			initial_x = player.getPosition()[0];
			initial_y = player.getPosition()[1];
			initial_theta = player.getPosition()[2];
			missile = new Missile(initial_x, initial_y, initial_theta, this);
			this.missile_list.add(missile);
			missile.start();
			}
			else {
				JOptionPane.showMessageDialog(null, "Sorry but you are out of bullets :(");
			}
		}
	}

	


	public void startGraphics()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("Seek 'N' Destroy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		// container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setLayout(new BorderLayout());
		this.setOpaque(false);
		setFocusable(true);
		setMinimumSize(new Dimension(1000,600));
		setSize(new Dimension(1000,600));
		setPreferredSize(new Dimension(1000,600));
		addKeyListener(this);
		container.add(this, BorderLayout.WEST);

		// myMap.repaint();
		//myMap.add(this);
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
		Iterator<Missile> missIterator= this.missile_list.listIterator();
		while(missIterator.hasNext()){
				Missile mle=missIterator.next();
				MissileState state=MissileState.RUNNING;
				state=mle.getMissileState();
				if(state==MissileState.STOPPED){
					System.out.println("Missile has been stopped");
				}
				else {
					g.drawImage(mle.getImage(),(int)(mle.getPosition()[0]),(int)(mle.getPosition()[1]), null);
				}
			}
		//End Game if UAVs crash
		double delta_x = enemy.getPosition()[0] - player.getPosition()[0];
		double delta_y = enemy.getPosition()[1] - player.getPosition()[1];
		if (Math.sqrt(delta_x*delta_x + delta_y*delta_y) <= 20){
			frame.dispose();
			new GameOverScreen();
		}
		//End Game if user hits enemy 3 times
//		int n = 0;
//		for (int i=0; i < this.missile_list.size(); i++){
//			double x = this.missile_list.get(i).getPosition()[0];
//			double y = this.missile_list.get(i).getPosition()[1];
//			double deltx = x-enemy.getPosition()[0];
//			double delty = y-enemy.getPosition()[1];
//			if (Math.sqrt(deltx*deltx + delty*delty) <= 25){
//				n = n + 1;
//				if(n == 3){
//					System.out.println("You win!");
//				frame.dispose();
//				new GameOverScreen();}
//			}
//		}
		}
		
		
		

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //paints the background and image

		g.setColor(Color.white);
		g.drawImage(this.myMap.getMapImage(), 0, 0,null);
		for(Obstacle ob: this.myMap.getList_obstacles()){
			//System.out.println("image height and width: ["+ob.getImage().getHeight(null)+","+ob.getImage().getWidth(null)+"]");
			g.drawImage(ob.getImage(), (int) ob.getX(), (int) ob.getY(),null);
		}
		g.setColor(Color.MAGENTA);
		for(Obstacle ob: this.myMap.getCspace().getCspace_obs()){
			//System.out.println("image height and width: ["+ob.getImage().getHeight(null)+","+ob.getImage().getWidth(null)+"]");
			g.drawRect((int) ob.getX(), (int) ob.getY(),(int) (ob.getWidth()), (int)(ob.getHeight()));
		}
		//Target
		g.setColor(Color.red);
		g.fillRoundRect(950, 550, 50, 50,5,5);
		drawUAVs(g);
	}




}