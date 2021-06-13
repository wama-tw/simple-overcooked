package finalProject;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage currentStage;
	public static Scene menuScene;
	public static Scene levelMenuScene;

	public static ArrayList<Integer> scores = new ArrayList<Integer>();
	public static ArrayList<Integer> stars = new ArrayList<Integer>();
	public static ArrayList<Boolean> open = new ArrayList<Boolean>();
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	for (int i = 0; i < 2; i++) {
    		scores.add(0);
    		stars.add(0);
    		open.add(false);
    	}
    	open.set(0, true);
    	
    	currentStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        menuScene = new Scene(root);
        levelMenuScene = new Scene(FXMLLoader.load(getClass().getResource("levelMenu.fxml")));
        
        primaryStage.setTitle("OverCooked!");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
