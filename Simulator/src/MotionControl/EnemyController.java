package MotionControl;


public class EnemyController {
	final double _tolerance=1E-6; 
	private int _numSides = 5;

	
	  
	  private double minTransSpeed = 5;
	  private double maxTransSpeed = 10;
	  private double maxRotSpeed = Math.PI/4;
	    
	  private double circumCircleRadius = 25.0;

	  private boolean isTurning = false;
	  private boolean controllerInitialized = false;

	  private double turnDuration;
	  private double edgeTravelDuration;

	  private double timeOfManoeuverStart; 
	public EnemyController() {
			
	}
			protected void initializeController() {

			    double interiorAngle = Math.PI*(_numSides-2)/_numSides;
			    double turningAngle = Math.PI - interiorAngle;

			    double minTurningRadius = minTransSpeed / maxRotSpeed;
			    
			    double arcLength = turningAngle * minTurningRadius;

			    turnDuration = arcLength / minTransSpeed;

			    // Edge length of n-polygon
			    double polyEdge = 2 * circumCircleRadius * Math.cos(interiorAngle / 2);
			    // Subtract by chord length spent for turns
			    double edgeLength = polyEdge - 2 * (minTurningRadius * Math.tan(turningAngle/2));

			    /* And we now have the amount of time to spend travelling straight along
			     * each edge. */
			    edgeTravelDuration = edgeLength/maxTransSpeed;

			   
			    isTurning = true;
			    timeOfManoeuverStart = -turnDuration/2.0; 
				
			    controllerInitialized = true;
			  }
			
			public Control getControl(int sec, int msec) {
						System.out.println("VehicleController!");
						double controlTime = sec+msec*1E-3;

						Control vecControl = null;

						if (!controllerInitialized) 
							initializeController();

						if (isTurning) {
							if (controlTime - timeOfManoeuverStart < turnDuration)
								vecControl = new Control(minTransSpeed, maxRotSpeed);
							else {
								isTurning = false;
								timeOfManoeuverStart = controlTime;
								vecControl = new Control(maxTransSpeed, 0);
							} 
						} else {
							if (controlTime - timeOfManoeuverStart < edgeTravelDuration)
								vecControl = new Control(maxTransSpeed, 0);
							else {
								isTurning = true;
								timeOfManoeuverStart = controlTime;
								vecControl = new Control(minTransSpeed, maxRotSpeed);
							} 
						}

						return vecControl;
					}

	}

}
