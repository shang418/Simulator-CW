package SimulatorGUI;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

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
import MotionControl.EnemyController;
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
	EnemyController controller;
	int n=0;
	

	public DisplayServer () {
		// generate random number of obstacles on the map
		Random rand=new Random();
		int num=rand.nextInt(20);
		System.out.println(num);
		//int num=1;
		myMap=new Map(num);
		this.enemy=new EnemyUAV(new double[] {900,500,0},0,0);
		
		this.player = new PlayerUAV();
		this.missile_list=new ArrayList<Missile>();
		this.controller=new EnemyController(this.enemy,this.player,this.myMap.getList_obstacles(),this);
		this.enemy.setController(controller);
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
				
			if((nx-5)>10 )
			nx -= 5;
				
				
				
		}
		if (e.getKeyCode() == KeyEvent.VK_D){
			
			
			if((nx+5)<(950) )
				nx += 5;
		
		}
		if (e.getKeyCode() == KeyEvent.VK_W){
			
			if((ny-5)>10 )
				ny -= 5;
			
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			if((ny+5)<(550) )
				ny += 5;
			
		}
		if (e.getKeyCode() == KeyEvent.VK_I){
			{
				count=0.01;
				ntheta += count;
			}
			
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			{
				count=0.01;
				ntheta -= count;
			}
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
		
		AffineTransform tx = AffineTransform.getRotateInstance(count, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Drawing the rotated image at the required drawing locations
		g2d.drawImage(op.filter(image, null), drawLocationX, drawLocationY, null);
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
		if (Math.sqrt(delta_x*delta_x + delta_y*delta_y) <= 25){
			frame.dispose();
			new GameOverScreen();
			JOptionPane.showMessageDialog(null,"Kamikaze is one way to do it....");
		}
		//End Game if user hits enemy 3 times
		
		for (int i=0; i < this.missile_list.size(); i++){
			double x = this.missile_list.get(i).getPosition()[0];
			double y = this.missile_list.get(i).getPosition()[1];
			double deltx = x-enemy.getPosition()[0];
			double delty = y-enemy.getPosition()[1];
			if (Math.sqrt(deltx*deltx + delty*delty) <= 25){
				n++;
				if(n == 1){
					System.out.println("You win!");
				frame.dispose();
				new GameOverScreen();}
			}
		}
		}
		
		
		

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //paints the background and image

		g.setColor(Color.white);
		g.drawImage(this.myMap.getMapImage(), 0, 0,null);
		for(Obstacle ob: this.myMap.getList_obstacles()){
			//System.out.println("image height and width: ["+ob.getImage().getHeight(null)+","+ob.getImage().getWidth(null)+"]");
			g.drawImage(ob.getImage(), (int) ob.getX(), (int) ob.getY(),null);
		}
		/*g.setColor(Color.MAGENTA);
		for(Obstacle ob: this.myMap.getCspace().getCspace_obs()){
			//System.out.println("image height and width: ["+ob.getImage().getHeight(null)+","+ob.getImage().getWidth(null)+"]");
			g.drawRect((int) (ob.getX()), (int) (ob.getY()),(int) (ob.getWidth()), (int)(ob.getHeight()));
		}*/
		//Target
		g.setColor(Color.red);
		g.fillRoundRect(40, 30, 50, 50,5,5);
		drawUAVs(g);
	}




}