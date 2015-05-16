package Avatars;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import MapClass.Map;
import MapClass.MapTest;
import SimulatorGUI.DisplayServer;

public class MissileTest {

	// check that null pointer exception is thrown when null displayserver is entered
	@Test(expected=NullPointerException.class)
	public void checkDisplayServer(){
	Missile missile=new Missile(0.0,0.0,0.0,null);	
	}
	// test edge cases of position
	@Test
	public void verifyLimits(){
		DisplayServer ds=new DisplayServer();
		// less than 0
		Missile missile= new Missile(-1.0,30.0,0.0,ds);
		assertEquals(null, missile.getPosition()[0], 0.0, 1E-6);
		missile.setPosition(30.0, -1.0, 0.0);
		assertEquals(null, missile.getPosition()[1], 0.0, 1E-6);
		// less than pi
		missile.setPosition(-1.0,30.0,-2.0*Math.PI);
		assertEquals(null, missile.getPosition()[2], -Math.PI, 1E-6);
		// greater than screen width
		missile.setPosition(1000.0, 30.0, 0.0);
		assertEquals(null, missile.getPosition()[0], 900.0, 1E-6);
		//greater than screen length
		missile.setPosition(100.0, 900.0, 0.0);
		assertEquals(null, missile.getPosition()[1],500.0, 1E-6);
		// greater than Math.PI
		missile.setPosition(100.0, 100.0, Math.PI+1);
		assertEquals(null, missile.getPosition()[2],Math.PI, 1E-6);
	}
	
	// test trajectory function 
	@Test
	public void verifyTrajectory(){
		// test straight line 
		DisplayServer ds=new DisplayServer();
		Missile missile=new Missile(0,0,0,ds);
		for(int i=0; i<10; i++){
			missile.trajectory();
		}
		// for theta =0, verify x=0 and y=-50
		assertEquals(null, missile.getPosition()[0],0.0, 1E-6);
		assertEquals(null, missile.getPosition()[1],-50.0, 1E-6);
		
	}
	@Test
	public void verifyTrajectory2(){
		// test straight line 
		DisplayServer ds=new DisplayServer();
		Missile missile=new Missile(0,0,Math.PI/2.0,ds);
		for(int i=0; i<10; i++){
			missile.trajectory();
		}
		// for theta =90, verify x=50 and y=0
		assertEquals(null, missile.getPosition()[0],50.0, 1E-6);
		assertEquals(null, missile.getPosition()[1],0.0, 1E-6);
	}
	
	public static void main(String[] args){
		JUnitCore.main(MapTest.class.getName());	
		}
}
