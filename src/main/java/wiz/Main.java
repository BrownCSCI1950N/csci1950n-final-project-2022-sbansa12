package wiz;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;

public class Main {

    public static void main(String[] args) {
        FXFrontEnd app = new App("Wiz");
        FXApplication application = new FXApplication();
        application.begin(app);
    }
}