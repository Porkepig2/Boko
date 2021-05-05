import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;


public class GamePanel extends JPanel implements ActionListener {

    //static
    static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height-25;
    static final int UNIT_SIZE = 50;
    static final int DELAY = 16;

    //changable stats
    int PLAYER_SPEED;

    //images
    Image menuImage;
    Image backgroundImage;
    Image characterImage;


    //sounds
    File menuMusic;
    Clip menuClip;


    //main
    int posX;
    int posY;
    Dimension playerHitbox;
    int mouseX;
    int mouseY;
    boolean mainmenu;
    boolean level1;
    Timer timer;
    Random random;
    boolean movingL;
    boolean movingR;
    boolean movingU;
    boolean movingD;
    boolean attackButton;
    boolean mousePressed;
    long levelStartTime;
    long currentTime;
    int whichBulletToSpawn;
    int levelItemSpawner;
    long tick;
    Map<Integer, BasicBullet> basicBulletMap = new HashMap<>();
    Map<Integer, BasicEnemy> basicEnemyMap = new HashMap<>();

    DecimalFormat numberFormat = new DecimalFormat("#.00");


    public void refreshStats() {

        //changable stats
        PLAYER_SPEED = 8;

        //images
        menuImage = getToolkit().getImage("images/menu.jpg");
        backgroundImage = getToolkit().getImage("images/background.jpg");
        characterImage = getToolkit().getImage("images/character.jpg");


        //sounds
        menuMusic = new File("music/menu.wav");

        //main
        posX = UNIT_SIZE*10;
        posY = UNIT_SIZE*12;
        playerHitbox = new Dimension (UNIT_SIZE, UNIT_SIZE);
        level1 = false;
        mainmenu = true;
        movingL = false;
        movingR = false;
        movingU = false;
        movingD = false;
        mousePressed = false;
        levelStartTime = 0;
        currentTime = 0;
        whichBulletToSpawn = 0;
        levelItemSpawner = 0;
        tick = 0;
    }

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MyMouseAdapter());
        startGame();
    }

    public void startGame() {

        timer = new Timer(DELAY, this);
        timer.start();

        refreshStats();
        setupMusic();

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Dirty Roma.otf")).deriveFont(120f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupMusic() {
        try {
            AudioInputStream menuStream = AudioSystem.getAudioInputStream(menuMusic);
            menuClip = AudioSystem.getClip();
            menuClip.open(menuStream);
            FloatControl gainControl = (FloatControl) menuClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            menuClip.start();
            menuClip.loop(999);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainMenu() {


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (mainmenu) {

            g.setColor(Color.WHITE);
            g.drawImage(menuImage,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,this);
            g.drawRect(30, 500, 500, 200);
            //g.setFont( new Font("Dirty Roma",Font.PLAIN, 170));
            //g.drawString("START", 120, 650);


        } else if (level1) {

            g.drawImage(backgroundImage,0,0, SCREEN_WIDTH,SCREEN_HEIGHT, this);
            g.drawImage(characterImage,posX,posY, playerHitbox.width,playerHitbox.height, this);

           for (BasicBullet b : basicBulletMap.values()) {
               g.drawImage(b.image,(int)b.x,(int)b.y,b.hitbox.width,b.hitbox.height,this);
           }
            for (BasicEnemy e : basicEnemyMap.values()) {
                g.drawImage(e.image,(int)e.x,(int)e.y,e.hitbox.width,e.hitbox.height,this);
            }
        }


    }



    public void checkClickLocation() {

        if (mainmenu) {

            if (mouseX > 0 && mouseX < SCREEN_WIDTH && mouseY > 0 && mouseY < SCREEN_HEIGHT) {

                mainmenu = false;
                level1 = true;
                levelStartTime = System.currentTimeMillis();
                levelItemSpawner = 3000;
                whichBulletToSpawn = 1;
            }
        }

        mousePressed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (mainmenu) {
            mainMenu();
        } else if (level1) {
            move();
            level1();
        }


        if (mousePressed) {
            checkClickLocation();
        }

        repaint();

    }

    public void level1() {


        currentTime = System.currentTimeMillis();

        /*if (tick % 60 == 0) {
            makeBullet(((int) (Math.random() * 1900)), 0, 4000, 6, 270, false, false, new Dimension(24,24),getToolkit().getImage("images/basicBullet.jpg"));
        } else if (tick % 60 == 30) {
            makeBullet(((int) (Math.random() * 1900)), 0, 10000, 2, 270, false, true, new Dimension(24,24),getToolkit().getImage("images/trackBullet.jpg"));
        }*/

        if (tick % 600 == 0) {
            makeEnemy(((int) (Math.random() * 1900)), 0, false, "basic", new Dimension (100,100), getToolkit().getImage("images/basicEnemy.jpg"));
        } else if (tick % 800 == 0) {
            makeEnemy(((int) (Math.random() * 1900)), 0, false, "track", new Dimension (100,100), getToolkit().getImage("images/trackEnemy.jpg"));
        }

        if (attackButton && tick % 5 == 0) {
            makeBullet(posX, posY, 6000, 10, 90, true, false, new Dimension(32, 32), Toolkit.getDefaultToolkit().getImage("images/friendlyBullet.jpg"));
        }
        if (attackButton && tick % 25 == 0 && basicEnemyMap.values().toArray().length > 0) {
            makeBullet(posX, posY, 3000, 16, 90, true, true, new Dimension(40, 40), Toolkit.getDefaultToolkit().getImage("images/friendlyBullet.jpg"));
        }

        updateEnemies();
        updateBullets();
        tick++;
    }

    private static Integer NextBulletID = 1;
    private static Integer NextEnemyID = 1;

    public void makeEnemy(double x, double y, boolean dead, String name, Dimension hitbox, Image image) {
        BasicEnemy e = new BasicEnemy();
        e.x = x;
        e.y = y;
        e.dead = dead;
        e.name = name;
        e.hitbox = hitbox;
        e.image = image;

        basicEnemyMap.put(NextEnemyID, e);
        NextEnemyID++;
    }

    public void makeBullet(double x, double y, long lifespan, int speed, double trajectory, boolean friendly, boolean track, Dimension hitbox, Image image) {
        BasicBullet b = new BasicBullet();
        b.x = x;
        b.y = y;

        long current = System.currentTimeMillis();

        b.death = current + lifespan;
        b.speed = speed;
        b.trajectory = Math.toRadians(trajectory);
        b.hitbox = hitbox;
        b.image = image;
        b.friendly = friendly;
        b.track = track;

        basicBulletMap.put(NextBulletID, b);
        NextBulletID++;
    }

    public boolean isHit(BasicBullet b, double targetX, double targetY, Dimension targetD) {

        return ((b.x >= targetX && b.x <= targetX + targetD.width) || (b.x + b.hitbox.width >= targetX && b.x + b.hitbox.width <= targetX + targetD.width)) &&
                ((b.y >= targetY && b.y <= targetY + targetD.height) || (b.y + b.hitbox.height >= targetY && b.y + b.hitbox.height <= targetY + targetD.height));
    }

    public void updateEnemies () {

        Iterator<Map.Entry<Integer, BasicEnemy>> iterator = basicEnemyMap.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<Integer, BasicEnemy> entry = iterator.next();

            BasicEnemy e = entry.getValue();

            if (e.dead) {    // kill enemy
                iterator.remove();
            } else {    // if enemy is still alive
                if (e.name.equalsIgnoreCase("basic")) {
                    basicBulletMap = e.basic(tick, basicBulletMap, this);
                } else if (e.name.equalsIgnoreCase("track")) {
                    basicBulletMap = e.track(tick, basicBulletMap, this);
                }
            }
        }
    }

    public void updateBullets () {

        long current = System.currentTimeMillis();

        Iterator<Map.Entry<Integer, BasicBullet>> iterator = basicBulletMap.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<Integer, BasicBullet> entry = iterator.next();

            BasicBullet b = entry.getValue();

            if (b.friendly) {
                Iterator<Map.Entry<Integer, BasicEnemy>> iterator2 = basicEnemyMap.entrySet().iterator();

                while (iterator2.hasNext()) {

                    Map.Entry<Integer, BasicEnemy> entry2 = iterator2.next();

                    BasicEnemy e = entry2.getValue();

                    e.dead = isHit(b, e.x,e.y,e.hitbox);


                    if (e.dead) {    // kill enemy
                        iterator2.remove();
                    }
                }
            }   //  checks friendly bullet on enemy collision

            if (b.death < current) {    // kill bullet
                iterator.remove();
            } else {    // if bullet is still alive
                if (b.friendly && basicEnemyMap.values().toArray().length > 0) {

                    BasicEnemy e = (BasicEnemy) basicEnemyMap.values().toArray()[0];

                    b.MoveBullet(e.x, e.y, e.hitbox, this);
                } else {
                    b.MoveBullet(posX, posY, playerHitbox, this);
                }
                System.out.println(b.trajectory);
            }
        }
    }

    public void move() {

        if (movingL) {  // move player horizontally
            posX = posX - PLAYER_SPEED;
        }
        if (movingR) {
            posX = posX + PLAYER_SPEED;
        }
        if (movingU) {  // move player vertically
            posY = posY - PLAYER_SPEED;
        }
        if (movingD) {
            posY = posY + PLAYER_SPEED;
        }


        if (posX < 0) {
            posX = 0;
        } else if (posX + playerHitbox.width >= SCREEN_WIDTH) {
            posX = SCREEN_WIDTH - playerHitbox.width;
        }

        if (posY < 0) {
            posY = 0;
        } else if (posY + playerHitbox.height>= SCREEN_HEIGHT) {
            posY = SCREEN_HEIGHT - playerHitbox.height;
        }
    }

    public class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mousePressed = true;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    movingL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    movingR = false;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    movingU = false;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    movingD = false;
                    break;
                case KeyEvent.VK_SPACE:
                    attackButton = false;
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    movingL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    movingR = true;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    movingU = true;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    movingD = true;
                    break;
                case KeyEvent.VK_SPACE:
                    attackButton = true;
                    break;
            }
        }
    }
}