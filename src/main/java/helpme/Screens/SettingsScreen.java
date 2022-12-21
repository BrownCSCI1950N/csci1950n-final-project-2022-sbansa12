package helpme.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsSettingsScreen;
import javafx.scene.input.MouseEvent;

public class SettingsScreen extends Screen {
    public SettingsScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsSettingsScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsSettingsScreen.titlePosition,
                ConstantsSettingsScreen.title,
                ConstantsSettingsScreen.titleColor,
                ConstantsSettingsScreen.titleFont);
        background.addChildren(title);

        // Create Back Button
        UIElement backButton = new UIButton(
                this,
                background,
                ConstantsSettingsScreen.backButtonPosition,
                ConstantsSettingsScreen.backButtonSize,
                ConstantsSettingsScreen.buttonColor,
                ConstantsSettingsScreen.buttonArcSize,
                ConstantsSettingsScreen.backButtonText,
                ConstantsSettingsScreen.backButtonTextPosition,
                ConstantsSettingsScreen.buttonTextColor,
                ConstantsSettingsScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("select");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSettingsScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsSettingsScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(backButton);

        // Create Controls Button
        UIElement controlsButton = new UIButton(
                this,
                background,
                ConstantsSettingsScreen.controlsButtonPosition,
                ConstantsSettingsScreen.controlsButtonSize,
                ConstantsSettingsScreen.buttonColor,
                ConstantsSettingsScreen.buttonArcSize,
                ConstantsSettingsScreen.controlsButtonText,
                ConstantsSettingsScreen.controlsButtonTextPosition,
                ConstantsSettingsScreen.buttonTextColor,
                ConstantsSettingsScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("controls");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSettingsScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsSettingsScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(controlsButton);
    }
}
