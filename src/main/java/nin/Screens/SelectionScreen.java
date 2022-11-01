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
import nin.NinGameLevel;

public class SelectionScreen extends Screen {
    NinGameLevel ninGameLevel;
    public SelectionScreen(Application engine, NinGameLevel ninGameLevel) {
        super(engine);

        this.ninGameLevel = ninGameLevel;

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsSelectionScreen.selectionScreenBackgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsSelectionScreen.selectionScreenTitlePosition,
                ConstantsSelectionScreen.selectionScreenTitle,
                ConstantsSelectionScreen.selectionScreenTitleColor,
                ConstantsSelectionScreen.selectionScreenTitleFont);
        background.addChildren(title);

        // Create Level Selection Buttons
        for (int j = 0; j < ConstantsSelectionScreen.levels.length; j++) {
            double buttonPositionY = ConstantsSelectionScreen.buttonStartingPosition.y + (ConstantsSelectionScreen.selectionScreenButtonSize.y * j) + (ConstantsSelectionScreen.buttonPadding.y * j);
            String[] row = ConstantsSelectionScreen.levels[j];
            for (int i = 0; i < row.length; i++) {
                double buttonPositionX = ConstantsSelectionScreen.buttonStartingPosition.x + (ConstantsSelectionScreen.selectionScreenButtonSize.x * i) + (ConstantsSelectionScreen.buttonPadding.x * i);
                background.addChildren(createLevelButton(background, new Vec2d(buttonPositionX, buttonPositionY), row[i]));
            }
        }
    }

    private UIElement createLevelButton(UIElement parent, Vec2d buttonPosition, String buttonText) {
        return new UIButton(
                SelectionScreen.this,
                parent,
                buttonPosition,
                ConstantsSelectionScreen.selectionScreenButtonSize,
                ConstantsSelectionScreen.levelIncompleteButtonColor,
                ConstantsSelectionScreen.selectionScreenButtonArcSize,
                buttonText,
                ConstantsSelectionScreen.selectionScreenButtonTextPosition,
                ConstantsSelectionScreen.selectionScreenButtonTextColor,
                ConstantsSelectionScreen.selectionScreenButtonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    ninGameLevel.setCurrentLevel(this.buttonText);
                    setActiveScreen("game");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSelectionScreen.selectionScreenButtonHoverColor;
                } else {
                    if (ConstantsSelectionScreen.levelComplete.get(this.buttonText)) {
                        this.color = ConstantsSelectionScreen.levelCompleteButtonColor;
                    } else {
                        this.color = ConstantsSelectionScreen.levelIncompleteButtonColor;
                    }

                }

                super.onMouseMoved(e);
            }
        };
    }

    @Override
    protected void reset() {
        for (boolean levelComplete : ConstantsSelectionScreen.levelComplete.values()) {
            if (!levelComplete) {
                super.reset();
                return;
            }
        }

        // If All Levels Complete
        setActiveScreen("win");
        super.reset();
    }
}
