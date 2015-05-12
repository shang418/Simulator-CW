package MapClass;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	// fix the width and length of the map 
	final static int WIDTH=480; 
	final static int LENGTH=680; 
	final int MAX_NUM_OBSTACLES=10; 
	final int MIN_NUM_OBSTACLES=0;
	final Point startpoint=new Point(450,650); 
	final Point goalpoint=new Point(10,10);
	ArrayList<Obstacle> list_obstacles; 
	/*
	 * <p> the number of obstacles located on the map
	 */
	int num; 
	
	public Map(int num_obs){
			this.num=validateIntegers(num_obs, MIN_NUM_OBSTACLES,MAX_NUM_OBSTACLES);
			//generateObstacles();
	}
	public int validateIntegers(int n, int min, int max){
				if(n<min){
					System.out.println("Number is less than minimun, Number automatically reset to "+min);
				return min;	
				}
				else if (n>max){
					System.out.println("Number is greater than maxmimum, Number automatically reset to "+max);
					return max;	
				}
		System.out.println("Number valid: you're good to go");
		return n; 
		}
	public void generateObstacles(){
		// leave capability for different types of obstacles
		Random rand=new Random();
		for(int i=0; i<num; i++){
			int x=this.validateIntegers(rand.nextInt(WIDTH +1), 0, WIDTH);
			int y =this.validateIntegers(rand.nextInt(LENGTH +1),0,LENGTH);
			int w=this.validateIntegers((rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE), Obstacle.MINSIZE, Obstacle.MAXSIZE);
			int l=this.validateIntegers((rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE), Obstacle.MINSIZE, Obstacle.MAXSIZE);
			// if obstacle is always a rectangle, then only provide width and length;
			Obstacle newObs=new Obstacle(x,y,w,l);
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
		boolean isvalid=true; 
		//  check to see if obstacle is at the startpoint
			if(newObs.contains(startpoint)){
				isvalid=false;
			}
		// check to see if obstacle is at the goalpoint
			if(newObs.contains(goalpoint)){
				isvalid=false;
			}
		// check to see if obstacle is outside the border of the map
			if(WIDTH-((int)(newObs.getMaxX()))<0){
				isvalid=false; 
			}
			if(LENGTH-((int)(newObs.getMaxY()))<0){
				isvalid=false;
			}
			if(!isvalid){
				Random rand=new Random();
				int x=this.validateIntegers(rand.nextInt(WIDTH +1), 0, WIDTH);
				int y =this.validateIntegers(rand.nextInt(LENGTH +1),0,LENGTH);
				int w=this.validateIntegers((rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE), Obstacle.MINSIZE, Obstacle.MAXSIZE);
				int l=this.validateIntegers((rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE), Obstacle.MINSIZE, Obstacle.MAXSIZE);
				newObs=new Obstacle(x,y,w,l);
				isValidObject(newObs);
			}
		return newObs; 
	}
	/*
	 * Method that loads the map to gui screen
	 */
/*	public boolean loadMap(){
		
	}*/
	public Point2D getStartpoint() {
		return startpoint;
	}
	public Point getGoalpoint() {
		return goalpoint;
	}
	public ArrayList<Obstacle> getList_obstacles() {
		return list_obstacles;
	}
	public void setList_obstacles(ArrayList<Obstacle> list_obstacles) {
		this.list_obstacles = list_obstacles;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = validateIntegers(num, MIN_NUM_OBSTACLES,MAX_NUM_OBSTACLES);
	}	
}
