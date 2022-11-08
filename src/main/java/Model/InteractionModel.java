package Model;

import Interface.IModelListener;
import Interface.SMModelListener;

import java.util.ArrayList;

public class InteractionModel {

    SMStateNode selection = null;
    SMTransitionLink selectionLink = null;
    int selectedButtonIndex = 0;
    private ArrayList<IModelListener> subscribers = new ArrayList<>();
    ArrayList<SMModelListener> smSubscribers = new ArrayList<>();
    ArrayList<SMModelListener> smLinkSubscribers = new ArrayList<>();

    public InteractionModel() {
    }

    public void setSelectedButton(Integer buttonNum) {
        this.selectedButtonIndex = buttonNum;
        this.notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    private void notifySmSubscribers() {
        smSubscribers.forEach(s -> s.modelChanged());
    }

    public void addSubscriber(IModelListener sub) { // for version 2
        subscribers.add(sub);
    }

    public void addSMSubscriber(SMModelListener sub){
        smSubscribers.add(sub);
    }

    public int getSelectedButtonIndex() {
        return selectedButtonIndex;
    }

    public void setSelection(SMStateNode node) {
        selection = node;
        notifySmSubscribers();
    }

    public void setSelectionLink(SMTransitionLink link){
        selectionLink = link;
        notifySmSubscribers();
    }

    public SMTransitionLink getSelectionLink() {
        return selectionLink;
    }

    public SMStateNode getSelection() {
        return this.selection;
    }

    public void unselect() {
        selection = null;
        notifySmSubscribers();
    }

    public void deselectNode() {
        if (selection != null) {
            selection = null;
            notifySmSubscribers();
        }
    }

    public void deSelectEventBox() {
        if (selectionLink != null) {
            selectionLink = null;
            notifySmSubscribers();
        }
    }
}
