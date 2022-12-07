package hel.Screens;

import engine.Application;
import engine.SavingLoading.SaveFile;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import hel.Constants.ConstantsGameValues;
import hel.Constants.ConstantsSaveFileSelectionScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Objects;

public class SaveFileSelectionScreen extends Screen {

    // Save files are saved as <buttonName>-<number of completed levels>

    Boolean showPopup;
    public SaveFileSelectionScreen(Application engine) {
        super(engine);

        showPopup = false;

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsSaveFileSelectionScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsSaveFileSelectionScreen.titlePosition,
                ConstantsSaveFileSelectionScreen.title,
                ConstantsSaveFileSelectionScreen.titleColor,
                ConstantsSaveFileSelectionScreen.titleFont);
        background.addChildren(title);

        // Create New Game/Load Game Buttons
        checkFilesAndCreateGameAndDeletionButtons(background);

        // Create Save File Corrupted Popup
        createPopUp(background);
    }

    private void checkFilesAndCreateGameAndDeletionButtons(UIElement parent) {

        for (int i = 0; i < ConstantsSaveFileSelectionScreen.numberOfPossibleSavefiles; i++ ) {
            double buttonPositionX = ConstantsSaveFileSelectionScreen.buttonStartingPosition.x;
            double buttonPositionY = ConstantsSaveFileSelectionScreen.buttonStartingPosition.y + (ConstantsSaveFileSelectionScreen.loadButtonSize.y * i) + (ConstantsSaveFileSelectionScreen.buttonPadding.y * i);
            String buttonNumber;
            if (i < 10) {
                buttonNumber = "0" + i;
            } else {
                buttonNumber = String.valueOf(i);
            }

            String file = findSaveFile(buttonNumber);
            if (file != null) {
                UIElement button = createLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), buttonNumber, file, "load: " + file.substring(3, 5) + "/" + ConstantsGameValues.levels.size());
                parent.addChildren(button);
                button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveFileSelectionScreen.deleteButtonRelativePosition), buttonNumber, file));
            } else {
                // if do not find a file that corresponds to that button, this should lead to a new game.
                UIElement button = createLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), buttonNumber, null, " new game");
                parent.addChildren(button);
                button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveFileSelectionScreen.deleteButtonRelativePosition), buttonNumber, null));
            }
        }
    }

    private UIElement createLoadButton(UIElement parent, Vec2d buttonPosition, String buttonNumber, String filename, String buttonText) {
        return new UIButton(
                this,
                parent,
                buttonPosition,
                ConstantsSaveFileSelectionScreen.loadButtonSize,
                ConstantsSaveFileSelectionScreen.buttonColor,
                ConstantsSaveFileSelectionScreen.buttonArcSize,
                buttonText,
                ConstantsSaveFileSelectionScreen.buttonTextPosition,
                ConstantsSaveFileSelectionScreen.buttonTextColor,
                ConstantsSaveFileSelectionScreen.buttonTextFont) {

            final String buttonName = buttonNumber;
            String saveFilename = filename;

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    if (saveFilename != null) {
                        loadFile(saveFilename);
                    } else {
                        newGame();
                    }

                    engine.addScreen("select", new LevelSelectScreen(engine));

                    setActiveScreen("select");
                }

                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSaveFileSelectionScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsSaveFileSelectionScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }

            @Override
            public void reset() {
                String file = findSaveFile(buttonName);
                if (file != null) {
                    this.saveFilename = file;
                    this.buttonText = "Load: " + file.substring(3, 5) + "/" + ConstantsGameValues.levels.size();
                } else {
                    this.saveFilename = null;
                    this.buttonText = " new game";
                }

                super.reset();
            }
        };
    }

    private UIElement createDeleteButton(UIElement parent, Vec2d buttonPosition, String buttonNumber, String filename) {
        return new UIButton(
                this,
                parent,
                buttonPosition,
                ConstantsSaveFileSelectionScreen.deleteButtonSize,
                ConstantsSaveFileSelectionScreen.buttonColor,
                ConstantsSaveFileSelectionScreen.buttonArcSize,
                ConstantsSaveFileSelectionScreen.deleteButtonText,
                ConstantsSaveFileSelectionScreen.buttonTextPosition,
                ConstantsSaveFileSelectionScreen.buttonTextColor,
                ConstantsSaveFileSelectionScreen.buttonTextFont) {

            final String buttonName = buttonNumber;
            String saveFilename = filename;

            @Override
            public void onDraw(GraphicsContext g) {
                if (saveFilename != null) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    deleteFile(saveFilename);
                    parent.reset();
                }

                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSaveFileSelectionScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsSaveFileSelectionScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }

            @Override
            public void reset() {
                this.saveFilename = findSaveFile(buttonName);

                super.reset();
            }
        };
    }

    private String findSaveFile(String startName) {
        String[] files = Objects.requireNonNull(new File(ConstantsSaveFileSelectionScreen.savefilesPathway).list());
        for (String file : files) {
            if (file.substring(0, 2).equals(startName)) {
                return file;
            }
        }

        return null;
    }

    private void loadFile(String filename) {

        ConstantsGameValues.levelComplete.clear();

        Document doc = SaveFile.read(ConstantsSaveFileSelectionScreen.savefilesPathway + "\\" + filename);

        NodeList nList = doc.getElementsByTagName("Level");

        for (int i = 0; i < nList.getLength(); i++) {
            Element level = (Element) nList.item(i);
            String levelName = level.getAttribute("name");
            Boolean levelComplete = Boolean.valueOf(level.getAttribute("complete"));
            ConstantsGameValues.levelComplete.put(levelName, levelComplete);
        }

        for (String level: ConstantsGameValues.levels) {
            if (ConstantsGameValues.levelComplete.get(level) == null) {
                showPopup = true;
                deleteFile(filename);
            }
        }
    }

    private void deleteFile(String saveFilename) {
        File f = new File(ConstantsSaveFileSelectionScreen.savefilesPathway + "\\" + saveFilename);
        if (saveFilename != null) {
            if (!f.delete()){
                System.out.println("Failed to Delete Old Save File");
                System.out.println(f.getAbsolutePath() + " Exists: " + f.exists());
                System.exit(1);
            }
        }
    }

    private void newGame() {
        for (String level: ConstantsGameValues.levels) {
            ConstantsGameValues.levelComplete.put(level, false);
        }
    }

    private void createPopUp(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsSaveFileSelectionScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopup) {
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
                if (showPopup) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsSaveFileSelectionScreen.popUpTextPosition,
                ConstantsSaveFileSelectionScreen.popUpText,
                ConstantsSaveFileSelectionScreen.popUpTextColor,
                ConstantsSaveFileSelectionScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopup) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                ConstantsSaveFileSelectionScreen.popUpButtonPosition,
                ConstantsSaveFileSelectionScreen.popUpButtonSize,
                ConstantsSaveFileSelectionScreen.buttonColor,
                ConstantsSaveFileSelectionScreen.buttonArcSize,
                ConstantsSaveFileSelectionScreen.popUpButtonText,
                ConstantsSaveFileSelectionScreen.buttonTextPosition,
                ConstantsSaveFileSelectionScreen.buttonTextColor,
                ConstantsSaveFileSelectionScreen.buttonTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopup) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    showPopup = false;
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSaveFileSelectionScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsSaveFileSelectionScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }
}
