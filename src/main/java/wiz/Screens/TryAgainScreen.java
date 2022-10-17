package wiz.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import wiz.Constants;
import wiz.WizGame;

public class TryAgainScreen extends Screen {
    public TryAgainScreen(Application engine, WizGame wizGame) {
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
                Constants.endScreenTitlePosition,
                Constants.endScreenTitleTryAgain,
                Constants.titleColor,
                Constants.titleFont);
        background.addChildren(title);

        // Number of Deaths Shown
        UIElement deaths = new UIText(
                this,
                background,
                Constants.endScreenDeathsPosition,
                "Deaths: " + wizGame.getDeathCount(),
                Constants.deathsColor,
                Constants.deathsFont){
            @Override
            public void onDraw(GraphicsContext g) {
                this.text = "Deaths: " + wizGame.getDeathCount();

                super.onDraw(g);
            }
        };
        background.addChildren(deaths);

        // Create Restart Button (Changes Screen to Start Screen)
        UIElement startButton = new UIButton(
                this,
                background,
                Constants.buttonPosition,
                Constants.buttonSize,
                Constants.buttonColor,
                Constants.buttonArcSize,
                Constants.endScreenButtonText,
                Constants.buttonTextPosition,
                Constants.buttonTextColor,
                Constants.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("start");
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
