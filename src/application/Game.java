package application;

import java.util.*;
import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class Game {
	private ArrayList<Player> players= new ArrayList<>();
	private Stack<Card> cardSet = new Stack<>();
	private int round = 1;
	private int phase = 1;

	private BorderPane pane = new BorderPane();
	private GridPane cardPane = new GridPane();
	private Text title;
	private HBox buttonPane = new HBox(20);
	private StackPane scorePane = new StackPane();
	
	Game(String name1, String name2, String name3){
		createPlayers(name1, name2, name3);
		
		createCards();
		
		shuffleCards();
		
		// GUI
		phase1();
		
		new Text("Phase1: 3-Player Phase");
		
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setMargin(title, new Insets(30, 0, 30, 0));
		
		Scene phase = new Scene(pane, 1400, 700);
		Main.stage.setScene(phase);
	}
	
	private void createPlayers(String name1, String name2, String name3) {
		Player player1 = new Player(name1);
		this.players.add(player1);
		
		Player player2 = new Player(name2);
		this.players.add(player2);
		
		Player player3 = new Player(name3);
		this.players.add(player3);
	}
	
	private void createCards() {
		char[] suit = {'d', 'c', 'h', 's'};
		char[] face = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K'};
		
		for(int i = 0; i < suit.length; i++) {
			for(int j = 0; j < face.length; j++) {
				Card card = new Card(suit[i], face[j]);
				this.cardSet.push(card);
			}
		}
	}

	private void shuffleCards() {
		Stack<Card> temp1 = new Stack<>();
		Stack<Card> temp2 = new Stack<>();
		Stack<Card> temp3 = new Stack<>();
		Stack<Card> temp4 = new Stack<>();
		
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
		
		giveCards();
	}
	
	private void giveCards() {
		cardPane.getChildren().clear();
		
		// give players cards
		while(!cardSet.empty()){
			for(int i = 0; i < players.size(); i++){
				if(cardSet.empty()){
					break;
				}
				players.get(i).getCards().push(cardSet.pop());
			}
		}
		
		// render players' cards
		int row = 0;
		int column = 0;
		for(int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			Stack<Card> playerCards = player.getCloneCards();
			Label label = new Label(player.getName());
			label.setFont(new Font(30));
			cardPane.add(label, column, row);
			int n = playerCards.size();
			for(int j = 0; j < n; j++){
				Card card = playerCards.pop();
				ImageView cardImage = card.getImage();
				column++;
				if(j % 15 == 0) {
					column = 1;
					row++;
				}
				if(column % 6 == 0) {
					Rectangle rectangle = new Rectangle(5, 40);
					cardPane.add(rectangle, column, row);
					column++;
				}
				cardPane.add(cardImage, column, row);
			}
			column = 0;
			row++;
		}
	}

	private void returnCards() {
		for(int i = 0; i < players.size(); i++){
			Stack<Card> cards = players.get(i).getCards();
			while(!cards.empty()){
				cardSet.push(cards.pop());
			}
		}
	}
	
	private void phase1() {
		title = new Text("Phase1: 3-Player Phase");
		
		cardPane.setPrefWidth(1400);
		cardPane.setAlignment(Pos.CENTER_LEFT);
		cardPane.setPadding(new Insets(0, 0, 0, 100));
		
		title.setFont(new Font(20));
		
		Button shuffleBtn = new Button("Shuffle");
		Button startBtn = new Button("Start");
		buttonPane.getChildren().addAll(shuffleBtn, startBtn);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		buttonPane.setPadding(new Insets(0, 60, 60, 0));
		
		pane.setTop(title);
		pane.setCenter(cardPane);
		pane.setBottom(buttonPane);
		
		shuffleBtn.setOnAction(e -> {
			returnCards();
			shuffleCards();
		});
		
		startBtn.setOnAction(e -> {
			playRound(3);
		});
	}
	
	private void phase2() {
		buttonPane.getChildren().clear();
		cardPane.getChildren().clear();
		scorePane.getChildren().clear();
		
		createCards();
		shuffleCards();
		
		title.setText("Phase2: 2-Player Phase");
		
		cardPane.setPrefWidth(1400);
		cardPane.setAlignment(Pos.CENTER_LEFT);
		cardPane.setPadding(new Insets(0, 0, 0, 100));
		
		title.setFont(new Font(20));
		
		Button shuffleBtn = new Button("Shuffle");
		Button startBtn = new Button("Start");
		buttonPane.getChildren().addAll(shuffleBtn, startBtn);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		buttonPane.setPadding(new Insets(0, 60, 60, 0));
		
		pane.setTop(title);
		pane.setCenter(cardPane);
		pane.setBottom(buttonPane);
		
		shuffleBtn.setOnAction(e -> {
			returnCards();
			shuffleCards();
		});
		
		startBtn.setOnAction(e -> {
			playRound(4);
		});
	}
	
	private void playRound(int totalRound) {
		if(round > totalRound) {
			showWinner();
			return;
		}
		
		// sort and calculate players' card
		ArrayList<ArrayList<Card>> playersCards = new ArrayList<>();
		for(int i = 0; i < players.size(); i++){
			ArrayList<Card> currentCards = new ArrayList<>();
			Stack<Card> playerAllCards = players.get(i).getCards();
			
			// pick first 5 cards
			for(int j = 0; j < 5; j++){
				Card card = playerAllCards.pop();
				currentCards.add(card);
			}
			
			// sort the card in order
			sortCards(currentCards);
			
			// calculate player point
			players.get(i).calcPoint(currentCards);
			
			playersCards.add(currentCards);
		}
		
		int highestScore = countHighestScore();
		
		// render GUI
		cardPane.getChildren().clear();
		buttonPane.getChildren().clear();
		scorePane.getChildren().clear();
		
		cardPane.setAlignment(Pos.CENTER);
		cardPane.setVgap(15);
		
		Button nextButton = new Button("Next");
		buttonPane.getChildren().add(nextButton);
		
		title.setText("Phase" + phase + " - Round " + round);
		
		int row = 0;
		int column = 0;
		for(int i = 0; i < players.size(); i++){
			ArrayList<Card> currentCards = playersCards.get(i);
			Player player = players.get(i);
			Label label = new Label(player.getName());
			label.setFont(new Font(30));
			cardPane.add(label, column, row);
			
			for(int j = 0; j < currentCards.size(); j++){
				column++;
				ImageView cardImage = currentCards.get(j).getImage();
				cardPane.add(cardImage, column, row);
			}
			
			column++;
			Rectangle rectangle = new Rectangle(5, 40);
			cardPane.add(rectangle, column, row);
			
			column++;
			Label point = new Label("Point : " + String.valueOf(player.getRoundPoint()));
			point.setFont(new Font(20));
			cardPane.add(point, column, row);
			
			if(players.get(i).getRoundPoint() == highestScore) {
				column++;
				ImageView crown = new ImageView(new Image(new File("cards/crown.png").toURI().toString()));
				crown.setFitWidth(32);
				crown.setFitHeight(32);
				Label winLabel = new Label("Win", crown);
				
				winLabel.setContentDisplay(ContentDisplay.LEFT);
				winLabel.setGraphicTextGap(5);
				winLabel.setFont(new Font(20));
				
				cardPane.add(winLabel, column, row);
				GridPane.setMargin(winLabel, new Insets(0, 0, 0, 20));
			}
			
				column = 0;
				row++;
				
				GridPane.setMargin(point, new Insets(0, 0, 0, 20));
		}
		
		VBox scoreBox = new VBox(10);
		
		Text title = new Text("Scoreboard");
		title.setUnderline(true);
		title.setStyle("-fx-font-weight: bold;");
		title.setFont(new Font(20));
		
		scoreBox.getChildren().add(title);
		
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			Label score = new Label(player.getName() + "\t" + ":  " + String.valueOf(player.getTotalPoint()));
			score.setFont(new Font(20));
			scoreBox.getChildren().add(score);
		}
		
		scoreBox.setAlignment(Pos.CENTER);
		
		scorePane.getChildren().add(scoreBox);
		scorePane.setPadding(new Insets(0, 30, 0, 30));
		
		pane.setLeft(scorePane);
		
		nextButton.setOnAction(e -> {
			showCardLeft(totalRound);
		});
	}
	
	private void sortCards(ArrayList<Card> currentCards) {
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

	private int countHighestScore() {
		int maxPoint = 0;
		
		for(int i = 0; i < players.size(); i++){
			int point = players.get(i).getRoundPoint();
			if(point > maxPoint){
				maxPoint = point;
			}
		}
		
		return maxPoint;
	}

	private void showCardLeft(int totalRound) {
		cardPane.getChildren().clear();
		buttonPane.getChildren().clear();
		
		cardPane.setAlignment(Pos.CENTER_LEFT);
		
		title.setText("Phase" + phase + " - Round " + round + "\n" + "Left Cards");
		title.setTextAlignment(TextAlignment.CENTER);
		
		int row = 0;
		int column = 0;
		for(int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			Stack<Card> remainingCards = player.getCloneCards();
			Label label = new Label(player.getName() + " : ");
			label.setFont(new Font(30));
			cardPane.add(label, column, row);
			column++;
			
			int n = remainingCards.size();
			for(int j = 0; j < n; j++){
				Card card = remainingCards.pop();
				ImageView cardImage = card.getImage();
				if(j % 15 == 0) {
					column = 1;
					row++;
				}
				
				if(column % 6 == 0) {
					Rectangle rectangle = new Rectangle(5, 40);
					cardPane.add(rectangle, column, row);
					column++;
				}
				cardPane.add(cardImage, column, row);
				column++;
			}
			column = 0;
			row++;
		}
		
		Button nextButton = new Button("Next");
		buttonPane.getChildren().add(nextButton);
		
		round++;
		nextButton.setOnAction(e -> {
			playRound(totalRound);
		});
	}
	
	private void showWinner() {
		int loserIndex = whoLose();
		pane.getChildren().clear();
		buttonPane.getChildren().clear();
		scorePane.getChildren().clear();
		
		// render title
		title.setText("Phase " + phase + "\nWinner");
		
		// render button
		Button nextButton = new Button("Next");
		Button quitButton = new Button("Exit");
		
		if(phase == 1) {
			buttonPane.getChildren().add(nextButton);
		}else {
			buttonPane.getChildren().add(quitButton);
		}
		
		// render score board
		VBox scoreBox = new VBox(10);
		
		Text title2 = new Text("Scoreboard");
		title2.setUnderline(true);
		title2.setStyle("-fx-font-weight: bold;");
		title2.setFont(new Font(20));
		
		scoreBox.getChildren().add(title2);

		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			Label score;
			if(i != loserIndex) {
				ImageView crown = new ImageView(new Image(new File("cards/crown.png").toURI().toString()));
				crown.setFitWidth(32);
				crown.setFitHeight(32);
				score = new Label(player.getName() + "\t" + ":  " + String.valueOf(player.getTotalPoint()), crown);
				score.setContentDisplay(ContentDisplay.RIGHT);
				score.setGraphicTextGap(5);
			}else {
				score = new Label(player.getName() + "\t" + ":  " + String.valueOf(player.getTotalPoint()));
			}
			
			score.setFont(new Font(20));
			scoreBox.getChildren().add(score);
		}
		
		// clear players' cards
		for(int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			while(!player.getCards().empty()){
				player.getCards().pop();
			}
		}
		
		// remove loser
		players.remove(loserIndex);
		
		Text winText;
		if(players.size() == 3) {
			winText = new Text(players.get(0).getName() + ", " + players.get(0).getName() + " and " + players.get(1).getName() + " win and proceed to next phase.");
		}else if(players.size() == 2) {
			winText = new Text(players.get(0).getName() + " and " + players.get(1).getName() + " win and proceed to next phase.");
		}else {
			winText = new Text(players.get(0).getName() + " win the game.");
		}
		
		winText.setFont(new Font(20));
		winText.setFill(Color.RED);
		scoreBox.getChildren().add(winText);
		
		scoreBox.setAlignment(Pos.CENTER);
		
		scorePane.getChildren().add(scoreBox);
		
		pane.setTop(title);
		pane.setCenter(scorePane);
		pane.setBottom(buttonPane);
		
		phase++;
		round = 1;
		nextButton.setOnAction(e -> {
			phase2();
		});
		
		quitButton.setOnAction(e -> {
			Main.stage.close();
		});
	}
	
	private int whoLose() {
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
}
