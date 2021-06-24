package finalProject;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class menuControllor {
	
	@FXML
	Button menuStart;
	Button menuExit;
	@FXML
	Label title;
	
	@FXML
	public void onStartPressed() throws IOException  {
		Parent levelMenu = FXMLLoader.load(getClass().getResource("levelMenu.fxml"));
		Scene levelMenuScene = new Scene(levelMenu);
		levelMenuScene.getRoot().requestFocus();
		Main.currentStage.setScene(levelMenuScene);
		Font font = Font.loadFont(getClass().getResourceAsStream("/font.ttf"), 14);
	    title.setFont(font);
	}
	
	@FXML
	public void onExitPressed() {
		Main.currentStage.close();
	}

}
