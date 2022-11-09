package nin.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.input.MouseEvent;
import nin.Constants.ConstantsInstructionsScreen;

public class InstructionScreen extends Screen {
    public InstructionScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsInstructionsScreen.instructionsScreenBackgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsInstructionsScreen.instructionsScreenTitlePosition,
                ConstantsInstructionsScreen.instructionsScreenTitle,
                ConstantsInstructionsScreen.instructionsScreenTitleColor,
                ConstantsInstructionsScreen.instructionsScreenTitleFont);
        background.addChildren(title);

        // Create Instructions
        UIElement instructions = new UIText(
                this,
                background,
                ConstantsInstructionsScreen.instructionsScreenInstructionsPosition,
                ConstantsInstructionsScreen.instructionsScreenInstructions,
                ConstantsInstructionsScreen.instructionsScreenTitleColor,
                ConstantsInstructionsScreen.instructionsScreenInstructionsFont);
        background.addChildren(instructions);

        // Create Next Button (Changes Screen to Level Choose)
        UIElement startButton = new UIButton(
                this,
                background,
                ConstantsInstructionsScreen.instructionsScreenButtonPosition,
                ConstantsInstructionsScreen.instructionsScreenButtonSize,
                ConstantsInstructionsScreen.instructionsScreenButtonColor,
                ConstantsInstructionsScreen.instructionsScreenButtonArcSize,
                ConstantsInstructionsScreen.instructionsScreenButtonText,
                ConstantsInstructionsScreen.instructionsScreenButtonTextPosition,
                ConstantsInstructionsScreen.instructionsScreenButtonTextColor,
                ConstantsInstructionsScreen.instructionsScreenButtonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("saveLoad");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsInstructionsScreen.instructionsScreenButtonHoverColor;
                } else {
                    this.color = ConstantsInstructionsScreen.instructionsScreenButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
