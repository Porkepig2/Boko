/*
This class is created for every enemy on screen.
 */

import java.awt.*;
import java.util.Map;

public class BasicEnemy {

    double x;   // x position
    double y;   // y position
    int health; // health
    int totalHealth;    // total health (constant, for healthbar)
    boolean dead;  // if enemy gets shot (adding health in future)
    String name;   // enemy name
    Dimension hitbox;   // hitbox
    Image image;        // image of enemy
    double movementDirection = 4;
    double cosCounter = 0;
    public long tickWhenCreated = 0;

    public BasicEnemy(double x, double y, int health, boolean dead, String name, Dimension hitbox, Image image) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.totalHealth = health;
        this.dead = dead;
        this.name = name;
        this.hitbox = hitbox;
        this.image = image;
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
            if (tick - tickWhenCreated == 400) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 60, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 410) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 70, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 420) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 80, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 430) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 90, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 440) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 100, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 450) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 110, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 460) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 5, 120, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
                cosCounter = 0;
            }
        } else if (x*2 < GamePanel.SCREEN_WIDTH) {
            if (tick - tickWhenCreated == 400) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 120, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 410) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 110, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 420) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 100, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 430) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 90, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 440) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 80, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 450) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 70, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            } else if (tick - tickWhenCreated == 460) {
                new BasicBullet().basicBullet(this.x + (this.hitbox.width / 2.5), this.y + (this.hitbox.height / 1.4), 4000, 8, 60, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
                cosCounter = 0;
            }
        }

        if (tick - tickWhenCreated > 800) {
            y = y + cosCounter;
            if (cosCounter < 5) {
                cosCounter += 0.05;
            }
        }

        if (tick - tickWhenCreated > 1100) {
            dead = true;
        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> basic(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 8, 90,  Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"), g);

        } else if (tick % 10 == 0) {
            new BasicBullet().slowdownBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 8, 90, 0,0,0.01,Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"),g);
        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> track(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().trackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, 400, Toolkit.getDefaultToolkit().getImage("images/trackBullet.jpg"), g);

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
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 0,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 30,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 60,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 90,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 120,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 150,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 180,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 210,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 240,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 270,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 300,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 330,2,.2, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        } else if (tick % 20 == 0) {
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        }
        if (tick % 400 == 0) {
            new BasicBullet().trackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, 10, 600, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        }

        cosCounter+= .2;

        return basicBulletMap;  // returns map, as bullet might of been added
    }
}
