package Model;

public class SMTransitionLink {

    public double left, top, width, height;
    public String sideEffects, Context, event = "No Event";

    public SMTransitionLink(double newLeft, double newTop, double newWidth, double newHeight) {
           left = newLeft;
            top = newTop;
            width = newWidth;
            height = newHeight;
        }

        public boolean checkHit(double x, double y) {
            return x >= left && x <= left+width && y >= top && y <= top+height;
        }

        public void move(double dX, double dY) {
            left += dX;
            top += dY;
        }
    }



