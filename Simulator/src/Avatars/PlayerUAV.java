package Avatars;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayerUAV implements KeyListener{
	private double _x,_y,_theta;
	static final int WIDTH = 1400;
	static final int HEIGHT = 800;
	int[] _startPose = {500,300,0};
	private int _velx = 2,_vely=2;
	private double rotvel = Math.PI/4;
	 ImageIcon icon;
	 public static int RADIUS=50;

	public PlayerUAV(){
		//set intial position
		_x = _startPose[0];
		_y= _startPose[1];
		_theta = _startPose[2];
		
		this.icon=new ImageIcon("Images/Avatars/PlayerBlue.png");
    	icon.setImage(icon.getImage().getScaledInstance(RADIUS, RADIUS, Image.SCALE_SMOOTH));
		
	}
	
	public Image getImage(){
		return this.icon.getImage();
	}

	public double [] getPosition() {
		double[] position = new double[3];
		position[0] = _x;
		position[1] = _y;
		position[2] = _theta;

		return position;
	}
	
	public void setPosition(double[] newPos) {
	    if (newPos.length != 3)
	      throw new IllegalArgumentException("newPos must be of length 3");      
	    _x = newPos[0];
	    _y = newPos[1];
	    _theta = newPos[2];

	  }


	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed");
		// Directional controls based off key events for player uav
		if (e.getKeyCode() == KeyEvent.VK_A){
			_x = _x - _velx;
			
		}
		if (e.getKeyChar() == KeyEvent.VK_D){
			_x = _x + _velx;
		}
		if (e.getKeyChar() == KeyEvent.VK_W){
			_y = _y + _vely;
		}
		if (e.getKeyChar() == KeyEvent.VK_S){
			_y = _y - _vely;
		}
		if (e.getKeyChar() == KeyEvent.VK_I){
			_theta = _theta + rotvel;
		}
		if (e.getKeyChar() == KeyEvent.VK_S){
			_theta = _theta - rotvel;
		}
		// Missle Fire Event
		if (e.getKeyChar() == KeyEvent.VK_SPACE){
			//run missile class
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// not used
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// not used	
	}


}
