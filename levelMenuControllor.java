package finalProject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.util.Duration;

public class levelMenuControllor implements Initializable{
	@FXML
	AnchorPane field;
	@FXML
	Pane white;
	@FXML
	Pane readme;
	@FXML
	ImageView character;
	@FXML
	ImageView level1;
	@FXML
	Pane levelOne;
	@FXML
	ImageView l11;
	@FXML
	ImageView l12;
	@FXML
	ImageView l13;
	@FXML
	Pane levelTwo;
	@FXML
	ImageView l21;
	@FXML
	ImageView l22;
	@FXML
	ImageView l23;
	
	boolean goNorth = false, goSouth = false, goEast = false, goWest = false;
	boolean running = false;
	boolean turnLeft = false;
	boolean state = false;
	
    public void pressedHandle(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.UP) {
        	goNorth = true;
        }
        if (e.getCode() == KeyCode.DOWN) {
        	goSouth = true;
        }
        if (e.getCode() == KeyCode.RIGHT) {
        	goEast = true;
        	turnLeft = false;
        }
        if (e.getCode() == KeyCode.LEFT) {
        	goWest = true;
        	turnLeft = true;
        }
        if (e.getCode() == KeyCode.SHIFT) {
        	running = true;
        }
        if (e.getCode() == KeyCode.Q && inLevelOne(character.getLayoutX(), character.getLayoutY())) {
        	entryLevelOne();
        }
        if (e.getCode() == KeyCode.Q && inLevelTwo(character.getLayoutX(), character.getLayoutY())) {
        	entryLevelTwo();
        }
        if (e.getCode() == KeyCode.ESCAPE) {
        	state = !state;
        	white.setVisible(state);
        	readme.setVisible(state);
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
    	if (y < -((character.getFitHeight() / 2)) + (field.getHeight() * (2.0/3))) {
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
    
    public boolean inLevelOne(double x, double y) {
    	double characterX = x + (character.getFitWidth() / 2);
    	double characterY = y + (character.getFitHeight() / 2);
    	if (characterX > 239 && characterX < 239+96 && characterY > 292 && characterY < 292+91 && Main.open.get(0)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean inLevelTwo(double x, double y) {
    	double characterX = x + (character.getFitWidth() / 2);
    	double characterY = y + (character.getFitHeight() / 2);
    	if (characterX > 407 && characterX < 407+96 && characterY > 263 && characterY < 263+91 && Main.open.get(1)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void entryLevelOne() throws IOException {
    	Parent levelOne = FXMLLoader.load(getClass().getResource("levelOne.fxml"));
		Scene levelOneScene = new Scene(levelOne);
		levelOneScene.getRoot().requestFocus();
		Main.currentStage.setScene(levelOneScene);
    }

    public void entryLevelTwo() throws IOException {
    	Parent levelTwo = FXMLLoader.load(getClass().getResource("levelTwo.fxml"));
		Scene levelTwoScene = new Scene(levelTwo);
		levelTwoScene.getRoot().requestFocus();
		Main.currentStage.setScene(levelTwoScene);
    }
    
    boolean foot = true;
    Image image0 = new Image(getClass().getResource("./image/chrome_dinosaur.png").toExternalForm());
    Image image1 = new Image(getClass().getResource("./image/chrome_dinosaur_left_foot_up.png").toExternalForm());
	Image image2 = new Image(getClass().getResource("./image/chrome_dinosaur_right_foot_up.png").toExternalForm());
	Image image3 = new Image(getClass().getResource("./image/chrome_dinosaur_turn_left.png").toExternalForm());
    Image image4 = new Image(getClass().getResource("./image/chrome_dinosaur_left_foot_up_turn_left.png").toExternalForm());
	Image image5 = new Image(getClass().getResource("./image/chrome_dinosaur_right_foot_up_turn_left.png").toExternalForm());
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	if (Main.firstOpen == false) {
    		state = true;
    		Main.firstOpen = true;
        	white.setVisible(state);
        	readme.setVisible(state);
    	}
    	levelOne.setVisible(Main.open.get(0));
    	if (Main.stars.get(0) >= 1) {
    		l11.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
    	}
    	if (Main.stars.get(0) >= 2) {
    		l12.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
    	}
    	if (Main.stars.get(0) >= 3) {
    		l13.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
    	}
    	levelTwo.setVisible(Main.open.get(1));
    	if (Main.stars.get(1) >= 1) {
    		l21.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
    	}
    	if (Main.stars.get(1) >= 2) {
    		l22.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
    	}
    	if (Main.stars.get(1) >= 3) {
    		l23.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
    	}
    	
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
    			if (!turnLeft) {
    				if (foot) {
        				character.setImage(image1);
        			} else {
        				character.setImage(image2);
        			}
    			} else {
    				if (foot) {
        				character.setImage(image4);
        			} else {
        				character.setImage(image5);
        			}
    			}
    			foot = !foot;
    		} else {
    			if (!turnLeft) {
    				character.setImage(image0);
    			} else {
    				character.setImage(image3);
    			}
    		}
		}));
		walk.setCycleCount(Timeline.INDEFINITE);
		walk.play();
	}

}
