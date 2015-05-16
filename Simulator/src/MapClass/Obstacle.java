package MapClass;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import MotionPlanning.Node;


public class Obstacle extends Rectangle {
	
	final static int MINSIZE=25; 
	final static int MAXSIZE=100;
	ObstacleImage image; 
		
		public class ObstacleImage extends ImageIcon{
				public ObstacleImage(){
					try {
						this.setImage(ImageIO.read(new File("Images/GameScreen/Obstacles/balloon.png")));
						this.setDescription("Building");
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		
		public Obstacle(int x, int y, int width, int height ){
			int[] pose= clampPosition(x,y,width,height);
			this.setRect(pose[0], pose[1], pose[2], pose[3]);
			
			image=new ObstacleImage();
			image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		}
		 private int[] clampPosition(int _x, int _y,int width, int height) {
			    _x = Math.min(Math.max(_x,0),900);
			    _y = Math.min(Math.max(_y,0),500);
			    height = Math.min(Math.max(height,MINSIZE),MAXSIZE);
			    width = Math.min(Math.max(width, MINSIZE),MAXSIZE);
			    return new int[]{_x,_y,width,height}; 
			  }

		public boolean contains(Node N){
			int delta_x=(int)(N.getX()-this.getX()); 
			int delta_y=(int)(N.getY()-this.getY());
			int count=0;
			if(delta_x<(this.getWidth()/2) && delta_x>(-this.getWidth()/2)){
				count++; 
			}
			if(delta_y<(this.getHeight()/2) && delta_y>(-this.getHeight()/2)){
				count++; 
			}
			if(count<2)
				return false;
			else return true;
		}
		@Override
		public void setBounds(int x, int y, int width, int height){
			int[] pose= clampPosition(x,y,width,height);
			this.setRect(pose[0], pose[1], pose[2], pose[3]);
			
			
		}
		public Image getImage(){
			return image.getImage();
		}

}
