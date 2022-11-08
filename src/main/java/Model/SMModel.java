package Model;

import Interface.SMModelListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SMModel {

    public ArrayList<SMStateNode> nodes = new ArrayList<>();
    public ArrayList<SMTransitionLink> links = new ArrayList<>();
    public HashMap<SMStateNode, ArrayList<SMTransitionLink>> nodeLinksStart = new HashMap<>();
    public HashMap<SMStateNode, ArrayList<SMTransitionLink>> nodeLinksEnd = new HashMap<>();
    public HashMap<SMTransitionLink, ArrayList<SMTransitionLink>> eventLinksStart = new HashMap<>();
    public HashMap<SMTransitionLink, ArrayList<SMTransitionLink>> eventLinksEnd = new HashMap<>();
    public double lineX, lineY;
    public SMStateNode initialNode;
    public SMTransitionLink initialLink;
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

    public boolean checkEventBoxHit(double x, double y) {
        return links.stream()
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

    public SMTransitionLink whichEventBox(double x, double y) {
        SMTransitionLink found = null;
        for (SMTransitionLink link : links) {
            if (link.checkHit(x,y)) {
                found = link;
            }
        }
        return found;
    }

    public void moveBox(SMStateNode node, double dX, double dY) {
        node.move(dX,dY);

        nodeLinksStart.forEach((startNode,nodelinks) -> {
            if (startNode == node){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineStart(dX, dY);
                }
            }
        });
        nodeLinksEnd.forEach((endNode,nodelinks) -> {
            if (endNode == node){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineEnd(dX, dY);
                }
            }
        });

        notifySubscribers();
    }

    public void moveEventBox(SMTransitionLink link, double dX, double dY) {
        link.move(dX,dY);
        eventLinksStart.forEach((startlink,nodelinks) -> {
            if (startlink == link){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineStart(dX, dY);
                }
            }
        });
        eventLinksEnd.forEach((endlink,nodelinks) -> {
            if (endlink == link){
                for (SMTransitionLink nodelink: nodelinks) {
                    nodelink.moveLineEnd(dX, dY);
                }
            }
        });
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

    int linkCount = 0;
    public void createLink(double startX, double startY, double dx, double dy){
        SMTransitionLink link = new SMTransitionLink(lineX,lineY, dx, dy);
        links.add(link);
        notifySubscribers();
        if (startX == Double.MAX_VALUE){
            linkCount+=1;
            System.out.println(linkCount+ "and"+ links.size());
            SMStateNode node = whichBox(dx,dy);
            if (nodeLinksEnd.containsKey(node)){
                ArrayList<SMTransitionLink> prevlinks = nodeLinksEnd.get(node);
                prevlinks.add(links.get(linkCount-1));
                nodeLinksEnd.put(node,prevlinks);
            }else {
                ArrayList<SMTransitionLink> templink = new ArrayList<>();
                templink.add(links.get(linkCount-1));
                nodeLinksEnd.put(node,templink);
            }
            if (nodeLinksStart.containsKey(initialNode)){
                ArrayList<SMTransitionLink> prevlinks = nodeLinksStart.get(initialNode);
                prevlinks.add(links.get(linkCount-1));
                nodeLinksStart.put(initialNode,prevlinks);
            }else {
                ArrayList<SMTransitionLink> templink = new ArrayList<>();
                templink.add(links.get(linkCount-1));
                nodeLinksStart.put(initialNode,templink);
            }
        }else {
            if (links.size() >= linkCount){
                links.remove(link);
            }
        }
    }

    public void updateLinkEvent(String event, String context, String effects) {



    }

//    public void createDragLink(double prevX, double prevY, double normX, double normY) {
//        SMTransitionLink link = new SMTransitionLink(lineX,lineY, normX, normY);
//        templinks.add(link);
//        notifySubscribers();
//        templinks.remove(link);
//    }

//    public void updateEvents(String state, ) {
//
//    }
}
