package hel.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.support.Vec2d;
import hel.Constants.ConstantsSaveFileSelectionScreen;

import java.io.File;
import java.util.Objects;

public class SaveFileSelectionScreen extends Screen {
    public SaveFileSelectionScreen(Application engine) {
        super(engine);

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
    }

    private void checkFilesAndCreateGameAndDeletionButtons(UIElement parent) {
        String[] files = Objects.requireNonNull(new File(ConstantsSaveFileSelectionScreen.savefilesPathway).list());
        for (int i = 0; i < ConstantsSaveFileSelectionScreen.numberOfPossibleSavefiles; i++ ) {
            boolean newGame = true;
            double buttonPositionX = ConstantsSaveFileSelectionScreen.buttonStartingPosition.x;
            double buttonPositionY = ConstantsSaveFileSelectionScreen.buttonStartingPosition.y + (ConstantsSaveFileSelectionScreen.buttonSize.y * i) + (ConstantsSaveFileSelectionScreen.buttonPadding.y * i);
            UIElement button = null;
            for (String file : files) {
                if (file.substring(0, 2).equals("0" + i)) {
                    newGame = false;
                    button = createLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), "0"+i, file, "Load: " + file.substring(3, 5) + "/" + ConstantsSaveFileSelectionScreen.levelComplete.keySet().size());
                    parent.addChildren(button);
                    button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveFileSelectionScreen.saveLoadScreenDeleteButtonRelativePosition), "0"+i, file));
                    break;
                }
            }

            if (newGame) {
                button = createSaveLoadButton(parent, new Vec2d(buttonPositionX, buttonPositionY), "0"+i, null, " new game");
                parent.addChildren(button);
                button.addChildren(createDeleteButton(button, new Vec2d(buttonPositionX, buttonPositionY).plus(ConstantsSaveFileSelectionScreen.saveLoadScreenDeleteButtonRelativePosition), "0"+i,null));
            }
        }
    }
}
