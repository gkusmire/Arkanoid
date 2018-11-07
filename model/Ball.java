package model;


import others.MapSize;
import others.Position;
import enumTypes.Direction;
import enumTypes.HorizontalDirection;


	public class Ball extends ObjectPos{
	
	public Ball(Position position, double speed, MapSize mapSize, int ballSize) throws Exception
	{
		super(position);
		checkArgs(position, speed, mapSize, ballSize);
		xV = -3;
		yV = -7;
		this.speed = speed;
		this.width = mapSize.getWidth();
		this.height = mapSize.getHeight();
		this.ballSize = ballSize;
	}
	public Ball(Ball b) throws Exception {
		this(new Position(b.getXpos(), b.getYpos()), b.getSpeed(), new MapSize(b.getWidth(), b.getHeight()), b.getBallSize());
	}
	public void move()
	{
		int xpos = super.getXpos();
		int ypos = super.getYpos();
		
		xpos += round(xV*speed);
		ypos += round(yV*speed);
		xpos = checkIfXposOutsideMap(xpos);
		ypos = checkIfYposOutsideMap(ypos);
		super.setXpos(xpos);
		super.setYpos(ypos);
	}
	public void changeDir(HorizontalDirection dir){
		if(dir == HorizontalDirection.LEFT && (xV > -7)){
			xV -= 3;
			yV += 3;
		}
		if(dir == HorizontalDirection.RIGHT && (xV < 7)){
			xV += 3;
			yV -= 3;
		}
	}
	public void bounce(Direction dir)
	{
		if(dir == Direction.VERTICAL)
			bounceVertical();
		if(dir == Direction.HORIZONTAL)
			bounceHorizontal();
	}
	public int getBallSize()
	{
		return ballSize;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void increaseSpeed(double s)
	{
		if(s <= 0 || state > 0){
			return;
		}
		speed += s;
		state += 1;
		return;
	}
	public void decreaseSpeed(double s)
	{
		if(s <= 0 || s >= speed || state <= 0){
			return;
		}
		speed -= s;
		state -= 1;
		return;
	}
	public double getSpeed()
	{
		return speed;
	}
	public int getXvelocity()
	{
		return xV;
	}
	public int getYvelocity()
	{
		return yV;
	}
	private int checkIfXposOutsideMap(int xpos){
		if(xpos <= 0){
			xpos = 0;
			xV = xV * (-1);
		}
		if(xpos >= width-ballSize){
			xpos = width - ballSize;
			xV = xV * (-1);
		}
		return xpos;
	}
	private int checkIfYposOutsideMap(int ypos){
		if(ypos <= 0){
			ypos = 0;
			yV = yV * (-1);
		}
		if(ypos >= height-ballSize){
			ypos = height - ballSize;
			yV = yV * (-1);
		}
		return ypos;
	}
	private void checkArgs(Position position, double s, MapSize mapSize, int ballSize) throws Exception 
	{
		checkIfPositiveArgs(position, s, mapSize, ballSize);
		checkIfBallIsOnMap(position, mapSize, ballSize);
	}
	private void checkIfPositiveArgs(Position position, double s, MapSize mapSize, int ballSize) throws Exception
	{
		if(position.getXpos() < 0 || position.getYpos() < 0 
				|| s <= 0 || mapSize.getWidth()<=0 || mapSize.getHeight() <= 0 || ballSize<=0)
		{
			throw new Exception("ERROR: Ball constructor arguments are incorrect");
		}
	}
	private void checkIfBallIsOnMap(Position position, MapSize mapSize, int ballSize) throws Exception
	{
		if(position.getXpos()+ballSize > mapSize.getWidth() || 
				position.getYpos()+ballSize > mapSize.getHeight())
		{
				throw new Exception("ERROR: Ball is out of map");
		
		}
	}
	private void bounceVertical()
	{
		xV = xV * (-1);
	}
	private int round(double d) {
		if(d <= 1 && d>0){
			return 1;
		}
		if(d >= -1 && d<0){
			return -1;
		}
		return (int) d;
	}
	private int bounceHorizontal()
	{
		yV = yV * (-1);
		return 0;
	}
	private int xV, yV, width, height, ballSize;
	private double speed;
	private int state;
}