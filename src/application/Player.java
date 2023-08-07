package application;

import java.util.ArrayList;
import java.util.Stack;

public class Player {
	private String name;
	private int roundPoint;
	private int totalPoint;
	private Stack<Card> cards = new Stack<>();
	
	Player(String name){
		this.name = name;
		this.roundPoint = 0;
		this.totalPoint = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRoundPoint() {
		return roundPoint;
	}
	
	public int getTotalPoint() {
		return totalPoint;
	}
	
	public void calcPoint(ArrayList<Card> currentCards) {
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
		
		this.roundPoint = point;
		this.totalPoint += point;
	}
	
	public Stack<Card> getCloneCards() {
		Stack<Card> cloneCards = (Stack<Card>)cards.clone();
		return cloneCards;
	}
	
	public Stack<Card> getCards() {
		return cards;
	}
}
