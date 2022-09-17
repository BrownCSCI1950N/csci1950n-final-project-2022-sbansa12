package tic.screens;

import engine.Application;
import engine.FontWrapper;
import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import tic.Constants;
import tic.UIBoard;
import tic.UITimer;

import java.util.Objects;

public class GameScreen extends Screen {
    private String currentPlayer = "x";
    private String[][] boardArray = new String[3][3];

    public GameScreen(Application engine) {
        super(engine);

        // Create Background
        UIElement background = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColor);
        uiElements.add(background);

        // Create Board
        UIElement board = new UIBoard(
                this,
                background,
                Constants.initialBoardTopRight,
                Constants.initialBoardSize,
               Constants.barsColor);
        background.addChildren(board);

        // Create Timer
        UIElement timer = new UITimer(
                this,
                background,
                Constants.timerPosition,
                Constants.timerSize,
                Constants.timerBorder);
        background.addChildren(timer);

        // Create Pieces
        createPieces(background);

        // Create Turn Text
        UIElement turnText = new UIText(this, background, Constants.turnTextPosition, currentPlayer + "'s turn!", Constants.turnTextColor, Constants.turnTextFont) {
            @Override
            public void onMouseClicked(MouseEvent e) {
                this.text = currentPlayer + "'s turn!";

                super.onMouseMoved(e);
            }
        };
        background.addChildren(turnText);
    }

    private void swapPlayer() {
        if (Objects.equals(currentPlayer, "x")) {
            currentPlayer = "o";
        } else {
            currentPlayer = "x";
        }
    }

    private boolean checkWin(String piece) {
        return ((Objects.equals(boardArray[0][0], piece) && Objects.equals(boardArray[0][1], piece) && Objects.equals(boardArray[0][2], piece)) ||
                (Objects.equals(boardArray[1][0], piece) && Objects.equals(boardArray[1][1], piece) && Objects.equals(boardArray[1][2], piece)) ||
                (Objects.equals(boardArray[2][0], piece) && Objects.equals(boardArray[2][1], piece) && Objects.equals(boardArray[2][2], piece)) ||
                (Objects.equals(boardArray[0][0], piece) && Objects.equals(boardArray[1][0], piece) && Objects.equals(boardArray[2][0], piece)) ||
                (Objects.equals(boardArray[0][1], piece) && Objects.equals(boardArray[1][1], piece) && Objects.equals(boardArray[2][1], piece)) ||
                (Objects.equals(boardArray[0][2], piece) && Objects.equals(boardArray[1][2], piece) && Objects.equals(boardArray[2][2], piece)) ||
                (Objects.equals(boardArray[0][0], piece) && Objects.equals(boardArray[1][1], piece) && Objects.equals(boardArray[2][2], piece)) ||
                (Objects.equals(boardArray[0][2], piece) && Objects.equals(boardArray[1][1], piece) && Objects.equals(boardArray[2][0], piece)));
    }

    private boolean checkFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3;j++) {
                if (Objects.equals(boardArray[i][j], null)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkDraw() {
        return !checkWin("x") && !checkWin("o") && checkFull();
    }

    @Override
    protected void reset() {
        currentPlayer = "x";
        boardArray = new String[3][3];

        super.reset();
    }

    @Override
    protected void onTick(long nanosSincePreviousTick) {
        // check whether timer ran out

        super.onTick(nanosSincePreviousTick);
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        String oldPlayer = currentPlayer;
        super.onMouseClicked(e);
        if (checkWin(oldPlayer)) {
            setActiveScreen(oldPlayer+"-win");
        }
        if (checkDraw()) {
            setActiveScreen("draw");
        }
    }

    public void createPieces(UIElement background) {
        Vec2d A1PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(0, Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 1, 0))).plus(Constants.gamePiecesLocation);
        Vec2d A2PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 1, 1), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 1, 0))).plus(Constants.gamePiecesLocation);
        Vec2d A3PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 2, 2), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 1, 0))).plus(Constants.gamePiecesLocation);
        Vec2d B1PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(0, Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 2, 1))).plus(Constants.gamePiecesLocation);
        Vec2d B2PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 1, 1), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 2, 1))).plus(Constants.gamePiecesLocation);
        Vec2d B3PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 2, 2), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 2, 1))).plus(Constants.gamePiecesLocation);
        Vec2d C1PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(0, Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 3, 2))).plus(Constants.gamePiecesLocation);
        Vec2d C2PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 1, 1), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 3, 2))).plus(Constants.gamePiecesLocation);
        Vec2d C3PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 2, 2), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, 3, 2))).plus(Constants.gamePiecesLocation);

        UIElement A1 = new UIPiece(this, background, A1PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(0,0), new Vec2i(0,0), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(0,0));
        UIElement A2 = new UIPiece(this, background, A2PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(1,1), new Vec2i(0,0), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(0,1));
        UIElement A3 = new UIPiece(this, background, A3PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(2,2), new Vec2i(0,0), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(0,2));

        UIElement B1 = new UIPiece(this, background, B1PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(0,0), new Vec2i(1,1), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(1,0));
        UIElement B2 = new UIPiece(this, background, B2PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(1,1), new Vec2i(1,1), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(1,1));
        UIElement B3 = new UIPiece(this, background, B3PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(2,2), new Vec2i(1,1), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(1,2));

        UIElement C1 = new UIPiece(this, background, C1PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(0,0), new Vec2i(2,2), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(2,0));
        UIElement C2 = new UIPiece(this, background, C2PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(1,1), new Vec2i(2,2), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(2,1));
        UIElement C3 = new UIPiece(this, background, C3PieceDraw, null, null,  Constants.gamePieceFont, new Vec2i(2,2), new Vec2i(2,2), new Vec2i(1,0), new Vec2i(1,0), new Vec2i(2,2));

        background.addChildren(A1);
        background.addChildren(A2);
        background.addChildren(A3);
        background.addChildren(B1);
        background.addChildren(B2);
        background.addChildren(B3);
        background.addChildren(C1);
        background.addChildren(C2);
        background.addChildren(C3);
    }

    private class UIPiece extends UIText {

        private final Vec2i boardPosition;
        private boolean piecePlaced = false;
        private final Vec2d originalBoundingBoxPosition;
        private Vec2d currentBoundingBoxPosition;
        private final Vec2d originalBoundingBoxSize;
        private Vec2d currentBoundingBoxSize;

        public UIPiece(Screen screen, UIElement parent, Vec2d position, String text, Color color, FontWrapper font, Vec2i boundingBoxPositionX, Vec2i boundingBoxPositionY, Vec2i boundingBoxSizeX, Vec2i boundingBoxSizeY, Vec2i boardPosition) {
            super(screen, parent, position, text, color, font);
            this.boardPosition = boardPosition;

            this.originalBoundingBoxPosition = new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxPositionX.x, boundingBoxPositionX.y), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxPositionY.x, boundingBoxPositionY.y)).plus(Constants.initialBoardTopRight);
            this.currentBoundingBoxPosition = new Vec2d(originalBoundingBoxPosition);
            this.originalBoundingBoxSize = new Vec2d(Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxSizeX.x, boundingBoxSizeX.y), Constants.intialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxSizeY.x, boundingBoxSizeY.y));
            this.currentBoundingBoxSize = new Vec2d(originalBoundingBoxSize);
        }

        @Override
        public void reset() {
            piecePlaced = false;
            text = "";
        }

        @Override
        public void onMouseClicked(MouseEvent e) {
            if (!piecePlaced) {
                if (Utility.inBoundingBox(currentBoundingBoxPosition, currentBoundingBoxPosition.plus(currentBoundingBoxSize), new Vec2d(e.getX(), e.getY()))) {
                    this.text = currentPlayer;
                    this.color = Constants.placedColors.get(currentPlayer);

                    piecePlaced = true;
                    boardArray[boardPosition.x][boardPosition.y] = currentPlayer;
                    swapPlayer();
                }
            }

            super.onMouseMoved(e);
        }

        @Override
        public void onMouseMoved(MouseEvent e) {
            if (!piecePlaced) {
                if (Utility.inBoundingBox(currentBoundingBoxPosition, currentBoundingBoxPosition.plus(currentBoundingBoxSize), new Vec2d(e.getX(), e.getY()))) {
                    this.text = currentPlayer;
                    this.color = Constants.hoverColors.get(currentPlayer);
                } else {
                    this.text = "";
                }
            }

            super.onMouseMoved(e);
        }

        @Override
        public void onResize(Vec2d newSize) {
            this.currentBoundingBoxPosition = originalBoundingBoxPosition.pmult(newSize);
            this.currentBoundingBoxSize = originalBoundingBoxSize.pmult(newSize);

            super.onResize(newSize);
        }
    }
}
