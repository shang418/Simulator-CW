package MapClass;

import java.util.ArrayList;
import java.util.Random;

public class Map {
	// fix the width and length of the map 
	final int width=480; 
	final int length=680; 
	ArrayList<Obstacle> list_obstacles; 
	/*
	 * <p> the number of obstacles located on the map
	 */
	int num; 
	public Map(int num_obs){
			try {
				if(num_obs<0 || num_obs>10){
				throw new Exception("Number of obstacles needs to be between 0 and 10");
				}
					} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			this.num=num_obs;
			generateObstacles();
	}
	public void generateObstacles(){
		// leave capability for different types of obstacles
		Random rand=new Random();
		for(int i=0; i<num; i++){
			int w=rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE;
			int l=rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE;
			int xc=rand.nextInt(width +1);
			int yx=rand.nextInt(length+1);
			// if obstacle is always a rectangle, then onnly provide width and length;
			Obstacle newObs=new Obstacle(xc,yc,w,l);
			newObs=isValidObject(newObs);
			this.list_obstacles.add(newObs);
		}
	}
	/*
	 * Method that checks to see if: 
	 * 1. Obstacles don't intersect each other
	 * 2. is not drawn outside the borders of the map
	 */
	public Obstacle isValidObject(Obstacle newObs){
		
		return newObs; 
	}
	/*
	 * Method that loads the map to gui screen
	 */
	public boolean loadMap(){
		
	}	
}
