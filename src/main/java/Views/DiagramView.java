package Views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class DiagramView extends StackPane {
    GraphicsContext gc;
    Canvas myCanvas;
    //    ArrayList<XLine> lines;
    double mouseX, mouseY;

    public DiagramView() {


        myCanvas = new Canvas(600,600);
        gc = myCanvas.getGraphicsContext2D();

        this.getChildren().addAll(myCanvas);
    }
}
