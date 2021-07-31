package graphicView;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class GraphicUtils {
    public static Label getGoodLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.BROWN);
        label.setStyle("-fx-font-size: 15");
        return label;
    }

    public static Background getGreyBackground() {
        return new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Background getBackground(Color color){
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Background getBackground(String path){
        return new Background(new BackgroundFill(
                new ImagePattern(new Image(GraphicUtils.class.getResource(path).toExternalForm())),
                CornerRadii.EMPTY, Insets.EMPTY));
    }
}
