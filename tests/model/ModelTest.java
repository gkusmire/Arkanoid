package tests.model;

import static org.junit.Assert.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import model.Ball;
import model.Block;
import model.Model;
import model.GameBoardElements;
import model.Points;
import org.junit.Before;
import org.junit.Test;

import enumTypes.BonusAction;
import enumTypes.Direction;
import actions.ExtendedActionEvent;

public class ModelTest {

	@Before
	public void initialize() {
		obj = new GameBoardElements();
		objCopy = new GameBoardElements();
		queue = new LinkedBlockingQueue<ExtendedActionEvent>();
		try{
			model = new Model(obj, queue);
		} catch(Exception e) {
			fail("ERROR: cannot create Model");
			e.printStackTrace();
		}
	}
	
	@Test (expected = Exception.class)
	public void nullPoinerAsArgument1Test() throws Exception {
		new Model(null, new LinkedBlockingQueue<ExtendedActionEvent>());
	}
	
	@Test (expected = Exception.class)
	public void nullPoinerAsArgument2Test() throws Exception {
		new Model(new GameBoardElements(), null);
	}
	
	@Test 
	public void moveObjectsTest() throws Exception {
		model.moveObjects();
		assertNotEquals(objCopy.balls.get(0).getXpos(), obj.balls.get(0).getXpos());
		assertNotEquals(objCopy.balls.get(0).getYpos(), obj.balls.get(0).getYpos());
	}
	
	@Test
	public void BounceHorizontalBallFromBlockTest(){
		setBallUnderBlock(BonusAction.NOTHING);
		model.moveObjects();
		Ball ball = (Ball)obj.balls.get(0);
		if(ball.getYvelocity() < 0){
			fail("Ball not bounced");
		}
	}
	
	@Test
	public void platformShouldBeLengthenedAfterBeatingBonusBlock(){
		setBallUnderBlock(BonusAction.LENGHTEN_PLATFORM);
		int l = obj.platform.getLength();
		model.moveObjects();
		assertNotEquals(l, obj.platform.getLength());
	}
	
	@Test
	public void platformShouldBeShortenedAfterBeatingBonusBlock(){
		setBallUnderBlock(BonusAction.SHORTEN_PLATFORM);
		int l = obj.platform.getLength();
		model.moveObjects();
		assertNotEquals(l, obj.platform.getLength());
	}
	
	@Test
	public void ballShouldBeSpeededUpAfterBeatingBonusBlock(){
		setBallUnderBlock(BonusAction.SPEED_UP_BALL);
		double s = ((Ball)obj.balls.get(0)).getSpeed();
		model.moveObjects();
		assertNotEquals(s, ((Ball)obj.balls.get(0)).getSpeed());
	}
	
	@Test
	public void ballShouldBeSlowedDownAfterBeatingBonusBlock(){
		setBallUnderBlock(BonusAction.SLOW_DOWN_BALL);
		((Ball)obj.balls.get(0)).increaseSpeed(0.3);
		double s = ((Ball)obj.balls.get(0)).getSpeed();
		model.moveObjects();
		assertNotEquals(s, ((Ball)obj.balls.get(0)).getSpeed());
	}
	
	@Test
	public void ballShouldBeTripledAfterBeatingBonusBlock(){
		setBallUnderBlock(BonusAction.TRIPLE_BALL);
		model.moveObjects();
		assertEquals(obj.balls.size(), 3);
	}

	@Test
	public void pointsShouldAppearAfterBeatingBlock(){
		setBallUnderBlock(BonusAction.NOTHING);
		model.moveObjects();
		if(obj.points.size() != 1){
			fail("Running points should appear after beating block");
		}
	}
	
	@Test
	public void pointsShouldRunning(){
		setBallUnderBlock(BonusAction.NOTHING);
		model.moveObjects();
		Points points = obj.points.get(0);
		int yPos = points.getYpos();
		model.moveObjects();
		assertNotEquals(yPos, points.getYpos());
	}
	
	@Test
	public void pointsShouldDisappearAfterMovingOutsideMap(){
		try {
			obj.points.add(new Points(0, obj.mapSize.getHeight()*50-1, 100, obj.mapSize.getHeight()*50));
			model = new Model(obj, queue);
		} catch (Exception e) {
			fail("ERROR: cannot create Model");
			e.printStackTrace();
		}
		model.moveObjects();
		assertEquals(obj.points.size(), 0);
	}
	
	@Test
	public void pointsShouldBeAddedAfterTouchingPlatform(){
		try {
			obj.points.add(new Points(5, obj.mapSize.getHeight()*50-1, 100, obj.mapSize.getHeight()*50));
			model = new Model(obj, queue);
		} catch (Exception e) {
			fail("ERROR: cannot create Model");
			e.printStackTrace();
		}
		obj.platform.setXpos(0);
		int scores = obj.scores;
		model.moveObjects();
		if(scores + 100 != obj.scores){
			fail("Scores should be added after touching platform");
		}
	}
	
	
	
	@Test
	public void pointsShouldBeNotAddedAfterBeingOutsideMap(){
		try {
			obj.points.add(new Points(0, obj.mapSize.getHeight()*50-1, 100, obj.mapSize.getHeight()*50));
			model = new Model(obj, queue);
		} catch (Exception e) {
			fail("ERROR: cannot create Model");
			e.printStackTrace();
		}
		obj.platform.setXpos(10);
		int scores = obj.scores;
		model.moveObjects();
		assertEquals(scores, obj.scores);
	}
	
	@Test
	public void BounceVerticalBallFromBlockTest(){
		setBallNextToBlock();
		model.moveObjects();
		Ball ball = (Ball)obj.balls.get(0);
		if(ball.getXvelocity() < 0){
			fail("Ball not bounced");
		}
	}
	
	@Test
	public void RemoveBlockAfterBallBouncing(){
		setBallUnderBlock(BonusAction.NOTHING);
		model.moveObjects();
		assertEquals(objCopy.blocks.size(), obj.blocks.size());
	}
	
	@Test
	public void BounceBallFromPlatform(){
		Ball b = (Ball)obj.balls.get(0);
		setBallAbovePlatform();
		model.moveObjects();
		if(b.getYvelocity() > 0){
			fail("Ball not bounced");
		}
	}
	
	@Test
	public void moveBallOutsideMap(){
		setBallAbovePlatform();
		obj.platform.setXpos(20);
		model.moveObjects();
		if(queue.size() != 1){
			fail("There should be put an ResetEvent in the Queue");
		}
	}
	
	private void setBallNextToBlock(){
		Block b;
		Ball ball = (Ball)obj.balls.get(0);
		try {
			b = new Block(0, 0, 100, BonusAction.LENGHTEN_PLATFORM);
			obj.blocks.add(b);
		} catch (Exception e) {
			fail("ERROR: cannot create block");
		}
		try{
			model = new Model(obj, queue);
		} catch (Exception e) {
			fail("ERROR: cannot create Model");
		}
		ball.setXpos(50);
		ball.setYpos(15);
		if(ball.getXvelocity() > 0){
			ball.bounce(Direction.VERTICAL);
		}
	}
	
	private void setBallAbovePlatform(){
		Ball b = (Ball)obj.balls.get(0);
		b.setYpos(obj.mapSize.getHeight()*50-b.getBallSize());
		b.setXpos(15);
		b.bounce(Direction.HORIZONTAL);
		obj.platform.setXpos(10);
	}
	
	private void setBallUnderBlock(BonusAction bonus){
		Block b;
		Ball ball = (Ball)obj.balls.get(0);
		try {
			b = new Block(0, 0, 100, bonus);
			obj.blocks.add(b);
		} catch (Exception e) {
			fail("ERROR: cannot create block");
		}
		try{
			model = new Model(obj, queue);
		} catch (Exception e) {
			fail("ERROR: cannot create Model class");
		}
		ball.setXpos(15);
		ball.setYpos(51);
	}
	private Model model;
	private BlockingQueue<ExtendedActionEvent> queue;
	private GameBoardElements obj, objCopy;
}
