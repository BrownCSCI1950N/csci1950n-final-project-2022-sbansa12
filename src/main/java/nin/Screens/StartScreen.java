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
import nin.Constants.ConstantsStartScreen;

public class StartScreen extends Screen {

    public StartScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsStartScreen.startScreenBackgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsStartScreen.startScreenTitlePosition,
                ConstantsStartScreen.startScreenTitle,
                ConstantsStartScreen.startScreenTitleColor,
                ConstantsStartScreen.startScreenTitleFont);
        background.addChildren(title);

        // Create Start Button (Changes Screen to Choose Seed)
        UIElement startButton = new UIButton(
                this,
                background,
                ConstantsStartScreen.startScreenButtonPosition,
                ConstantsStartScreen.startScreenButtonSize,
                ConstantsStartScreen.startScreenButtonColor,
                ConstantsStartScreen.startScreenButtonArcSize,
                ConstantsStartScreen.startScreenButtonText,
                ConstantsStartScreen.startScreenButtonTextPosition,
                ConstantsStartScreen.startScreenButtonTextColor,
                ConstantsStartScreen.startScreenButtonTextFont) {
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
                    this.color = ConstantsStartScreen.startScreenButtonHoverColor;
                } else {
                    this.color = ConstantsStartScreen.startScreenButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
