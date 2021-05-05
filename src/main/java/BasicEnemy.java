import java.awt.*;
import java.util.Map;

public class BasicEnemy {

    double x;
    double y;
    boolean dead;
    String name;
    Dimension hitbox;
    Image image;

    public Map<Integer, BasicBullet> basic(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {

        if (tick % 120 == 0) {

            g.makeBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, 270, false, false, new Dimension(24, 24), Toolkit.getDefaultToolkit().getImage("images/basicBullet.jpg"));

        }
        return basicBulletMap;
    }

    public Map<Integer, BasicBullet> track(long tick, Map<Integer, BasicBullet> basicBulletMap, GamePanel g) {

        if (tick % 120 == 0) {

            g.makeBullet(this.x+ (this.hitbox.width/2.5), this.y+(this.hitbox.height/1.4), 4000, 6, 270, false, true, new Dimension(24, 24), Toolkit.getDefaultToolkit().getImage("images/trackBullet.jpg"));

        }
        return basicBulletMap;
    }
}
