package MotionPlanning;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import MapClass.Map;
import MapClass.Obstacle;

public class RRTstar {
	ArrayList<Node> path;
	ArrayList<Node> queue;
	Node currentNode; 
	Node goalNode; 
	long sampleN;
	Map map;
	final static double _tolerance=1E-6; 
	
	public RRTstar(Map map){
		this.map=map;
		initialize();
	}
	
	public Node sampleRandomPoint(){
		Random rand1=new Random(); 
		int x= (int) rand1.nextGaussian()*Map.WIDTH;
		int y=(int) rand1.nextGaussian()*Map.LENGTH;
		Node node=new Node(x,y);
		return node;
	}
	public void extend_queue(){
		//TODO
	}
	public void steer(){
		//TODO
	}
	public void initialize(){
		this.path=new ArrayList<Node>(); 
		this.queue=new ArrayList<Node>();
		// define start node as static and place in map class
		Node startnode=new Node(0,0);
		this.sampleN=Map.WIDTH*Map.LENGTH;
		this.currentNode=startnode;
	}
	public int getdistanceCost(Node from, Node to){
		double x=from.getX()-to.getX();
		double y=from.getY()-to.getY();
		return (int) Math.sqrt(x*x + y*y);
	}
	public Node getNearest(Node z_rand){
		long dist=234782723;
		Node node_near=null;
		for(Node n: this.queue){
			int d=this.getdistanceCost(n, z_rand);
			if(d<dist){
				node_near=n; 
				dist=d; 
			}
		}
	return node_near;
	}
	public Path steerFcn(Node z_rand, Node z_near){
		Path connectNodes=new Path((int)z_near.getX(), (int)z_near.getY(), (int)z_rand.getX(), (int)z_rand.getY());	
		double delta_y=z_near.getY()-z_rand.getY();
		double delta_x=z_near.getX()-z_rand.getX();
		double rot_vel=Math.atan2(delta_y, delta_x);
		if((Math.abs(rot_vel/4.0)-(Math.PI/4.0))<_tolerance){
			connectNodes=null;
		}
		return connectNodes;
	}
	public boolean getPath(){
		this.queue.add(currentNode);
		for(int i=0; i<sampleN; i++){
			Node z_rand=this.sampleRandomPoint();
			Node z_nearest=this.getNearest(z_rand);
			Path x_new=this.steerFcn(z_rand, z_nearest);
				if(!(this.map.getCspace().violatesCSpace(x_new))){
					z_rand.Parent= z_nearest;
					z_rand.cost=this.getdistanceCost(z_nearest, z_rand);
					this.queue.add(z_rand);
					if(z_rand==this.goalNode){
						return true;
					}
				}
			}
		return false;
		}
	
}
