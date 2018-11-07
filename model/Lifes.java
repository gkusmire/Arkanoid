package model;



public class Lifes {
	private int lifes;
	
	public Lifes(int l)
	{
		if(l>0){
			lifes = l;
		}
	}
	public int decLife()
	{
		if(lifes > 0)
			lifes -= 1;
		if(lifes > 0)
			return 0;
		return 1;
	}
	public int getLifes()
	{
		return lifes;
	}

}