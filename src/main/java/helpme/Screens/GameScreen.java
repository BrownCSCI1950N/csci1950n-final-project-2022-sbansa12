package helpme.Screens;

import engine.Application;
import engine.Screen;
import engine.TerrainGeneration.LevelParseException;
import engine.UI.*;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsGameScreen;
import helpme.Constants.ConstantsGameValues;
import helpme.Constants.ConstantsSaveFileSelectionScreen;
import helpme.HelpMeGame;
import helpme.HelpMeGameLevel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class GameScreen extends Screen {
    HelpMeGameLevel helLevel;
    HelpMeGame hel;
    Boolean showPopupGameFileCorruption;
    public GameScreen(Application engine, HelpMeGameLevel helLevel, HelpMeGame hel) {
        super(engine);

        this.helLevel = helLevel;
        this.hel = hel;
        showPopupGameFileCorruption = false;

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsGameScreen.backgroundColor);
        uiElements.add(background);

        // Viewport
        Viewport viewport = new Viewport(
                this,
                null,
                ConstantsGameScreen.startingViewportGameCoordinates,
                ConstantsGameScreen.viewportScreenPosition,
                ConstantsGameScreen.viewportScreenSize,
                null,
                ConstantsGameScreen.scale,
                null,
                null,
                null,
                null
        );
        background.addChildren(viewport);

        // Level Complete Pop Up
        createPopUpLevelComplete(viewport);

        // Map Corrupted Pop Up
        createPopUpGameFileCorruption(viewport);

        hel.setViewport(viewport);
    }

    @Override
    protected void onKeyPressed(KeyEvent e) {

        if (hel.isLevelComplete()) {
            return;
        }

        if (hel.isNeedRestart()) {
            return;
        }

        // Level Not Complete Yet

        if (e.getCode() == ConstantsGameValues.clickButtonLevelQuit) {
            setActiveScreen("select");
        }

        if (e.getCode() == ConstantsGameValues.clickButtonLevelReset) {
            try {
                helLevel.resetCurrentLevel();
            } catch (IOException | LevelParseException ex) {
                System.out.println(ex.getMessage());
                showPopupGameFileCorruption = true;
                return;
            }
        }

        super.onKeyPressed(e);
    }

    private void createPopUpNeedRestart(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsGameScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isNeedRestart()) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsGameScreen.popUpPanelPosition,
                ConstantsGameScreen.popUpPanelSize,
                ConstantsGameScreen.popUpPanelColor,
                ConstantsGameScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isNeedRestart()) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsGameScreen.popUpTextPosition,
                ConstantsGameScreen.popUpTextRestartLevel,
                ConstantsGameScreen.popUpTextColor,
                ConstantsGameScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isNeedRestart()) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                ConstantsGameScreen.popUpButtonPosition,
                ConstantsGameScreen.popUpButtonSize,
                ConstantsGameScreen.buttonColor,
                ConstantsGameScreen.buttonArcSize,
                ConstantsGameScreen.popUpButtonText,
                ConstantsGameScreen.popUpButtonTextPosition,
                ConstantsGameScreen.buttonTextColor,
                ConstantsGameScreen.buttonTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isNeedRestart()) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    try {
                        helLevel.resetCurrentLevel();
                    } catch (IOException | LevelParseException ex) {
                        System.out.println(ex.getMessage());
                        showPopupGameFileCorruption = true;
                        return;
                    }
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsGameScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsGameScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }

    private void createPopUpLevelComplete(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsGameScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isLevelComplete()) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsGameScreen.popUpPanelPosition,
                ConstantsGameScreen.popUpPanelSize,
                ConstantsGameScreen.popUpPanelColor,
                ConstantsGameScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isLevelComplete()) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsGameScreen.popUpTextPosition,
                ConstantsGameScreen.popUpTextLevelComplete,
                ConstantsGameScreen.popUpTextColor,
                ConstantsGameScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isLevelComplete()) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                ConstantsGameScreen.popUpButtonPosition,
                ConstantsGameScreen.popUpButtonSize,
                ConstantsGameScreen.buttonColor,
                ConstantsGameScreen.buttonArcSize,
                ConstantsGameScreen.popUpButtonText,
                ConstantsGameScreen.popUpButtonTextPosition,
                ConstantsGameScreen.buttonTextColor,
                ConstantsGameScreen.buttonTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (hel.isLevelComplete()) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("select");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsGameScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsGameScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }

    private void createPopUpGameFileCorruption(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsSaveFileSelectionScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupGameFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsSaveFileSelectionScreen.popUpPanelPosition,
                ConstantsSaveFileSelectionScreen.popUpPanelSize,
                ConstantsSaveFileSelectionScreen.popUpPanelColor,
                ConstantsSaveFileSelectionScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupGameFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsSaveFileSelectionScreen.popUpTextPosition,
                ConstantsSaveFileSelectionScreen.popUpTextGameFileCorruption,
                ConstantsSaveFileSelectionScreen.popUpTextColor,
                ConstantsSaveFileSelectionScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupGameFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);
    }
}
