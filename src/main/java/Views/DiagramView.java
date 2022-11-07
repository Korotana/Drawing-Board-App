package Views;

import Controller.AppController;
import Interface.IModelListener;
import Interface.SMModelListener;
import Model.InteractionModel;
import Model.SMModel;
import Model.SMStateNode;
import Model.SMTransitionLink;
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
//    double startX, startY;
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
        myCanvas.setOnMousePressed(e -> {
            controller.handlePressed(e.getX()/width,e.getY()/height,e);}
        );
        myCanvas.setOnMouseDragged(e -> {
            controller.handleDragged(e.getX()/width,e.getY()/height);}
        );
        myCanvas.setOnMouseReleased(e -> {
            controller.handleReleased(e.getX()/width,e.getY()/height,e);
        });
        state.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER))controller.handleStateUpdate(state.getText());});
        update.setOnMousePressed(e -> controller.handleUpdatePressed(stateEvent.getText()));
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
    }

    public void draw() {

        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, width, height);
        gc.setLineWidth(3.0);
        gc.stroke();

        for (SMTransitionLink link: model.links) {
            gc.strokeLine(link.startX * width,link.startY*height,link.left*width,link.top*height);
            gc.strokeLine(link.left*width,link.top*height,link.endX*width,link.endY*height);
//            if (link.startX > link.left && model.lineX == link.startX){
//                gc.setStroke(Color.RED);
//                gc.strokeLine((link.left) * width,(link.top)*height,(link.left*width)+40,(link.top*height)+30);
//                gc.strokeLine((link.left) * width,(link.top)*height,(link.left*width)+20,(link.top*height)-30);
//                gc.strokeLine((link.startX * width)*2,(link.startY*height)*2,((link.startX * width)*2)-40,((link.startY*height)*2)-40);
//            }else {
//                gc.strokeLine((link.startX * width)*2,(link.startY*height)*2,((link.startX * width)/2)+40,((link.startY*height)/2)+40);
//                gc.strokeLine((link.startX * width)*2,(link.startY*height)*2,((link.startX * width))/2-40,((link.startY*height)/2)-40);
//            }
            gc.setFill(Color.ALICEBLUE);
            gc.setStroke(Color.YELLOW);
            gc.strokeRect(link.left*width-80,link.top*height-80,link.width*width,link.height*height);
            gc.fillRect(link.left*width-80,link.top*height-80,link.width*width,link.height*height);

            fillEventDetails(link);

//            System.out.println(link.startX+ "InView" + link.endX);
        }

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
        }
    }

    private void fillEventDetails(SMTransitionLink link) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(link.eventLabel,link.left*width-65,link.top*height-65);
        gc.fillText(link.contextLabel,link.left*width-65,link.top*height-20);
        gc.fillText(link.effectsLabel,link.left*width-65,link.top*height+20);

        gc.fillText(link.event,link.left*width-65,link.top*height-55);
        gc.fillText(link.context,link.left*width-65,link.top*height-10);
        gc.fillText(link.sideEffects,link.left*width-65,link.top*height+30);
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
