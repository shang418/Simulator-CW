package MapClass;

import java.util.ArrayList;
import Avatars.*;

public class CSpace {
	ArrayList<Obstacle> cspace_obs;
	
	public CSpace(){ 
		this.cspace_obs=new ArrayList<Obstacle>();
	}
	
	public void configureCSpace(Map mapclass){
		for(Obstacle obs: mapclass.getList_obstacles()){
			int[] d={(int) obs.getX(), (int) obs.getY(), (int) obs.getWidth(),(int) obs.getHeight()};
			Obstacle n= new Obstacle(d[0]-Player.RADIUS,d[1]-Player.RADIUS,d[2]+2*Player.RADIUS,d[3]+2*Player.RADIUS);
			this.cspace_obs.add(n);
		}
	}
}
