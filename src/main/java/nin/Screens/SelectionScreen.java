package nin.Screens;

import engine.Application;
import engine.SavingLoading.SaveFile;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import nin.Constants.ConstantsSelectionScreen;
import nin.NinGameLevel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;

public class SelectionScreen extends Screen {
    NinGameLevel ninGameLevel;
    Boolean saved;
    public SelectionScreen(Application engine, NinGameLevel ninGameLevel) {
        super(engine);

        this.ninGameLevel = ninGameLevel;
        this.saved = false;

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

        // Back Button
        UIElement backButton = new UIButton(
                SelectionScreen.this,
                background,
                ConstantsSelectionScreen.selectionScreenBackButtonPosition,
                ConstantsSelectionScreen.selectionScreenBackButtonSize,
                ConstantsSelectionScreen.selectionScreenBackButtonColor,
                ConstantsSelectionScreen.selectionScreenBackButtonArcSize,
                ConstantsSelectionScreen.selectionScreenBackButtonText,
                ConstantsSelectionScreen.selectionScreenBackButtonTextPosition,
                ConstantsSelectionScreen.selectionScreenBackButtonTextColor,
                ConstantsSelectionScreen.selectionScreenBackButtonTextFont) {
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
                    this.color = ConstantsSelectionScreen.selectionScreenBackButtonHoverColor;
                } else {
                    this.color = ConstantsSelectionScreen.selectionScreenBackButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(backButton);

        // Save Button
        UIElement saveButton = new UIButton(
                SelectionScreen.this,
                background,
                ConstantsSelectionScreen.selectionScreenSaveButtonPosition,
                ConstantsSelectionScreen.selectionScreenSaveButtonSize,
                ConstantsSelectionScreen.selectionScreenSaveButtonColor,
                ConstantsSelectionScreen.selectionScreenSaveButtonArcSize,
                ConstantsSelectionScreen.selectionScreenSaveButtonText,
                ConstantsSelectionScreen.selectionScreenSaveButtonTextPosition,
                ConstantsSelectionScreen.selectionScreenSaveButtonTextColor,
                ConstantsSelectionScreen.selectionScreenSaveButtonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    saved = true;
                    saveFile();
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSelectionScreen.selectionScreenSaveButtonHoverColor;
                } else {
                    this.color = ConstantsSelectionScreen.selectionScreenSaveButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(saveButton);

        UIElement saveMessage = new UIText(
                this,
                background,
                ConstantsSelectionScreen.selectionScreenSaveMessagePosition,
                "",
                ConstantsSelectionScreen.selectionScreenSaveMessageColor,
                ConstantsSelectionScreen.selectionScreenSaveMessageFont) {

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
                    this.text = "Saved!";
                    count = count.add(new BigDecimal(nanosSincePreviousTick));
                    if (count.compareTo(ConstantsSelectionScreen.selectionScreenSaveMessageTime) < 0) {
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

                super.onDraw(g);
            }
        };
        background.addChildren(saveMessage);
    }

    private UIElement createLevelButton(UIElement parent, Vec2d buttonPosition, String buttonText) {
        Color initialColor;
        if (ConstantsSelectionScreen.levelComplete.get(buttonText)) {
            initialColor = ConstantsSelectionScreen.levelCompleteButtonColor;
        } else {
            initialColor = ConstantsSelectionScreen.levelIncompleteButtonColor;
        }
        return new UIButton(
                SelectionScreen.this,
                parent,
                buttonPosition,
                ConstantsSelectionScreen.selectionScreenButtonSize,
                initialColor,
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

    private void saveFile() {
        Document doc = SaveFile.create();

        Element game = doc.createElement("Game");

        int count = 0;

        for (String key : ConstantsSelectionScreen.levelComplete.keySet()) {
            Element level = doc.createElement("Level");
            Boolean complete = ConstantsSelectionScreen.levelComplete.get(key);
            if (complete) {
                count += 1;
            }
            level.setAttribute("complete", String.valueOf(complete));
            level.setAttribute("name", key);
            game.appendChild(level);
        }

        doc.appendChild(game);
        String fix = "-";
        if (count < 10) {
            fix = "-0";
        }

        // Delete Previously Existing Save File
        String[] files = Objects.requireNonNull(new File(".\\src\\main\\java\\nin\\SaveFiles").list());
        for (String file : files) {
            if (file.substring(0, 2).equals(SaveLoadScreen.saveFileName)) {
                if (!new File(".\\src\\main\\java\\nin\\SaveFiles\\"+file).delete()){
                    System.out.println("Failed to Delete Old Save File");
                    System.exit(1);
                }
            }
        }

        SaveFile.save(doc, ".\\src\\main\\java\\nin\\SaveFiles\\" + SaveLoadScreen.saveFileName + fix + count + ".xml");
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
