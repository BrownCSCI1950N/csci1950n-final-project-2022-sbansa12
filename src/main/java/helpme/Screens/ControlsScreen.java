package helpme.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.*;
import engine.Utility;
import engine.support.Vec2d;
import helpme.Constants.ConstantsControlScreen;
import helpme.Constants.ConstantsGameValues;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ControlsScreen extends Screen {

    Boolean showPopupControlsError;
    Boolean showPopupControlsSaved;
    List<List<KeyCode>> temp;
    public ControlsScreen(Application engine) {
        super(engine);

        showPopupControlsError = false;
        showPopupControlsSaved = false;
        temp = new LinkedList<>(ConstantsGameValues.controls);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0, 0),
                engine.getCurrentStageSize(),
                ConstantsControlScreen.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                ConstantsControlScreen.titlePosition,
                ConstantsControlScreen.title,
                ConstantsControlScreen.titleColor,
                ConstantsControlScreen.titleFont);
        background.addChildren(title);

        // Create Back Button
        UIElement backButton = new UIButton(
                this,
                background,
                ConstantsControlScreen.backButtonPosition,
                ConstantsControlScreen.backButtonSize,
                ConstantsControlScreen.buttonColor,
                ConstantsControlScreen.buttonArcSize,
                ConstantsControlScreen.backButtonText,
                ConstantsControlScreen.backButtonTextPosition,
                ConstantsControlScreen.buttonTextColor,
                ConstantsControlScreen.buttonTextFont) {
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
                    this.color = ConstantsControlScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsControlScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(backButton);

        // Create Save Button
        UIElement saveButton = new UIButton(
                this,
                background,
                ConstantsControlScreen.saveButtonPosition,
                ConstantsControlScreen.saveButtonSize,
                ConstantsControlScreen.buttonColor,
                ConstantsControlScreen.buttonArcSize,
                ConstantsControlScreen.saveButtonText,
                ConstantsControlScreen.saveButtonTextPosition,
                ConstantsControlScreen.buttonTextColor,
                ConstantsControlScreen.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    showPopupControlsError = false;
                    showPopupControlsSaved = true;

                    ConstantsGameValues.controls = temp;

                    LevelSelectScreen.saveFile();
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsControlScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsControlScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(saveButton);

        // Text: Textbox
        createSingleControlBox(background, "Player 1: Move Up: ", 0, 0, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(0, 0)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(0,0)));
        createSingleControlBox(background, "Player 1: Move Down: ", 0, 1, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(0,1)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(0,1)));
        createSingleControlBox(background, "Player 1: Move Right: ", 0, 2, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(0,2)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(0,2)));
        createSingleControlBox(background, "Player 1: Move Left: ", 0, 3, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(0,3)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(0,3)));
        createSingleControlBox(background, "Player 1: Action: ", 0, 4, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(0,4)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(0,4)));
        createSingleControlBox(background, "Player 2: Move Up: ", 1, 0, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(1,0)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(1,0)));
        createSingleControlBox(background, "Player 2: Move Down: ", 1, 1, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(1,1)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(1,1)));
        createSingleControlBox(background, "Player 2: Move Right: ", 1, 2, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(1,2)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(1,2)));
        createSingleControlBox(background, "Player 2: Move Left: ", 1, 3, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(1,3)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(1,3)));
        createSingleControlBox(background, "Player 2: Action: ", 1, 4, ConstantsControlScreen.startPositionExplanation.plus(ConstantsControlScreen.explanationPadding.pmult(1,4)), ConstantsControlScreen.startPositionTextboxes.plus(ConstantsControlScreen.explanationPadding.pmult(1,4)));

        // Create Control Popups
        createPopUpControlsError(background);
        createPopUpControlsSaved(background);
    }

    private String convertKeyString(KeyCode key) {
        if (key == KeyCode.UP) {
            return "↑";
        } else if (key == KeyCode.DOWN) {
            return "↓";
        } else if (key == KeyCode.LEFT) {
            return "←";
        } else if (key == KeyCode.RIGHT) {
            return "→";
        } else {
            return key.getName().toUpperCase();
        }
    }

    private void createSingleControlBox(UIElement parent, String explanation, int playerControl, int whichControl,  Vec2d textPosition, Vec2d textboxPosition) {
        UIElement text = new UIText(
                this,
                parent,
                textPosition,
                explanation,
                ConstantsControlScreen.textColor,
                ConstantsControlScreen.textFont
        ) {
            @Override
            public void onDraw(GraphicsContext g) {
                    super.onDraw(g);
            }
        };
        parent.addChildren(text);

        // Create Control Textbox
        UIElement textBox = new UITextBox(
                this,
                parent,
                textboxPosition,
                ConstantsControlScreen.textboxSize,
                ConstantsControlScreen.buttonColor,
                ConstantsControlScreen.buttonArcSize,
                convertKeyString(ConstantsGameValues.controls.get(playerControl).get(whichControl)),
                ConstantsControlScreen.textboxTextPosition,
                ConstantsControlScreen.buttonTextColor,
                ConstantsControlScreen.buttonTextFont
        ) {
            boolean typing = false;
            KeyCode key = ConstantsGameValues.controls.get(playerControl).get(whichControl);

            @Override
            public void onMouseClicked(MouseEvent e) {
                boolean oldTyping = typing;
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    typing = !typing;
                    if (typing) {
                        this.color = ConstantsControlScreen.buttonHoverColor;
                    } else {
                        this.color = ConstantsControlScreen.buttonColor;
                    }
                } else {
                    typing = false;
                }

                if (oldTyping && !typing) {
                    KeyCode control = key;
                    if (control == null || control == ConstantsGameValues.clickButtonLevelQuit || control == ConstantsGameValues.clickButtonLevelReset) {
                        showPopupControlsError = true;
                        showPopupControlsSaved = false;
                        typing = true;
                    } else {
                        // Ensure No Other Action With Same Button
                        for (int p = 0; p < temp.size(); p++) {
                            for (int k = 0; k < temp.get(p).size(); k++) {
                                if (p != playerControl || k != whichControl) {
                                    if (control == temp.get(p).get(k)) {
                                        showPopupControlsError = true;
                                        showPopupControlsSaved = false;
                                        typing = true;

                                        super.onMouseClicked(e);
                                        return;
                                    }
                                }
                            }
                        }
                        temp.get(playerControl).set(whichControl, control);
                    }
                }

                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsControlScreen.buttonHoverColor;
                } else {
                    if (typing) {
                        this.color = ConstantsControlScreen.buttonHoverColor;
                    } else {
                        this.color = ConstantsControlScreen.buttonColor;
                    }
                }

                super.onMouseMoved(e);
            }

            @Override
            public void onKeyPressed(KeyEvent e) {
                if (typing) {
                    if (e.getCode() == KeyCode.BACK_SPACE) {
                        if (!this.text.equals("")) {
                            this.text = this.text.substring(0, this.text.length() - 1);
                            key = null;
                        }

                    } else if (this.text.length() != 1) {
                        if (!ConstantsGameValues.validControls.contains(e.getCode())) {
                            super.onKeyPressed(e);
                            return;
                        }
                        key = e.getCode();
                        this.text = convertKeyString(key);
                    }
                }

                super.onKeyPressed(e);
            }
        };
        parent.addChildren(textBox);
    }
    private void createPopUpControlsError(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsControlScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsError) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsControlScreen.popUpPanelPosition,
                ConstantsControlScreen.popUpPanelSize,
                ConstantsControlScreen.popUpPanelColor,
                ConstantsControlScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsError) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsControlScreen.popUpTextPosition,
                ConstantsControlScreen.popUpTextControlsError,
                ConstantsControlScreen.popUpTextColor,
                ConstantsControlScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsError) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                ConstantsControlScreen.popUpButtonPosition,
                ConstantsControlScreen.popUpButtonSize,
                ConstantsControlScreen.buttonColor,
                ConstantsControlScreen.buttonArcSize,
                ConstantsControlScreen.popUpButtonText,
                ConstantsControlScreen.popUpButtonTextPosition,
                ConstantsControlScreen.buttonTextColor,
                ConstantsControlScreen.buttonTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsError) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    showPopupControlsError = false;
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsControlScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsControlScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }
    private void createPopUpControlsSaved(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsControlScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsSaved) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsControlScreen.popUpPanelPosition,
                ConstantsControlScreen.popUpPanelSize,
                ConstantsControlScreen.popUpPanelColor,
                ConstantsControlScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsSaved) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsControlScreen.popUpTextPosition,
                ConstantsControlScreen.popUpTextControlsSaved,
                ConstantsControlScreen.popUpTextColor,
                ConstantsControlScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsSaved) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                ConstantsControlScreen.popUpButtonPosition,
                ConstantsControlScreen.popUpButtonSize,
                ConstantsControlScreen.buttonColor,
                ConstantsControlScreen.buttonArcSize,
                ConstantsControlScreen.popUpButtonText,
                ConstantsControlScreen.popUpButtonTextPosition,
                ConstantsControlScreen.buttonTextColor,
                ConstantsControlScreen.buttonTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (showPopupControlsSaved) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    showPopupControlsSaved = false;
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = ConstantsControlScreen.buttonHoverColor;
                } else {
                    this.color = ConstantsControlScreen.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }
}
