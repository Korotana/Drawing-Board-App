package Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ToolPalette extends StackPane {

    private ArrayList<Button> buttonList = new ArrayList<>();

    public ToolPalette() throws FileNotFoundException {

        Button arrow = new Button();
        arrow.setMaxWidth(90);
        arrow.setMaxHeight(90);
        arrow.setStyle("-fx-background-color: #00fff8");
        FileInputStream input = new FileInputStream("src/main/java/Views/arrow.png");
        ImageView imageView = new ImageView(new Image(input));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        arrow.setGraphic(imageView);
        VBox buttonToolbar = new VBox(arrow);
        buttonToolbar.setBackground(new Background(new BackgroundFill(Color.HOTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonToolbar.setMaxWidth(100);
        System.out.println("tool\n\n");
        this.getChildren().addAll(buttonToolbar);

    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    public void setButtonList(ArrayList<Button> buttonList) {
        this.buttonList = buttonList;
    }
}
