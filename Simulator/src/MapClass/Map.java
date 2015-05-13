package MapClass;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel {
	// fix the width and length of the map 
	final static int WIDTH=480; 
	final static int LENGTH=680; 
	final static int MAX_NUM_OBSTACLES=15; 
	final static int MIN_NUM_OBSTACLES=5;
	final Point startpoint=new Point(450,650); 
	final Point goalpoint=new Point(10,10);
	ArrayList<Obstacle> list_obstacles; 
	 Image mapImage; 
	/*
	 * <p> the number of obstacles located on the map
	 */
	int num; 
	
	public Map(int num_obs){
			this.num=validateIntegers(num_obs, MIN_NUM_OBSTACLES,MAX_NUM_OBSTACLES);
			this.list_obstacles=new ArrayList<Obstacle>();
			try {
				this.mapImage=ImageIO.read(new File("Images/GameScreen/background.jpg"));
				this.mapImage=mapImage.getScaledInstance(LENGTH, WIDTH, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Loading image file exception");
				e.printStackTrace();
			}
			this.setPreferredSize(new Dimension(LENGTH, WIDTH));
			this.setMinimumSize(new Dimension(LENGTH, WIDTH));
			this.setMaximumSize(new Dimension(LENGTH, WIDTH));
			this.setOpaque(true);
			this.setVisible(true);
			generateObstacles();
			addWallsasObstacles();
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
		return n; 
		}
	public void addWallsasObstacles(){
		Obstacle leftWall=new Obstacle(0,0,1,WIDTH-1); 
		list_obstacles.add(leftWall);
		Obstacle topWall=new Obstacle(0,0,LENGTH-1,1); 
		list_obstacles.add(topWall);
		Obstacle rightWall=new Obstacle(0,LENGTH,1,WIDTH-1); 
		list_obstacles.add(rightWall);
		Obstacle bottomWall=new Obstacle(WIDTH,0,LENGTH-1,1); 
		list_obstacles.add(leftWall);
	}
	public void generateObstacles(){
		// leave capability for different types of obstacles
		Random rand=new Random();
		for(int i=0; i<num; i++){
			int x=this.validateIntegers(rand.nextInt(LENGTH +1), 0, LENGTH);
			int y =this.validateIntegers(rand.nextInt(WIDTH +1),0,WIDTH);
			int w=this.validateIntegers((rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE), Obstacle.MINSIZE, Obstacle.MAXSIZE);
			int l=this.validateIntegers((rand.nextInt(Obstacle.MAXSIZE-Obstacle.MINSIZE +1) + Obstacle.MINSIZE), Obstacle.MINSIZE, Obstacle.MAXSIZE);
			// if obstacle is always a rectangle, then only provide width and length;
			Obstacle newObs=new Obstacle(x,y,w,l);
			newObs=isValidObject(newObs);
			System.out.println("Obstacle Height and Width: "+newObs.getHeight()+", "+newObs.getWidth());
			this.list_obstacles.add(newObs);
		}
	}
	@Override 
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//BufferedImage buffimage=new BufferedImage(Obstacle.MINSIZE, Obstacle.MAXSIZE, BufferedImage.TYPE_INT_ARGB);
	//	Graphics2D bg=buffimage.createGraphics();
		g.drawImage(this.mapImage, 0, 0,null);
		for(Obstacle ob: this.list_obstacles){
			System.out.println("image height and width: ["+ob.getImage().getHeight(null)+","+ob.getImage().getWidth(null)+"]");
			g.drawImage(ob.getImage(), (int) ob.getY(), (int) ob.getX(),null);
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
			if(LENGTH-((int)(newObs.getMaxX()))<0){
				isvalid=false; 
			}
			if(WIDTH-((int)(newObs.getMaxY()))<0){
				isvalid=false;
			}
			for(Obstacle ob: list_obstacles){
				if(newObs.intersects(ob)){
					isvalid=false;
				}
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
