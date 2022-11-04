package Controller;

import Model.InteractionModel;
import Model.SMModel;
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
                    iModel.setSelection(smModel.whichBox(normX, normY));
                    // move to new state
                    currentState = State.DRAGGING;
                } else {
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
        prevX = normX;
        prevY = normY;

        switch (currentState) {
            case DRAGGING -> {
                if (iModel.getSelectedButtonIndex() == 0){
                // context: none
                // side effects:
                // - move box
                smModel.moveBox(iModel.getSelection(), dX, dY);}
                else{
                    smModel.createLink(dX,dY);
                }
                // stay in this state
            }
            case PREPARE_CREATE -> {
                // context: none
                // side effects:
                // - none
                // move to new state
                currentState = State.READY;
            }
        }
    }

    public void handleReleased(double normX, double normY, MouseEvent event) {
        switch (currentState) {
            case DRAGGING -> {
                // context: none
                // side effects:
                // - set to no selection
//                iModel.unselect();
                // move to new state
                currentState = State.READY;
            }
            case PREPARE_CREATE -> {
                // context: none
                // side effects
                // - create box
                smModel.createNode(normX,normY);
                iModel.setSelection(smModel.whichBox(normX, normY));
                // move to new state
                currentState = State.READY;
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
