package Avatars;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import SimulatorGUI.DisplayServer;

public class Missile extends Thread{
	public static int RADIUS=25;
	ImageIcon icon;
	private double _x,_y,_theta;
	DisplayServer ds;
	MissileState state;
	private boolean isRunning;

	public Missile(double x, double y, double theta, DisplayServer ds){
		_x = x;
		_y = y;
		_theta = theta;

		this.ds = ds;
		this.icon=new ImageIcon("Images/Avatars/rocket.png");
		icon.setImage(icon.getImage().getScaledInstance(RADIUS, RADIUS, Image.SCALE_SMOOTH));
		this.state=MissileState.FIRED;
	}

	public Image getImage(){
		return this.icon.getImage();
	}

	public double [] getPosition() {
		double[] position = new double[3];
		position[0] = _x;
		position[1] = _y;
		position[2] = _theta;
		//this.trajectory();
		return position;
	}
	
	public MissileState getMissileState(){
		return this.state;
	}

	public void trajectory(){
		_x = _x + 5 * Math.sin(_theta);
		_y = _y - 5 * Math.cos(_theta);
	}
	public boolean running(){
		return isRunning;
	}
	public void setRunning(boolean run){
		isRunning =  run;
	}

	@Override
	public void run(){
		this.state=MissileState.RUNNING;
		for (int i = 0; i < 50; i++) {
			trajectory();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ds.repaint();
		}
		this.state=MissileState.STOPPED;
	}



}
