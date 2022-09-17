package tic.screens;

import engine.Application;
import engine.FontWrapper;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import tic.Constants;

public class StartScreen extends Screen {
    public StartScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                Constants.startScreenTitlePosition,
                Constants.startScreenTitle,
                Constants.titleColor,
                Constants.titleFont);
        background.addChildren(title);

        // Create Start Button (Changes Screen to Game)
        UIElement startButton = new UIButton(
                this,
                background,
                Constants.buttonPosition,
                Constants.buttonSize,
                Constants.buttonColor,
                Constants.buttonArcSize,
                Constants.startScreenButtonText,
                Constants.buttonTextPosition,
                Constants.buttonTextColor,
                Constants.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("game");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.buttonHoverColor;
                } else {
                    this.color = Constants.buttonColor;
                }


                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
