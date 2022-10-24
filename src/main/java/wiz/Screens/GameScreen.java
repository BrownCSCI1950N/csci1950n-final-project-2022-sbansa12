package wiz.Screens;

import Pair.Pair;
import engine.Application;
import engine.Components.DiscoveredComponent;
import engine.Components.TileComponent;
import engine.GameObject;
import engine.Screen;
import engine.TerrainGeneration.Terrain;
import engine.UI.*;
import engine.Utility;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import wiz.Constants;
import wiz.UIHealthBarBoss;
import wiz.WizGame;

import java.util.List;

public class GameScreen extends Screen {
    Viewport viewport;
    WizGame wizGame;
    public GameScreen(Application engine, WizGame wizGame) {
        super(engine);
        this.wizGame = wizGame;

        // Create Background
        UIElement backgroundGame = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColorGame);
        uiElements.add(backgroundGame);

        // Viewport
        this.viewport = new Viewport(
                this,
                null,
                Constants.startingViewportGameCoordinates,
                Constants.viewportScreenPosition,
                Constants.viewportScreenSize,
                null,
                Constants.scale,
                null,
                null,
                null,
                null
        );
        backgroundGame.addChildren(viewport);

        // Number of Deaths Shown
        UIElement deaths = new UIText(
                this,
                viewport,
                Constants.deathsPosition,
                "Deaths: " + wizGame.getDeathCount(),
                Constants.deathsColor,
                Constants.deathsFont){
            @Override
            public void onDraw(GraphicsContext g) {
                this.text = "Deaths: " + wizGame.getDeathCount();

                super.onDraw(g);
            }
        };
        viewport.addChildren(deaths);

        // Create Hint Button (Changes Screen to Instructions)
        UIElement hintButton = new UIButton(
                this,
                viewport,
                Constants.hintButtonPosition,
                Constants.hintButtonSize,
                Constants.buttonColor,
                Constants.hintButtonArcSize,
                Constants.hintButtonText,
                Constants.hintButtonTextPosition,
                Constants.buttonTextColor,
                Constants.buttonTextFont) {

            @Override
            public void onDraw(GraphicsContext g) {
                if (!wizGame.getBossFight()) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (wizGame.getBossFight()) {return;}
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    wizGame.setPopUp(true);
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (wizGame.getBossFight()) {return;}
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.buttonHoverColor;
                } else {
                    this.color = Constants.buttonColor;
                }

                super.onMouseMoved(e);
            }
        };
        viewport.addChildren(hintButton);

        // Create Minimap
        UIElement minimap = new UIMinimap(
                this,viewport,
                Constants.minimapPosition,
                Constants.minimapSize){
            @Override
            public void onDraw(GraphicsContext g) {
                Pair<Terrain, List<GameObject>> everything = wizGame.getMapWorld();
                Vec2i mapSize = everything.getLeft().getSizeOfMap();

                Vec2d sizeTileDrawRaw = currentSize.pdiv(new Vec2d(mapSize));
                Vec2d sizeTileDraw = new Vec2d(Math.min(sizeTileDrawRaw.x, sizeTileDrawRaw.y));

                Vec2d scale = sizeTileDraw.pdiv(Constants.tileSize);

                double startPositionY = currentPosition.y + ((sizeTileDrawRaw.y - sizeTileDraw.y) * mapSize.y);

                for (GameObject gO: everything.getRight()) {
                    if (gO.hasComponentTag("tile")) {
                        Color setColor = Constants.tileToColor(((TileComponent) gO.getComponent("tile")).getTileType());
                        if (gO.hasComponentTag("discovered")) {
                            DiscoveredComponent c = (DiscoveredComponent) gO.getComponent("discovered");
                            if (!c.isDiscovered()) {
                                setColor = Constants.notDiscoveredColor;
                            }
                        }
                        g.setFill(setColor);
                        Vec2d p = gO.getTransform().getCurrentGameSpacePosition().pmult(scale);
                        Vec2d s = gO.getTransform().getSize().pmult(scale);
                        g.fillRect(currentPosition.x + p.x, startPositionY + p.y, s.x, s.y);
                    }
                }

                super.onDraw(g);
            }
        };
        viewport.addChildren(minimap);

        createPopUp(viewport);

        // Boss Health Bar
        UIElement bossHealthBar = new UIHealthBarBoss(
                this, viewport,
                Constants.bossHealthBarPosition,
                Constants.bossHealthBarSize,
                Constants.bossHealthBarBorder,
                Constants.bossHealthBarContainerColor,
                Constants.bossHealthBarMaxBarColor,
                Constants.bossHealthBarColor,
                new Vec2d(520, 41),
                "Boss Health",
                Color.rgb(255,255,255),
                Constants.bossHealthBarTextFont
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getBossFight()) {
                    super.onDraw(g);
                }
            }

            @Override
            public double getScale() {
                if (wizGame.getBossFight()) {
                    return wizGame.getBossHealth();
                } else {
                    return 1;
                }
            }
        };
        viewport.addChildren(bossHealthBar);

        // Set wizGame Up
        this.wizGame.setViewport(viewport);
        this.wizGame.setGameScreen(this);
    }

    @Override
    protected void reset() {
        wizGame.setPopUp(false);

        // Start Wiz
        this.wizGame.startGame();

        super.reset();
    }

    @Override
    protected void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.R) {
            setActiveScreen("seed");
        }

        super.onKeyPressed(e);
    }

    private void createPopUp(UIElement parent) {
        // Create Background
        UIElement overlay = new UIRectangle(
                this,
                parent,
                new Vec2d(0,0),
                this.engine.getCurrentStageSize(),
                Constants.popUpOverlayColor){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }
        };
        parent.addChildren(overlay);

        UIElement box = new UIRectangle(
                this,
                overlay,
                Constants.popUpPanelPosition,
                Constants.popUpPanelSize,
                Constants.popUpPanelColor,
                Constants.popUpPanelArcSize){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }
        };
        overlay.addChildren(box);

        UIElement text = new UIText(
                this,
                box,
                Constants.popUpTextPosition,
                Constants.popUpText,
                Constants.popUpTextColor,
                Constants.popUpTextFont
                ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }
        };
        box.addChildren(text);

        UIElement button = new UIButton(
                this,
                box,
                Constants.popUpButtonPosition,
                Constants.popUpButtonSize,
                Constants.popUpButtonColor,
                Constants.popUpButtonArcSize,
                Constants.popUpButtonText,
                Constants.popUpButtonPositionText,
                Constants.popUpButtonColorText,
                Constants.popUpButtonFontText
        ){
            @Override
            public void onDraw(GraphicsContext g) {
                if (wizGame.getPopUp()) {
                    super.onDraw(g);
                }
            }

            @Override
            public void onMouseClicked(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    wizGame.setPopUp(false);
                }
                super.onMouseClicked(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.popUpButtonHoverColor;
                } else {
                    this.color = Constants.popUpButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        box.addChildren(button);
    }
}