package nin.Screens;

import engine.Application;
import engine.Screen;
import engine.UI.*;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nin.Constants.ConstantsGameScreen;
import nin.NinGame;
import nin.NinGameLevel;

public class GameScreen extends Screen {
    NinGameLevel ninGameLevel;
    NinGame ninGame;
    SelectionScreen ss;

    public GameScreen(Application engine, SelectionScreen ss, NinGameLevel ninGameLevel, NinGame ninGame) {
        super(engine);
        this.ss = ss;
        this.ninGameLevel = ninGameLevel;
        this.ninGame = ninGame;

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                ConstantsGameScreen.gameScreenBackgroundColor);
        uiElements.add(background);

        // Viewport
        Viewport viewport = new Viewport(
                this,
                null,
                ConstantsGameScreen.startingViewportGameCoordinates,
                ConstantsGameScreen.viewportScreenPosition,
                ConstantsGameScreen.viewportScreenSize,
                null,
                ConstantsGameScreen.scale,
                null,
                null,
                null,
                null
        );
        background.addChildren(viewport);

        // Level Complete Pop Up
        createPopUp(viewport, ninGame);

        // Set wizGame Up
        ninGame.setViewport(viewport);
//        ninGame.setGameScreen(this);
    }

    @Override
    protected void onKeyPressed(KeyEvent e) {

        if (ninGame.levelComplete()) {
            if (e.getCode() == KeyCode.SPACE) {
                // Check Win Condition
                if (ss.isGameComplete()) {
                    setActiveScreen("win");
                }

                // Go To Next Level
                if (!ninGameLevel.nextLevel()) {
                    setActiveScreen("select");
                }
            }

            // Only Allow Space If Game Over
            return;
        }

        // Level Not Complete Yet

        if (e.getCode() == KeyCode.Q) {
            setActiveScreen("select");
        }

        if (e.getCode() == KeyCode.K) {
            ninGameLevel.resetCurrentLevel();
        }

        super.onKeyPressed(e);
    }

    private void createPopUp(UIElement parent, NinGame ninGame) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                ConstantsGameScreen.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (ninGame.levelComplete()) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                ConstantsGameScreen.popUpPanelPosition,
                ConstantsGameScreen.popUpPanelSize,
                ConstantsGameScreen.popUpPanelColor,
                ConstantsGameScreen.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (ninGame.levelComplete()) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                ConstantsGameScreen.popUpTextPosition,
                ConstantsGameScreen.popUpText,
                ConstantsGameScreen.popUpTextColor,
                ConstantsGameScreen.popUpTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (ninGame.levelComplete()) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);
    }
}
