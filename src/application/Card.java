package application;

import java.io.File;
import javafx.scene.image.*;

public class Card {
	private char suit;
	private int point;
	private String name;
	private ImageView image;
	
	Card(char suit, char face){
		if(face == 'A') {
			this.point = 1;
		}else if(face >= '2' && face <= '9') {
			this.point = Integer.parseInt(String.valueOf(face));
		}else {
			this.point = 10;
		}
		
		this.suit = suit;
		this.name = "" + suit + face;
		
		this.image = new ImageView(new Image(new File("cards/" + name + ".png").toURI().toString()));
	}
	
	public char getSuit() {
		return suit;
	}
	
	public int getPoint() {
		return point;
	}
	
	public String getName() {
		return name;
	}
	
	public ImageView getImage() {
		return image;
	}
}
