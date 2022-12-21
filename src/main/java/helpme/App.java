package helpme;

import engine.Application;
import engine.support.Vec2d;
import helpme.Screens.*;
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
        this.addScreen("start", new StartScreen(this));
        this.addScreen("instructions", new InstructionsScreen(this));
        this.addScreen("saveFileSelection", new SaveFileSelectionScreen(this));
        this.addScreen("win", new WinScreen(this));

        // Set Active Screen
        this.setActiveScreen("start");

        super.onStartup();
    }
}
