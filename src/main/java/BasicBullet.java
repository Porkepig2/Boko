import java.awt.*;

public class BasicBullet {

    double x;
    double y;
    long death;
    int speed;
    double trajectory; //  angle in radians on which direction the bullet is shooting
    boolean friendly;
    boolean track;
    Dimension hitbox;
    Image image;

    public void MoveBullet(double pX, double pY, Dimension pH, GamePanel g) {



        if (track && (!friendly || g.basicEnemyMap.values().toArray().length > 0)) {

            double movementX = 0;
            double movementY = 0;

            double posX = (pX+(double)pH.width/2);
            double posY = (pY+(double)pH.height/2);
            double distanceBetween = Math.abs((pX-(x))) + Math.abs(pY-(y));

            movementX = ((posX-x) / distanceBetween);
            movementY = ((posY-y) / distanceBetween);

            trajectory = Math.atan2(movementY, movementX);

            x = x + (speed * (Math.cos(trajectory)));
            y = y + (speed * (Math.sin(trajectory)));
        } else if (track && friendly && g.basicEnemyMap.values().toArray().length == 0) {
            x = x + (speed * (Math.cos(trajectory)));
            y = y + (speed * (Math.sin(trajectory)));
        } else {
            x = x + (speed * (Math.cos(trajectory)));
            y = y + -(speed * (Math.sin(trajectory)));
        }
    }
}