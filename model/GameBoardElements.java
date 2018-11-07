package model;


import java.util.ArrayList;

import others.MapSize;
import others.Position;

import enumTypes.BonusAction;

public class GameBoardElements {
	public MapSize mapSize;
	public ArrayList<ObjectPos> blocks;
	public ArrayList<ObjectPos> balls;
	public ArrayList<Points> points;
	public int ballSize;
	public int PlatformLength;
	public int platformXpos;
	public int scores;
	public Lifes lifes;
	public Platform platform;
	public Position startingBallPosition;
	public double ballSpeed;
	public GameBoardElements() 
	{
		mapSize = new MapSize(10, 10);
		points = new ArrayList<Points>();
		blocks = new ArrayList<ObjectPos>();
		balls = new ArrayList<ObjectPos>();
		scores = 0;
		try{
			blocks.add(new Block(1, 3, 100, BonusAction.LENGHTEN_PLATFORM));
			blocks.add(new Block(2, 3, 200, BonusAction.SHORTEN_PLATFORM));
			blocks.add(new Block(3, 3, 300, BonusAction.SPEED_UP_BALL));
			blocks.add(new Block(4, 3, 400, BonusAction.SLOW_DOWN_BALL));
			blocks.add(new Block(5, 3, 500, BonusAction.TRIPLE_BALL));
			blocks.add(new Block(6, 3, 600, BonusAction.LENGHTEN_PLATFORM));
			blocks.add(new Block(7, 3, 700, BonusAction.SHORTEN_PLATFORM));
			blocks.add(new Block(8, 3, 800, BonusAction.SPEED_UP_BALL));
			blocks.add(new Block(1, 4, -1000, BonusAction.SLOW_DOWN_BALL));
			blocks.add(new Block(2, 4, 200, BonusAction.TRIPLE_BALL));
			blocks.add(new Block(3, 4, 300, BonusAction.LENGHTEN_PLATFORM));
			blocks.add(new Block(4, 4, 400, BonusAction.SHORTEN_PLATFORM));
			blocks.add(new Block(5, 4, 500, BonusAction.SPEED_UP_BALL));
			blocks.add(new Block(6, 4, 600, BonusAction.SLOW_DOWN_BALL));
			blocks.add(new Block(7, 4, 700, BonusAction.TRIPLE_BALL));
			blocks.add(new Block(8, 4, -1000, BonusAction.LENGHTEN_PLATFORM));
			platform = new Platform(120, 5, mapSize.getWidth()*50); 
			startingBallPosition = new Position(240,  480);
			balls.add(new Ball(new Position(240, 480), 0.3, new MapSize(10*50, 10*50), 10));
			ballSpeed = 0.3;
		} catch(Exception e)
		{
			System.err.print("BLAD utworzenia obiektow gry. Progarm zakancza dzialanie");
			e.printStackTrace();
			System.exit(1);
		}
		ballSize = 10;
		lifes = new Lifes(3);
	}
}