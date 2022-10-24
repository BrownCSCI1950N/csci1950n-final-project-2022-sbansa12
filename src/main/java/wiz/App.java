package wiz;

import engine.Application;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import wiz.Screens.*;

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

        WizGame wizGame = new WizGame();

        // Add All Screens to Application
        this.addScreen("start", new StartScreen(this));
        this.addScreen("instructions", new InstructionScreen(this));
        this.addScreen("seed", new WorldGenerationScreen(this, wizGame));
        this.addScreen("game", new GameScreen(this, wizGame));
        this.addScreen("win", new WinScreen(this, wizGame));
        this.addScreen("tryAgain", new TryAgainScreen(this, wizGame));

        // Set Active Screen
        this.setActiveScreen("instructions");

        super.onStartup();
    }
}
