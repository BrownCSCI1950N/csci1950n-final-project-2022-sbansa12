package hel;

import engine.Application;
import engine.support.Vec2d;
import hel.Screens.LevelSelectScreen;
import hel.Screens.SaveFileSelectionScreen;
import hel.Screens.SettingsScreen;
import hel.Screens.StartScreen;
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

//        NinGame nin = new NinGame();
//        NinGameLevel ninGameLevel = new NinGameLevel(nin);

        // Add All Screens to Application
        this.addScreen("start", new StartScreen(this));
        this.addScreen("saveFileSelection", new SaveFileSelectionScreen(this));
        this.addScreen("settings", new SettingsScreen(this));
//        SelectionScreen ss = new SelectionScreen(this, ninGameLevel);
//        this.addScreen("select", ss);
//        this.addScreen("game", new GameScreen(this, ss, ninGameLevel, nin));
//        this.addScreen("win", new WinScreen(this));

        // Set Active Screen
        this.setActiveScreen("start");

        super.onStartup();
    }
}
