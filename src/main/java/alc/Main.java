package alc;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;
import alc.App;

public class Main {

    public static void main(String[] args) {
        FXFrontEnd app = new App("Alchemy");
        FXApplication application = new FXApplication();
        application.begin(app);
    }
}
