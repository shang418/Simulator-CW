package MotionControl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;

public class TestControl {   
  @Test(expected=IllegalArgumentException.class)
    public void makeLowS(){
    //Put an invalid low s value in. Should throw an exception
    new Control(0,0);
  }
   
  @Test(expected=IllegalArgumentException.class)
    public void makeHighS(){
    //Put an invalid low s value in. Should throw an exception
    new Control(0,50);
  }
 
  @Test(expected=IllegalArgumentException.class)
    public void makeLowTheta(){
    //Put an invalid low s value in. Should throw an exception
    new Control(-10,7.5);
  }
 
  @Test(expected=IllegalArgumentException.class)
    public void makeHighTheta(){
    //Put an invalid low s value in. Should throw an exception
    new Control(10,7.5);
  }
 
  @Test
    public void CheckOutputs(){
    double s = 7.5;
    double omega = .3;
    Control test = new Control(s,omega);
    assertEquals(omega, test.getRotVel(), 1e-6);
    assertEquals(s, test.getSpeed(), 1e-6);
  }
 
  public static void main(String[] args){
    JUnitCore.main(TestControl.class.getName());
  }
}