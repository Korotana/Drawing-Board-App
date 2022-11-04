package Model;

import Interface.SMModelListener;

import java.util.ArrayList;

public class SMModel {

    public ArrayList<SMStateNode> nodes = new ArrayList<>();
    public ArrayList<SMTransitionLink> Links = new ArrayList<>();
    public double lineX, lineY;
    ArrayList<SMModelListener> subscribers = new ArrayList<>();

    public SMModel() {}

    public void createNode(double left, double top) {
        SMStateNode node = new SMStateNode(left - 0.05, top-0.05, 0.25, 0.1);
        nodes.add(node);
        notifySubscribers();
    }

    public boolean checkHit(double x, double y) {
        return nodes.stream()
                .anyMatch(b -> b.checkHit(x,y));
    }

    public SMStateNode whichBox(double x, double y) {
        SMStateNode found = null;
        for (SMStateNode node : nodes) {
            if (node.checkHit(x,y)) {
                found = node;
            }
        }
        return found;
    }

    public void moveBox(SMStateNode node, double dX, double dY) {
        node.move(dX,dY);
        notifySubscribers();
    }

    public void addSubscriber (SMModelListener aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.modelChanged());
    }


    public void updateState(String state, SMStateNode selection) {
        selection.state = state;
        notifySubscribers();
    }

    public void createLink(double dx, double dy){
        lineX = dx;
        lineY = dy;
        notifySubscribers();
    }

//    public void updateEvents(String state, ) {
//
//    }
}
