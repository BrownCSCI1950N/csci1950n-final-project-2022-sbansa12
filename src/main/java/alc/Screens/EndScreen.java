package alc.Screens;

import alc.Constants;
import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;

public class EndScreen extends Screen {
    public EndScreen(Application engine) {
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
                Constants.endScreenTitle,
                Constants.titleColor,
                Constants.titleFont);
        background.addChildren(title);

        // Create Restart Button (Changes Screen to Game)
        UIElement restartButton = new UIButton(
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
        background.addChildren(restartButton);
    }
}
