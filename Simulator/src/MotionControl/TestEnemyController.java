package MotionControl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Avatars.EnemyUAV;
import Avatars.PlayerUAV;
import MapClass.Obstacle;
import SimulatorGUI.DisplayServer;

public class TestEnemyController {

      /**
    * Test adding controller to enemyUAV
    */
   @Test
	public void setConstructorTest() {
	double[] pose = { 0, 0, 0 };
	DisplayServer sim = new DisplayServer();
	EnemyUAV gv = new EnemyUAV(pose, 1, 0);
	PlayerUAV player=new PlayerUAV();
	ArrayList<Obstacle> obsList=new ArrayList<Obstacle>();
	EnemyController vc = new EnemyController(gv,player,obsList,sim);
		
   }

   @Test(expected=IllegalArgumentException.class)
	public void addNullObsListTest() {
	   double[] pose = { 0, 0, 0 };
	   DisplayServer sim = new DisplayServer();
		EnemyUAV gv = new EnemyUAV(pose, 1, 0);
		PlayerUAV player=new PlayerUAV();
		ArrayList<Obstacle> obsList=null;
		EnemyController vc = new EnemyController(gv,player,obsList,sim);
		
		
   }
   @Test(expected=IllegalArgumentException.class)
	public void addNullPlayerTest() {
	   double[] pose = { 0, 0, 0 };
	   DisplayServer sim = new DisplayServer();
		EnemyUAV gv = new EnemyUAV(pose, 1, 0);
		PlayerUAV player=null;
		ArrayList<Obstacle> obsList=new ArrayList<Obstacle>();
		EnemyController vc = new EnemyController(gv,player,obsList,sim);
		
		
  }
   @Test(expected=IllegalArgumentException.class)
	public void addNullEnemyTest() {
	   DisplayServer sim = new DisplayServer();
		EnemyUAV gv = null;
		PlayerUAV player=new PlayerUAV();
		ArrayList<Obstacle> obsList=new ArrayList<Obstacle>();
		EnemyController vc = new EnemyController(gv,player,obsList,sim);
 }
   @Test(expected=IllegalArgumentException.class)
	public void addNullDisplayServerTest() {
	   double[] pose = { 0, 0, 0 };
	   DisplayServer sim = null;
		EnemyUAV gv = new EnemyUAV(pose, 1, 0);
		PlayerUAV player=new PlayerUAV();
		ArrayList<Obstacle> obsList=new ArrayList<Obstacle>();
		EnemyController vc = new EnemyController(gv,player,obsList,sim);
		
		
 }
   public static void main(String[] args){
	org.junit.runner.JUnitCore.main(TestEnemyController.class.getName());
   }
}
