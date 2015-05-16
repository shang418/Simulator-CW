package MapClass;

import java.awt.Point;
import java.util.ArrayList;

import Avatars.*;
import MotionPlanning.Node;
import MotionPlanning.Path;

public class CSpace {
	ArrayList<Obstacle> cspace_obs;
	
	public CSpace(){ 
		this.cspace_obs=new ArrayList<Obstacle>();
	}
	
	public void configureCSpace(Map mapclass){
		for(Obstacle obs: mapclass.getList_obstacles()){
			int[] d={(int) obs.getX(), (int) obs.getY(), (int) obs.getWidth(),(int) obs.getHeight()};
			Obstacle n= new Obstacle(d[0]-EnemyUAV.RADIUS/2,d[1]-EnemyUAV.RADIUS/2,d[2]+2*EnemyUAV.RADIUS/2,d[3]+2*EnemyUAV.RADIUS/2);
			this.cspace_obs.add(n);
		}
	}

	public ArrayList<Obstacle> getCspace_obs() {
		return cspace_obs;
	}

	public void setCspace_obs(ArrayList<Obstacle> cspace_obs) {
		this.cspace_obs = cspace_obs;
	}
	
	public boolean violatesCSpace(Node n){
		for(Obstacle obs: cspace_obs){
			System.out.println("Node: ["+n.getX()+","+n.getY());
			System.out.println("Obstacle: ["+obs.getX()+","+obs.getY());
			if(obs.contains(n)){
				System.out.println("Invading my space!!");
				return true;
			}
		}
	return false;
	}
	public boolean violatesCSpace(Path p){
		for(Obstacle obs: cspace_obs){
			if(obs.intersectsLine(p.getStartX(), p.getScaleY(), p.getEndX(), p.getEndY())){
				return true;
			}
		}
	return false;
	}
}
