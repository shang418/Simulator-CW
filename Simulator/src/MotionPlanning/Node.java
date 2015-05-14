package MotionPlanning;

import java.awt.Point;

public class Node extends Point {

	double cost; 
	boolean isFree; 
	Node Parent; 
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public Node getParent() {
		return Parent;
	}

	public void setParent(Node parent) {
		Parent = parent;
	}

	public Node(int x, int y) {
		this.setLocation((double) x, (double)y);
		this.isFree=false; 
	}

}
