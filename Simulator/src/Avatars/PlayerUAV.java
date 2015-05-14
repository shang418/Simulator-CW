package Avatars;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;

public class PlayerUAV extends JLabel implements KeyListener{
	private double _x,_y,_theta;
	static final int WIDTH = 1400;
	static final int HEIGHT = 800;
	int[] _startPose = {WIDTH - 50,HEIGHT - 50,0};
	
	Image image = GenerateImage.toImage(true);
	JLabel player_uav = new JLabel();

	public PlayerUAV(){
			
	}
	
	public double [] getPosition() {
		    double[] position = new double[3];
		    position[0] = _x;
		    position[1] = _y;
		    position[2] = _theta;
		    
		    return position;
		  }
	
	private void updatePose(){
		_x = _startPose[0];
		_y= _startPose[1];
		_theta = _startPose[2];
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
