package MotionControl;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import Avatars.*;
import MapClass.Obstacle;
import SimulatorGUI.DisplayServer;
import SimulatorGUI.GameOverScreen;

	public class EnemyController extends Thread {

	    private ArrayList<Obstacle> ObsList;
	    private PlayerUAV player;
	    private EnemyUAV enemy;
	    final int avoidWallDist=50;
	    MissileState state;
	    DisplayServer server;
	  
	    protected static int totalNumControllers = 0;
	    protected int controllerID = 0;

	    // Hard-coded constraints come from documentation. Min translation 
	    // speed of the vehicle is 5 m/s, max translation speed is 10 m/s, 
	    // max rotational speed is PI/4. The diameter of outer circle is 50.
	    private double minTransSpeed = 5;
	    private double maxTransSpeed = 10;
	    private double maxRotSpeed = Math.PI/4;

	    private int _numSides = 5;
	    private double circumCircleRadius = 25.0;


	    public EnemyController(EnemyUAV enemy, PlayerUAV player, ArrayList<Obstacle> ObsList, DisplayServer server) {
		this.ObsList = ObsList;
		this.player=player;
		this.enemy=enemy;
		this.state=MissileState.FIRED;
		this.server=server;
		if(this.ObsList==null || this.player==null || this.enemy==null|| this.server==null){
			throw new IllegalArgumentException("");
		}
	    }
	    
	    public void run()
	    {
		
		while(this.state!=MissileState.STOPPED){
			this.getStopped();
		    // Generate a new control
			Random rng=new Random(); 
	    	//Control a = avoidWalls(this.player.getPosition());
	    	//if(a==null){
	    	// otherwise generate a random control
	    	//Control a = new Control(rng.nextDouble() * 5 + 5,
	    			//	rng.nextDouble() * Math.PI / 2.0 - Math.PI / 4.0);
	    	//}
			Control a=this.getControl();
	    	enemy.controlVehicle(a);
		    enemy.updateState();
		    this.server.repaint();
		    }
	    }
	    private void getStopped(){
	    	
	    	double delta_x = enemy.getPosition()[0] - 40.0;
			double delta_y = enemy.getPosition()[1] - 30.0;
			if (Math.sqrt(delta_x*delta_x + delta_y*delta_y) <= 25){
				this.state=MissileState.STOPPED;
			}
	    }
	   

	    /**
	     * Returns a control negating the output for the FollowingControler. Added
	     * special controls when the GroundVehicle is close to the walls.
	     */
	    public Control getControl() {
		
	    	Random rng=new Random(); 
	    	/*Control a = avoidWalls(this.player.getPosition());
	    	if (a != null)
	    	    return a;
	    		
	    	// otherwise generate a random control
	    	Control c = new Control(rng.nextDouble() * 5 + 5,
	    				rng.nextDouble() * Math.PI / 2.0 - Math.PI / 4.0);
	    	return c;*/
	    	double desiredOmega;
	    	double[] leaderPos;
	    	double[] myPos;

	    	leaderPos = new double[]{40,30,0};
	    	myPos = enemy.getPosition();

	    	//heading of the leading vehicle in global reference

	    	double xDiff = leaderPos[0] - myPos[0];
	    	double yDiff = leaderPos[1] - myPos[1];
	    	double desiredTheta = Math.atan(yDiff / xDiff);

	    	if (xDiff < 0) {
	    	    desiredTheta += Math.PI;
	    	}

	    	double gain = 5;

	    	//needed change in angle 
	    	desiredTheta = normalizeAngle(desiredTheta);
	    	desiredOmega = normalizeAngle(desiredTheta - myPos[2]);

	    	desiredOmega *= gain;

	    	if (desiredOmega > Math.PI/4) {
	    	    desiredOmega = Math.PI/4;
	    	}
	    	if (desiredOmega < -Math.PI/4) {
	    	    desiredOmega = -Math.PI/4;
	    	}

	    	double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	    	double desiredSpeed = distance;
	    	if (desiredSpeed > 10) {
	    	    desiredSpeed = 10;
	    	}
	    	if (desiredSpeed < 5) {
	    	    desiredSpeed = 5;
	    	}
	    	Control a = avoidObstacles(myPos);
	    	if (a != null)
	    	    return a;

	    	Control c = new Control(desiredSpeed, desiredOmega);
	    	return c;
	        
	    }
	  //Normalize angle 
	    protected static double normalizeAngle(double theta)
	    {
		double rtheta = ((theta - Math.PI) % (2 * Math.PI));			
		if (rtheta < 0) {	// Note that % in java is remainder, not modulo.
		    rtheta += 2*Math.PI;
		}
		rtheta -= Math.PI;

	    	return rtheta;
	    }

	    protected Control avoidObstacles(double[] pos) {
	    	for(Obstacle obs: this.ObsList){
		if (pos[0] > obs.getCenterX() - avoidWallDist && pos[1] > obs.getCenterY() - avoidWallDist) {
		    if (pos[2] > -3 * Math.PI / 4.0) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

	    	}
		return null;
	    }

	}
	   
