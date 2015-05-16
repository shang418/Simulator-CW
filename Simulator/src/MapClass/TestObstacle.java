package MapClass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Avatars.EnemyUAV;

public class TestObstacle {
	@Test
    public void testConstructor() {
    int [] pose = {1, 2, 3,4};
    Obstacle obs = new Obstacle(pose[0], pose[1], pose[2], pose[3]);
    assertEquals(pose[0], obs.getX(), 1e-6);
    assertEquals(pose[1], obs.getY(), 1e-6);
    assertEquals(pose[2], obs.getWidth(), 1e-6);
    assertEquals(pose[3], obs.getHeight(), 1e-6);
  }

  // Test get/set Position/Velocity at all legal position bounds

  @Test
    public void testGetSetPositionValid() {
    int [] pose = {1, 2, 30,50};
    
    Obstacle obs = new Obstacle(pose[0], pose[1], pose[2], pose[3]);
    assertEquals(pose[0], obs.getX(), 1e-6);
    assertEquals(pose[1], obs.getY(), 1e-6);
    assertEquals(pose[2], obs.getWidth(), 1e-6);
    assertEquals(pose[3], obs.getHeight(), 1e-6);


    // First test getPosition and setPosition at legal bounds

    pose[0] = 0;
   obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
    double newPose = obs.getX();
    assertEquals(pose[0], newPose, 1e-6);

    pose[0] = 900;
    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
     newPose = obs.getX();
    assertEquals(pose[0], newPose, 1e-6);

    pose[1] = 0;
    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
     newPose = obs.getY();
    assertEquals(pose[1], newPose, 1e-6);

    pose[1] = 500;
    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
     newPose = obs.getY();
    assertEquals(pose[1], newPose, 1e-6);

    pose[2] = 25;
    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
     newPose = obs.getWidth();
    assertEquals(pose[2], newPose, 1e-6);

    pose[2] = 100;
    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
    newPose = obs.getWidth();
   assertEquals(pose[2], newPose, 1e-6);
   
   pose[3] = 25;
   obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
    newPose = obs.getHeight();
   assertEquals(pose[3], newPose, 1e-6);

   pose[3] = 100;
   obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
   newPose = obs.getHeight();
  assertEquals(pose[3], newPose, 1e-6);


  }

  // Test get/set Position and Velocity at illegal position bounds

  @Test
    public void testGetSetPositionInvalid(){
	  int [] pose = {1, 2, 3,4};
	    
	    Obstacle obs = new Obstacle(pose[0], pose[1], pose[2], pose[3]);

	    // First test getPosition and setPosition at illegal bounds

	    pose[0] = -1;
	   obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	    int newPose = 0;
	    assertEquals(pose[0], newPose, 1e-6);

	    pose[0] = 901;
	    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	     newPose = 900;
	    assertEquals(pose[0], newPose, 1e-6);

	    pose[1] = -1;
	    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	     newPose = 0;
	    assertEquals(pose[1], newPose, 1e-6);

	    pose[1] = 501;
	    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	     newPose = 500;
	    assertEquals(pose[1], newPose, 1e-6);

	    pose[2] = 24;
	    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	     newPose = 25;
	    assertEquals(pose[2], newPose, 1e-6);

	    pose[2] = 101;
	    obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	    newPose = 100;
	   assertEquals(pose[2], newPose, 1e-6);
	   
	   pose[3] = 24;
	   obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	    newPose = 25;
	   assertEquals(pose[3], newPose, 1e-6);

	   pose[3] = 101;
	   obs.setBounds(pose[0], pose[1], pose[2], pose[3]);
	   newPose = 100;
	  assertEquals(pose[3], newPose, 1e-6);




  }


}
