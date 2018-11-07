package enumTypes;

public enum Keys {
	LEFT_KEY(37), RIGHT_KEY(39), START_KEY('s'), PAUSE_KEY('p'), RESET_KEY('r');
	
	private int value;
	
	private Keys(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}
}
