package MapClass;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.JUnitCore;

public class MapTest {

	// validate num of obstacles stays between ranges of 0 and 10;
	@Test
	public void verifyObstacleLimits(){
		// greater than 10
		Map map= new Map(12);
		assertEquals(map.getNum(), map.MAX_NUM_OBSTACLES);
		// less than 0
		map.setNum(-1);
		assertEquals(map.getNum(), map.MIN_NUM_OBSTACLES);
		// equal to 0 
		map.setNum(map.MIN_NUM_OBSTACLES);
		assertEquals(map.getNum(), map.MIN_NUM_OBSTACLES);	
		// equal to 10 
		map.setNum(map.MAX_NUM_OBSTACLES);
		assertEquals(map.getNum(), map.MAX_NUM_OBSTACLES);
		// in between 0 and 10 
		map.setNum(5);
		assertEquals(map.getNum(),5);
	}
	public static void main(String[] args){
	JUnitCore.main(MapTest.class.getName());	
	}
}
