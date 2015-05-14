package MotionPlanning;

import java.awt.geom.Line2D;

import javafx.scene.shape.Line;


public class Path extends Line{

	public Path(int sx, int sy, int ex, int ey)  {
		this.setStartX(sx);
		this.setStartY(sy); 
		this.setEndX(ex);
		this.setEndY(ey);
	}
	

}
