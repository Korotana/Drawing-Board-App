package Model;

import javafx.scene.control.Label;

public class SMTransitionLink {

    public double startX, startY, endX, endY;
    public double left, top, width, height;
    public Label eventLabel, contextLabel, effectsLabel;
    public String sideEffects, context, event = "No Event";

    public SMTransitionLink( double newStartX, double newStartY, double newendX, double newendY) {
            eventLabel = new Label(event);
            contextLabel = new Label(context);
            effectsLabel = new Label(sideEffects);
            startX = newStartX;
            startY = newStartY;
            endX = newendX;
            endY = newendY;
        System.out.println("strtx"+newStartX);
        System.out.println("endx"+newendX);

        left = (newStartX+newendX)/2;
            top = (newStartY+newendY)/2;
            width = 0.3;
            height = 0.25;
        }

        public boolean checkHit(double x, double y) {
            return x >= left && x <= left+width && y >= top && y <= top+height;
        }

        public void moveLine(double dX, double dY) {
//            if (endY-dY)
        }
    }



