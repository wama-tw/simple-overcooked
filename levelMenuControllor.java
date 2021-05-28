package finalProject;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class levelMenuControllor implements Initializable{
	@FXML
	AnchorPane field;
	@FXML
	ImageView character;
	
	boolean goNorth = false, goSouth = false, goEast = false, goWest = false;
	
    public void pressedHandle(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
        	goNorth = true;
        }
        if (e.getCode() == KeyCode.DOWN) {
        	goSouth = true;
        }
        if (e.getCode() == KeyCode.RIGHT) {
        	goEast = true;
        }
        if (e.getCode() == KeyCode.LEFT) {
        	goWest = true;
        }
    }
    
    public void releaseHandle(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
        	goNorth = false;
        }
        if (e.getCode() == KeyCode.DOWN) {
        	goSouth = false;
        }
        if (e.getCode() == KeyCode.RIGHT) {
        	goEast = false;
        }
        if (e.getCode() == KeyCode.LEFT) {
        	goWest = false;
        }
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000/60),(e)-> {
    		if (goNorth) {
    			character.setLayoutY(character.getLayoutY() - 1);
    		}
    		if (goSouth) {
    			character.setLayoutY(character.getLayoutY() + 1);
    		}
    		if (goEast) {
    			character.setLayoutX(character.getLayoutX() + 1);
    		}
    		if (goWest) {
    			character.setLayoutX(character.getLayoutX() - 1);
    		}
		}));
		fps.setCycleCount(Timeline.INDEFINITE);
		fps.play();
	}

}
