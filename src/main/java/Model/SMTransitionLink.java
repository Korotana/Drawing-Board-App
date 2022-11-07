package Model;

import javafx.scene.control.Label;

public class SMTransitionLink {

    public double startX, startY, endX, endY;
    public double left, top, width, height;
    public String eventLabel, contextLabel, effectsLabel;
    public String sideEffects = "No SideEffects", context = "No Context", event = "No Event";

    public SMTransitionLink( double newStartX, double newStartY, double newendX, double newendY) {
            eventLabel = "Event";
            contextLabel = "Context";
            effectsLabel = "SideEffects";
            startX = newStartX;
            startY = newStartY;
            endX = newendX;
            endY = newendY;
//        System.out.println("strtx"+newStartX);
//        System.out.println("endx"+newendX);

        left = (newStartX+newendX)/2;
            top = (newStartY+newendY)/2;
            width = 0.3;
            height = 0.25;
        }

        public boolean checkHit(double x, double y) {
            return x >= left && x <= left+width && y >= top && y <= top+height;
        }

        public void moveLineEnd(double dX, double dY) {
            endX += dX;
            endY += dY;
//            if (endY-dY)
        }

    public void moveLineStart(double dX, double dY) {
            startX += dX;
            startY += dY;
    }

}



