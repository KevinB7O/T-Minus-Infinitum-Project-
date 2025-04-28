import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class TestingLevel5 extends GraphicsProgram implements ActionListener {
	private ArrayList<GOval> enemyBullets;
	private ArrayList<GOval> userBullets;
	private Timer movement;
	private RandomGenerator rgen;

	public static final int PROGRAM_WIDTH = 900;
	public static final int PROGRAM_HEIGHT = 600;
	public static final int SIZE = 25;
	public static final int MS = 25;
	public static final int ENEMY_PROJ_SPEED = 23;
	public static final int ENEMY_PROJ_SIZE = 10;
	private final int USER_PROJ_SPEED = 7;
	private final int USER_PROJ_SIZE = 8;
	private static final int ENEMY_MOVE_SPEED = 23;

	private int enemyShootCooldown = 50;
	private int enemyTicksSinceLastShot = 0;

	private int mainShipShootCooldown = 13; // lower = faster shooting
	private int mainShipTicksSinceLastShot = 0;

	private int elapsedTime = 0; // time in seconds
	private int score = 0;
	private GLabel timerLabel;
	private GLabel scoreLabel;
	private int msCounter = 0;

	
	private boolean mousePressed = false;
	private boolean gameOverFlag = false;

	private ArrayList<GPolygon> enemyVisuals;
	private GPolygon visualMainShip;
	private GRect retryButton;
	private GLabel retryLabel;
	
    private boolean levelEnded = false;
    private GameData gameData;
	private int waveNumber = 1;

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		addMouseListeners();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		getGCanvas().setCursor(blankCursor);
	}

	public void run() {
		rgen = RandomGenerator.getInstance();
		enemyBullets = new ArrayList<>();
		enemyVisuals = new ArrayList<>();
		userBullets = new ArrayList<>();
		
		int carriedOverScore = 0;
		if (gameData != null) {
		        carriedOverScore = gameData.getTotalScore();
		    }
		
		// Initialize score from GameData
	    if (gameData != null) {
	        score = gameData.getTotalScore();
	    }

		UserSpaceship mainship = new UserSpaceship(SpaceshipType.userSpaceship, 14, 12);
		visualMainShip = mainship.getVisualMainShip();
		add(visualMainShip);

		spawnWave(1);

		movement = new Timer(MS, this);
		movement.start();

		// Added a timer
		timerLabel = new GLabel("Time: 0s", PROGRAM_WIDTH - 900, 20);
		timerLabel.setFont("SansSerif-bold-16");
		add(timerLabel);

	

		// Added a point system
		scoreLabel = new GLabel("Score: " + carriedOverScore, 810, 20);
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		scoreLabel.setColor(Color.BLACK);
		add(scoreLabel);
		hideCursor();

	}
	
	public void setGameData (GameData data) {
		this.gameData = data;
	}

	public void userSpaceshipMovement(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
		visualMainShip.setLocation(mouseX, mouseY);
	}

	public void projectileCollisionDetection() {
		for (GOval bullet : enemyBullets) {
			if (bullet.getBounds().intersects(visualMainShip.getBounds())) {
				System.out.println("Collision Detected!");
				enemyBullets.remove(bullet);
				remove(bullet);
				gameOver();
				break;
			}
		}
	}

	public void enemyCollisionDetection() {
		for (GPolygon enemyVisual : enemyVisuals) {
			if (enemyVisual.getBounds().intersects(visualMainShip.getBounds())) {
				System.out.println("Enemy Collision Detected!");
				remove(enemyVisual);
				enemyVisuals.remove(enemyVisual);
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
		// Retry button click
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
		userSpaceshipMovement(e);
		projectileCollisionDetection();
		enemyCollisionDetection();
		if (levelEnded) return;// Stop the ship from moving
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		userSpaceshipMovement(e);
		projectileCollisionDetection();
		enemyCollisionDetection();
	}

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
 	    for (GPolygon enemy : enemyVisuals) {
 	        enemyTicksSinceLastShot++;
 	        if (enemyTicksSinceLastShot >= enemyShootCooldown) {
 	            if (rgen.nextBoolean(0.1)) { // 10% chance to shoot
 	                shootFromEnemy(enemy.getX() + SIZE / 2, enemy.getY() + SIZE);
 	                enemyTicksSinceLastShot = 0;
 	            }
 	        }
 	    }

 	    // Enemy movement with collision and boundary detection
 	    for (GPolygon enemy : new ArrayList<>(enemyVisuals)) {
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
 	    }

 	    // Timer update for elapsed time
 	    msCounter += MS;
 	    if (msCounter >= 1000) { // 1000 milliseconds = 1 second
 	        elapsedTime++;
 	        timerLabel.setLabel("Time: " + elapsedTime + "s");
 	        msCounter = 0;
 	    }


 	    if (levelEnded) return;

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
		double shipX = visualMainShip.getX() + SIZE / 2;
		double shipY = visualMainShip.getY();

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

	private void moveUserBullets() {
		ArrayList<GOval> bulletsToRemove = new ArrayList<>();
		ArrayList<GPolygon> enemiesToRemove = new ArrayList<>();

		for (GOval bullet : userBullets) {
			bullet.move(0, -USER_PROJ_SPEED);

			if (bullet.getY() < 0) {
				bulletsToRemove.add(bullet);
				continue;
			}

			for (GPolygon enemy : enemyVisuals) {
				if (bullet.getBounds().intersects(enemy.getBounds())) {
					bulletsToRemove.add(bullet);
					enemiesToRemove.add(enemy);
					score += 100; // +100 points per enemy
					updateScoreLabel();
					break;
				}
			}
		}

		for (GOval bullet : bulletsToRemove) {
			remove(bullet);
			userBullets.remove(bullet);
		}

		for (GPolygon enemy : enemiesToRemove) {
			remove(enemy);
			enemyVisuals.remove(enemy);
		}

		if (enemyVisuals.isEmpty()) {
			if (waveNumber == 1) {
 				waveNumber = 2;
 				spawnWave(2);
 			} else {
 				// Level completed
 				
 				movement.stop();
 				showEndLevelSummary();
 			}
		}
	}
	
	private void showEndLevelSummary() {
		levelEnded = true;
		gameOverFlag = true; 
		
		// Update gameData with this level's results
	    if (gameData != null) {
	        gameData.addScore(score);
	        
	       
	        
	    }
	    
	   // EndLevelSummary summary = new EndLevelSummary(score, elapsedTime, this::nextLevel);
	    removeAll();
	    showCursor();
	    
	   // add(summary, (PROGRAM_WIDTH - summary.getWidth()) / 2, (PROGRAM_HEIGHT - summary.getHeight()) / 2);

	}
 	
 	private void nextLevel() {
	    // Logic to transition to the next level
	    System.out.println("Moving to next level...");
	    TestingLevel5 next = new TestingLevel5();
	    next.start(); // or next.startApplication() if needed
	}
		
		private void spawnWave(int wave) {
	 	    enemyVisuals.clear();
	 
	 	    if (wave == 1) {
	 	        Enemyship4[] wave1Enemies = {
	 	            new Enemyship4(SpaceshipType.eType4, 5, 7),
	 	            new Enemyship4(SpaceshipType.eType4, 5, 11),
	 	            new Enemyship4(SpaceshipType.eType4, 5, 15),
	 	            new Enemyship4(SpaceshipType.eType4, 1, 5),
	 	            new Enemyship4(SpaceshipType.eType4, 1, 9),
	 	            new Enemyship4(SpaceshipType.eType4, 1, 13),
	 	            new Enemyship4(SpaceshipType.eType4, 1, 17)
	 	        };
	 
	 	        for (Enemyship4 enemy : wave1Enemies) {
	 	            GPolygon enemyVisual = enemy.getVisual();
	 	            enemyVisuals.add(enemyVisual);
	 	            add(enemyVisual);
	 	        }
	 	    } else if (wave == 2) {
	 	        Enemyship5[] wave2Enemies = {
	 	            new Enemyship5(SpaceshipType.eType4, 5, 7),
	 	            new Enemyship5(SpaceshipType.eType4, 5, 11),
	 	            new Enemyship5(SpaceshipType.eType4, 5, 15),
	 	            new Enemyship5(SpaceshipType.eType4, 1, 5),
	 	            new Enemyship5(SpaceshipType.eType4, 1, 9),
	 	            new Enemyship5(SpaceshipType.eType4, 1, 13),
	 	            new Enemyship5(SpaceshipType.eType4, 1, 17)
	 	        };
	 
	 	        for (Enemyship5 enemy : wave2Enemies) {
	 	            GPolygon enemyVisual = enemy.getVisual();
	 	            enemyVisuals.add(enemyVisual);
	 	            add(enemyVisual);
	 	        }
	 	    }
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
		mousePressed = false;
		gameOverFlag = false;
		waveNumber = 1;

		enemyBullets.clear();
		userBullets.clear();
		enemyVisuals.clear();

		// Restart run logic
		run();

	}

	private void updateScoreLabel() {
		scoreLabel.setLabel("Score: " + score);
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
		//new TestingLevel5().start();
		GameData gameData = GameData.getInstance();
		
		TestingLevel5 level5 = new TestingLevel5();
		
		level5.setGameData(gameData);
		
		level5.start();
	}
}
