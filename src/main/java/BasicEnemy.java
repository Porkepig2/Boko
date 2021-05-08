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

    public Map<Integer, BasicBullet> basic(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().addBasicBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000,  Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"), g);

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> track(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            new BasicBullet().addTrackBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, true, Toolkit.getDefaultToolkit().getImage("images/trackBullet.jpg"), g);

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }
}
