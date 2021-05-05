/*
This class is created for every bullet on screen.
 */

import java.awt.*;

public class BasicBullet {

    double x;   // x position
    double y;   // y position
    long death; // if bullet dies?
    int speed;  // speed
    double trajectory; //  angle in radians on which direction the bullet is shooting
    double curve; // curves trajectory (in 1/10 degrees: 10 = 1degree, -450 = -45 degrees
    boolean friendly;   // if the bullet is friendly to us or not (ex, our bullets)
    boolean track;  // if the bullet tracks a target
    Dimension hitbox;   // hitbox of bullet
    Image image;    // bullet image

    public void MoveBullet(double pX, double pY, Dimension pH, GamePanel g) {   // moves the bullet


        trajectory += curve;

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
