import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Collections;

class Game{
	private ArrayList<Player> players = new ArrayList<>();
	public static Stack cardSet = new Stack();
	private static Stack usedCardSet = new Stack();
	Scanner input = new Scanner(System.in);
	
	Game(){
		// Phase 1
		System.out.println();
		System.out.println("******************");
		System.out.println("* 3-Player Phase *");
		System.out.println("******************");
		
		// create 52 cards
		createCardSet();
		
		// create 3 players
		createPlayer();
		
		// shuffle and give cards to player
		shuffleCard();
		
		// read user choice to shuffle
		while(true){
			System.out.println();
			System.out.print("Enter 1 to shuffle or Enter 2 to start: ");
			int c = input.nextInt();
			
			if(c == 1){
				returnCard();
				shuffleCard();
			}else{
				break;
			}
		}
		
		playRound(3);
		
		System.out.println();
		System.out.println("***** " + players.get(0).getName() + " and " + players.get(1).getName() + " proceed to 2-Player phase *****");
		
		System.out.println("\n\n");
		System.out.println("******************");
		System.out.println("* 2-Player Phase *");
		System.out.println("******************");
		
		// players return their cards to card set
		createCardSet();
			
		// shuffle and give cards to player
		shuffleCard();
		
		while(true){
			System.out.println();
			System.out.print("Enter 1 to shuffle or Enter 2 to start: ");
			int c = input.nextInt();
			
			if(c == 1){
				returnCard();
				shuffleCard();
			}else{
				break;
			}
		}
		
		playRound(4);
		
		Player winner = players.get(0);
		System.out.println();
		System.out.println("***** " + winner.getName() + " is the WINNER! *****");
	}
	
	// create 3 players
	private void createPlayer(){
		// Player 1
		System.out.print("Enter player 1 name: ");
		String name1 = input.nextLine();
		Player player1 = new Player(name1);
		players.add(player1);
		
		// Player 2
		System.out.print("Enter player 2 name: ");
		String name2 = input.nextLine();
		Player player2 = new Player(name2);
		players.add(player2);
		
		// Player 3
		System.out.print("Enter player 3 name: ");
		String name3 = input.nextLine();
		Player player3 = new Player(name3);
		players.add(player3);
	}
	
	// create 52 cards
	private void createCardSet(){
		char[] suits = {'d', 'c', 'h', 's'};
		char[] face = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K'};
		
		// push 52 combinations to cardSet
		for(int i = 0; i < suits.length; i++){
			for(int j = 0; j < face.length; j++){
				Card card = new Card(suits[i], face[j]);
				cardSet.push(card);
			}
		}
	}
	
	// shuffle 52 cards and give players
	private void shuffleCard(){
		Stack temp1 = new Stack();
		Stack temp2 = new Stack();
		Stack temp3 = new Stack();
		Stack temp4 = new Stack();
		
		Random random = new Random();
		
		while(!cardSet.empty()){
			int x = random.nextInt(4) + 1;
			if(x == 1){
				temp1.push(cardSet.pop());
			}else if(x == 2){
				temp2.push(cardSet.pop());
			}else if(x == 3){
				temp3.push(cardSet.pop());
			}else if(x == 4){
				temp4.push(cardSet.pop());
			}
		}
		
		while(!temp1.empty() || !temp2.empty() || !temp3.empty() || !temp4.empty()){
			if(!temp1.empty()){
				cardSet.push(temp1.pop());
			}else if(!temp2.empty()){
				cardSet.push(temp2.pop());
			}else if(!temp3.empty()){
				cardSet.push(temp3.pop());
			}else if(!temp4.empty()){
				cardSet.push(temp4.pop());
			}
		}
		
		// give card to players
		giveCard();
	}
	
	// give players cards
	private void giveCard(){
		// give card to players
		while(!cardSet.empty()){
			for(int i = 0; i < players.size(); i++){
				if(cardSet.empty()){
					break;
				}
				players.get(i).getCards().push(cardSet.pop());
			}
		}
		
		System.out.println();
		System.out.println("Available Cards:");
		
		for(int i = 0; i < players.size(); i++){
			System.out.print(players.get(i).getName() + "\t" + ": ");
			Stack playerCards = players.get(i).getCloneCards();
			int n = playerCards.size();
			for(int j = 0; j < n; j++){
				Card card = (Card)playerCards.pop();
				if((j+1) % 5 == 0){
					System.out.print(card.getName() + ", ");
				}else{
					System.out.print(card.getName() + " ");
				}
			}
			System.out.println();
		}
	}
	
	// players return their cards
	private void returnCard(){
		for(int i = 0; i < players.size(); i++){
			Stack cards = players.get(i).getCards();
			while(!cards.empty()){
				cardSet.push(cards.pop());
			}
		}
	}
	
	// play phase with n round
	private void playRound(int totalRound){
		int round = 1;
		while(round <= totalRound){
			System.out.println("\n");
			System.out.println("***Round " + round + "***");
			System.out.println("Cards at hand:");
			
			
			ArrayList<ArrayList<Card>> playersCards = new ArrayList<>();
			for(int i = 0; i < players.size(); i++){
				ArrayList<Card> currentCards = new ArrayList<>();
				Stack playerAllCards = players.get(i).getCards();
				
				// pick first 5 cards
				for(int j = 0; j < 5; j++){
					Card card = (Card)playerAllCards.pop();
					currentCards.add(card);
				}
				
				// sort the card in order
				sortCard(currentCards);
				
				// count player point
				players.get(i).calcPoint(currentCards);
				
				playersCards.add(currentCards);
			}
			
			int winPlayerIndex = whoWinRound();
			
			for(int i = 0; i < players.size(); i++){
				System.out.print(players.get(i).getName() + "\t" + ": ");
				ArrayList<Card> currentCards = playersCards.get(i);
				Player player = players.get(i);
				
				for(int j = 0; j < currentCards.size(); j++){
					System.out.print(currentCards.get(j).getName() + " ");
				}
				
				if(i == winPlayerIndex){
					System.out.println("|  Point = " + player.getPoint() + " | Win");
				}else{
					System.out.println("|  Point = " + player.getPoint());
				}	
			}
			
			// print players' score
			System.out.println();
			System.out.println("Score:");
			for(int i = 0; i < players.size(); i++){
				Player player = players.get(i);
				System.out.println(player.getName() + "\t" + "= " + player.getTotalPoint());
			}
			
			// print players' remaining cards
			System.out.println();
			System.out.println("Available Cards:");
			for(int i = 0; i < players.size(); i++){
				Player player = players.get(i);
				Stack remainingCards = player.getCloneCards();
				System.out.print(player.getName() + "\t" + ": ");
				int n = remainingCards.size();
				for(int j = 0; j < n; j++){
					Card card = (Card)remainingCards.pop();
					if((j+1)%5 == 0){
						System.out.print(card.getName() + ", ");
					}else{
						System.out.print(card.getName() + " ");
					}
				}
				System.out.println();
			}
			
			if(round == totalRound){
				break;
			}
			
			System.out.println();
			System.out.print("Press 1 to next round: ");
			int c = input.nextInt();
			round++;
		}
		
		for(int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			while(!player.getCards().empty()){
				player.getCards().pop();
			}
		}
		
		int index = whoLostPhase();
		players.remove(index);
	}
	
	// sort the cards according to suit
	private void sortCard(ArrayList<Card> currentCards){
		for(int i = 0; i < currentCards.size(); i++){
			for(int j = i+1; j < currentCards.size(); j++){
				Card card1 = currentCards.get(i);
				Card card2 = currentCards.get(j);
				if(card1.getSuit() > card2.getSuit()){
					currentCards.set(i, card2);
					currentCards.set(j, card1);
				}
			}
		}
	}
	
	// determine who win the round
	private int whoWinRound(){
		int maxPoint = 0;
		int index = 0;;
		
		for(int i = 0; i < players.size(); i++){
			int point = players.get(i).getPoint();
			if(point > maxPoint){
				maxPoint = point;
				index = i;
			}
		}
		
		return index;
	}
	
	// determine who lost phase
	private int whoLostPhase(){
		int minPoint = players.get(0).getTotalPoint();
		int index = 0;
		
		for(int i = 1; i < players.size(); i++){
			int point = players.get(i).getTotalPoint();
			if(point < minPoint){
				minPoint = point;
				index = i;
			}
		}
		
		return index;
	}
	
	// return players
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	// return game cardSet
	public static Stack getCardSet(){
		return cardSet;
	}
}
