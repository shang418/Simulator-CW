package Avatars;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayerUAV {
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

	public synchronized double [] getPosition() {
		double[] position = new double[3];
		position[0] = _x;
		position[1] = _y;
		position[2] = _theta;

		return position;
	}
	
	public synchronized void setPosition(double[] newPos) {
	    if (newPos.length != 3)
	      throw new IllegalArgumentException("newPos must be of length 3");   
	    _x = newPos[0];
	    _y = newPos[1];
	    
	    _theta = newPos[2];

	  }





}
