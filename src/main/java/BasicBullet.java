/*
This class is created for every bullet on screen.
 */

import java.awt.*;

public class BasicBullet {

    double x;   // x position
    double y;   // y position
    long death = 10000; // if bullet dies?
    int speed = 5;  // speed
    int damage = 5;
    double generalCounter;  // counts to change nature of some bullets
    double period = .2;
    double amplitude = 1;
    boolean cos = false;
    boolean pulse = false;
    double trajectory = Math.toRadians(270); //  angle in radians on which direction the bullet is shooting
    double curve = 0; // curves trajectory (in 1/10 degrees: 10 = 1degree, -450 = -45 degrees
    boolean friendly = false;   // if the bullet is friendly to us or not (ex, our bullets)
    boolean track = false;  // if the bullet tracks a target
    Dimension hitbox = new Dimension (24,24);   // hitbox of bullet
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
        } else if (cos){

            x = x + (speed * ((Math.cos(trajectory)+(amplitude*Math.cos(generalCounter)))));
            y = y + -(speed * (Math.sin(trajectory)));
            generalCounter+= period;

        } else if (pulse) {

            x = x + (speed * (Math.cos(trajectory)));
            y = y + -(speed * (Math.sin(trajectory)+(amplitude*Math.cos(generalCounter))));
            generalCounter+= period;

        } else {
            x = x + (speed * (Math.cos(trajectory)));
            y = y + -(speed * (Math.sin(trajectory)));
        }
    }

    public void addFriendlyBasicBullet(double x, double y, long lifespan, int speed, double trajectory, boolean pulse, boolean friendly, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = x;
        b.y = y;

        long current = System.currentTimeMillis();

        b.death = current + lifespan;
        b.speed = speed;
        b.pulse = pulse;
        b.trajectory = Math.toRadians(trajectory);
        b.friendly = friendly;
        b.hitbox = hitbox;
        b.image = image;

        g.addBulletToMap(b);

    }

    public void addFriendlyTrackBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, boolean friendly, boolean track, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = x;
        b.y = y;

        long current = System.currentTimeMillis();

        b.death = current + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.friendly = friendly;
        b.track = track;
        b.hitbox = hitbox;
        b.image = image;

        g.addBulletToMap(b);

    }

    public void addBasicBullet(double x, double y, long lifespan, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = x;
        b.y = y;

        long current = System.currentTimeMillis();

        b.death = current + lifespan;
        b.hitbox = hitbox;
        b.image = image;

        g.addBulletToMap(b);

    }

    public void addTrackBullet(double x, double y, long lifespan, int speed, boolean track, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = x;
        b.y = y;

        long current = System.currentTimeMillis();

        b.death = current + lifespan;
        b.speed = speed;
        b.track = track;
        b.hitbox = hitbox;
        b.image = image;

        g.addBulletToMap(b);

    }
}
