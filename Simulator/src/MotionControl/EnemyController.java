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
	    final int avoidWallDist=20;
	    MissileState state;
	    DisplayServer server;
	    private int _lastCheckedTime = 0;
	    private int _lastCheckedMTime = 0;

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

	    private boolean isTurning = false;
	    private boolean controllerInitialized = false;

	    private double turnDuration;
	    private double edgeTravelDuration;

	    private double timeOfManoeuverStart;

	    public EnemyController(EnemyUAV enemy, PlayerUAV player, ArrayList<Obstacle> ObsList, DisplayServer server) {
		this.ObsList = ObsList;
		this.player=player;
		this.enemy=enemy;
		this.state=MissileState.FIRED;
		this.server=server;
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
	    private void initializeController()
	    {
		/* The bulk of this method is to determine how long to spend turning at
		 * each corner of the polygon, and how long to spend driving along each
		 * edge. We calculate turnDuration and edgeTravelDuration, and then use
		 * these inside getControl to decide when to switch from turning to
		 * travelling straight and back again. */ 

		/* Firstly, we know we need to turn the vehicle by PI - the internal angle
		 * of the polygon */

		double interiorAngle = Math.PI*(_numSides-2)/_numSides;
		double turningAngle = Math.PI - interiorAngle;

		/* And we're going to turn the vehicle along the circumference of the
		 * smallest circle we can make. */ 

		double minTurningRadius = minTransSpeed / maxRotSpeed;

		/* The distance we have to travel along that smallest circle is a function
		 * of the angle and the radius, and is an arc along that small circle. */
		double arcLength = turningAngle * minTurningRadius;

		/* We can work out how long each turn will take based on the arcLength and
		 * how fast we are travelling. Of course, we could also work it out based
		 * on the angle and our maximum angular velocity. */

		turnDuration = arcLength / minTransSpeed;

		// Edge length of n-polygon
		double polyEdge = 2 * circumCircleRadius * Math.cos(interiorAngle / 2);
		// Subtract by chord length spent for turns
		double edgeLength = polyEdge - 2 * (minTurningRadius * Math.tan(turningAngle/2));

		/* And we now have the amount of time to spend travelling straight along
		 * each edge. */
		edgeTravelDuration = edgeLength/maxTransSpeed;

		/* Also in method, we initialize the controller state. It's a little ugly,
		 * but we'll start as if we're half-way through a turn, and tangent to the
		 * outer circle. This makes it easy to put the vehicle on a legal part of
		 * the polyon, rather than having to drive to it. */ 
	    
		isTurning = true;
		timeOfManoeuverStart = -turnDuration/2.0; 
		
		controllerInitialized = true;
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

	    protected Control avoidWalls(double[] pos) {
		if (pos[0] > 100 - avoidWallDist && pos[1] > 100 - avoidWallDist) {
		    if (pos[2] > -3 * Math.PI / 4.0) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

		if (pos[0] > 100 - avoidWallDist && pos[1] < 0 + avoidWallDist) {
		    if (pos[2] > 3 * Math.PI / 4.0) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

		if (pos[0] < 0 + avoidWallDist && pos[1] > 100 - avoidWallDist) {
		    if (pos[2] > -Math.PI / 4.0) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

		if (pos[0] < 0 + avoidWallDist && pos[1] < 0 + avoidWallDist) {
		    if (pos[2] > Math.PI / 4.0) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

		if (pos[0] > 100 - avoidWallDist) {
		    if (pos[2] > 0) {
			return new Control(5, Math.PI/4);
		    } else {
			return new Control(5, -Math.PI/4);
		    }
		}
		if (pos[0] < 0 + avoidWallDist) {
		    if (pos[2] > 0) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

		if (pos[1] < 0 + avoidWallDist) {
		    if (pos[2] > Math.PI / 2) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}

		if (pos[1] > 100- avoidWallDist) {
		    if (pos[2] > -Math.PI / 2) {
			return new Control(5, -Math.PI/4);
		    } else {
			return new Control(5, Math.PI/4);
		    }
		}
		return null;
	    }

	}
	   
