package helpme.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsStartScreen;
import javafx.scene.input.MouseEvent;

public class StartScreen extends Screen {
    public StartScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsStartScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsStartScreen.titlePosition,
                ConstantsStartScreen.title,
                ConstantsStartScreen.titleColor,
                ConstantsStartScreen.titleFont);
        background.addChildren(title);

        // Create Start Button
        UIElement startButton = new UIButton(
                this,
                background,
                ConstantsStartScreen.startButtonPosition,
                ConstantsStartScreen.buttonSize,
                ConstantsStartScreen.buttonColor,
                ConstantsStartScreen.buttonArcSize,
                ConstantsStartScreen.startButtonText,
                ConstantsStartScreen.startButtonTextPosition,
                ConstantsStartScreen.buttonTextColor,
                ConstantsStartScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("instructions");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsStartScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsStartScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
