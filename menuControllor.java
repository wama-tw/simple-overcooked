package finalProject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class menuControllor {
	@FXML
	Button menuStart;
	Button menuExit;
	
	@FXML
	public void onStartPressed() throws IOException  {
		Parent levelMenu = FXMLLoader.load(getClass().getResource("levelMenu.fxml"));
		Scene levelMenuScene = new Scene(levelMenu);
		levelMenuScene.getRoot().requestFocus();
		Main.currentStage.setScene(levelMenuScene);
	}
	
	@FXML
	public void onExitPressed() {
		Main.currentStage.close();
	}

}
