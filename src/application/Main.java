package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;


public class Main extends Application {
	public static Stage stage = new Stage();
	
	@Override
	public void start(Stage primaryStage) {
		GridPane pane = new GridPane();
		
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		
		Label text1 = new Label("Player1 Name: ");
		Label text2 = new Label("Player2 Name: ");
		Label text3 = new Label("Player3 Name: ");
		TextField textField1 = new TextField();
		TextField textField2 = new TextField();
		TextField textField3 = new TextField();
		Button button = new Button("Play");
		button.setPrefSize(80, 30);
		
		pane.add(text1, 0, 0);
		pane.add(text2, 0, 1);
		pane.add(text3, 0, 2);
		pane.add(textField1, 1, 0);
		pane.add(textField2, 1, 1);
		pane.add(textField3, 1, 2);
		pane.add(button, 1, 3);
		pane.setHalignment(button, HPos.RIGHT);
		
		Scene scene = new Scene(pane, 1400, 700);
		stage.setScene(scene);
		stage.setTitle("Assignment");
		stage.show();
		
		button.setOnAction(e -> {
			if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty()) {
				Text warning = new Text("Please Enter Players' Name.");
				warning.setFill(Color.RED);
				pane.add(warning, 1, 4);
			}else {
				Game game = new Game(textField1.getText(), textField2.getText(), textField3.getText());
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
