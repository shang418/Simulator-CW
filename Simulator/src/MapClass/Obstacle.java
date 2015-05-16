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
			this.setRect(x, y, width, height);
			image=new ObstacleImage();
			image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
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
		public Image getImage(){
			return image.getImage();
		}

}
