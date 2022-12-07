package engine;

import engine.support.FXFrontEnd;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * This is your main Application class that you will contain your
 * 'draws' and 'ticks'. This class is also used for controlling
 * user input.
 */
public class Application extends FXFrontEnd {

  private final Map<String, Screen> screens;
  private Screen activeScreen;

  protected Vec2d originalSize;

  public Application(String title) {
    super(title);
    this.screens = new HashMap<>();
    this.activeScreen = null;
  }
  public Application(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
    this.screens = new HashMap<>();
    this.activeScreen = null;
  }

  public void addScreen(String name, Screen screen) {
    screens.put(name, screen);
  }
  public void setActiveScreen(String name) {
    activeScreen = screens.get(name);
    activeScreen.reset();
  }
  public Vec2d getCurrentStageSize() {
    return this.currentStageSize;
  }

  /**
   * Called periodically and used to update the state of your game.
   * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
   */
  @Override
  protected void onTick(long nanosSincePreviousTick) {
    activeScreen.onTick(nanosSincePreviousTick);
  }

  /**
   * Called after onTick().
   */
  @Override
  protected void onLateTick() {
    activeScreen.onLateTick();
  }

  /**
   *  Called periodically and meant to draw graphical components.
   * @param g		a {@link GraphicsContext} object used for drawing.
   */
  @Override
  protected void onDraw(GraphicsContext g) {
    activeScreen.onDraw(g);
  }

  /**
   * Called when a key is typed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyTyped(KeyEvent e) {
    activeScreen.onKeyTyped(e);
  }

  /**
   * Called when a key is pressed.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyPressed(KeyEvent e) {
    activeScreen.onKeyPressed(e);
  }

  /**
   * Called when a key is released.
   * @param e		an FX {@link KeyEvent} representing the input event.
   */
  @Override
  protected void onKeyReleased(KeyEvent e) {
    activeScreen.onKeyReleased(e);
  }

  /**
   * Called when the mouse is clicked.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseClicked(MouseEvent e) {
    activeScreen.onMouseClicked(e);
  }

  /**
   * Called when the mouse is pressed.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMousePressed(MouseEvent e) {
    activeScreen.onMousePressed(e);
  }

  /**
   * Called when the mouse is released.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseReleased(MouseEvent e) {
    activeScreen.onMouseReleased(e);
  }

  /**
   * Called when the mouse is dragged.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseDragged(MouseEvent e) {
    activeScreen.onMouseDragged(e);
  }

  /**
   * Called when the mouse is moved.
   * @param e		an FX {@link MouseEvent} representing the input event.
   */
  @Override
  protected void onMouseMoved(MouseEvent e) {
    activeScreen.onMouseMoved(e);
  }

  /**
   * Called when the mouse wheel is moved.
   * @param e		an FX {@link ScrollEvent} representing the input event.
   */
  @Override
  protected void onMouseWheelMoved(ScrollEvent e) {
    activeScreen.onMouseWheelMoved(e);
  }

  /**
   * Called when the window's focus is changed.
   * @param newVal	a boolean representing the new focus state
   */
  @Override
  protected void onFocusChanged(boolean newVal) {
    activeScreen.onFocusChanged(newVal);
  }

  /**
   * Called when the window is resized.
   * @param newSize	the new size of the drawing area.
   */
  @Override
  protected void onResize(Vec2d newSize) {
    // Change Size from Absolute to Scale
    currentStageSize = newSize;
    double scale = Math.min(newSize.x/ originalSize.x, newSize.y/ originalSize.y);
    for (Screen s: screens.values()) {
      s.onResize(new Vec2d(scale, scale));
    }
  }

  /**
   * Called when the app is shutdown.
   */
  @Override
  protected void onShutdown() {
    activeScreen.onShutdown();
  }

  /**
   * Called when the app is starting up.s
   */
  @Override
  protected void onStartup() {
    // Set Original Size (Used for Resizing)
    this.originalSize = currentStageSize;
  }
}
