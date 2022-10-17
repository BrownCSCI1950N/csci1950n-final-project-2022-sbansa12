package wiz.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.*;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import wiz.Constants;
import wiz.WizGame;

import java.util.List;

public class WorldGenerationScreen extends Screen {
    public WorldGenerationScreen(Application engine, WizGame wizGame) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColor);
        uiElements.add(background);

        // Create Title
        UIElement title = new UIText(
                this,
                background,
                Constants.worldGenerationScreenTitlePosition,
                Constants.worldGenerationScreenTitle,
                Constants.titleColor,
                Constants.titleFont);
        background.addChildren(title);

        // Create Seed Textbox
        UIElement textBox = new UITextBox(
                this,
                background,
                Constants.worldGenerationScreenTextBoxPosition,
                Constants.worldGenerationTextBoxSize,
                Constants.buttonColor,
                Constants.buttonArcSize,
                wizGame.getSeed().toString(),
                Constants.buttonTextPosition,
                Constants.buttonTextColor,
                Constants.worldGenerationFont
        ) {

            final List<KeyCode> allowedCharacters = List.of(KeyCode.DIGIT0, KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9);

            boolean typing = false;

            boolean firstCharacterType = true;

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    typing = !typing;
                    if (typing) {
                        this.color = Constants.worldGenerationTextBoxHoverColor;
                    } else {
                        this.color = Constants.buttonColor;
                    }
                }

                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.worldGenerationTextBoxHoverColor;
                } else {
                    if (typing) {
                        this.color = Constants.worldGenerationTextBoxHoverColor;
                    } else {
                        this.color = Constants.buttonColor;
                    }
                }

                super.onMouseMoved(e);
            }

            @Override
            public void onKeyPressed(KeyEvent e) {
                if (typing) {
                    if (allowedCharacters.contains(e.getCode())){
                        if (this.text.length() != 9) {
                            if (!firstCharacterType) {
                                this.text = this.text + e.getText();
                            } else {
                                this.text = e.getText();
                                this.firstCharacterType = false;
                            }

                            if (this.text.equals("")) {
                                wizGame.setSeed(0);
                            } else {
                                wizGame.setSeed(Integer.parseInt(this.text));
                            }
                        }
                    }
                    if (e.getCode() == KeyCode.BACK_SPACE) {
                        if (this.text.equals("")) {
                            return;
                        }
                        this.text = this.text.substring(0, this.text.length() - 1);
                        if (this.text.equals("")) {
                            wizGame.setSeed(0);
                        } else {
                            wizGame.setSeed(Integer.parseInt(this.text));
                        }
                    }
                    if (e.getCode() == KeyCode.ENTER) {
                        setActiveScreen("game");
                    }
                }

                super.onKeyPressed(e);
            }
        };
        background.addChildren(textBox);

        // Create Confirm Button (Changes Screen to Game)
        UIElement startButton = new UIButton(
                this,
                background,
                Constants.worldGenerationButtonPosition,
                Constants.worldGenerationButtonSize,
                Constants.buttonColor,
                Constants.buttonArcSize,
                Constants.worldGenerationScreenButtonText,
                Constants.worldGenerationButtonTextPosition,
                Constants.buttonTextColor,
                Constants.buttonTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    setActiveScreen("game");
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.buttonHoverColor;
                } else {
                    this.color = Constants.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        background.addChildren(startButton);
    }
}
