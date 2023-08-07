class Card{
	private String name;
	private int point;
	private char suit;
	
	Card(char suit, char face){
		if(face == 'A'){
			this.point = 1;
		}else if(face >= '2' && face <= '9'){
			int point = Integer.parseInt(String.valueOf(face));
			this.point = point;
		}else{
			this.point = 10;
		}
		
		this.suit = suit;
		this.name = "" + suit + face;
	}
	
	// return card name
	public String getName(){
		return name;
	}
	
	// return card suit
	public char getSuit(){
		return suit;
	}
	
	// return card point
	public int getPoint(){
		return point;
	}
}
