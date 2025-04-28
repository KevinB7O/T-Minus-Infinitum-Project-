import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainApplication extends GraphicsProgram {
    // Settings
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 600;

    // Panes for the main menu system
    private WelcomePane welcomePane;
    private DescriptionPane descriptionPane;
    private GraphicsPane currentScreen;
    
    private GameData gameData;
    
    public GameData getGameData() {
        return gameData;
    }
    
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public MainApplication() {
        super();
    }

    protected void setupInteractions() {
        requestFocus();
        addKeyListeners();
        addMouseListeners();
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameData = new GameData();
    }

    public void run() {
        System.out.println("Let's Go");
        setupInteractions();

        // Initialize all Panes
        welcomePane = new WelcomePane(this);
        descriptionPane = new DescriptionPane(this);

        // Default Pane
        switchToScreen(welcomePane);
    }
    
    public void showMainMenu() {
        // Reset ONLY when starting a new game
    	gameData.reset();
        launchLevel1();
    }
    
    /*public void switchToLevel1() {
        TestingLevel1 lvl1 = new TestingLevel1();
        lvl1.setMainScreen(this); // Use a setter to pass MainApplication reference
        new Thread(() -> lvl1.start()).start(); // Launch as new window
    }*/
    
    /*public void switchToLevel2() {
        TestingLevel2 level2 = new TestingLevel2();
        level2.setMainScreen(this);
        level2.setGameData(gameData);
        new Thread(() -> level2.start()).start();
    }*/

    public static void main(String[] args) {
        new MainApplication().start();
    }

    public void switchToDescriptionScreen() {
        switchToScreen(descriptionPane);
    }

    public void switchToWelcomeScreen() {
        switchToScreen(welcomePane);
    }
    
    public void switchToEndGameSummary() {
        EndGameSummary endGameSummary = new EndGameSummary(this);
        switchToScreen(endGameSummary);
    }

    // This launches TestingLevel1 as a new window
    public void launchLevel1() {
        TestingLevel1 level1 = new TestingLevel1();
        level1.setMainScreen(this);
        if (gameData != null) {
            level1.setGameData(gameData);
        }
        new Thread(() -> level1.start()).start();
    }
    // This launches TestingLevel2 as a new window
    public void launchLevel2() {
        TestingLevel2 level2 = new TestingLevel2();
        level2.setMainScreen(this);
        if (gameData != null) {
            level2.setGameData(gameData);
        }
        new Thread(() -> level2.start()).start();
    }
    // This launches TestingLevel3 as a new window
    public void launchLevel3() {
        TestingLevel2 level3 = new TestingLevel2();
        level3.setMainScreen(this);
        if (gameData != null) {
            level3.setGameData(gameData);
        }
        new Thread(() -> level3.start()).start();
    }
    // This launches TestingLevel4 as a new window
    public void launchLevel4() {
        TestingLevel2 level4 = new TestingLevel2();
        level4.setMainScreen(this);
        if (gameData != null) {
            level4.setGameData(gameData);
        }
        new Thread(() -> level4.start()).start();
    }
    // This launches TestingLevel5 as a new window
    public void launchLevel5() {
        TestingLevel2 level5 = new TestingLevel2();
        level5.setMainScreen(this);
        if (gameData != null) {
            level5.setGameData(gameData);
        }
        new Thread(() -> level5.start()).start();
    }
    
    

    protected void switchToScreen(GraphicsPane newScreen) {
        if (currentScreen != null) {
            currentScreen.hideContent();
        }
        newScreen.showContent();
        currentScreen = newScreen;
    }

    public GObject getElementAtLocation(double x, double y) {
        return getElementAt(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentScreen != null) {
            currentScreen.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentScreen != null) {
            currentScreen.mouseReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentScreen != null) {
            currentScreen.mouseClicked(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentScreen != null) {
            currentScreen.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentScreen != null) {
            currentScreen.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentScreen != null) {
            currentScreen.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentScreen != null) {
            currentScreen.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (currentScreen != null) {
            currentScreen.keyTyped(e);
        }
    }
}
