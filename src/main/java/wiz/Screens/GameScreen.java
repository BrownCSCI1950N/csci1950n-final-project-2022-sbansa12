package wiz.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.*;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import wiz.Constants;
import wiz.WizGame;

public class GameScreen extends Screen {
    Viewport viewport;
    WizGame wizGame;
    public GameScreen(Application engine, WizGame wizGame) {
        super(engine);
        this.wizGame = wizGame;

        // Create Background
        UIElement backgroundGame = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColorGame);
        uiElements.add(backgroundGame);

        // Viewport
        this.viewport = new Viewport(
                this,
                null,
                Constants.startingViewportGameCoordinates,
                Constants.viewportScreenPosition,
                Constants.viewportScreenSize,
                null,
                Constants.scale,
                null,
                Constants.zoomingButtons,
                null,
                Constants.zoomSpeed
        );
        backgroundGame.addChildren(viewport);

        // Number of Deaths Shown
        UIElement deaths = new UIText(
                this,
                viewport,
                Constants.deathsPosition,
                "Deaths: " + wizGame.getDeathCount(),
                Constants.deathsColor,
                Constants.deathsFont){
            @Override
            public void onDraw(GraphicsContext g) {
                this.text = "Deaths: " + wizGame.getDeathCount();

                super.onDraw(g);
            }
        };
        viewport.addChildren(deaths);

        // Create Instructions Button (Changes Screen to Instructions)
        UIElement hintButton = new UIButton(
                this,
                viewport,
                Constants.hintButtonPosition,
                Constants.hintButtonSize,
                Constants.buttonColor,
                Constants.hintButtonArcSize,
                Constants.hintButtonText,
                Constants.hintButtonTextPosition,
                Constants.buttonTextColor,
                Constants.buttonTextFont) {

            @Override
            public void onDraw(GraphicsContext g) {
                if (!wizGame.getBossFight()) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (wizGame.getBossFight()) {return;}
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    wizGame.setPopUp(true);
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (wizGame.getBossFight()) {return;}
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.buttonHoverColor;
                } else {
                    this.color = Constants.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        viewport.addChildren(hintButton);

        createPopUp(viewport);

        // Set wizGame Up
        this.wizGame.setViewport(viewport);
        this.wizGame.setGameScreen(this);
    }

    @Override
    protected void reset() {
        // Start Wiz
        this.wizGame.startGame();

        super.reset();
    }

    private void createPopUp(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                Constants.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                Constants.popUpPanelPosition,
                Constants.popUpPanelSize,
                Constants.popUpPanelColor,
                Constants.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                Constants.popUpTextPosition,
                Constants.popUpText,
                Constants.popUpTextColor,
                Constants.popUpTextFont
                ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                Constants.popUpButtonPosition,
                Constants.popUpButtonSize,
                Constants.popUpButtonColor,
                Constants.popUpButtonArcSize,
                Constants.popUpButtonText,
                Constants.popUpButtonPositionText,
                Constants.popUpButtonColorText,
                Constants.popUpButtonFontText
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    wizGame.setPopUp(false);
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.popUpButtonHoverColor;
                } else {
                    this.color = Constants.popUpButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }
}