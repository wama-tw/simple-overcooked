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
	boolean running = false;
	
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
        if (e.getCode() == KeyCode.SHIFT) {
        	running = true;
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
        if (e.getCode() == KeyCode.SHIFT) {
        	running = false;
        }
    }
    
    // public void getMarginSize() {
    // 	top = (field.getHeight() - (character.getFitHeight() / 2));
    //	bottom = ((character.getFitHeight() / 2));
    //	right = (field.getWidth() - (character.getFitWidth() / 2));
    //	left = ((character.getFitWidth() / 2));
    // }
    
    public boolean isRightMargin(double x) {
    	if (x > (field.getWidth() - (character.getFitWidth() / 2))) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isLeftMargin(double x) {
    	if (x < -((character.getFitWidth() / 2))) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public boolean isTopMargin(double y) {
    	if (y < -((character.getFitHeight() / 2))) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public boolean isBottomMargin(double y) {
    	if (y > (field.getHeight() - (character.getFitHeight() / 2))) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    boolean foot = true;
    Image image0 = new Image(getClass().getResource("./image/chrome_dinosaur.png").toExternalForm());
    Image image1 = new Image(getClass().getResource("./image/chrome_dinosaur_left_foot_up.png").toExternalForm());
	Image image2 = new Image(getClass().getResource("./image/chrome_dinosaur_right_foot_up.png").toExternalForm());
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000/60),(e)-> {
    		double x = character.getLayoutX();
    		double y = character.getLayoutY();
    		int runSpeed = 3;
    		if (running) {
    			runSpeed += 3;
    		}
    		if (goNorth && !isTopMargin(y)) {
    			character.setLayoutY(y - runSpeed);
    		}
    		if (goSouth && !isBottomMargin(y)) {
    			character.setLayoutY(y + runSpeed);
    		}
    		if (goEast && !isRightMargin(x)) {
    			character.setLayoutX(x + runSpeed);
    		}
    		if (goWest && !isLeftMargin(x)) {
    			character.setLayoutX(x - runSpeed);
    		}
		}));
		fps.setCycleCount(Timeline.INDEFINITE);
		fps.play();
		
		Timeline walk = new Timeline(new KeyFrame(Duration.millis(1000/10),(e)-> {
    		if (goNorth || goSouth || goEast || goWest) {
    			if (foot) {
    				character.setImage(image1);
    			} else {
    				character.setImage(image2);
    			}
    			foot = !foot;
    		} else {
				character.setImage(image0);
    		}
		}));
		walk.setCycleCount(Timeline.INDEFINITE);
		walk.play();
	}

}
