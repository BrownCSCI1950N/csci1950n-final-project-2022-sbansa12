package tic;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;
import javafx.scene.text.Font;

/**
 * Here is your main class. You should not have to edit this
 * class at all unless you want to change your window size
 * or turn off the debugging information.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Font.getFamilies());
        FXFrontEnd app = new App("Tic-Tac-Toe");
        FXApplication application = new FXApplication();
        application.begin(app);
    }
}
