package hel.Constants;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConstantsGameValues {
    // Controls
    public static final KeyCode moveUp = KeyCode.W;
    public static final KeyCode moveDown = KeyCode.S;
    public static final KeyCode moveRight = KeyCode.D;
    public static final KeyCode moveLeft = KeyCode.A;

    // Levels
    public static final List<String> levels = new LinkedList<>(){{
        add("00");
    }};
    public static final Integer numberOfLevelsAcross = 4;
    public static final Map<String, Boolean> levelComplete = new HashMap<>();
}
