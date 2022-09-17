package tic;

import engine.Application;
import engine.support.Vec2d;
import tic.screens.GameScreen;
import tic.screens.StartScreen;
import tic.screens.WinScreen;


/**
 * This is your Tic-Tac-Toe top-level, App class.
 * This class will contain every other object in your game.
 */
public class App extends Application {

  public App(String title) {
    super(title);
  }

  public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
  }

  @Override
  protected void onStartup() {
    // Add All Screens to Application
    this.addScreen("start", new StartScreen(this));
    this.addScreen("game", new GameScreen(this));
    this.addScreen("x-win", new WinScreen(this, "x wins!", "restart"));
    this.addScreen("o-win", new WinScreen(this, "o wins!", "restart"));
    this.addScreen("draw", new WinScreen(this, " draw  ", "restart"));

    // Set Active Screen
    this.setActiveScreen("start");

    super.onStartup();
  }
}
