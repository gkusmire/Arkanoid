package model;

import enumTypes.BonusAction;


public class Block extends ObjectPos{
	
	public Block(int x, int y, int p, BonusAction b) throws Exception
	{
		super(x, y);
		points = p;
		bonus = b;
	}
	public int getPoints()
	{
		return points;
	}
	public BonusAction getBonus()
	{
		return bonus;
	}
	private int points;
	private BonusAction bonus;
}