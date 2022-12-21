package nin.Screens;

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
import javafx.scene.input.MouseEvent;
import nin.Constants.ConstantsSaveLoadScreen;
import nin.Constants.ConstantsSelectionScreen;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Objects;

public class SaveLoadScreen extends Screen {
    public static String saveFileName;
    public SaveLoadScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsSaveLoadScreen.saveLoadScreenBackgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsSaveLoadScreen.saveLoadScreenTitlePosition,
                ConstantsSaveLoadScreen.saveLoadScreenTitle,
                ConstantsSaveLoadScreen.saveLoadScreenTitleColor,
                ConstantsSaveLoadScreen.saveLoadScreenTitleFont);
        background.addChildren(title);

        // Create Save Load Buttons
        checkFilesAndCreateButtons(background);

        // Back Button
        UIElement backButton = new UIButton(
                SaveLoadScreen.this,
                background,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonPosition,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonSize,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonColor,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonArcSize,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonText,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonTextPosition,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonTextColor,
                ConstantsSaveLoadScreen.saveLoadScreenBackButtonTextFont) {
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
                    this.color = ConstantsSaveLoadScreen.saveLoadScreenBackButtonHoverColor;
                } else {
                    this.color = ConstantsSaveLoadScreen.saveLoadScreenBackButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(backButton);
    }

    private void checkFilesAndCreateButtons(UIElement parent) {
        String[] files = Objects.requireNonNull(new File(".\\src\\main\\java\\nin\\SaveFiles").list());
        for (int i = 0; i < ConstantsSaveLoadScreen.numberOfButtons; i++ ) {
            boolean newGame = true;
            double buttonPositionX = ConstantsSaveLoadScreen.buttonStartingPosition.x;
            double buttonPositionY = ConstantsSaveLoadScreen.buttonStartingPosition.y + (ConstantsSaveLoadScreen.saveLoadScreenButtonSize.y * i) + (ConstantsSaveLoadScreen.buttonPadding.y * i);
            UIElement button = null;
            for (String file : files) {
                if (file.substring(0, 2).equals("0" + i)) {
                    newGame = false;
                    button = createSaveLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), "0"+i, file, "Load: " + file.substring(3, 5) + "/" + ConstantsSelectionScreen.levelComplete.keySet().size());
                    parent.addChildren(button);
                    button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonRelativePosition), "0"+i, file));
                    break;
                }
            }

            if (newGame) {
                button = createSaveLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), "0"+i, null, " new game");
                parent.addChildren(button);
                button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonRelativePosition), "0"+i,null));
            }
        }
    }

    private UIElement createSaveLoadButton(UIElement parent, Vec2d buttonPosition, String buttonNumber, String filename, String buttonText) {
        return new UIButton(
                SaveLoadScreen.this,
                parent,
                buttonPosition,
                ConstantsSaveLoadScreen.saveLoadScreenButtonSize,
                ConstantsSaveLoadScreen.saveLoadScreenButtonColor,
                ConstantsSaveLoadScreen.saveLoadScreenButtonArcSize,
                buttonText,
                ConstantsSaveLoadScreen.saveLoadScreenButtonTextPosition,
                ConstantsSaveLoadScreen.saveLoadScreenButtonTextColor,
                ConstantsSaveLoadScreen.saveLoadScreenButtonTextFont) {

            final String bn = buttonNumber;
            String fn = filename;

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    if (fn != null) {
                        loadFile(fn);
                    } else {
                        newGame();
                    }

                    saveFileName = buttonNumber;

                    setActiveScreen("select");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSaveLoadScreen.saveLoadScreenButtonHoverColor;
                } else {
                    this.color = ConstantsSaveLoadScreen.saveLoadScreenButtonColor;
                }

                super.onMouseMoved(e);
            }

            @Override
            public void reset() {
                String[] files = Objects.requireNonNull(new File(".\\src\\main\\java\\nin\\SaveFiles").list());
                boolean found = false;
                for (String file : files) {
                    if (file.substring(0, 2).equals(bn)) {
                        found = true;
                        this.fn = file;
                        this.buttonText = "Load: " + file.substring(3, 5) + "/" + ConstantsSelectionScreen.levelComplete.keySet().size();
                        break;
                    }
                }

                if (!found) {
                    this.fn = null;
                    this.buttonText = " new game";
                }

                super.reset();
            }
        };
    }

    private UIElement createDeleteButton(UIElement parent, Vec2d buttonPosition, String buttonNumber, String filename) {
        return new UIButton(
                SaveLoadScreen.this,
                parent,
                buttonPosition,
                ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonSize,
                ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonColor,
                ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonArcSize,
                "X",
                ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonTextPosition,
                ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonTextColor,
                ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonTextFont) {

            final String bn = buttonNumber;
            String fn = filename;

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    File f = new File(".\\src\\main\\java\\nin\\SaveFiles\\"+fn);
                    if (fn != null) {
                        if (!f.delete()){
                            System.out.println("Failed to Delete Old Save File");
                            System.out.println(f.getAbsolutePath() + " Exists: " + f.exists());
                            System.exit(1);
                        }
                        parent.reset();
                    }
                }

                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonHoverColor;
                } else {
                    this.color = ConstantsSaveLoadScreen.saveLoadScreenDeleteButtonColor;
                }

                super.onMouseMoved(e);
            }

            @Override
            public void reset() {
                String[] files = Objects.requireNonNull(new File(".\\src\\main\\java\\nin\\SaveFiles").list());
                boolean found = false;
                for (String file : files) {
                    if (file.substring(0, 2).equals(bn)) {
                        found = true;
                        this.fn = file;
                        break;
                    }
                }

                if (!found) {
                    this.fn = null;
                }

                super.reset();
            }
        };
    }

    private void loadFile(String filename) {
        try {
            Document doc = SaveFile.read(".\\src\\main\\java\\nin\\SaveFiles\\" + filename);

            NodeList nList = doc.getElementsByTagName("Level");

            for (int i = 0; i < nList.getLength(); i++) {
                Element level = (Element) nList.item(i);
                String levelName = level.getAttribute("name");
                Boolean levelComplete = Boolean.valueOf(level.getAttribute("complete"));
                ConstantsSelectionScreen.levelComplete.put(levelName, levelComplete);
            }
        } catch (SaveFileParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void newGame() {
        ConstantsSelectionScreen.levelComplete.replaceAll((k, v) -> false);
    }
}
