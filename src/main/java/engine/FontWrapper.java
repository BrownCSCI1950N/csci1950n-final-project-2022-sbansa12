package engine;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Wrapper for the Font class
 */
public class FontWrapper {
    private final String font;
    private final FontWeight fontWeight;
    private final FontPosture fontPosture;
    private double size;

    public FontWrapper(String font, FontWeight fontWeight, FontPosture fontPosture, double size) {
        this.font = font;
        this.fontWeight = fontWeight;
        this.fontPosture = fontPosture;
        this.size = size;
    }

    public FontWrapper copy() {
        return new FontWrapper(this.font, this.fontWeight, this.fontPosture, this.size);
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double newSize) {
        this.size = newSize;
    }

    public Font font() {
        return Font.font(this.font, this.fontWeight, this.fontPosture, this.size);
    }
}
