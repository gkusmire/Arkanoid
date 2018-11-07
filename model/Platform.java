package model;

import enumTypes.HorizontalDirection;

public class Platform {
	
	public Platform(int l, int s, int w) throws Exception
	{
		checkIfCorrectArgs(l, s, w);
		lengthCopy = length = l;
		speed = s;
		xPos = (w-l)/2;
		mapWidth = w;
		direction = null;
	}
	public void setDir(HorizontalDirection dir)
	{
		direction = dir;
	}
	public void stopMovingPlatform(){
		direction = null;
	}
	public void setXpos(int x){
		if(x<0){
			return;
		}
		if(x+length >= mapWidth){
			xPos = mapWidth - length;
			return;
		}
		xPos = x;
	}
	public void move()
	{
		if(direction == HorizontalDirection.LEFT) {
			movLeft();
		}
		if(direction == HorizontalDirection.RIGHT) {
			movRight();
		}
	}
	public HorizontalDirection getDir() {
		return direction;
	}
	public void reset() {
		length = lengthCopy;
		xPos = (mapWidth - length)/2;
	}
	public void lengthen(int l)
	{
		if(!isPositiveArg(l))
			return;
		if(l+length > mapWidth){
			length = mapWidth;
			return;
		}
		length += l;
		if(xPos + length > mapWidth){
			xPos = mapWidth - length;
		}
	}
	public void shorten(int l)
	{
		if(!isPositiveArg(l))
			return;
		if(length<=l){
			return;
		}
		length -= l;
	}
	public int getXpos ()
	{
		return xPos;
	}
	public int getLength ()
	{
		return length;
	}
	private boolean isPositiveArg(int l){
		return l >= 0;
	}
	private void movLeft()
	{
		xPos -= speed;
		if(xPos <0)
		{
			xPos = 0;
		}
	}
	private void movRight()
	{
		xPos += speed;
		if(xPos > mapWidth - length)
		{
			xPos = mapWidth - length;
		}
	}
	private void checkIfCorrectArgs(int l, int s, int w) throws Exception{
		checkIfPositiveArgs(l, s, w);
		checkIfCorrectSize(l, w);
	}
	private void checkIfCorrectSize(int l, int w) throws Exception{
		if(l > w)
			throw new Exception("ERROR: Width shorter than platform length");
	}
	private void checkIfPositiveArgs(int l, int s, int w) throws Exception{
		if(l<=0 || s<=0 || w<=0)
			throw new Exception("ERROR: Incorrect arguments");
	}
	private int length, speed, xPos, lengthCopy, mapWidth; 
	HorizontalDirection direction;
}