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
import nin.Constants.ConstantsSelectionScreen;
import nin.Constants.ConstantsWinScreen;

public class WinScreen extends Screen {
    public WinScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsWinScreen.winScreenBackgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsWinScreen.winScreenTitlePosition,
                ConstantsWinScreen.winScreenTitle,
                ConstantsWinScreen.winScreenTitleColor,
                ConstantsWinScreen.winScreenTitleFont);
        background.addChildren(title);

        // Create Credits
        UIElement instructions = new UIText(
                this,
                background,
                ConstantsWinScreen.winScreenCreditsPosition,
                ConstantsWinScreen.winScreenCredits,
                ConstantsWinScreen.winScreenTitleColor,
                ConstantsWinScreen.winScreenCreditsFont);
        background.addChildren(instructions);

        // Create Restart Button (Changes Screen to Start Screen)
        UIElement startButton = new UIButton(
                this,
                background,
                ConstantsWinScreen.winScreenButtonPosition,
                ConstantsWinScreen.winScreenButtonSize,
                ConstantsWinScreen.winScreenButtonColor,
                ConstantsWinScreen.winScreenButtonArcSize,
                ConstantsWinScreen.winScreenButtonText,
                ConstantsWinScreen.winScreenButtonTextPosition,
                ConstantsWinScreen.winScreenButtonTextColor,
                ConstantsWinScreen.winScreenButtonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {

                    // Reset Game by Resetting Levels
                    ConstantsSelectionScreen.levelComplete.replaceAll((k, v) -> false);
                    
                    setActiveScreen("start");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsWinScreen.winScreenButtonHoverColor;
                } else {
                    this.color = ConstantsWinScreen.winScreenButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
