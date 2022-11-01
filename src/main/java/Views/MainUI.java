package Views;

import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class MainUI extends StackPane {

    public MainUI() throws FileNotFoundException {


        ToolPalette toolPalette = new ToolPalette();

        System.out.println("Mian\n\n");
        this.getChildren().addAll(toolPalette);


    }
}
