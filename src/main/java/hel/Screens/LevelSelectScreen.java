package hel.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import hel.Constants.ConstantsGameValues;
import hel.Constants.ConstantsLevelSelectScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class LevelSelectScreen extends Screen {
    public LevelSelectScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsLevelSelectScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsLevelSelectScreen.titlePosition,
                ConstantsLevelSelectScreen.title,
                ConstantsLevelSelectScreen.titleColor,
                ConstantsLevelSelectScreen.titleFont);
        background.addChildren(title);

        // Back Button
        UIElement backButton = new UIButton(
                this,
                background,
                ConstantsLevelSelectScreen.backButtonPosition,
                ConstantsLevelSelectScreen.backButtonSize,
                ConstantsLevelSelectScreen.buttonColor,
                ConstantsLevelSelectScreen.buttonArcSize,
                ConstantsLevelSelectScreen.backButtonText,
                ConstantsLevelSelectScreen.backButtonTextPosition,
                ConstantsLevelSelectScreen.buttonTextColor,
                ConstantsLevelSelectScreen.buttonTextFont) {
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
                    this.color = ConstantsLevelSelectScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsLevelSelectScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(backButton);

        int row = 0;
        int col = 0;
        for (String level : ConstantsGameValues.levels) {
            double buttonPositionX = ConstantsLevelSelectScreen.buttonStartingPosition.x + (ConstantsLevelSelectScreen.levelButtonSize.x * col) + (ConstantsLevelSelectScreen.buttonPadding.x * col);
            double buttonPositionY = ConstantsLevelSelectScreen.buttonStartingPosition.y + (ConstantsLevelSelectScreen.levelButtonSize.y * row) + (ConstantsLevelSelectScreen.buttonPadding.y * row);
            background.addChildren(createLevelButton(background, new Vec2d(buttonPositionX, buttonPositionY), level));
        }

        // Settings Button
        UIElement settingsButton = new UIButton(
                this,
                background,
                ConstantsLevelSelectScreen.settingsButtonPosition,
                ConstantsLevelSelectScreen.settingsButtonSize,
                ConstantsLevelSelectScreen.buttonColor,
                ConstantsLevelSelectScreen.buttonArcSize,
                ConstantsLevelSelectScreen.settingsButtonText,
                ConstantsLevelSelectScreen.settingsButtonTextPosition,
                ConstantsLevelSelectScreen.buttonTextColor,
                ConstantsLevelSelectScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("settings");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsLevelSelectScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsLevelSelectScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(settingsButton);

        // Saved Popup
        saved = false;
        createPopUpSaved(background);
    }

    private UIElement createLevelButton(UIElement parent, Vec2d buttonPosition, String buttonText) {
        Color initialColor;
        System.out.println(buttonText);
        System.out.println(ConstantsGameValues.levelComplete);
        System.out.println(ConstantsGameValues.levelComplete.get(buttonText));
        if (ConstantsGameValues.levelComplete.get(buttonText)) {
            initialColor = ConstantsLevelSelectScreen.levelButtonCompleteColor;
        } else {
            initialColor = ConstantsLevelSelectScreen.buttonColor;
        }
        return new UIButton(
                this,
                parent,
                buttonPosition,
                ConstantsLevelSelectScreen.levelButtonSize,
                initialColor,
                ConstantsLevelSelectScreen.buttonArcSize,
                buttonText,
                ConstantsLevelSelectScreen.levelButtonTextPosition,
                ConstantsLevelSelectScreen.buttonTextColor,
                ConstantsLevelSelectScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    // TODO
//                    ninGameLevel.setCurrentLevel(this.buttonText);
                    setActiveScreen("game");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsLevelSelectScreen.buttonHoverColor;
                } else {
                    if (ConstantsGameValues.levelComplete.get(this.buttonText)) {
                        this.color = ConstantsLevelSelectScreen.levelButtonCompleteColor;
                    } else {
                        this.color = ConstantsLevelSelectScreen.buttonColor;
                    }
                }

                super.onMouseMoved(e);
            }

            @Override
            public void reset() {
                if (ConstantsGameValues.levelComplete.get(this.buttonText)) {
                    this.color = ConstantsLevelSelectScreen.levelButtonCompleteColor;
                } else {
                    this.color = ConstantsLevelSelectScreen.buttonColor;
                }

                super.reset();
            }
        };
    }

    private Boolean saved;
    public void save() {
        saved = true;
    }

    private void createPopUpSaved(UIElement parent) {
        UIElement saveMessage = new UIText(
                this,
                parent,
                ConstantsLevelSelectScreen.savedPopupPosition,
                ConstantsLevelSelectScreen.savedPopupText,
                ConstantsLevelSelectScreen.savedPopupColor,
                ConstantsLevelSelectScreen.savedPopupFont) {

            BigDecimal count = new BigDecimal("0");
            Boolean timerStarted = false;

            @Override
            public void onTick(long nanosSincePreviousTick) {

                if (saved) {
                    timerStarted = true;
                    count = new BigDecimal("0");
                    saved = false;
                }

                if (timerStarted) {
                    count = count.add(new BigDecimal(nanosSincePreviousTick));
                    if (count.compareTo(ConstantsLevelSelectScreen.savedPopupTimer) < 0) {
                        return;
                    }
                    count = new BigDecimal("0");
                    timerStarted = false;
                    this.text = "";
                }

                super.onTick(nanosSincePreviousTick);
            }

            @Override
            public void onDraw(GraphicsContext g) {
                if (timerStarted) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(saveMessage);
    }
}
