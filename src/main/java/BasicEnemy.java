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

    public Map<Integer, BasicBullet> basic(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 8, 270,  Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"), g);

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> track(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().trackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, 400,true, Toolkit.getDefaultToolkit().getImage("images/trackBullet.jpg"), g);

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> boss(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        x = x + movementDirection;
        y = y + Math.cos(cosCounter);

        if (x < 100) {
            movementDirection = 4;
        } else if (x > 1800) {
            movementDirection = -4;
        }

        if (tick % 600 == 0) {
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 0,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 30,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 60,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 90,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 120,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 150,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 180,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 210,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 240,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 270,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 300,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().pulseBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 8000, 6, 330,2,.2,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        } else if (tick % 20 == 0) {
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
            new BasicBullet().basicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, (int)(Math.random()*10)+5, (Math.random() * 1900), Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        }
        if (tick % 400 == 0) {
            new BasicBullet().trackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 5000, 10, 600,true, Toolkit.getDefaultToolkit().getImage("images/enemyBullet.jpg"), g);
        }

        cosCounter+= .2;

        return basicBulletMap;  // returns map, as bullet might of been added
    }
}
