package alc;

import alc.Screens.*;
import engine.Application;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        // Add All Screens to Application
        try {
            this.addScreen("start", new StartScreen(this));
            this.addScreen("instructions", new InstructionScreen(this));
            this.addScreen("game", new GameScreen(this));

            // Set Active Screen
            this.setActiveScreen("start");

            super.onStartup();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}