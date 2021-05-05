/*
This class is created for every enemy on screen.
 */

import java.awt.*;
import java.util.Map;

public class BasicEnemy {

    double x;   // x position
    double y;   // y position
    boolean dead;  // if enemy gets shot (adding health in future)
    String name;   // enemy name
    Dimension hitbox;   // hitbox
    Image image;        // image of enemy

    public Map<Integer, BasicBullet> basic(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            g.makeBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, 270, false, false, new Dimension(24, 24), Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"));

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }

    public Map<Integer, BasicBullet> track(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {  // for enemy named this

        if (tick % 120 == 0) {

            g.makeBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, 270, false, true, new Dimension(24, 24), Toolkit.getDefaultToolkit().getImage("images/trackBullet.jpg"));

        }
        return basicBulletMap;  // returns map, as bullet might of been added
    }
}
