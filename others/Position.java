package others;

public class Position {
	
	public Position(int x, int y){
		xPos = x;
		yPos = y;
	}
	public int getXpos(){
		return xPos;
	}
	public int getYpos(){
		return yPos;
	}
	public void setXpos(int newXpos){
		xPos = newXpos;
	}
	public void setYpos(int newYpos){
		xPos = newYpos;
	}
	private int xPos, yPos;
}
