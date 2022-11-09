package nin;

import engine.Application;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nin.Screens.*;

public class App extends Application {

    public App(String title) {
        super(title);
    }

    public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
    }

    @Override
    protected void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE){
            this.shutdown();
        }

        super.onKeyPressed(e);
    }

    @Override
    protected void onStartup() {

        NinGame nin = new NinGame();
        NinGameLevel ninGameLevel = new NinGameLevel(nin);

        // Add All Screens to Application
        this.addScreen("start", new StartScreen(this));
        this.addScreen("instructions", new InstructionScreen(this));
        this.addScreen("saveLoad", new SaveLoadScreen(this));
        this.addScreen("select", new SelectionScreen(this, ninGameLevel));
        this.addScreen("game", new GameScreen(this, ninGameLevel, nin));
        this.addScreen("win", new WinScreen(this));

        // Set Active Screen
        this.setActiveScreen("start");

        super.onStartup();
    }
}
