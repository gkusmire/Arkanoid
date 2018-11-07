package model;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import others.MapSize;

import enumTypes.BonusAction;
import enumTypes.Direction;
import enumTypes.HorizontalDirection;

import actions.EndOfGameEvent;
import actions.ExtendedActionEvent;
import actions.ResetEvent;
import actions.StopEvent;

public class Model {
	
	public Model(GameBoardElements objects, BlockingQueue<ExtendedActionEvent> blockingQueue) throws Exception 
	{
		checkArgs(objects, blockingQueue);
		this.objects = objects;
		this.queue = blockingQueue;
		map = new Map();
	}
	public void moveObjects()
	{
		movePoints();
		moveBalls();
		objects.platform.move();
		if(!isAnyBallOnMap()){
			prepareModelAfterBallFallingOut();
		}
		checkIfEndOfGame();
	}
	public void stopMovingPlatform(){
		objects.platform.stopMovingPlatform();
	}
	public GameBoardElements getObjectPos()
	{
		return objects;
	}
	public void setDirPlatform(HorizontalDirection dir)
	{
		objects.platform.setDir(dir);
	}
	public void reset() {
		objects = new GameBoardElements();
		map = new Map();
	}
	private void moveBalls()
	{
		Ball b;
		for(int i = 0; i < objects.balls.size();)
		{
			b = (Ball)objects.balls.get(i);
			b.move();
			bounceFromPlatform(b);
			bounceFromBlock(b);	
			if(isBallOutsideMap(b)) {
				removeBallFromList(b);
			} else {
				++i;
			}
		}
		return;
	}
	private void createNewBall(){
		try {
			objects.balls.add(new Ball (objects.startingBallPosition, 
						objects.ballSpeed, new MapSize(objects.mapSize.getWidth()*50, objects.mapSize.getHeight()*50), 
						objects.ballSize));
		} catch (Exception e) {
			System.err.print("BLAD dodania elementu (kulki) do listy");
			e.printStackTrace();
			System.exit(1);
		}
	}
	private void prepareModelAfterBallFallingOut(){
		objects.lifes.decLife();
		createNewBall();
		objects.platform.reset();
		putStopEvent();
	}
	private boolean isAnyBallOnMap(){
		return objects.balls.size() != 0;
	}
	private void removeBallFromList(Ball b)
	{
		objects.balls.remove(b);
	}
	private void bounceFromBlock(Ball b){
		b.bounce(map.remCollisions(b.getXpos(), b.getYpos(), b.getBallSize()));
	}
	private void bounceFromPlatform(Ball b){
		if(isBallTouchingPlatform(b))
		{
			b.changeDir(objects.platform.getDir());
		}
	}
	private void checkIfEndOfGame(){
		if(objects.lifes.getLifes() == 0){
			putResetEvent();
		}
		if(objects.blocks.size() == 0 && objects.points.size() == 0){
			try {
				queue.put(new EndOfGameEvent());
			} catch (InterruptedException e) {
				System.err.print("BLAD dodania elementu do kolejki blokującej");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	private void movePoints() {
		Points p;
		
		for (int i = 0; i< objects.points.size();) {
			p = objects.points.get(i);
			p.move();
			if(isOnLowerMapBorder(p)){
				if(isOnPlatform(p))
					objects.scores += p.getValue();
				removeRunningPoints(p);
			} else {
				++i;
			}
		}
	}
	private boolean isOnPlatform(Points p) {
		if(p.getXpos() >= objects.platform.getXpos() 
				&& p.getXpos() <= objects.platform.getLength() + objects.platform.getXpos())
			return true;
		return false;
	}
	public void removeRunningPoints(Points p) {
		objects.points.remove(p);
	}
	private boolean isOnLowerMapBorder(Points p) {
		return p.getYpos() == objects.mapSize.getHeight()*50;
	}
	private boolean isBallTouchingPlatform(Ball b)
	{
		return b.getYpos() >= objects.mapSize.getHeight()*50;
	}
	private boolean isBallOutsideMap(Ball b)
	{
		return isBallOnLowerBorder(b) && !isBallAbovePlatform(b);
	}
	private boolean isBallAbovePlatform(Ball b) {
		
		return objects.platform.getXpos() <= b.getXpos() && 
				objects.platform.getXpos()+objects.platform.getLength() >= b.getXpos();
	}
	private boolean isBallOnLowerBorder(Ball b){
		return b.getYpos() >= objects.mapSize.getHeight()*50-((Ball)b).getBallSize();
	}
	private void putResetEvent() {
		try {
			queue.put((ExtendedActionEvent)new ResetEvent());
		} catch (InterruptedException e1) {
			System.err.print("BLAD dodania elementu do kolejki blokującej");
			e1.printStackTrace();
			System.exit(1);
		}
	}
	private void putStopEvent() {
		try {
			queue.put((ExtendedActionEvent)new StopEvent());
		} catch (InterruptedException e1) {
			System.err.print("BLAD dodania elementu do kolejki blokującej");
			e1.printStackTrace();
			System.exit(1);
		}
	}
	private void checkArgs(GameBoardElements objects, BlockingQueue<ExtendedActionEvent> blockingQueue) throws Exception{
		if(objects == null){
			throw new Exception("ERROR: Argument of ObjectsPos type is incorrect");
		}
		if(blockingQueue == null){
			throw new Exception("ERROR: Argument of BlockingQueue<ExtendedActionEvent> type is incorrect");
		}
		checkObjects(objects);
	}
	private void checkObjects(GameBoardElements obj) throws Exception{
		checkIfListNotEmpty(obj.balls);
		checkIfListNotEmpty(obj.blocks);
		checkIfPositive(obj.mapSize.getWidth());
		checkIfPositive(obj.mapSize.getHeight());
		checkBlocks(obj);
		checkBalls(obj);
	}
	private void checkBalls(GameBoardElements obj) throws Exception{
		ObjectPos b;
		for(int i = 0; i < obj.balls.size();++i)
		{
			b = obj.balls.get(i);
			if(b.getXpos()/50 >= obj.mapSize.getWidth() || 
					b.getYpos()/50 >= obj.mapSize.getHeight()){
				throw new Exception("ERROR: Ball outside Map!!!");
			}
		}
	}
	private void checkIfPositive(int x) throws Exception{
		if(x <= 0){
			throw new Exception("ERROR: wrong map size");
		}
	}
	private void checkBlocks(GameBoardElements obj) throws Exception{
		ObjectPos b;
		for(int i = 0; i < obj.blocks.size();++i)
		{
			b = obj.blocks.get(i);
			if(b.getXpos() >= obj.mapSize.getWidth() || 
					b.getYpos() >= obj.mapSize.getHeight()){
				throw new Exception("ERROR: Block outside Map!!!");
			}
		}
	}
	private void checkIfListNotEmpty(ArrayList<ObjectPos> list) throws Exception{
		if(list.size() == 0){
			throw new Exception("ERROR: There are wrong number of objects!!!");
		}
	}
	private class Map{
		public Map(){
			map = new ObjectPos[objects.mapSize.getHeight()][objects.mapSize.getWidth()];
			for(ObjectPos p : objects.blocks){
				map[p.getYpos()][p.getXpos()] = p;
			}
		}
		public Direction remCollisions(int x, int y, int ballSize)
		{
			int size = (int)ballSize/2-1;
			int xPos = (int) x + size;
			int yPos = (int) y + size;
			if(removeIfOnLeftBlock(xPos, yPos, size) || 
					removeIfOnRightBlock(xPos, yPos, size)){
				return Direction.VERTICAL;
			}
			if( removeIfOnUpperBlock(xPos, yPos, size) || 
					removeIfOnLowerBlock(xPos, yPos, size)){
				return Direction.HORIZONTAL;
			}
			return null;
		}
		private boolean removeIfOnLeftBlock(int x, int y, int ballSize)
		{
			int xPos = (x-ballSize)/50;
			int yPos = y/50;
			if(map[yPos][xPos] != null){
				removeBlock(xPos, yPos);
				return true;
			}
			return false;
		}
		private boolean removeIfOnRightBlock(int x, int y, int ballSize)
		{
			int xPos = (x+ballSize)/50;
			int yPos = y/50;
			if(map[yPos][xPos] != null){
				removeBlock(xPos, yPos);
				return true;
			}
			return false;
		}
		private boolean removeIfOnUpperBlock(int x, int y, int ballSize)
		{
			int xPos = x/50;
			int yPos = (y-ballSize)/50;
			if(map[yPos][xPos] != null){
				removeBlock(xPos, yPos);
				return true;
			}
			return false;
		}
		private boolean removeIfOnLowerBlock(int x, int y, int ballSize)
		{
			int xPos = x/50;
			int yPos = (y+ballSize)/50;
			if(map[yPos][xPos] != null){
				removeBlock(xPos, yPos);
				return true;
			}
			return false;
		}
		private void removeBlock(int x, int y)
		{
			if(map[y][x] != null){
				createRunningPoints(((Block)map[y][x]));
				createBonus(((Block) map[y][x]).getBonus());
				objects.blocks.remove(map[y][x]);
				map[y][x] = null;
			}
		}
		
		private void createBonus(BonusAction b)
		{
			switch(b){
			case LENGHTEN_PLATFORM:
				objects.platform.lengthen(50);
				break;
			case SHORTEN_PLATFORM:
				objects.platform.shorten(50);
				break;
			case SPEED_UP_BALL:
				speedUpBalls(0.2);
				break;
			case SLOW_DOWN_BALL:
				slowDownBall(0.2);
				break;
			case TRIPLE_BALL:
				tripleBall();
				break;
			case NOTHING:
				break;
			}
		}
		
		private void tripleBall() {
			try{
				Ball b = (Ball) objects.balls.get(0);
				Ball ball = new Ball(b);
				ball.bounce(Direction.VERTICAL);
				objects.balls.add(ball);
				ball = new Ball(b);
				ball.bounce(Direction.HORIZONTAL);
				objects.balls.add(ball);
			} catch(Exception e){
				System.err.print("BLAD dodania elementu do listy");
				e.printStackTrace();
				System.exit(1);
			}
		}
		private int speedUpBalls(double d)
		{
			for(ObjectPos b: objects.balls)
			{
				((Ball) b).increaseSpeed(d);
			}
			return 0;
		}
		private int slowDownBall(double d)
		{
			for(ObjectPos b: objects.balls)
			{
				((Ball) b).decreaseSpeed(d);
			}
			return 0;
		}
		private void createRunningPoints(Block b)
		{
			try{
				objects.points.add(new Points(b.getXpos()*50, b.getYpos()*50, 
					b.getPoints(), objects.mapSize.getHeight()*50));
			} catch(Exception e){
				System.err.print("BLAD dodania elementu do kolejki blokującej");
				e.printStackTrace();
				System.exit(1);
			}
		}
		private ObjectPos[][] map;
	}
	private Map map;
	private GameBoardElements objects;
	private BlockingQueue<ExtendedActionEvent> queue;
}