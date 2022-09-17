package tic;

import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class UIBoard extends UIElement {

    public UIBoard(Screen screen, UIElement parent, Vec2d position, double size, Color color) {
        super(screen, parent, position, color);

        // Bars of Board
        UIElement horz1 = new UIRectangle(screen, this, new Vec2d(position.x + Constants.intialBoardSquaresAndBars(size, 1, 0), position.y), new Vec2d(size/Constants.DIVISION_FACTOR, size), color);
        UIElement horz2 = new UIRectangle(screen, this, new Vec2d(position.x + Constants.intialBoardSquaresAndBars(size, 2, 1), position.y), new Vec2d(size/Constants.DIVISION_FACTOR, size), color);

        UIElement vert1 = new UIRectangle(screen, this, new Vec2d(position.x, position.y + Constants.intialBoardSquaresAndBars(size, 1, 0)), new Vec2d(size, size/Constants.DIVISION_FACTOR), color);
        UIElement vert2 = new UIRectangle(screen, this, new Vec2d(position.x, position.y + Constants.intialBoardSquaresAndBars(size, 2, 1)), new Vec2d(size, size/Constants.DIVISION_FACTOR), color);

        this.addChildren(horz1);
        this.addChildren(horz2);
        this.addChildren(vert1);
        this.addChildren(vert2);
    }
}
