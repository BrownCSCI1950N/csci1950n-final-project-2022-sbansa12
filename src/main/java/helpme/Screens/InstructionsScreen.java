package helpme.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import helpme.Constants.ConstantsInstructionsScreen;

public class InstructionsScreen extends Screen {
    public InstructionsScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsInstructionsScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsInstructionsScreen.titlePosition,
                ConstantsInstructionsScreen.title,
                ConstantsInstructionsScreen.titleColor,
                ConstantsInstructionsScreen.titleFont);
        background.addChildren(title);

        // Create Instructions
        UIElement instructions = new UIText(
                this,
                background,
                ConstantsInstructionsScreen.instructionsPosition,
                ConstantsInstructionsScreen.instructions,
                ConstantsInstructionsScreen.instructionsColor,
                ConstantsInstructionsScreen.instructionsFont);
        background.addChildren(instructions);

        // Create Next Button (Changes Screen to Level Choose)
        UIElement startButton = new UIButton(
                this,
                background,
                ConstantsInstructionsScreen.buttonPosition,
                ConstantsInstructionsScreen.buttonSize,
                ConstantsInstructionsScreen.buttonColor,
                ConstantsInstructionsScreen.buttonArcSize,
                ConstantsInstructionsScreen.buttonText,
                ConstantsInstructionsScreen.buttonTextPosition,
                ConstantsInstructionsScreen.buttonTextColor,
                ConstantsInstructionsScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("saveFileSelection");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsInstructionsScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsInstructionsScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
