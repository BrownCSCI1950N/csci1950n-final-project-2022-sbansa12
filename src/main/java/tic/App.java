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
    this.addScreen("X-win", new WinScreen(this, "X Wins!", "Restart"));
    this.addScreen("O-win", new WinScreen(this, "O Wins!", "Restart"));
    this.addScreen("draw", new WinScreen(this, "Draw~!!", "Restart"));

    // Set Active Screen
    this.setActiveScreen("start");

    super.onStartup();
  }
}
