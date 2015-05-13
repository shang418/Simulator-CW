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


public class Obstacle extends Rectangle {
	
	final static int MINSIZE=10; 
	final static int MAXSIZE=100;
	ObstacleImage image; 
		
		public class ObstacleImage extends ImageIcon{
				public ObstacleImage(){
					try {
						this.setImage(ImageIO.read(new File("Images/GameScreen/Obstacles/building.jpg")));
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
		
		public Image getImage(){
			return image.getImage();
		}

}
