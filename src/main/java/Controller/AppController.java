package Controller;

import Model.InteractionModel;
import Model.SMModel;
import Model.SMStateNode;
import javafx.scene.input.MouseEvent;

public class AppController {

    InteractionModel iModel;
    SMModel smModel;
    double prevX, prevY;

    private enum State {
        READY, PREPARE_CREATE, DRAGGING
    }

    private State currentState;

    public AppController() {
        currentState = State.READY;
    }

    public void handleButtonClick(Integer buttonNum){
        iModel.setSelectedButton(buttonNum);
    }

    public void setInteractionModel(InteractionModel newIModel) {
        this.iModel = newIModel;
    }

    public void setSmModel(SMModel newSmModel){
        this.smModel = newSmModel;
    }

    public void handlePressed(double normX, double normY, MouseEvent event) {
        prevX = normX;
        prevY = normY;

        switch (currentState) {
            case READY -> {
                // context: are we on a box?
                boolean hit = smModel.checkHit(normX, normY);
                if (hit) {
                    // side effects:
                    // set selection
//                    System.out.println("in hit");
//                    iModel.setSelectionLink(smModel.whichLink(smModel.whichBox(normX, normY)));
                    if (iModel.getSelectedButtonIndex() == 2) {
                        SMStateNode node = smModel.whichBox(normX,normY);
                        smModel.lineX = normX;
                        smModel.lineY = normY;
                        smModel.initialNode = node;
//                        smModel.createLink(prevX, prevY, normX, normY);
                        currentState = State.DRAGGING;
                    }
                    else {
                        iModel.setSelection(smModel.whichBox(normX, normY));
                    // move to new state
                        currentState = State.DRAGGING;}
                }
                else {
                    // side effects
                    // none
                    // move to new state
                        currentState = State.PREPARE_CREATE;
                }
            }
        }
    }

    public void handleDragged(double normX, double normY) {
        double dX = normX - prevX;
        double dY = normY - prevY;
//        prevX = normX;
//        prevY = normY;

        switch (currentState) {
            case DRAGGING -> {

                if (iModel.getSelectedButtonIndex() == 0){
                    smModel.moveBox(iModel.getSelection(), dX, dY);
//                    smModel.moveLink(iModel.getSelectionLink(), dX, dY);
                }
                else {
//                    smModel.lineX = prevX;
//                    smModel.lineY = prevY;
                    SMStateNode node = smModel.whichBox(normX,normY);
                    smModel.createLink(prevX, prevY, normX, normY);
                }
            }
            case PREPARE_CREATE -> {
                // context: none
                // side effects:
                // - none
                // move to new state
                currentState = State.READY;
            }
        }
        prevX = normX;
        prevY = normY;
    }

    public void handleReleased(double normX, double normY, MouseEvent event) {
        switch (currentState) {
            case DRAGGING -> {
                // context: none
                // side effects:
                // - set to no selection
//                iModel.unselect();
                // move to new state
//                System.out.println("realeas drag");
                boolean hit = smModel.checkHit(normX, normY);
                if (hit){
//                    SMStateNode node = smModel.whichBox(normX,normY);
//                    System.out.println(node.left+""+normX);
//                    smModel.createLink(node.left,node.top);
                    if (iModel.getSelectedButtonIndex() == 2){
//                        SMStateNode node = smModel.whichBox(normX,normY);
                        smModel.createLink(Double.MAX_VALUE, Double.MAX_VALUE,normX,normY);
                    }

                }
                else {
                    System.out.println("NO LINK CAN BE FORMED");
                }
                currentState = State.READY;
            }
            case PREPARE_CREATE -> {
                // context: none
                // side effects
                // - create box
//                if (iModel.getSelectedButtonIndex() == 2){
//                    smModel.createLink(Double.MAX_VALUE, Double.MAX_VALUE,normX,normY);
//                    currentState = State.READY;
//                }
//                else{
                smModel.createNode(normX,normY);
                iModel.setSelection(smModel.whichBox(normX, normY));
                // move to new state
                currentState = State.READY;
//                }
            }
        }
    }

    public void handleStateUpdate(String state) {
        smModel.updateState(state,iModel.getSelection());
    }

    public void handleUpdatePressed(String event) {
//        smModel.updateStates(event);
    }


}
