package MapClass;

import java.awt.Rectangle;
import java.awt.geom.*;

public class Obstacle extends Rectangle {
	
	final static int MINSIZE=5; 
	final static int MAXSIZE=10;
	
		public Obstacle(int x, int y, int width, int height ){
			this.setRect(x, y, width, height);
		}
		

}
