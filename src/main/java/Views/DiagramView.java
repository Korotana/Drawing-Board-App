package Views;

import Controller.AppController;
import Interface.IModelListener;
import Interface.SMModelListener;
import Model.InteractionModel;
import Model.SMModel;
import Model.SMStateNode;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DiagramView extends StackPane implements SMModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    double width, height;
    SMModel model;
    private InteractionModel iModel;
    double nodeLeft, nodeTop, nodeWidth, nodeHeight;

    HBox Box = new HBox();
    VBox stateBox = new VBox();
    TextField state = new TextField();
    TextField stateEvent = new TextField();
    TextField stateSideEffects = new TextField();
    TextField stateContext = new TextField();
    Button update = new Button("Update");

    public DiagramView(double w, double h) {
        width = w;
        height = h;
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        stateSideEffects.setPrefHeight(200);
        stateContext.setPrefHeight(200);
        Label stateLabel = new Label("Event:");
        stateBox.getChildren().addAll(stateLabel,state);
        stateBox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
        Box.getChildren().addAll(myCanvas,stateBox);
        this.getChildren().addAll(Box);
    }

    public void setController(AppController controller) {
        // set up event handling - normalize coordinates
        myCanvas.setOnMousePressed(e -> controller.handlePressed(e.getX()/width,e.getY()/height,e));
        myCanvas.setOnMouseDragged(e -> controller.handleDragged(e.getX()/width,e.getY()/height));
        myCanvas.setOnMouseReleased(e -> controller.handleReleased(e.getX()/width,e.getY()/height,e));
        state.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER)){
                controller.handleStateUpdate(state.getText());
            }
        });
        update.setOnMousePressed(e -> controller.handleUpdatePressed(stateEvent.getText()));
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void draw() {
        if (iModel.getSelectedButtonIndex() == 2){
            gc.setLineWidth(2.0);
            gc.moveTo(model.lineX, model.lineY);
            gc.lineTo(model.lineX, model.lineY);
            gc.stroke();
        }
        else {
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, width, height);
        for (SMStateNode node : model.nodes) {
            if (node == iModel.getSelection()) {
                gc.setFill(Color.TEAL);
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(Color.BLACK);
                gc.setFill(Color.ORANGE);
            }
            nodeLeft = node.left * width;
            nodeTop = node.top * height;
            nodeWidth = node.width * width;
            nodeHeight = node.height * height;
            gc.fillRect(nodeLeft, nodeTop, nodeWidth, nodeHeight);
            gc.strokeRect(nodeLeft, nodeTop, nodeWidth, nodeHeight);
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.BLACK);
            gc.fillText(node.state,node.left * width + 80,node.top * height + 40);
//            gc.setFill(Color.ORANGE);
        }}
    }

    @Override
    public void modelChanged() {
        draw();
    }

    public void setSmModel(SMModel smModel) {
        model = smModel;
    }

    @Override
    public void iModelChanged() {
        updateToolPalleteChange();
    }

    private void updateToolPalleteChange() {
        stateBox.getChildren().clear();
        if (iModel.getSelectedButtonIndex() == 2){
            Label eventLabel = new Label("Event:");
            Label contextLabel = new Label("Context:");
            Label effectsLabel = new Label("SideEffects:");
            stateBox.getChildren().addAll(eventLabel,stateEvent,contextLabel,stateSideEffects,effectsLabel,stateContext,update);}
        else {
            Label stateLabel = new Label("State:");
            stateBox.getChildren().addAll(stateLabel,state);
        }

    }
}
