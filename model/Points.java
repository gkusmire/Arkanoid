package model;

public class Points {
	public Points(int x, int y, int p, int maxHeight) throws Exception
	{
		checkIfCorrectArgs(x, y, p, maxHeight);
		xPos = x;
		yPos = y;
		value = p;
		this.maxHeight = maxHeight;
	}
	public int getXpos(){
		return xPos;
	}
	public int getYpos(){
		return yPos;
	}
	public int getValue(){
		return value;
	}
	public int move(){
		yPos = yPos+1;
		if(yPos >= maxHeight)
			return 2;
		return 0;
	}
	private void checkIfCorrectArgs(int x, int y, int p, int maxHeight) throws Exception{
		if(x < 0 || y < 0 || maxHeight < y){
			throw new Exception("ERROR: Incorrect argument");
		}
	}
	private int xPos, yPos, value, maxHeight;
}