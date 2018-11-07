package tests.model;



import static org.junit.Assert.*;

import model.Ball;

import org.junit.Test;

import others.MapSize;
import others.Position;

import enumTypes.Direction;

public class BallTest {
	@Test
	public void testBall() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			assertEquals(1, b.getXpos());
			assertEquals(2, b.getYpos());
			assertEquals(6, b.getBallSize());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(expected = Exception.class)
	public void testBall_tooBigBall() throws Exception{
		new Ball(new Position(0, 0), 0.1, new MapSize(20,  20), 30);
	}
	
	@Test(expected = Exception.class)
	public void testBall_ballOverMap() throws Exception{
		new Ball(new Position(0, 25), 0.1, new MapSize(20,  20), 1);
	}
	
	@Test(expected = Exception.class)
	public void testBall_negativeXpos() throws Exception{
		new Ball(new Position(-1, 15), 0.1, new MapSize(20,  20), 1);
	}
	
	@Test(expected = Exception.class)
	public void testBall_negativeYpos() throws Exception{
		new Ball(new Position(0, -1), 0.1, new MapSize(20,  20), 1);
	}
	
	@Test(expected = Exception.class)
	public void testBall_negativeSpeed() throws Exception{
		new Ball(new Position(0, 0), -1, new MapSize(20,  20), 1);
	}
	
	@Test(expected = Exception.class)
	public void testBall_negativeMapWidth() throws Exception{
		new Ball(new Position(0, 0), 1, new MapSize(-20,  20), 1);
	}
	
	@Test(expected = Exception.class)
	public void testBall_negativeMapHeight() throws Exception{
		new Ball(new Position(0, 0), 1, new MapSize(20,  -20), 1);
	}
	
	@Test(expected = Exception.class)
	public void testBall_negativeBallSize() throws Exception{
		new Ball(new Position(0, 0), 1, new MapSize(20,  20), -1);
	}
	
	@Test
	public void testIncreaseSpeed() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			double s = b.getSpeed();
			b.increaseSpeed(0.1);
			assertEquals(s+0.1, b.getSpeed(), 0);
		} catch(Exception e){
			e.printStackTrace();
			return;
		}	
	}
	
	@Test
	public void testIncreaseSpeed_negativeArgument() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			double s = b.getSpeed();
			b.increaseSpeed(-1);
			assertEquals(s, b.getSpeed(), 0);
		} catch(Exception e){
			e.printStackTrace();
			return;
		}	
	}
	
	@Test
	public void testDecreaseSpeed() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			double s = b.getSpeed();
			b.increaseSpeed(1);
			b.decreaseSpeed(1.1);
			assertEquals(s-0.1, b.getSpeed(), 0);
		} catch(Exception e){
			e.printStackTrace();
			return;
		}	
	}
	
	@Test
	public void testDecreaseSpeed_negativeArgument() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			double s = b.getSpeed();
			b.decreaseSpeed(-1);
			assertEquals(s, b.getSpeed(), 0);
		} catch(Exception e){
			e.printStackTrace();
			return;
		}	
	}
	
	@Test
	public void testDecreaseSpeed_tooBigValueAsArgument() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			double s = b.getSpeed();
			b.decreaseSpeed(3);
			assertEquals(s, b.getSpeed(), 0);
		} catch(Exception e){
			e.printStackTrace();
			return;
		}	
	}
	
	@Test
	public void testBounce_verticalBounce() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			int xV = b.getXvelocity();
			b.bounce(Direction.VERTICAL);
			assertEquals(xV*(-1), b.getXvelocity());
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	@Test
	public void testBounce_horizontalBounce() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			int yV = b.getYvelocity();
			b.bounce(Direction.HORIZONTAL);
			assertEquals(yV*(-1), b.getYvelocity());
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	@Test
	public void testBounce_incorrectArg() {
		try{
			Ball b = new Ball(new Position(1, 2), 3, new MapSize(10,  20), 6);
			int yV = b.getYvelocity();
			int xV = b.getXvelocity();
			b.bounce(null);
			assertEquals(yV, b.getYvelocity());
			assertEquals(xV, b.getXvelocity());
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	@Test
	public void testMove() {
		try{
			Ball b = new Ball(new Position(5, 5), 3, new MapSize(100,  200), 6);
			int x = b.getXpos();
			int y = b.getYpos();
			b.move();
			assertNotEquals(b.getXpos(), x, 0);
			assertNotEquals(b.getYpos(), y, 0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMove_ballExceedsLeftMapBorder() {
		try{
			Ball b = new Ball(new Position(0, 5), 3, new MapSize(100,  200), 6);
			b.move();
			if(b.getXpos() < 0) {
				fail("ERROR: ball over the map");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMove_ballExceedsRightMapBorder() {
		try{
			Ball b = new Ball(new Position(0, 94), 3, new MapSize(100,  200), 6);
			b.bounce(Direction.VERTICAL);
			b.move();
			if(b.getXpos() > 94) {
				fail("ERROR: ball over the map");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMove_ballExceedsUpperMapBorder() {
		try{
			Ball b = new Ball(new Position(10, 0), 3, new MapSize(100,  200), 6);
			b.move();
			if(b.getYpos() < 0) {
				fail("ERROR: ball over the map");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMove_ballExceedsLowerMapBorder() {
		try{
			Ball b = new Ball(new Position(10, 194), 3, new MapSize(100,  200), 6);
			b.bounce(Direction.HORIZONTAL);
			b.move();
			if(b.getYpos() > 194) {
				fail("ERROR: ball over the map");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
