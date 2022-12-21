package helpme.Screens;

import engine.Application;
import engine.SavingLoading.SaveFile;
import engine.SavingLoading.SaveFileParseException;
import engine.Screen;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsGameValues;
import helpme.Constants.ConstantsSaveFileSelectionScreen;
import helpme.HelpMeGame;
import helpme.HelpMeGameLevel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SaveFileSelectionScreen extends Screen {

    // Save files are saved as <buttonName>-<number of completed levels>

    Boolean showPopupSaveFileCorruption;
    Boolean showPopupGameFileCorruption;

    public SaveFileSelectionScreen(Application engine) {
        super(engine);

        showPopupSaveFileCorruption = false;
        showPopupGameFileCorruption = false;

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
        createPopUpSaveFileCorruption(background);
        createPopUpGameFileCorruption(background);
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
                UIElement button = createLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), buttonNumber, file, loadButtonMessage(file));
                parent.addChildren(button);
                button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveFileSelectionScreen.deleteButtonRelativePosition), buttonNumber, file));
            } else {
                // If do not find a file that corresponds to that button, this should lead to a new game.
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
                    int err;
                    if (saveFilename != null) {
                        err = loadFile(saveFilename);
                    } else {
                        err = newGame();
                    }
                    if (err < 0) {
                        reset();
                        return;
                    }

                    HelpMeGame hel = new HelpMeGame();
                    HelpMeGameLevel helLevel = new HelpMeGameLevel(hel);
                    engine.addScreen("select", new LevelSelectScreen(engine, buttonName, helLevel));
                    engine.addScreen("settings", new SettingsScreen(engine));
                    engine.addScreen("controls", new ControlsScreen(engine));
                    engine.addScreen("game", new GameScreen(engine, helLevel, hel));
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
                    this.buttonText = loadButtonMessage(file);
                } else {
                    this.saveFilename = null;
                    this.buttonText = " new game";
                }

                super.reset();
            }
        };
    }

    private String loadButtonMessage(String file) {
        String numberLevels;
        if (ConstantsGameValues.levels.size() < 10) {
            numberLevels = "0" + ConstantsGameValues.levels.size();
        } else {
            numberLevels = String.valueOf(ConstantsGameValues.levels.size());
        }
        return "load: " + file.substring(3, 5) + "/" + numberLevels;
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

    private int loadFile(String filename) {

        ConstantsGameValues.levelComplete.clear();
        ConstantsGameValues.controls.clear();

        try {
            Document doc = SaveFile.read(ConstantsSaveFileSelectionScreen.savefilesPathway + "\\" + filename);

            NodeList levelList = doc.getElementsByTagName("Level");

            for (int i = 0; i < levelList.getLength(); i++) {
                Element level = (Element) levelList.item(i);
                String levelName = level.getAttribute("name");
                Boolean levelComplete = Boolean.valueOf(level.getAttribute("complete"));
                ConstantsGameValues.levelComplete.put(levelName, levelComplete);
            }

            for (String level : ConstantsGameValues.levels) {
                if (ConstantsGameValues.levelComplete.get(level) == null) {
                    showPopupSaveFileCorruption = true;
                    showPopupGameFileCorruption = false;
                    deleteFile(filename);
                    return -1;
                }
            }

            NodeList playerControlList = doc.getElementsByTagName("Player");

            List<List<KeyCode>> temp = new LinkedList<>();

            for (int i = 0; i < playerControlList.getLength(); i++) {
                Element level = (Element) playerControlList.item(i);
                KeyCode up = KeyCode.getKeyCode(level.getAttribute("up"));
                KeyCode down = KeyCode.getKeyCode(level.getAttribute("down"));
                KeyCode right = KeyCode.getKeyCode(level.getAttribute("right"));
                KeyCode left = KeyCode.getKeyCode(level.getAttribute("left"));
                KeyCode action = KeyCode.getKeyCode(level.getAttribute("action"));

                if (up == null || down == null || right == null || left == null || action == null) {
                    showPopupSaveFileCorruption = true;
                    showPopupGameFileCorruption = false;
                    deleteFile(filename);
                    return -1;
                }


                temp.add(new LinkedList<>() {{
                    add(up);
                    add(down);
                    add(right);
                    add(left);
                    add(action);
                }});
            }

            List<KeyCode> check = new LinkedList<>();
            check.addAll(temp.get(0));
            check.addAll(temp.get(1));

            for (KeyCode k : check) {
                if (k == ConstantsGameValues.clickButtonLevelQuit || k == ConstantsGameValues.clickButtonLevelReset || occurMoreThanOnce(k, check)) {
                    showPopupSaveFileCorruption = true;
                    showPopupGameFileCorruption = false;
                    deleteFile(filename);
                    return -1;
                }
            }

            ConstantsGameValues.controls = temp;

        } catch (SaveFileParseException e) {
            System.out.println(e.getMessage());
            showPopupSaveFileCorruption = true;
            showPopupGameFileCorruption = false;
            deleteFile(filename);
            return -1;
        }

        return 0;
    }

    private boolean occurMoreThanOnce(KeyCode k, List<KeyCode> lst) {
        int count = 0;
        for (KeyCode k1 : lst) {
            if (k == k1) {
                count++;
                if (count > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteFile(String saveFilename) {
        File f = new File(ConstantsSaveFileSelectionScreen.savefilesPathway + "\\" + saveFilename);
        if (saveFilename != null) {
            if (!f.delete()){
                System.out.println("Failed to Delete Old Save File");
                System.out.println(f.getAbsolutePath() + " Exists: " + f.exists());
                showPopupSaveFileCorruption = false;
                showPopupGameFileCorruption = true;
            }
        }
    }

    private int newGame() {
        for (String level: ConstantsGameValues.levels) {
            ConstantsGameValues.levelComplete.put(level, false);
        }

        ConstantsGameValues.controls.addAll(ConstantsGameValues.defaultControls);

        return 0;
    }

    private void createPopUpSaveFileCorruption(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsSaveFileSelectionScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupSaveFileCorruption) {
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
                if (showPopupSaveFileCorruption) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsSaveFileSelectionScreen.popUpTextPosition,
                ConstantsSaveFileSelectionScreen.popUpTextSaveFileCorruption,
                ConstantsSaveFileSelectionScreen.popUpTextColor,
                ConstantsSaveFileSelectionScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupSaveFileCorruption) {
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
                ConstantsSaveFileSelectionScreen.popUpButtonTextPosition,
                ConstantsSaveFileSelectionScreen.buttonTextColor,
                ConstantsSaveFileSelectionScreen.buttonTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupSaveFileCorruption) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    showPopupSaveFileCorruption = false;
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
