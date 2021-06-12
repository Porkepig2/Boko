/*
This class is created for every enemy on screen.
 */

import java.awt.*;
import java.util.Map;

public class BasicEnemy {

    double x;   // x position
    double y;   // y position
    double rotationSpawn;  // helps determine where enemy will spawn
    int distanceSpawn;  // helps determine where enemy will spawn
    int health; // health
    int totalHealth;    // total health (constant, for healthbar)
    boolean dead;  // if enemy gets shot (adding health in future)
    String name;   // enemy name
    Dimension hitbox;   // hitbox
    Image image;        // image of enemy
    double movementDirection = 4;
    double cosCounter = 0;
    public long tickWhenCreated = 0;

    public BasicEnemy(double rotationSpawn, int distanceSpawn, int health, boolean dead, String name, Dimension hitbox, Image image, GamePanel g) {
        this.rotationSpawn = Math.toRadians(rotationSpawn);
        this.distanceSpawn = distanceSpawn;
        this.health = health;
        this.totalHealth = health;
        this.dead = dead;
        this.name = name;
        this.hitbox = hitbox;
        this.image = image;
        testingStuff(g);
    }

    public void testingStuff(GamePanel g) {

        // I want to add variables that will let me simply spawn an enemy as well as face it towards the player/midpoint
        //
        // todo: variable to spawn enemy around player that goes from 0-360     DONE
        //       variable to choose distance from player                        DONE
        //

        int sX = GamePanel.SCREEN_WIDTH /2;    // for points enemy rotates around
        int sY = GamePanel.SCREEN_HEIGHT;


        x = ((distanceSpawn) * (Math.cos(rotationSpawn)) - (0) * (Math.sin(rotationSpawn)) + sX - (float)hitbox.width/2);
        y = ((0) * -(Math.cos(rotationSpawn)) + (distanceSpawn) * -(Math.sin(rotationSpawn)) + sY - (float)hitbox.height/2);

       // System.out.println("x: "+x+"   y: "+y);

    }

    public Map<Integer, BasicBullet> swooper(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this


        if (y < 90 && x * 2 > GamePanel.SCREEN_WIDTH) {
            y = y + 1.6 * (Math.cos(cosCounter));
            cosCounter += 0.01;
        } else if (y < 90 && x * 2 < GamePanel.SCREEN_WIDTH) {
            y = y + 1.6 * (Math.cos(cosCounter));
            cosCounter += 0.01;
        }

        if (x*2 > GamePanel.SCREEN_WIDTH) {
            if (tick - tickWhenCreated == 200) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,300, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 210) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,290, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 220) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,280, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 230) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,270, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 240) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,260, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 250) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,250, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 260) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 10,240, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
                cosCounter = 0;
            }
        } else if (x*2 < GamePanel.SCREEN_WIDTH) {
            if (tick - tickWhenCreated == 200) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 240, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 210) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 250, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 220) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 260, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 230) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 270, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 240) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 280, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 250) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 290, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 260) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8,10, 300, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
                cosCounter = 0;
            }
        }

        if (tick - tickWhenCreated > 400) {
            y = y + cosCounter;
            if (cosCounter < 5) {
                cosCounter += 0.05;
            }
        }

        if (tick - tickWhenCreated > 900) {
            dead = true;
        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> basic(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().basicBullet(this.x + (float) (this.hitbox.width / 2), this.y + (float) (this.hitbox.height / 2), 4000, 8, 10, Math.toDegrees(this.rotationSpawn) + 180, Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"), g);
        }

        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> track(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().trackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6,10, 400, Toolkit.getDefaultToolkit().getImage("images/trackBullet.jpg"), g);

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> boss(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        x = x + movementDirection;
        y = y + 5*Math.cos(cosCounter);

        if (x < 100) {
            movementDirection = 4;
        } else if (x > 1800) {
            movementDirection = -4;
        }

        if (tick % 600 == 0) {
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 0,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 30,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 60,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 90,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 120,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 150,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 180,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 210,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 240,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 270,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 300,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 10, 330,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        } else if (tick % 20 == 0) {
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5,10, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        }
        if (tick % 400 == 0) {
            new BasicBullet().trackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, 10,10, 600, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        }

        cosCounter+= .2;

        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> wall(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        y+=1;

        return basicBulletMap;  // returns map, as bullet might of been added
    }

    }
