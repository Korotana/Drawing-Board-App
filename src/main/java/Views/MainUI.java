package Views;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class MainUI extends StackPane {

    public MainUI() throws FileNotFoundException {

        HBox mainBox = new HBox();
        ToolPalette toolPalette = new ToolPalette();
        DiagramView diagramView = new DiagramView();

        System.out.println("Man\n\n");
        mainBox.getChildren().addAll(toolPalette, diagramView);
        this.getChildren().addAll(mainBox);

    }
}
