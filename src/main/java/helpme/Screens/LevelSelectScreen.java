package helpme.Screens;

import engine.Application;
import engine.SavingLoading.SaveFile;
import engine.SavingLoading.SaveFileParseException;
import engine.Screen;
import engine.TerrainGeneration.LevelParseException;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsGameValues;
import helpme.Constants.ConstantsLevelSelectScreen;
import helpme.Constants.ConstantsSaveFileSelectionScreen;
import helpme.HelpMeGameLevel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LevelSelectScreen extends Screen {
    static String saveFileName;
    static Boolean showPopupGameFileCorruption;
    HelpMeGameLevel helLevel;
    public LevelSelectScreen(Application engine, String saveFilename, HelpMeGameLevel helLevel) {
        super(engine);

        saveFileName = saveFilename;
        showPopupGameFileCorruption = false;
        this.helLevel = helLevel;

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

        // Create Level Buttons
        int row = 0;
        int col = 0;
        String previousLevel = null;
        for (String level : ConstantsGameValues.levels) {
            double buttonPositionX = ConstantsLevelSelectScreen.buttonStartingPosition.x + (ConstantsLevelSelectScreen.levelButtonSize.x * row) + (ConstantsLevelSelectScreen.buttonPadding.x * row);
            double buttonPositionY = ConstantsLevelSelectScreen.buttonStartingPosition.y + (ConstantsLevelSelectScreen.levelButtonSize.y * col) + (ConstantsLevelSelectScreen.buttonPadding.y * col);
            background.addChildren(createLevelButton(background, new Vec2d(buttonPositionX, buttonPositionY), level, previousLevel));
            previousLevel = level;
            row += 1;
            if (row == ConstantsGameValues.numberOfLevelsAcross) {
                row = 0;
                col += 1;
            }
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

        createPopUpGameFileCorruption(background);
    }

    private UIElement createLevelButton(UIElement parent, Vec2d buttonPosition, String buttonText, String previousLevel) {
        Color initialColor = ConstantsLevelSelectScreen.buttonColor;
        if (previousLevel != null && !ConstantsGameValues.levelComplete.get(previousLevel)) {
            initialColor = ConstantsLevelSelectScreen.levelButtonlockedColor;
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
                    if (previousLevel == null || ConstantsGameValues.levelComplete.get(previousLevel)) {
                        try {
                            helLevel.setCurrentLevel(this.buttonText);
                        } catch (IOException | LevelParseException ex) {
                            System.out.println(ex.getMessage());
                            showPopupGameFileCorruption = true;
                            return;
                        }
                        setActiveScreen("game");
                    }
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsLevelSelectScreen.buttonHoverColor;
                } else {
                    if (previousLevel == null || ConstantsGameValues.levelComplete.get(previousLevel)) {
                        this.color = ConstantsLevelSelectScreen.buttonColor;
                    } else {
                        this.color = ConstantsLevelSelectScreen.levelButtonlockedColor;
                    }
                }

                super.onMouseMoved(e);
            }

            @Override
            public void reset() {
                if (previousLevel == null || ConstantsGameValues.levelComplete.get(previousLevel)) {
                    this.color = ConstantsLevelSelectScreen.buttonColor;
                } else {
                    this.color = ConstantsLevelSelectScreen.levelButtonlockedColor;
                }

                super.reset();
            }
        };
    }

    @Override
    protected void reset() {
        saveFile();
        if (isGameComplete()) {
            setActiveScreen("win");
        }

        super.reset();
    }

    public static void saveFile() {
        try {
            Document doc = SaveFile.create();

            Element game = doc.createElement("Game");

            Element levels = doc.createElement("Levels");

            game.appendChild(levels);

            int count = 0;

            for (String key : ConstantsGameValues.levels) {
                Element level = doc.createElement("Level");
                Boolean complete = ConstantsGameValues.levelComplete.get(key);
                if (complete) {
                    count += 1;
                }
                level.setAttribute("complete", String.valueOf(complete));
                level.setAttribute("name", key);
                levels.appendChild(level);
            }

            Element controls = doc.createElement("Controls");

            game.appendChild(controls);

            for (List<KeyCode> playerControls : ConstantsGameValues.controls) {
                Element player = doc.createElement("Player");
                player.setAttribute("up", playerControls.get(0).getName());
                player.setAttribute("down", playerControls.get(1).getName());
                player.setAttribute("right", playerControls.get(2).getName());
                player.setAttribute("left", playerControls.get(3).getName());
                player.setAttribute("action", playerControls.get(4).getName());
                controls.appendChild(player);
            }

            doc.appendChild(game);
            String fix = "-";
            if (count < 10) {
                fix = "-0";
            }

            // Delete Previously Existing Save File
            String[] files = Objects.requireNonNull(new File(ConstantsSaveFileSelectionScreen.savefilesPathway).list());
            for (String file : files) {
                if (file.substring(0, 2).equals(saveFileName)) {
                    if (!new File(ConstantsSaveFileSelectionScreen.savefilesPathway + "\\" + file).delete()) {
                        System.out.println("Failed to Delete Old Save File");
                        showPopupGameFileCorruption = true;
                        return;
                    }
                }
            }

            SaveFile.save(doc, ConstantsSaveFileSelectionScreen.savefilesPathway + "\\" + saveFileName + fix + count + ".xml");
        } catch (SaveFileParseException e) {
            System.out.println(e.getMessage());
            showPopupGameFileCorruption = true;
        }
    }

    protected boolean isGameComplete() {
        for (boolean levelComplete : ConstantsGameValues.levelComplete.values()) {
            if (!levelComplete) {
                return false;
            }
        }

        return true;
    }

    private void createPopUpGameFileCorruption(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsSaveFileSelectionScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupGameFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsSaveFileSelectionScreen.popUpPanelPosition,
                ConstantsSaveFileSelectionScreen.popUpPanelSize,
                ConstantsSaveFileSelectionScreen.popUpPanelColor,
                ConstantsSaveFileSelectionScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupGameFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsSaveFileSelectionScreen.popUpTextPosition,
                ConstantsSaveFileSelectionScreen.popUpTextGameFileCorruption,
                ConstantsSaveFileSelectionScreen.popUpTextColor,
                ConstantsSaveFileSelectionScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupGameFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);
    }
}
