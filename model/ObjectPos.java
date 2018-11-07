package model;

import others.Position;

public class ObjectPos {
	public ObjectPos(int x, int y) throws Exception{
		checkIfPositiveArgs(x, y);
		xPos = x;
		yPos = y;
	}
	public ObjectPos(Position position) throws Exception{
		checkIfPositiveArgs(position.getXpos(), position.getYpos());
		xPos = position.getXpos();
		yPos = position.getYpos();
	}
	public int getXpos()
	{
		return xPos;
	}
	public int getYpos()
	{
		return yPos;
	}
	public void setXpos(int x)
	{
		xPos = x;
	}
	public void setYpos(int y)
	{
		yPos = y;
	}
	private void checkIfPositiveArgs(int x, int y) throws Exception{
		if(x < 0 || y < 0){
			throw new Exception("ERROR: Incorrect arguments");
		}
	}
	private int xPos;
	private int yPos;
}