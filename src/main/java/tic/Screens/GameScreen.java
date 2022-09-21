package tic.Screens;

import engine.Application;
import engine.FontWrapper;
import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.Utility;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import tic.Constants;
import tic.UIBoard;
import tic.UITimerBar;
import tic.UITimerText;

import java.math.BigDecimal;
import java.util.Objects;

public class GameScreen extends Screen {
    private String currentPlayer = "x";
    private String[][] boardArray = new String[3][3];

    public static BigDecimal count;

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
        UIElement timer = new UITimerBar(
                this,
                background,
                Constants.timerPosition,
                Constants.timerSize,
                Constants.timerBorder);
        background.addChildren(timer);

        // Create TimerText
        UIElement timer2 = new UITimerText(
                this,
                background,
                Constants.timerTextPosition,
                Constants.timerContainerColor,
                Constants.turnTextFont);
        background.addChildren(timer2);

        // Create Pieces
        createPieces(background);

        // Create Turn Text
        UIElement turnText = new UIText(this, background, Constants.turnTextPosition, currentPlayer + "'s turn!", Constants.turnTextColor, Constants.turnTextFont) {
            @Override
            public void onDraw(GraphicsContext g) {
                // Account for the Swapping of Current Player due to Clicks and Timer
                this.text = currentPlayer + "'s turn!";

                super.onDraw(g);
            }
        };
        background.addChildren(turnText);

        resetTimer();
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

    private void resetTimer() {count = new BigDecimal(String.valueOf(0));}

    @Override
    protected void reset() {
        super.reset();
        currentPlayer = "x";
        boardArray = new String[3][3];
        resetTimer();
    }

    @Override
    protected void onTick(long nanosSincePreviousTick) {
        count = count.add(new BigDecimal(nanosSincePreviousTick));
        if (count.compareTo(Constants.TIMER_VALUE) > 0) {
            swapPlayer();
            resetTimer();
        }

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
        Vec2d A1PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(0, Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 1, 0))).plus(Constants.gamePiecesLocation);
        Vec2d A2PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 1, 1), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 1, 0))).plus(Constants.gamePiecesLocation);
        Vec2d A3PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 2, 2), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 1, 0))).plus(Constants.gamePiecesLocation);
        Vec2d B1PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(0, Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 2, 1))).plus(Constants.gamePiecesLocation);
        Vec2d B2PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 1, 1), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 2, 1))).plus(Constants.gamePiecesLocation);
        Vec2d B3PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 2, 2), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 2, 1))).plus(Constants.gamePiecesLocation);
        Vec2d C1PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(0, Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 3, 2))).plus(Constants.gamePiecesLocation);
        Vec2d C2PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 1, 1), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 3, 2))).plus(Constants.gamePiecesLocation);
        Vec2d C3PieceDraw = Constants.initialBoardTopRight.plus(new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 2, 2), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, 3, 2))).plus(Constants.gamePiecesLocation);

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
        private boolean pieceHover = false;
        private final Vec2d originalBoundingBoxPosition;
        private Vec2d currentBoundingBoxPosition;
        private final Vec2d originalBoundingBoxSize;
        private Vec2d currentBoundingBoxSize;

        public UIPiece(Screen screen, UIElement parent, Vec2d position, String text, Color color, FontWrapper font, Vec2i boundingBoxPositionX, Vec2i boundingBoxPositionY, Vec2i boundingBoxSizeX, Vec2i boundingBoxSizeY, Vec2i boardPosition) {
            super(screen, parent, position, text, color, font);
            this.boardPosition = boardPosition;

            this.originalBoundingBoxPosition = new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxPositionX.x, boundingBoxPositionX.y), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxPositionY.x, boundingBoxPositionY.y)).plus(Constants.initialBoardTopRight);
            this.currentBoundingBoxPosition = new Vec2d(originalBoundingBoxPosition);
            this.originalBoundingBoxSize = new Vec2d(Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxSizeX.x, boundingBoxSizeX.y), Constants.initialBoardSquaresAndBars(Constants.initialBoardSize, boundingBoxSizeY.x, boundingBoxSizeY.y));
            this.currentBoundingBoxSize = new Vec2d(originalBoundingBoxSize);
        }

        @Override
        public void reset() {
            piecePlaced = false;
            pieceHover = false;
            text = "";
        }

        @Override
        public void onDraw(GraphicsContext g) {
            // Account for the Swapping of Current Player due to Clicks and Timer
            if (!piecePlaced && pieceHover) {
                this.text = currentPlayer;
                this.color = Constants.hoverColors.get(currentPlayer);
            }

            super.onDraw(g);
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
                    resetTimer();
                }
            }

            super.onMouseMoved(e);
        }

        @Override
        public void onMouseMoved(MouseEvent e) {
            if (!piecePlaced) {
                if (Utility.inBoundingBox(currentBoundingBoxPosition, currentBoundingBoxPosition.plus(currentBoundingBoxSize), new Vec2d(e.getX(), e.getY()))) {
                    pieceHover = true;
                    this.text = currentPlayer;
                    this.color = Constants.hoverColors.get(currentPlayer);
                } else {
                    pieceHover = false;
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
