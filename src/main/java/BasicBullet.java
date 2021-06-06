/*
This class is created for every bullet on screen.
 */

import java.awt.*;

public class BasicBullet {

    double x;   // x position
    double y;   // y position
    double startingX;
    double startingY;
    double hX;  // hidden x
    double hY;  // hidden y
    long death = 10000; // if bullet dies?
    double speed = 5;  // speed
    int damage = 5;
    int trackCutoff = 0;
    double generalCounter;  // counts to change nature of some bullets
    double period = .2;
    double amplitude = 1;
    int shiftToTrack;   // time until the bullet converts to tracking;
    double slowdownRate;    // speed at which bullet slows down (speed - this every second)
    boolean slowdown = false;
    boolean cos = false;
    boolean pulse = false;
    boolean rotationSensitive = false;
    double trajectory = Math.toRadians(90); //  angle in radians on which direction the bullet is shooting
    double curve = 0; // curves trajectory (in 1/10 degrees: 10 = 1degree, -450 = -45 degrees
    boolean friendly = false;   // if the bullet is friendly to us or not (ex, our bullets)
    boolean track = false;  // if the bullet tracks a target
    Dimension hitbox = new Dimension (24,24);   // hitbox of bullet
    Image image;    // bullet image

    // Method List:
    //
    //  FOR EACH BULLET:
    //
    // MoveBullet - updates the bullet being shot
    //
    //  FOR INITIALIZING BULLET:
    //
    // friendlyBasicBullet
    // friendlyTrackBullet
    // friendlySlowdownBullet
    // friendlyPulseBullet
    // friendlyCosBullet
    //
    // basicBullet
    // trackBullet
    // slowdownBullet
    // pulseBullet
    // cosBullet
    //
    // EverythingBullet - for developing and experimenting
    //

    public void MoveBullet(double pX, double pY, Dimension pH, GamePanel g) {   // moves the bullet


        trajectory += curve;


        if (cos) {

            hX = hX + speed;
            hY = hY + (amplitude * Math.cos(generalCounter));
            generalCounter += period;

        }
        if (pulse) {

            hX = hX + speed + (amplitude * Math.cos(generalCounter));
            generalCounter += period;

        }
        if (slowdown && !track) {

            if (speed > 0) {
                hX = hX + speed;
                speed -= slowdownRate;
            } else if (speed < 0) {
                speed = 0;
            }
            if (generalCounter >= shiftToTrack) {
                track = true;
                speed = 5;  // will switch to original speed, just too lazy to add now
            }
            generalCounter++;
        }
        if (!track && !cos && !pulse && !slowdown) {
            hX = hX + speed;
        }

        if (track && (!friendly || g.basicEnemyMap.values().toArray().length > 0)) {

            double movementX = 0;
            double movementY = 0;

            double posX = (pX + (double) pH.width / 2);
            double posY = (pY + (double) pH.height / 2);
            double distanceBetween = Math.abs((pX - (x))) + Math.abs(pY - (y));

            if (distanceBetween > trackCutoff) {
                movementX = ((posX - x) / distanceBetween);
                movementY = ((posY - y) / distanceBetween);

                trajectory = Math.atan2(movementY, movementX);
            }

            x = x + (speed * (Math.cos(trajectory)));
            y = y + (speed * (Math.sin(trajectory)));
        } else if (track && friendly && g.basicEnemyMap.values().toArray().length == 0) {
            x = x + (speed * (Math.cos(trajectory)));
            y = y + (speed * (Math.sin(trajectory)));
        }

        if (!track) {
            x = startingX + ((hX - startingX) * (Math.cos(trajectory)) - (hY - startingY) * (Math.sin(trajectory)));
            y = startingY + ((hY - startingY) * (Math.cos(trajectory)) + (hX - startingX) * (Math.sin(trajectory)));
            //http://danceswithcode.net/engineeringnotes/rotations_in_2d/rotations_in_2d.html       REFERENCE
        }
    }

    public void friendlyBasicBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.image = image;

        b.friendly = true;

        g.addBulletToMap(b);
    }

    public void friendlyTrackBullet(double x, double y, long lifespan, int speed, int damage, int trackCutoff, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trackCutoff = trackCutoff;
        b.image = image;

        b.friendly = true;
        b.track = true;
        b.rotationSensitive = true;

        g.addBulletToMap(b);
    }

    public void friendlySlowdownBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, int trackCutoff, int shiftToTrack, double slowdownRate, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.trackCutoff = trackCutoff;
        b.shiftToTrack = shiftToTrack;
        b.slowdownRate = slowdownRate/60;   // slows down this much every second (60fps)
        b.death = System.currentTimeMillis() + lifespan;
        b.image = image;

        b.slowdown = true;
        b.friendly = true;

        g.addBulletToMap(b);
    }

    public void friendlyPulseBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, double amplitude, double period, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.amplitude = amplitude;
        b.period = period;
        b.image = image;

        b.friendly = true;
        b.pulse = true;

        g.addBulletToMap(b);
    }

    public void friendlyCosBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, double amplitude, double period, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.amplitude = amplitude;
        b.period = period;
        b.image = image;

        b.friendly = true;
        b.cos = true;

        g.addBulletToMap(b);
    }

    public void basicBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.death = System.currentTimeMillis() + lifespan;
        b.image = image;

        g.addBulletToMap(b);
    }

    public void trackBullet(double x, double y, long lifespan, int speed, int damage, int trackCutoff, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(90);
        b.trackCutoff = trackCutoff;
        b.image = image;

        b.rotationSensitive = true;
        b.track = true;

        g.addBulletToMap(b);
    }

    public void slowdownBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, int trackCutoff, int shiftToTrack, double slowdownRate, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.trackCutoff = trackCutoff;
        b.shiftToTrack = shiftToTrack;
        b.slowdownRate = slowdownRate/60;   // slows down this much every second (60fps)
        b.death = System.currentTimeMillis() + lifespan;
        b.image = image;

        b.slowdown = true;

        g.addBulletToMap(b);
    }

    public void pulseBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, double amplitude, double period, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.amplitude = amplitude;
        b.period = period;
        b.image = image;

        b.pulse = true;

        g.addBulletToMap(b);
    }

    public void cosBullet(double x, double y, long lifespan, int speed, int damage, double trajectory, double amplitude, double period, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trajectory = Math.toRadians(trajectory);
        b.amplitude = amplitude;
        b.period = period;
        b.image = image;

        b.cos = true;

        g.addBulletToMap(b);
    }

    public void EverythingBullet(double x, double y, long lifespan, int speed, int damage, int trackCutoff, double period, double amplitude, double trajectory, double curve, boolean pulse, boolean cos, boolean friendly, boolean track, Dimension hitbox, Image image, GamePanel g) {

        BasicBullet b = new BasicBullet();
        b.x = b.startingX = b.hX = x;
        b.y = b.startingY = b.hY = y;
        b.period = period;
        b.amplitude = amplitude;
        b.pulse = pulse;
        b.cos = cos;
        b.trajectory = Math.toRadians(trajectory);
        b.curve = curve;
        b.death = System.currentTimeMillis() + lifespan;
        b.speed = speed;
        b.damage = damage;
        b.trackCutoff = trackCutoff;
        b.friendly = friendly;
        b.track = track;
        b.hitbox = hitbox;
        b.image = image;

        g.addBulletToMap(b);
    }
}
