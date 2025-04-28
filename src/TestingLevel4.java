import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class TestingLevel4 extends GraphicsProgram implements ActionListener {
	private ArrayList<GOval> enemyBullets;
	private ArrayList<GOval> userBullets;
	private Timer movement;
	private RandomGenerator rgen;

	public static final int PROGRAM_WIDTH = 900;
	public static final int PROGRAM_HEIGHT = 600;
	public static final int SIZE = 25;
	public static final int MS = 25;
	public static final int ENEMY_PROJ_SPEED = 32 ;
	public static final int ENEMY_PROJ_SIZE = 12;
	private final int USER_PROJ_SPEED = 10;
	private final int USER_PROJ_SIZE = 8;
	private static final int ENEMY_MOVE_SPEED = 30;

	private int enemyShootCooldown = 20;
	private int enemyTicksSinceLastShot = 0;

	private int mainShipShootCooldown = 12; // lower = faster shooting
	private int mainShipTicksSinceLastShot = 0;

	private int elapsedTime = 0; // time in seconds
	private int score;
	private GLabel timerLabel;
	private GLabel scoreLabel;
	private int msCounter = 0;

	private GLabel bonusTimerLabel;
	private int bonusPoints = 0;
	private long bonusStartTime;
	private final int BONUS_TIME_LIMIT = 30; // seconds

	private boolean mousePressed = false;
	private boolean gameOverFlag = false;

	//private ArrayList<GPolygon> enemyVisuals;
	private ArrayList<GImage> enemyImages;
	//private GPolygon visualMainShip;
	private GRect retryButton;
	private GLabel retryLabel;
	
	private int waveNumber = 1;
	private GImage mainShipImage;
	private boolean levelEnded = false;
	private GameData gameData;
	
	private MainApplication mainScreen;
	private EndLevelSummary summaryScreen; 
	
	
	public void setMainScreen(MainApplication mainScreen) {
	    this.mainScreen = mainScreen;
	}

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		addMouseListeners();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		getGCanvas().setCursor(blankCursor);
		
		enemyBullets = new ArrayList<>();
		//enemyVisuals = new ArrayList<>();
		userBullets = new ArrayList<>();
		enemyImages = new ArrayList<>();
	}

	public void run() {
		rgen = RandomGenerator.getInstance();
		
		GImage background = new GImage("Media/Background (T-minus Infinitum).png", 0, 0);
		add(background);
		
		
		mainShipImage = GraphicsPane.getMainSpaceshipImage(100, 400);
		add(mainShipImage);
		
		int carriedOverScore = 0;
		if (gameData != null) {
		        carriedOverScore = gameData.getTotalScore();
		    }
		
		 // Initialize score from GameData
	    if (gameData != null) {
	        score = gameData.getTotalScore();
	    }

		/*UserSpaceship mainship = new UserSpaceship(SpaceshipType.userSpaceship, 14, 12);
		visualMainShip = mainship.getVisualMainShip();
		add(visualMainShip);*/

		
		spawnWave(1);

		movement = new Timer(MS, this);
		movement.start();

		// Added a timer
		timerLabel = new GLabel("Time: 0s", PROGRAM_WIDTH - 900, 20);
		timerLabel.setFont("SansSerif-bold-16");
		timerLabel.setColor(Color.YELLOW);
		add(timerLabel);

		// Added a timer tracking bonus points
		bonusStartTime = System.currentTimeMillis();
		bonusTimerLabel = new GLabel("Bonus Time: 30", 0, 60);
		bonusTimerLabel.setFont("SansSerif-bold-16");
		bonusTimerLabel.setColor(Color.YELLOW);
		add(bonusTimerLabel);

		// Added a point system
		scoreLabel = new GLabel("Score: " + carriedOverScore, 810, 20);
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		scoreLabel.setColor(Color.YELLOW);
		add(scoreLabel);
		hideCursor();

	}
	
	public void setGameData (GameData data) {
		this.gameData = data;
	}

	public void userSpaceshipMovement(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
		//visualMainShip.setLocation(mouseX, mouseY);
		if (mainShipImage != null) {
	        mainShipImage.setLocation(
	            mouseX - mainShipImage.getWidth() / 2,
	            mouseY - mainShipImage.getHeight() / 2
	        );
	    }
	}

	public void projectileCollisionDetection() {
		for (GOval bullet : enemyBullets) {
			/*if (bullet.getBounds().intersects(visualMainShip.getBounds())) {
				System.out.println("Collision Detected!");
				enemyBullets.remove(bullet);
				remove(bullet);
				gameOver();
				break;
			}*/
			
			if (bullet.getBounds().intersects(mainShipImage.getBounds())) {
				System.out.println("Collision Detected!");
				enemyBullets.remove(bullet);
				remove(bullet);
				gameOver();
				break;
			}
		}
	}

	public void enemyCollisionDetection() {
		/*for (GPolygon enemyVisual : enemyVisuals) {
			if (enemyVisual.getBounds().intersects(visualMainShip.getBounds())) {
				System.out.println("Enemy Collision Detected!");
				remove(enemyVisual);
				enemyVisuals.remove(enemyVisual);
				gameOver();
				break;
			}
		}*/
		
		for (GImage enemy : enemyImages) {
	        if (enemy.getBounds().intersects(mainShipImage.getBounds())) {
	            System.out.println("Enemy Collision Detected!");
	            //enemiesToRemove.add(enemy);
	            gameOver();
	            break; // Exit loop after first collision
	        }
	    }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		userSpaceshipMovement(e);
		projectileCollisionDetection();
		enemyCollisionDetection();
	}

	private void shootFromEnemy(double x, double y) {
		double enemyTipX = x;
		double enemyTipY = y - SIZE / 2;

		GOval bullet = new GOval(enemyTipX - ENEMY_PROJ_SIZE / 2, enemyTipY - ENEMY_PROJ_SIZE / 2, ENEMY_PROJ_SIZE,
				ENEMY_PROJ_SIZE);
		bullet.setFilled(true);
		bullet.setColor(Color.RED);
		add(bullet);
		enemyBullets.add(bullet);
	}

	private void shootFromUser() {
		//double shipX = visualMainShip.getX() + SIZE / 2;
		//double shipY = visualMainShip.getY();
		double shipX = mainShipImage.getX() + SIZE / 2;
		double shipY = mainShipImage.getY();

		GOval bullet = new GOval(shipX - USER_PROJ_SIZE / 2, shipY - USER_PROJ_SIZE, USER_PROJ_SIZE, USER_PROJ_SIZE);
		bullet.setFilled(true);
		bullet.setColor(Color.GREEN);
		add(bullet);
		userBullets.add(bullet);
	}

	private void moveAllEnemyBullets() {
		ArrayList<GOval> bulletsToRemove = new ArrayList<>();

		for (GOval bullet : enemyBullets) {
			bullet.move(0, ENEMY_PROJ_SPEED);
			if (bullet.getY() > PROGRAM_HEIGHT) {
				bulletsToRemove.add(bullet);
			}
		}

		for (GOval bullet : bulletsToRemove) {
			remove(bullet);
			enemyBullets.remove(bullet);
		}
	}
	
	private void spawnWave(int wave) {
	    /*enemyVisuals.clear();

	    if (wave == 1) {
	        Enemyship1[] wave1Enemies = {
	            new Enemyship1(SpaceshipType.eType1, 5, 7),
	            new Enemyship1(SpaceshipType.eType1, 5, 11),
	            new Enemyship1(SpaceshipType.eType1, 5, 15),
	            new Enemyship1(SpaceshipType.eType1, 1, 5),
	            new Enemyship1(SpaceshipType.eType1, 1, 9),
	            new Enemyship1(SpaceshipType.eType1, 1, 13),
	            new Enemyship1(SpaceshipType.eType1, 1, 17)
	        };

	        for (Enemyship1 enemy : wave1Enemies) {
	            GPolygon enemyVisual = enemy.getVisual();
	            enemyVisuals.add(enemyVisual);
	            add(enemyVisual);
	        }
	    } else if (wave == 2) {
	        Enemyship2[] wave2Enemies = {
	            new Enemyship2(SpaceshipType.eType2, 5, 7),
	            new Enemyship2(SpaceshipType.eType2, 5, 11),
	            new Enemyship2(SpaceshipType.eType2, 5, 15),
	            new Enemyship2(SpaceshipType.eType2, 1, 5),
	            new Enemyship2(SpaceshipType.eType2, 1, 9),
	            new Enemyship2(SpaceshipType.eType2, 1, 13),
	            new Enemyship2(SpaceshipType.eType2, 1, 17)
	        };

	        for (Enemyship2 enemy : wave2Enemies) {
	            GPolygon enemyVisual = enemy.getVisual();
	            enemyVisuals.add(enemyVisual);
	            add(enemyVisual);
	        }
	    }
 		for (GOval bullet : enemyBullets) {
 			if (bullet.getBounds().intersects(visualMainShip.getBounds())) {
 				System.out.println("Collision Detected!");
 				enemyBullets.remove(bullet);
 				remove(bullet);
 				gameOver();
 				break;
 			}
 		}*/
		
		enemyImages.clear();

	    if (wave == 1) {
	        // Define positions for wave 1 enemies
	        int[] startRows = {7, 7, 7, 1, 1, 1, 1};
	        int[] startCols = {12, 17, 22, 8, 13, 18, 22};

	        for (int i = 0; i < startRows.length; i++) {
	            double x = startCols[i] * SIZE;
	            double y = startRows[i] * SIZE;
	            GImage enemyImage = GraphicsPane.getEnemySpaceship3(x, y);
	            enemyImages.add(enemyImage);
	            add(enemyImage);
	        }
	    } else if (wave == 2) {
	        // Define positions for wave 2 enemies
	        int[] startRows = {7, 7, 7, 1, 1, 1, 1};
	        int[] startCols = {12, 17, 22, 8, 13, 18, 22};

	        for (int i = 0; i < startRows.length; i++) {
	            double x = startCols[i] * SIZE;
	            double y = startRows[i] * SIZE;
	            GImage enemyImage = GraphicsPane.getEnemySpaceship4(x, y);
	            enemyImages.add(enemyImage);
	            add(enemyImage);
	        }
	    }
	    
	    // Update collision detection for enemy bullets
	    for (GOval bullet : enemyBullets) {
	        if (bullet.getBounds().intersects(mainShipImage.getBounds())) {
	            System.out.println("Collision Detected!");
	            enemyBullets.remove(bullet);
	            remove(bullet);
	            gameOver();
	            break;
	        }
	    }
 	}

 
 	// Firing from main ship using left mouse button
 	@Override
 	public void mousePressed(MouseEvent e) {
 		if (SwingUtilities.isLeftMouseButton(e)) {
 			mousePressed = true;
 		}
 		// Retry button clicked
 		if (gameOverFlag && retryButton != null && retryButton.contains(e.getX(), e.getY())) {
 			restartGame();
 		}
 	}
 
 	@Override
 	public void mouseReleased(MouseEvent e) {
 		if (SwingUtilities.isLeftMouseButton(e)) {
 			mousePressed = false;
 		}
 	}
 
 	@Override
 	public void mouseMoved(MouseEvent e) {
 		if (gameOverFlag) {
	        return; // Don't process mouse events if the game is over
	    }
 		userSpaceshipMovement(e);
 		projectileCollisionDetection();
 		enemyCollisionDetection();
 		if (levelEnded) return; // Stop the ship from moving
 	}
 
 	@Override
 	public void actionPerformed(ActionEvent e) {
 	    moveAllEnemyBullets();
 	    moveUserBullets();

 	    // Main ship shooting while mouse is held down
 	    if (mousePressed && mainShipTicksSinceLastShot >= mainShipShootCooldown) {
 	        shootFromUser();
 	        mainShipTicksSinceLastShot = 0;
 	    }
 	    if (mainShipTicksSinceLastShot < mainShipShootCooldown) {
 	        mainShipTicksSinceLastShot++;
 	    }

 	    // Enemy shooting
 	    
 	    /*for (GPolygon enemy : enemyVisuals) {
 	        enemyTicksSinceLastShot++;
 	        if (enemyTicksSinceLastShot >= enemyShootCooldown) {
 	            if (rgen.nextBoolean(0.1)) { // 10% chance to shoot
 	                shootFromEnemy(enemy.getX() + SIZE / 2, enemy.getY() + SIZE);
 	                enemyTicksSinceLastShot = 0;
 	            }
 	        }
 	    }*/
 	    
 	   for (GImage enemy : enemyImages) {
		    enemyTicksSinceLastShot++;
		    if (enemyTicksSinceLastShot >= enemyShootCooldown) {
		        if (rgen.nextBoolean(0.1)) {
		            shootFromEnemy(
		                enemy.getX() + enemy.getWidth() / 2, // center of image
		                enemy.getY() + enemy.getHeight()      // bottom of image (ship's tip)
		            );
		            enemyTicksSinceLastShot = 0;
		        }
		    }
		}

 	    // Enemy movement with collision and boundary detection
 	    
 	    /*for (GPolygon enemy : new ArrayList<>(enemyVisuals)) {
 	        if (rgen.nextBoolean(0.05)) { // 5% chance to move
 	            double dx = rgen.nextBoolean() ? ENEMY_MOVE_SPEED : -ENEMY_MOVE_SPEED;

 	            boolean willCollide = false;
 	            double newX = enemy.getX() + dx;
 	            double newY = enemy.getY(); // No change in Y

 	            double enemyLeft = newX;
 	            double enemyRight = newX + SIZE;
 	            double enemyTop = newY;
 	            double enemyBottom = newY + SIZE;

 	            for (GPolygon other : enemyVisuals) {
 	                if (other != enemy) {
 	                    double otherLeft = other.getX();
 	                    double otherRight = other.getX() + SIZE;
 	                    double otherTop = other.getY();
 	                    double otherBottom = other.getY() + SIZE;

 	                    if (enemyRight > otherLeft && enemyLeft < otherRight &&
 	                        enemyBottom > otherTop && enemyTop < otherBottom) {
 	                        willCollide = true;
 	                        break;
 	                    }
 	                }
 	            }

 	            // Only move if no collision AND within screen boundaries
 	            if (!willCollide) {
 	                if (newX >= 0 && newX + SIZE <= PROGRAM_WIDTH) {
 	                    enemy.move(dx, 0);
 	                }
 	            }
 	        }
 	    }*/
 	   
 	  for (GImage enemy : new ArrayList<>(enemyImages)) {
		    if (rgen.nextBoolean(0.05)) { // 5% chance to move
		        double dx = rgen.nextBoolean() ? ENEMY_MOVE_SPEED : -ENEMY_MOVE_SPEED;

		        // Check if the enemy would collide with another after moving
		        boolean willCollide = false;
		        double newX = enemy.getX() + dx;
		        double newY = enemy.getY(); // No change in Y, since they only move left or right

		        // Manually calculate the bounds of the moving enemy
		        double enemyLeft = newX;
		        double enemyRight = newX + enemy.getWidth();
		        double enemyTop = newY;
		        double enemyBottom = newY + enemy.getHeight();

		        // Iterate through all other enemies
		        for (GImage other : enemyImages) {
		            if (other != enemy) {
		                // Manually calculate the bounds of the other enemy
		                double otherLeft = other.getX();
		                double otherRight = other.getX() + other.getWidth();
		                double otherTop = other.getY();
		                double otherBottom = other.getY() + other.getHeight();

		                // Check if their bounding boxes overlap
		                if (enemyRight > otherLeft && enemyLeft < otherRight && enemyBottom > otherTop
		                        && enemyTop < otherBottom) {
		                    willCollide = true;
		                    break; // No need to check further if collision is detected
		                }
		            }
		        }

		        // If no collision, apply the move
		        if (!willCollide) {
 	                if (newX >= 0 && newX + SIZE <= PROGRAM_WIDTH) {
 	                    enemy.move(dx, 0);
 	                }
		        }
		    }
		}

 	    // Timer update for elapsed time
 	    msCounter += MS;
 	    if (msCounter >= 1000) { // 1000 milliseconds = 1 second
 	        elapsedTime++;
 	        timerLabel.setLabel("Time: " + elapsedTime + "s");
 	        msCounter = 0;
 	    }

 	    // Bonus countdown timer update
 	    long elapsedBonusTime = (System.currentTimeMillis() - bonusStartTime) / 1000;
 	    int remainingBonusTime = BONUS_TIME_LIMIT - (int) elapsedBonusTime;
 	    if (remainingBonusTime >= 0) {
 	        bonusTimerLabel.setLabel("Bonus Time: " + remainingBonusTime);
 	    } else {
 	        bonusTimerLabel.setLabel("Bonus Time: 0");
 	    }

 	    if (levelEnded) return;

 	    projectileCollisionDetection();
 	    enemyCollisionDetection();
 	}

 
 	private void moveUserBullets() {
 		ArrayList<GOval> bulletsToRemove = new ArrayList<>();
 		/*ArrayList<GPolygon> enemiesToRemove = new ArrayList<>();*/
 
 		for (GOval bullet : userBullets) {
			bullet.move(0, -USER_PROJ_SPEED);

			if (bullet.getY() < 0) {
				bulletsToRemove.add(bullet);
				continue;
			}

			/*for (GPolygon enemy : enemyVisuals) {
				if (bullet.getBounds().intersects(enemy.getBounds())) {
					bulletsToRemove.add(bullet);
					enemiesToRemove.add(enemy);
					score += 100; // +100 points per enemy
					//updateScore(100);
					updateScoreLabel();
					break;
				}
			}
		}*/
			
			for (GImage enemy : enemyImages) {
	            if (bullet.getBounds().intersects(enemy.getBounds())) {
	                bulletsToRemove.add(bullet);
	                //enemiesToRemove.add(enemy);
	                remove(enemy);               // Remove from display
	                enemyImages.remove(enemy);  // Remove from ArrayList
	                score += 100; // +100 points per enemy
	                //updateScore(100);
	                updateScoreLabel();
	                break;
	            }
	        }
	    }

		for (GOval bullet : bulletsToRemove) {
			remove(bullet);
			userBullets.remove(bullet);
		}

		/*for (GPolygon enemy : enemiesToRemove) {
			remove(enemy);
			enemyVisuals.remove(enemy);
		}*/
		
		/*for (GImage enemy : enemiesToRemove) {
	        remove(enemy);
	        enemyImages.remove(enemy);
	    }*/

		/*if (enemyVisuals.isEmpty()) {
			long timeToClear = (System.currentTimeMillis() - bonusStartTime) / 1000;
			if (timeToClear <= BONUS_TIME_LIMIT) {
				bonusPoints += 1500; // Add to bonus points for finishing the level quickly
				updateBonusPointsLabel();
			}
			movement.stop();
			showEndLevelSummary();// Show the end level summary
		}*/
		if (enemyImages.isEmpty()) {
		    long timeToClear = (System.currentTimeMillis() - bonusStartTime) / 1000;
		    if (timeToClear <= BONUS_TIME_LIMIT) {
		        bonusPoints += 1500;
		        updateBonusPointsLabel();
		    }

		    if (waveNumber < 2) {  // Assuming 2 waves total
		        waveNumber++;
		        spawnWave(waveNumber);
		        // Reset bonus timer for the new wave if you want
		        bonusStartTime = System.currentTimeMillis();
		    } else {
		        movement.stop();
		        showEndLevelSummary();
		        levelEnded = true;
		    }
		}
	}
 	
 	private void showEndLevelSummary() {
		levelEnded = true;
		gameOverFlag = true; 
		
		// Update gameData with this level's results
	    if (gameData != null) {
	        gameData.addScore(score);
	        gameData.addBonus(bonusPoints);
	       //gameData.addTimeSurvived(elapsedTime);
	    }
	    
	    removeAll();
	    showCursor();
	    
	    // Create summary screen with callback to launch Level 2 and close this window
	    summaryScreen = new EndLevelSummary(score, bonusPoints, elapsedTime, this::nextLevel);

	    add(summaryScreen, (PROGRAM_WIDTH - summaryScreen.getWidth()) / 2, (PROGRAM_HEIGHT - summaryScreen.getHeight()) / 2);

	}
 	
 	private void closeWindow() {
	    // Get the top-level window (the JFrame that contains this program)
	    java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(getGCanvas());
	    if (window != null) {
	        window.dispose();
	    }
	}
 	
 	private void nextLevel() {
	    // Logic to transition to the next level
	    /*System.out.println("Moving to next level...");
	    TestingLevel5 next = new TestingLevel5();
	    next.setGameData(gameData);
	    next.start();*/ // or next.startApplication() if needed
 		
 		System.out.println("Moving to next level...");
	    if (mainScreen != null) {
	    	mainScreen.setGameData(gameData);
	        mainScreen.launchLevel5();
	    }
	    closeWindow();
	}
 	
 	private void gameOver() {
 		gameOverFlag = true;
 		movement.stop(); // Stop the timer
 		removeAll(); // Clear the screen
 		showCursor();
 
 		GLabel gameOverLabel = new GLabel("GAME OVER", PROGRAM_WIDTH / 2 - 100, PROGRAM_HEIGHT / 2);
 		gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
 		gameOverLabel.setColor(Color.RED);
 		add(gameOverLabel);
 
 		GLabel finalScoreLabel = new GLabel("Score: " + score, PROGRAM_WIDTH / 2 - 40, PROGRAM_HEIGHT / 2 + 50);
 		finalScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
 		add(finalScoreLabel);
 
 		GLabel survivedLabel = new GLabel("You survived: " + elapsedTime + " seconds", PROGRAM_WIDTH / 2 - 110,
 				PROGRAM_HEIGHT / 2 + 90);
 		survivedLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
 		survivedLabel.setColor(Color.BLUE);
 		add(survivedLabel);
 
 		// Retry Button
 		retryButton = new GRect(PROGRAM_WIDTH / 2 - 50, PROGRAM_HEIGHT / 2 + 130, 100, 40);
 		retryButton.setFilled(true);
 		retryButton.setFillColor(Color.LIGHT_GRAY);
 		add(retryButton);
 
 		retryLabel = new GLabel("Retry", PROGRAM_WIDTH / 2 - 25, PROGRAM_HEIGHT / 2 + 153);
 		retryLabel.setFont("SansSerif-bold-18");
 		add(retryLabel);
 	}
 
 	private void restartGame() {
 		if (movement != null) {
 			movement.stop();
 		}
 
 		removeAll();
 
 		// Reset game state
 		elapsedTime = 0;
 		score = 0;
 		msCounter = 0;
 		enemyTicksSinceLastShot = 0;
 		mainShipTicksSinceLastShot = 0;
 		bonusStartTime = System.currentTimeMillis();
 		mousePressed = false;
 		gameOverFlag = false;
 		levelEnded = false;
 		waveNumber = 1;
 
 		enemyBullets.clear();
 		userBullets.clear();
 		//enemyVisuals.clear();
 
 		// Restart run logic
 		run();
 
 	}
 	
 	 /*private void updateScore(int points) {
         score += points; // Update the local score
         updateScoreLabel(); // Update the score label on the screen

         if (gameData != null) {
             gameData.addScore(points); // Update the persistent score in GameData
         }
     }*/
 
 	private void updateScoreLabel() {
 		scoreLabel.setLabel("Score: " + score);
 	}
 
 	private void updateBonusPointsLabel() {
 		bonusTimerLabel.setLabel("Bonus Points: " + bonusPoints); // Assuming you use the existing label for bonus
 																	// points
 	}
 
 	private void hideCursor() {
 		Toolkit toolkit = Toolkit.getDefaultToolkit();
 		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
 		Cursor blankCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
 		getGCanvas().setCursor(blankCursor);
 	}
 
 	private void showCursor() {
 		getGCanvas().setCursor(Cursor.getDefaultCursor());
 	}
 
 	public static void main(String[] args) {
 		//new TestingLevel4().start();
 		
 		GameData gameData = GameData.getInstance();
		
		TestingLevel4 level4 = new TestingLevel4();
		
		level4.setGameData(gameData);
		
		level4.start();
		
 	}
 }