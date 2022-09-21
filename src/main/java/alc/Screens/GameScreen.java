package alc.Screens;

import engine.Application;
import engine.GameWorld;
import engine.Screen;
import engine.UI.UIViewport;

public class GameScreen extends Screen {

    UIViewport viewport;
    GameWorld gameWorld;
    public GameScreen(Application engine) {
        super(engine);

        // create uielement menu that holds elements on the right?
            // when click on the uielementPicture then create a game object
            // how does that interact with viewport?
        // create viewport into game world on left
        // create background game object given to game world?
        // add drawable gameObjects to draw system
        // add tickable gameObjects to tickSystem
        // register gameObject with system on addition to gameWorld, have it be automatic each system asks game object if interested
    }
}
