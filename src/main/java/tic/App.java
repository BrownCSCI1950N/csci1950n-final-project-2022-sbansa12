package tic;

import engine.Application;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
  protected void onKeyTyped(KeyEvent e) {
    // KeyEvent [source = javafx.scene.Scene@7b8ee390, target = javafx.scene.Scene@7b8ee390, eventType = KEY_TYPED, consumed = false, character = , text = , code = UNDEFINED]
    System.out.println(e);
    if (e.getCode() == KeyCode.ESCAPE){
      System.out.println("ESCAPED!");
    } else {
      System.out.println("TRAPPED!");
    }

    super.onKeyTyped(e);
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
