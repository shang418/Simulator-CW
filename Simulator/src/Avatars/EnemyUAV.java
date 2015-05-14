package Avatars;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import MotionControl.Control;

public class EnemyUAV{

		  private double _x, _y, _theta;
		  private double _dx, _dy, _dtheta;
		  public static int RADIUS=50;
		  ImageIcon icon;
		  
		    public EnemyUAV (double pose[], double s, double omega) {
		
		    	System.out.println(System.getProperty("user.dir"));
		    	this.icon=new ImageIcon("Images/Avatars/EnemyRed.png");
		    	icon.setImage(icon.getImage().getScaledInstance(RADIUS, RADIUS, Image.SCALE_SMOOTH));
			
		    if (pose.length != 3)
		      throw new IllegalArgumentException("newPos must be of length 3");      

		    _x = pose[0]; 
		    _y = pose[1]; 
		    _theta = pose[2];

		    _dx = s * Math.cos(_theta);
		    _dy = s * Math.sin(_theta);
		    _dtheta = omega;
		    
		    clampPosition();
		    clampVelocity();
		  }

public Image getImage(){
	return this.icon.getImage();
}
		  private void clampPosition() {
		    _x = Math.min(Math.max(_x,0),100);
		    _y = Math.min(Math.max(_y,0),100);
		    _theta = Math.min(Math.max(_theta, -Math.PI), Math.PI);
		    if (_theta - Math.PI == 0 || Math.abs(_theta - Math.PI) < 1e-6)
		      _theta = -Math.PI;
		  }

		  private void clampVelocity() {

		    double velMagnitude = Math.sqrt(_dx*_dx+_dy*_dy);
		    if (velMagnitude > 10.0) {

		      _dx = 10.0 * _dx/velMagnitude;
		      _dy = 10.0 * _dy/velMagnitude;
		    }

		    if (velMagnitude < 5.0) {
		      /* Same logic as above. */ 

		      _dx = 5.0 * _dx/velMagnitude;
		      _dy = 5.0 * _dy/velMagnitude;
		    }

		    _dtheta = Math.min(Math.max(_dtheta, -Math.PI/4), Math.PI/4);		
		  }

		  public double [] getPosition() {
		    double[] position = new double[3];
		    position[0] = _x;
		    position[1] = _y;
		    position[2] = _theta;
		    
		    return position;
		  }

		  public double [] getVelocity() {
		    double[] velocity = new double[3];
		    velocity[0] = _dx;
		    velocity[1] = _dy;
		    velocity[2] = _dtheta;
				
		    return velocity;
		  }

		  public void setPosition(double[] newPos) {
		    if (newPos.length != 3)
		      throw new IllegalArgumentException("newPos must be of length 3");      

		    _x = newPos[0];
		    _y = newPos[1];
		    _theta = newPos[2];

		    clampPosition();
		  }

		  public void setVelocity(double[] newVel) {
		    if (newVel.length != 3)
		      throw new IllegalArgumentException("newVel must be of length 3");      

		    _dx = newVel[0];
		    _dy = newVel[1];
		    _dtheta = newVel[2];		

		    clampVelocity();
		  }

		  public void controlVehicle(Control c) {
			  if (c == null)
			      throw new NullPointerException();      

		    _dx = c.getSpeed() * Math.cos(_theta);
		    _dy = c.getSpeed() * Math.sin(_theta);
		    _dtheta = c.getRotVel();

		    clampVelocity();
		  }
		   
		  public synchronized void updateState(int sec, int msec) {
		    double t = sec + msec * 1e-3;

		    // Curve model
		    // Assuming that _dx, _dy, and _dtheta was set beforehand by controlVehicle()
		    double s = Math.sqrt( _dx * _dx + _dy * _dy );

		    if (Math.abs(_dtheta) > 1e-3) { // The following model is not well defined when _dtheta = 0
			// Circle center and radius
			double r = s/_dtheta;

			double xc = _x - r * Math.sin(_theta);
			double yc = _y + r * Math.cos(_theta);

			_theta = _theta + _dtheta * t;

			double rtheta = ((_theta - Math.PI) % (2 * Math.PI));
			if (rtheta < 0) {	// Note that % in java is remainder, not modulo.
			    rtheta += 2*Math.PI;
			}
			_theta = rtheta - Math.PI;

			// Update    
			_x = xc + r * Math.sin(_theta);
			_y = yc - r * Math.cos(_theta);
			_dx = s * Math.cos(_theta);
			_dy = s * Math.sin(_theta);

		    } else {			// Straight motion. No change in theta.
			_x = _x + _dx * t;
			_y = _y + _dy * t;
		    }

		    clampPosition();
		    clampVelocity();
		  }
		}