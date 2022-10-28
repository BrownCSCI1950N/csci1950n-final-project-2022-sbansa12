package wiz.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import wiz.Constants;

public class InstructionScreen extends Screen {
    public InstructionScreen(Application engine) {
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
                Constants.instructionsScreenTitlePosition,
                Constants.instructionsScreenTitle,
                Constants.titleColor,
                Constants.titleFont);
        background.addChildren(title);

        UIElement instructions = new UIText(
                this,
                background,
                Constants.instructionsScreenInstructionsPosition,
                Constants.instructionsScreenInstructions,
                Constants.titleColor,
                Constants.instructionsFont);
        background.addChildren(instructions);

        // Create Start Button (Changes Screen to Game)
        UIElement startButton = new UIButton(
                this,
                background,
                Constants.buttonPosition,
                Constants.buttonSize,
                Constants.buttonColor,
                Constants.buttonArcSize,
                Constants.instructionsScreenButtonText,
                Constants.buttonTextPosition,
                Constants.buttonTextColor,
                Constants.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("seed");
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
