import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Collections;

class Player{
	private String name;
	private int point;
	private int totalPoint;
	private Stack cards = new Stack();
	
	Player(String name){
		this.name = name;
		this.point = 0;
	}
	
	// calculate players' point in a round
	public void calcPoint(ArrayList<Card> currentCards){
		int point = 0;
		
		for(int i = 0; i < currentCards.size(); i++){
			Card card1 = currentCards.get(i);
			int currentPoint = card1.getPoint();
			for(int j = i+1; j < currentCards.size(); j++){
				Card card2 = currentCards.get(j);
				if(card1.getSuit() == card2.getSuit()){
					currentPoint += card2.getPoint();
					i++;
				}else{
					break;
				}
			}
			if(currentPoint > point){
				point = currentPoint;
			}
		}
		
		this.point = point;
		this.totalPoint += point;
	}
	
	// return player's name
	public String getName(){
		return name;
	}
	
	// return player's point in a round
	public int getPoint(){
		return point;
	}
	
	// return player's total point in game
	public int getTotalPoint(){
		return totalPoint;
	}
	
	// return player's read only handcards
	public Stack getCloneCards(){
		Stack clone = (Stack)cards.clone();
		return clone;
	}
	
	// return player's editable handcards
	public Stack getCards(){
		return cards;
	}
}