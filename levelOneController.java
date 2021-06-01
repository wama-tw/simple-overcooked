package finalProject;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class levelOneController implements Initializable{
	@FXML
	AnchorPane field;
	@FXML
	Pane paneField;
	@FXML
	GridPane map;
	@FXML
	ImageView character;
	@FXML
	ImageView chopBoard;
	int isEmpty = 0; // 0->chop board is empty 1->tomato 2->chopped tomato
	@FXML
	ImageView tomato;
	
	Image tomatoImage = new Image(getClass().getResource("./image/tomato.png").toExternalForm());
	Image tomatoInPlateImage = new Image(getClass().getResource("./image/tomato_in_plate.png").toExternalForm());
	Image cleanPlateImage = new Image(getClass().getResource("./image/clean_plate.png").toExternalForm());
	Image choppedTomatoImage = new Image(getClass().getResource("./image/chopped_tomato.png").toExternalForm());
	Image tomatoOnChopBoardImage = new Image(getClass().getResource("./image/tomato_on_chop_board.png").toExternalForm());
	Image choppedTomatoOnChopBoardImage = new Image(getClass().getResource("./image/chopped_tomato_on_chop_board.png").toExternalForm());
	Image chopBoardImage = new Image(getClass().getResource("./image/chop_board.png").toExternalForm());
	
	@FXML
	Circle test;
	
	double handX;
	double handY;
	boolean goNorth = false, goSouth = false, goEast = false, goWest = false;
	boolean running = false;
	boolean turnLeft = false;
	
	class item {
		public item(ImageView imageView, double x, double y, int type) {
			this.imageView = imageView;
			this.x = x + 25;
			this.y = y + 25;
			this.type = type;
		}
		public int type; // 1->tomato 2->chopped tomato 3->empty plate 4->full plate
		public ImageView imageView;
		public double x;
		public double y;
		public boolean isAround(double x, double y) {
			if (x < this.x + 15 && x > this.x - 15 && y < this.y + 15 && y > this.y - 15) {
				return true;
			} else {
				return false;
			}
		}
	}
	item plateItem = new item(new ImageView(cleanPlateImage), 64, 185, 3);
	int holding = -1; // (-1)->空手 (>=0)->index
	LinkedList<item> tomatos = new LinkedList<item>();
	
    public void pressedHandle(KeyEvent e) {
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
        
        if (e.getCode() == KeyCode.Q) {
        	/*ImageView progressBar = new ImageView(getClass().getResource("./image/progressBar/progressBar-1.png").toExternalForm());
        	paneField.getChildren().add(progressBar);
        	int count = 2;
        	Timeline progress = new Timeline(new KeyFrame(Duration.millis(1000/5),(p)-> {
        		if (count == 12) {
        			
        		}
        		count = count + 1;
    		}));
        	progress.setCycleCount(11);
        	progress.play();*/
        	if (holding == -1 && isArroundChopBoard(handX, handY) && isEmpty == 1) {
        		chopBoard.setImage(choppedTomatoOnChopBoardImage);
            	isEmpty = 2;
        	}
        }
        
        if (e.getCode() == KeyCode.E && isTakingTomato() && holding == -1) { // 拿新番茄
        	item newTomato = new item(new ImageView(tomatoImage), handX, handY, 1);
        	newTomato.imageView.setLayoutX(handX);
        	newTomato.imageView.setLayoutY(handY);
        	newTomato.imageView.setFitWidth(50);
        	newTomato.imageView.setFitHeight(50);
        	tomatos.push(newTomato);
        	paneField.getChildren().add(newTomato.imageView);
        	holding = tomatos.indexOf(newTomato);
        } else if (e.getCode() == KeyCode.E && holding != -1 && tomatos.get(holding).type == 2 && plateItem.isAround(handX, handY) && plateItem.type == 3) { // 放到盤子上
        	plateItem.imageView.setImage(tomatoInPlateImage);
        	plateItem.type = 4;
        	paneField.getChildren().remove(tomatos.get(holding).imageView);
        	tomatos.remove(holding);
        	holding = -1;
        } else if (e.getCode() == KeyCode.E && holding != -1 && isArroundChopBoard(handX, handY) && isEmpty == 0) { // 放到沾板上
        	if (tomatos.get(holding).type == 1) {
	        	chopBoard.setImage(tomatoOnChopBoardImage);
	        	isEmpty = 1;
        	} else if (tomatos.get(holding).type == 2) {
	        	chopBoard.setImage(choppedTomatoOnChopBoardImage);
	        	isEmpty = 2;
        	}
        	paneField.getChildren().remove(tomatos.get(holding).imageView);
        	tomatos.remove(holding);
        	holding = -1;
        } else if (e.getCode() == KeyCode.E && holding != -1) { // 把手中的東西放下
        	tomatos.get(holding).x = tomatos.get(holding).imageView.getLayoutX() + 25;
        	tomatos.get(holding).y = tomatos.get(holding).imageView.getLayoutY() + 25;
        	holding = -1;
        } else if (e.getCode() == KeyCode.E && holding == -1 && isArroundChopBoard(handX, handY) && isEmpty != 0) { // 從沾板上拿下來
        	if (isEmpty == 1) {
            	item newTomato = new item(new ImageView(tomatoImage), handX, handY, isEmpty);
            	newTomato.imageView.setLayoutX(handX);
            	newTomato.imageView.setLayoutY(handY);
            	newTomato.imageView.setFitWidth(50);
            	newTomato.imageView.setFitHeight(50);
            	tomatos.push(newTomato);
            	paneField.getChildren().add(newTomato.imageView);
            	holding = tomatos.indexOf(newTomato);
        	} else if (isEmpty == 2) {
        		item newTomato = new item(new ImageView(choppedTomatoImage), handX, handY, isEmpty);
            	newTomato.imageView.setLayoutX(handX);
            	newTomato.imageView.setLayoutY(handY);
            	newTomato.imageView.setFitWidth(50);
            	newTomato.imageView.setFitHeight(50);
            	tomatos.push(newTomato);
            	paneField.getChildren().add(newTomato.imageView);
            	holding = tomatos.indexOf(newTomato);
        	}
        	chopBoard.setImage(chopBoardImage);
        	isEmpty = 0;
        } else if (e.getCode() == KeyCode.E && holding == -1) { // 把附近的東西拿起來
	        for (var t:tomatos) {
	        	if (t.isAround(handX, handY)) {
	        		holding = tomatos.indexOf(t);
	        		break;
	        	}
	        }
        }
    }
    
    boolean isArroundChopBoard(double x, double y) {
		if (x < 96 && x > 78 && y < 314 && y > 266) {
			return true;
		} else {
			return false;
		}
	}

	public void releaseHandle(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.UP) {
        	goNorth = false;
        }
        if (e.getCode() == KeyCode.DOWN) {
        	goSouth = false;
        }
        if (e.getCode() == KeyCode.RIGHT) {
        	goEast = false;
        	// character.setImage(image0);
        }
        if (e.getCode() == KeyCode.LEFT) {
        	goWest = false;
        	// character.setImage(image3);
        }
        if (e.getCode() == KeyCode.SHIFT) {
        	running = false;
        }
        if (e.getCode() == KeyCode.ESCAPE) {
        	Parent levelMenu = FXMLLoader.load(getClass().getResource("levelMenu.fxml"));
    		Scene levelMenuScene = new Scene(levelMenu);
    		levelMenuScene.getRoot().requestFocus();
    		Main.currentStage.setScene(levelMenuScene);
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

    public void setHand(double x, double y) {
    	if (goNorth) {
        	handY = (y + (character.getFitHeight() / 2) - 10);
    	}
    	if (goSouth) {
        	handY = (y + (character.getFitHeight() / 2) - 10);
    	}
    	if (goEast) {
        	handX = (x + character.getFitWidth() - 30);
        	handY = (y + (character.getFitHeight() / 2) - 10);
    	}
    	if (goWest) {
        	handX = x + 30;
        	handY = (y + (character.getFitHeight() / 2) - 10);
    	}
    }
    
    public boolean isTakingTomato() {
    	if (handX < 530 && handX > 488 && handY < 263 && handY > 233) {
    		return true;
    	} else {
    		return false;
    	}
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
    	tomatos.push(plateItem);
    	plateItem.imageView.setLayoutX(64);
    	plateItem.imageView.setLayoutY(185);
    	plateItem.imageView.setFitWidth(50);
    	plateItem.imageView.setFitHeight(50);
    	paneField.getChildren().add(plateItem.imageView);
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
    		if (holding != -1) {
    			tomatos.get(holding).imageView.setLayoutX(handX - 25);
    			tomatos.get(holding).imageView.setLayoutY(handY - 25);
    		}
    		setHand(x ,y);
    		// test.setLayoutX(handX);
    		// test.setLayoutY(handY);
    		// if (isArroundChopBoard(handX, handY)) {
	    	// 	System.out.println(handX);
	    	// 	System.out.println(handY);
    		// }
		}));
    	// System.out.println(fps);
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
