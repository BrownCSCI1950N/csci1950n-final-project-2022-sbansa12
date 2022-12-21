package helpme.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsWinScreen;
import javafx.scene.input.MouseEvent;

public class WinScreen extends Screen {
    public WinScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsWinScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsWinScreen.titlePosition,
                ConstantsWinScreen.title,
                ConstantsWinScreen.titleColor,
                ConstantsWinScreen.titleFont);
        background.addChildren(title);

        // Create Credits
        UIElement instructions = new UIText(
                this,
                background,
                ConstantsWinScreen.creditsPosition,
                ConstantsWinScreen.credits,
                ConstantsWinScreen.creditsColor,
                ConstantsWinScreen.creditsFont);
        background.addChildren(instructions);

        // Create Restart Button (Changes Screen to Start Screen)
        UIElement startButton = new UIButton(
                this,
                background,
                ConstantsWinScreen.buttonPosition,
                ConstantsWinScreen.buttonSize,
                ConstantsWinScreen.buttonColor,
                ConstantsWinScreen.buttonArcSize,
                ConstantsWinScreen.buttonText,
                ConstantsWinScreen.buttonTextPosition,
                ConstantsWinScreen.buttonTextColor,
                ConstantsWinScreen.buttonTextFont) {
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
                    this.color = ConstantsWinScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsWinScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
