package finalProject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class levelTwoController implements Initializable{
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
	@FXML
	Pane pausePane;
	@FXML
	Label score;
	@FXML
	Label clock;
	@FXML
	Button resume;
	@FXML
	Button back;
	@FXML
	Pane finishPane;
	@FXML
	Label finalScore;
	@FXML
	ImageView star1;
	@FXML
	ImageView star2;
	@FXML
	ImageView star3;
	@FXML
	Button returnButton;
	
	Image tomatoImage = new Image(getClass().getResource("./image/tomato.png").toExternalForm());
	Image vegImage = new Image(getClass().getResource("./image/veg.png").toExternalForm());
	Image tomatoInPlateImage = new Image(getClass().getResource("./image/tomato_in_plate.png").toExternalForm());
	Image vegInPlateImage = new Image(getClass().getResource("./image/veg_in_plate.png").toExternalForm());
	Image saladInPlateImage = new Image(getClass().getResource("./image/salad.png").toExternalForm());
	Image cleanPlateImage = new Image(getClass().getResource("./image/clean_plate.png").toExternalForm());
	Image dirtyPlateImage = new Image(getClass().getResource("./image/dirty_plate.png").toExternalForm());
	Image choppedTomatoImage = new Image(getClass().getResource("./image/chopped_tomato.png").toExternalForm());
	Image choppedVegImage = new Image(getClass().getResource("./image/chopped_veg.png").toExternalForm());
	Image tomatoOnChopBoardImage = new Image(getClass().getResource("./image/tomato_on_chop_board.png").toExternalForm());
	Image vegOnChopBoardImage = new Image(getClass().getResource("./image/veg_on_chop_board.png").toExternalForm());
	Image choppedTomatoOnChopBoardImage = new Image(getClass().getResource("./image/chopped_tomato_on_chop_board.png").toExternalForm());
	Image choppedVegOnChopBoardImage = new Image(getClass().getResource("./image/chopped_veg_on_chop_board.png").toExternalForm());
	Image chopBoardImage = new Image(getClass().getResource("./image/chop_board.png").toExternalForm());
	
	@FXML
	Circle test;
	@FXML
	Pane readme;
	
	double handX;
	double handY;
	boolean goNorth = false, goSouth = false, goEast = false, goWest = false;
	boolean running = false;
	boolean turnLeft = false;
	int scoreValue = 0;
	int time = 125;
	boolean state = false;
	
	class item {
		public item(ImageView imageView, double x, double y, int type) {
			this.imageView = imageView;
			this.x = x + 25;
			this.y = y + 25;
			this.type = type;
		}
		public int type; // 1->tomato 2->chopped tomato 3->empty plate 4->full plate with tomato 5->veg 6->chopped veg 7->full plate with veg
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
	item plateItem = new item(new ImageView(cleanPlateImage), 493, 133, 3);
	int holding = -1; // (-1)->空手 (>=0)->index
	LinkedList<item> items = new LinkedList<item>();
	
	public void finish() {
		finalScore.setText(Integer.toString(scoreValue));
		finishPane.setVisible(true);
		if (scoreValue >= 90) {
			int stars = 0;
			if (scoreValue >= 90) {
				star1.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
				stars++;
			}
			if (scoreValue >= 150) {
				star2.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
				stars++;
			}
			if (scoreValue >= 200) {
				star3.setImage(new Image(getClass().getResource("./image/star.png").toExternalForm()));
				stars++;
			}
			if (scoreValue > Main.scores.get(1)) {
				Main.scores.set(0, scoreValue);
				Main.stars.set(1, stars);
			}
		} else {
			returnButton.setText("返回");
		}
	}
	
	public void back() throws IOException {
		Parent levelMenu = FXMLLoader.load(getClass().getResource("levelMenu.fxml"));
		Scene levelMenuScene = new Scene(levelMenu);
		levelMenuScene.getRoot().requestFocus();
		Main.currentStage.setScene(levelMenuScene);
	}

	public void retry() throws IOException {
		Parent levelTwo = FXMLLoader.load(getClass().getResource("levelTwo.fxml"));
		Scene levelTwoScene = new Scene(levelTwo);
		levelTwoScene.getRoot().requestFocus();
		Main.currentStage.setScene(levelTwoScene);
	}
	
    public void pressedHandle(KeyEvent e){
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
        	if (holding == -1 && isArroundChopBoard(handX, handY) && isEmpty == 1) {
        		chopBoard.setImage(choppedTomatoOnChopBoardImage);
            	isEmpty = 2;
        	} else if (holding == -1 && isArroundChopBoard(handX, handY) && isEmpty == 5) {
        		chopBoard.setImage(choppedVegOnChopBoardImage);
            	isEmpty = 6;
        	} else if (holding != -1 && items.get(holding).type == 9 && isAroundSink()) {
	        	plateItem.imageView.setImage(cleanPlateImage);
	        	plateItem.type = 3;
        	}
        }
        
        if (e.getCode() == KeyCode.E) {
	        if (isTakingTomato() && holding == -1) { // 拿新番茄
	        	item newTomato = new item(new ImageView(tomatoImage), handX, handY, 1);
	        	newTomato.imageView.setLayoutX(handX);
	        	newTomato.imageView.setLayoutY(handY);
	        	newTomato.imageView.setFitWidth(50);
	        	newTomato.imageView.setFitHeight(50);
	        	items.push(newTomato);
	        	paneField.getChildren().add(newTomato.imageView);
	        	holding = items.indexOf(newTomato);
	        } else if (isTakingVeg() && holding == -1) { // 拿新菜
	        	item newTomato = new item(new ImageView(vegImage), handX, handY, 5);
	        	newTomato.imageView.setLayoutX(handX);
	        	newTomato.imageView.setLayoutY(handY);
	        	newTomato.imageView.setFitWidth(50);
	        	newTomato.imageView.setFitHeight(50);
	        	items.push(newTomato);
	        	paneField.getChildren().add(newTomato.imageView);
	        	holding = items.indexOf(newTomato);
	        } else if (isServingMeal() && holding != -1 && items.get(holding).type == 4) { // 上番茄
	        	paneField.getChildren().remove(items.get(holding).imageView);
	        	items.remove(holding);
	        	holding = -1;
	        	scoreValue += 30;
	        	plateItem = new item(new ImageView(dirtyPlateImage), 501, 141, 9);
	        	items.push(plateItem);
	        	plateItem.imageView.setLayoutX(501);
	        	plateItem.imageView.setLayoutY(141);
	        	plateItem.imageView.setFitWidth(50);
	        	plateItem.imageView.setFitHeight(50);
	        	paneField.getChildren().add(plateItem.imageView);
	        	score.setText(Integer.toString(scoreValue));
	        } else if (isServingMeal() && holding != -1 && items.get(holding).type == 7) { // 上菜
	        	paneField.getChildren().remove(items.get(holding).imageView);
	        	items.remove(holding);
	        	holding = -1;
	        	scoreValue += 35;
	        	plateItem = new item(new ImageView(dirtyPlateImage), 501, 141, 9);
	        	items.push(plateItem);
	        	plateItem.imageView.setLayoutX(501);
	        	plateItem.imageView.setLayoutY(141);
	        	plateItem.imageView.setFitWidth(50);
	        	plateItem.imageView.setFitHeight(50);
	        	paneField.getChildren().add(plateItem.imageView);
	        	score.setText(Integer.toString(scoreValue));
	        } else if (isServingMeal() && holding != -1 && items.get(holding).type == 8) { // 上沙拉
	        	paneField.getChildren().remove(items.get(holding).imageView);
	        	items.remove(holding);
	        	holding = -1;
	        	scoreValue += 50;
	        	plateItem = new item(new ImageView(dirtyPlateImage), 501, 141, 9);
	        	items.push(plateItem);
	        	plateItem.imageView.setLayoutX(501);
	        	plateItem.imageView.setLayoutY(141);
	        	plateItem.imageView.setFitWidth(50);
	        	plateItem.imageView.setFitHeight(50);
	        	paneField.getChildren().add(plateItem.imageView);
	        	score.setText(Integer.toString(scoreValue));
	        } else if (holding != -1 && plateItem.isAround(handX, handY)) { // 放到盤子上
	        	if ((items.get(holding).type == 2 && plateItem.type == 7) || (items.get(holding).type == 6 && plateItem.type == 4)) { // 變沙拉
		        	plateItem.imageView.setImage(saladInPlateImage);
		        	plateItem.type = 8;
		        	paneField.getChildren().remove(items.get(holding).imageView);
		        	items.remove(holding);
		        	holding = -1;
	        	} else if (items.get(holding).type == 2 && plateItem.type == 3) { // 放番茄
		        	plateItem.imageView.setImage(tomatoInPlateImage);
		        	plateItem.type = 4;
		        	paneField.getChildren().remove(items.get(holding).imageView);
		        	items.remove(holding);
		        	holding = -1;
	        	} else if (items.get(holding).type == 6 && plateItem.type == 3) { // 放菜
		        	plateItem.imageView.setImage(vegInPlateImage);
		        	plateItem.type = 7;
		        	paneField.getChildren().remove(items.get(holding).imageView);
		        	items.remove(holding);
		        	holding = -1;
	        	}
	        } else if (holding != -1 && isArroundChopBoard(handX, handY) && isEmpty == 0) { // 放到沾板上
	        	if (items.get(holding).type == 1) {
		        	chopBoard.setImage(tomatoOnChopBoardImage);
		        	isEmpty = 1;
	        	} else if (items.get(holding).type == 2) {
		        	chopBoard.setImage(choppedTomatoOnChopBoardImage);
		        	isEmpty = 2;
	        	} else if (items.get(holding).type == 5) {
		        	chopBoard.setImage(vegOnChopBoardImage);
		        	isEmpty = 5;
	        	} else if (items.get(holding).type == 6) {
		        	chopBoard.setImage(choppedVegOnChopBoardImage);
		        	isEmpty = 6;
	        	}
	        	paneField.getChildren().remove(items.get(holding).imageView);
	        	items.remove(holding);
	        	holding = -1;
	        } else if (holding != -1) { // 把手中的東西放下
	        	items.get(holding).x = items.get(holding).imageView.getLayoutX() + 25;
	        	items.get(holding).y = items.get(holding).imageView.getLayoutY() + 25;
	        	holding = -1;
	        } else if (holding == -1 && isArroundChopBoard(handX, handY) && isEmpty != 0) { // 從沾板上拿下來
	        	if (isEmpty == 1) { // 拿番茄
	            	item newTomato = new item(new ImageView(tomatoImage), handX, handY, isEmpty);
	            	newTomato.imageView.setLayoutX(handX);
	            	newTomato.imageView.setLayoutY(handY);
	            	newTomato.imageView.setFitWidth(50);
	            	newTomato.imageView.setFitHeight(50);
	            	items.push(newTomato);
	            	paneField.getChildren().add(newTomato.imageView);
	            	holding = items.indexOf(newTomato);
	        	} else if (isEmpty == 2) { // 拿切好的番茄
	        		item newTomato = new item(new ImageView(choppedTomatoImage), handX, handY, isEmpty);
	            	newTomato.imageView.setLayoutX(handX);
	            	newTomato.imageView.setLayoutY(handY);
	            	newTomato.imageView.setFitWidth(50);
	            	newTomato.imageView.setFitHeight(50);
	            	items.push(newTomato);
	            	paneField.getChildren().add(newTomato.imageView);
	            	holding = items.indexOf(newTomato);
	        	} else if (isEmpty == 5) { // 拿菜
	        		item newVeg = new item(new ImageView(vegImage), handX, handY, isEmpty);
	        		newVeg.imageView.setLayoutX(handX);
	        		newVeg.imageView.setLayoutY(handY);
	        		newVeg.imageView.setFitWidth(50);
	        		newVeg.imageView.setFitHeight(50);
	            	items.push(newVeg);
	            	paneField.getChildren().add(newVeg.imageView);
	            	holding = items.indexOf(newVeg);
	        	} else if (isEmpty == 6) { // 拿切好的菜
	        		item newVeg = new item(new ImageView(choppedVegImage), handX, handY, isEmpty);
	        		newVeg.imageView.setLayoutX(handX);
	        		newVeg.imageView.setLayoutY(handY);
	        		newVeg.imageView.setFitWidth(50);
	        		newVeg.imageView.setFitHeight(50);
	            	items.push(newVeg);
	            	paneField.getChildren().add(newVeg.imageView);
	            	holding = items.indexOf(newVeg);
	        	}
	        	chopBoard.setImage(chopBoardImage);
	        	isEmpty = 0;
	        } else if (holding == -1) { // 把附近的東西拿起來
		        for (var t:items) {
		        	if (t.isAround(handX, handY)) {
		        		holding = items.indexOf(t);
		        		break;
		        	}
		        }
	        }
        }
    }
    
    boolean isArroundChopBoard(double x, double y) {
		if (x < 96 && x > 65 && y < 314 && y > 266) {
			return true;
		} else {
			return false;
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
        	state = !state;
        	pausePane.setVisible(state);
        	// pausePane.requestFocus();
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
    	if (handX < 545 && handX > 488 && handY < 263 && handY > 233) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isTakingVeg() {
    	if (handX < 545 && handX > 506 && handY < 320 && handY > 287) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isServingMeal() {
    	if (handX < 545 && handX > 488 && handY < 137 && handY > 89) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isAroundSink() {
    	if (handX < 115 && handX > 57 && handY < 220 && handY > 178) {
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
    	paneField.requestFocus();
    	items.push(plateItem);
    	plateItem.imageView.setLayoutX(501);
    	plateItem.imageView.setLayoutY(141);
    	plateItem.imageView.setFitWidth(50);
    	plateItem.imageView.setFitHeight(50);
    	paneField.getChildren().add(plateItem.imageView);
    	Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000/60),(e)-> {
    		if (time > 0 && time < 121) {
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
	    			items.get(holding).imageView.setLayoutX(handX - 25);
	    			items.get(holding).imageView.setLayoutY(handY - 25);
	    		}
	    		setHand(x ,y);
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
		
		Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000),(e)-> {
			time -= 1;
			if (time == 0) {
				finish();
			} else if (time == 120) {
				readme.setVisible(false);
			} else if (time < 120) {
				String m = Integer.toString(time / 60);
				String s;
				if ((time % 60) < 10) {
					s = '0' + Integer.toString(time % 60);
				} else {
					s = Integer.toString(time % 60);
				}
				clock.setText(m + ':' + s);
			}
		}));
		timer.setCycleCount(126);
		timer.play();
	}

}
