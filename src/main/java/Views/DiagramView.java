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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DiagramView extends StackPane implements SMModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    double width, height;
    double worldwidth, worldheight;
    SMModel model;
    private InteractionModel iModel;
    double nodeLeft, nodeTop, nodeWidth, nodeHeight;

    double opacity = 0.5;
    HBox Box = new HBox();
    VBox stateBox = new VBox();
    NodePropertiesView nodePropertiesView = new NodePropertiesView();
    LinkPropertiesView linkPropertiesView = new LinkPropertiesView();

    Button update = new Button("Update");

    public DiagramView(double newWorldwidth, double newWorldheight,double w, double h) {
        width = w;
        height = h;
        worldwidth = newWorldwidth;
        worldheight = newWorldheight;
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(171,255,247));
        gc.fillRect(0,0,width,height);
        linkPropertiesView.stateSideEffects.setPrefHeight(200);
        linkPropertiesView.stateContext.setPrefHeight(200);
        Label stateLabel = new Label("Event:");
        stateBox.getChildren().addAll(stateLabel,nodePropertiesView.state);
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
        nodePropertiesView.state.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER))controller.handleStateUpdate(nodePropertiesView.state.getText());});
        update.setOnMousePressed(e -> controller.handleUpdatePressed(linkPropertiesView.stateEvent.getText(), linkPropertiesView.stateContext.getText(), linkPropertiesView.stateSideEffects.getText()));
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
        iModel.setViewExtents(myCanvas.getWidth()/worldwidth, myCanvas.getHeight()/worldheight);
        iModel.setWorldExtents(worldwidth,worldheight);
    }

    public void draw() {

        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, width, height);
        gc.setFill(Color.rgb(171,255,247,opacity));
        gc.fillRect(0,0,width,height);
        gc.setLineWidth(3.0);
        gc.stroke();
        System.out.println("dv---"+model.links);
        for (SMTransitionLink link: model.links) {

//            updateLinkBounds(link);
            gc.strokeLine(link.startX * width,link.startY*height,link.left*width,link.top*height);
            gc.strokeLine(link.left*width,link.top*height,link.endX*width,link.endY*height);

//            if (link.startX < link.left) {
//                if (link.startY < link.top){
//                    double arrowStartX = link.startX+(link.left-link.startX)/3;
//                    double arrowStartY = (link.startY+(link.top-link.startY)/3);
//                    gc.strokeLine(arrowStartX*width, arrowStartY*height,
//                            arrowStartX*width-(width*0.05), arrowStartY*height-(height*0.05));
//
//                    gc.strokeLine(arrowStartX*width, arrowStartY*height,
//                            arrowStartX*width+(width*0.05), arrowStartY*height-(height*0.05));
//
//                }else {
//                    double arrowStartX = link.startX-(link.left-link.startX)/3;
//                    double arrowStartY = (link.startY-(link.startY-link.top)/3);
//                    gc.strokeLine(arrowStartX*width, arrowStartY*height,
//                            arrowStartX*width-(width*0.05), (arrowStartY*height)+(height*0.05));
//
//                    gc.strokeLine(arrowStartX*width, arrowStartY*height,
//                            arrowStartX*width+(width*0.05), (arrowStartY*height)+(height*0.05));
//
//                }

//                gc.strokeLine(
//                        (link.left+link.startX)/2*width,
//                        ((link.top+link.startY)/2*height),
//                        (link.left+link.startX)/2*width-(width*0.05),
//                        ((link.startY+link.top)/2*height)+(height*0.05));
//
//                gc.strokeLine(
//                        (link.left+link.endX)/2*width,
//                        ((link.top+link.endY)/2*height),
//                        (link.left+link.endX)/2*width-(width*0.05),
//                        ((link.endY+link.top)/2*height)-(height*0.05));
//
//                gc.strokeLine(
//                        (link.left+link.endX)/2*width,
//                        ((link.top+link.endY)/2*height),
//                        (link.left+link.endX)/2*width-(width*0.05),
//                        ((link.endY+link.top)/2*height)+(height*0.05));
//            }


//            if (link.startX > link.left) {gc.strokeLine(
//                    (link.left+link.startX)/2*width,
//                    ((link.top+link.startY)/2*height),
//                    (link.left+link.startX)/2*width+(width*0.05),
//                    ((link.startY+link.top)/2*height)+(height*0.05));
//
//                gc.strokeLine(
//                        (link.left+link.startX)/2*width,
//                        ((link.top+link.startY)/2*height),
//                        (link.left+link.startX)/2*width-(width*0.05),
//                        ((link.startY+link.top)/2*height)+(height*0.05));
//
//                gc.strokeLine(
//                        (link.left+link.endX)/2*width,
//                        ((link.top+link.endY)/2*height),
//                        (link.left+link.endX)/2*width+(width*0.05),
//                        ((link.endY+link.top)/2*height)+(height*0.05));
//
//                gc.strokeLine(
//                        (link.left+link.endX)/2*width,
//                        ((link.top+link.endY)/2*height),
//                        (link.left+link.endX)/2*width-(width*0.05),
//                        ((link.endY+link.top)/2*height)+(height*0.05));
//            }




//            gc.strokeLine((link.left+link.endX)/2*width,((link.top+link.endY)/2*height),(link.left+link.endX)/2*width+40,((link.endY+link.top)/2*height)-40);
//            gc.strokeLine((link.left+link.endX)/2*width,((link.top+link.endY)/2*height),(link.left+link.endX)/2*width-40,((link.endY+link.top)/2*height)+40);
//            gc.strokeLine((link.left+link.startX)/2*width,((link.top+link.startY)/2*height),(link.left+link.startX)/2*width+30,((link.endY+link.startY)/2*height)-30);

            if (link == iModel.getSelectionLink()) {
                gc.setFill(Color.CYAN);
                gc.setStroke(Color.BLACK);
            } else {
                gc.setFill(Color.ALICEBLUE);
                gc.setStroke(Color.YELLOW);
            }

            gc.strokeRect(link.left*width-(0.1 * width),link.top*height-(0.1 * height),link.width*width,link.height*height);
            gc.fillRect(link.left*width-(0.1 * width),link.top*height-(0.1 * height),link.width*width,link.height*height);

            fillEventDetails(link);

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
            gc.fillText(node.state,(node.left+(node.width/2)) * width,(node.top+(node.height/2)) * height);
//            gc.setFill(Color.ORANGE);
        }
    }

    private void updateLinkBounds(SMTransitionLink link) {
//        link.startX = (link.startX - iModel.viewLeft) * width;
//        link.startY = (link.startY - iModel.viewTop) * height;
//        link.endX = (link.endX - iModel.viewLeft) * width;
//        link.endY = (link.endY - iModel.viewTop) * height;
        System.out.println("this");
    }

    private void fillEventDetails(SMTransitionLink link) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(link.eventLabel,link.left*width-(0.08 * width),link.top*height-(0.08 * height));
        gc.fillText(link.contextLabel,link.left*width-(0.08 * width),link.top*height-(0.03 * height));
        gc.fillText(link.effectsLabel,link.left*width-(0.08 * width),link.top*height+(0.03 * height));

        gc.fillText(link.event,link.left*width-(0.08 * width),link.top*height-(0.06 * height));
        gc.fillText(link.context,link.left*width-(0.08 * width),link.top*height-(0.01 * height));
        gc.fillText(link.sideEffects,link.left*width-(0.08 * width),link.top*height+(0.05 * height));
    }

    @Override
    public void modelChanged() {
//        iModel
        draw();
    }

    public void setSmModel(SMModel smModel) {
        model = smModel;
    }

    @Override
    public void iModelChanged() {
        draw();
        updateToolPalleteChange();
    }

    private void updateToolPalleteChange() {
        stateBox.getChildren().clear();
        if (iModel.getSelectedButtonIndex() == 2){
            stateBox.getChildren().addAll(linkPropertiesView.eventLabel,linkPropertiesView.stateEvent,linkPropertiesView.contextLabel,linkPropertiesView.stateSideEffects,linkPropertiesView.effectsLabel,linkPropertiesView.stateContext,update);}
        else {
            stateBox.getChildren().addAll(nodePropertiesView.stateLabel,nodePropertiesView.state);
        }

    }
}


//            if (link.startX > link.left && model.lineX == link.startX){
//                gc.setStroke(Color.RED);
//                gc.strokeLine((link.left) * width,(link.top)*height,(link.left*width)+40,(link.top*height)+30);
//                gc.strokeLine((link.left) * width,(link.top)*height,(link.left*width)+20,(link.top*height)-30);
//                gc.strokeLine((link.startX * width)*2,(link.startY*height)*2,((link.startX * width)*2)-40,((link.startY*height)*2)-40);
//            }else {
//                gc.strokeLine((link.startX * width)*2,(link.startY*height)*2,((link.startX * width)/2)+40,((link.startY*height)/2)+40);
//                gc.strokeLine((link.startX * width)*2,(link.startY*height)*2,((link.startX * width))/2-40,((link.startY*height)/2)-40);
//            }