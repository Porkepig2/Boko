/*
This class deals with:

All the drawing
Character aspects
frame count
all scenes (menu, lv1)
key input
updating bullet & enemy classes

 */

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
    static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;      // gets any screen width
    static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height-25; // gets any screen height
    static final int UNIT_SIZE = 50;    // determines one unit, used for player size
    static final int DELAY = 16;    // delay between frames. 16ms = ~60fps

    //changable stats
    int PLAYER_SPEED;   // player speed

    //images
    Image menuImage;        // main menu image
    Image backgroundImage;  // level 1 background
    Image characterImage;   // character image


    //sounds
    File menuMusic;         // menu music
    Clip menuClip;


    //main
    int posX;   // character x
    int posY;   // character y
    int playerHealth; // player health
    int playerTotalHealth;  // for healthbar
    boolean playerDead; // if player died
    Dimension playerHitbox; // player hitbox in dimension (width, height)
    int mouseX; // mouse x position
    int mouseY; // mouse y position
    boolean mainmenu;   // game state menu
    boolean level1;     // game state lv1
    Timer timer;    // timer for everything (with the DELAY from above)
    Random random;
    boolean movingL;    // moving left
    boolean movingR;    // moving right
    boolean movingU;    // moving up
    boolean movingD;    // moving down
    boolean attackButton;   // if pressing attack button
    boolean mousePressed;   // if mouse was pressed
    long tick;              // goes up by 1 every DELAY time passed, 16ms = 60 times a second

    Map<Integer, BasicBullet> basicBulletMap = new HashMap<>(); // holds all bullets on screen in a hashmap
    Map<Integer, BasicEnemy> basicEnemyMap = new HashMap<>();   // holds all enemies on screen in a hashmap

    DecimalFormat numberFormat = new DecimalFormat("#.00"); // if used, will lock decimals to 2 decimal place


    public void refreshStats() {    // refreshes all changeable variables

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
        playerHealth = 25;
        playerTotalHealth = playerHealth;
        playerDead = false;
        playerHitbox = new Dimension (UNIT_SIZE, UNIT_SIZE);
        level1 = false;
        mainmenu = true;
        movingL = false;
        movingR = false;
        movingU = false;
        movingD = false;
        mousePressed = false;
        tick = 0;
    }

    GamePanel() {   // deals with odd stuff
        random = new Random();
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MyMouseAdapter());
        startGame();
    }

    public void startGame() {   // sets up game for the first time

        timer = new Timer(DELAY, this); // create the timer
        timer.start();

        refreshStats(); // reset all variables
        setupMusic();   // sets up music

        try {   // sets up one-time font setupper (changeable with one line after this)
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

        if (mainmenu) { // everything in main menu scene

            g.setColor(Color.WHITE);
            g.drawImage(menuImage,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,this);
            g.drawRect(30, 500, 500, 200);
            //g.setFont( new Font("Dirty Roma",Font.PLAIN, 170));
            //g.drawString("START", 120, 650);

        } else if (level1) {    // everything in level1 scene

            //g.drawImage(backgroundImage,0,0, SCREEN_WIDTH,SCREEN_HEIGHT, this);
            g.drawImage(characterImage,posX,posY, playerHitbox.width,playerHitbox.height, this);
            g.setColor(Color.WHITE);
            g.fillRect(posX,posY-25,playerHitbox.width, 20);
            g.setColor(Color.RED);
            g.fillRect(posX,posY-23,(int)(((double)playerHealth/playerTotalHealth)*playerHitbox.width), 16);

           for (BasicBullet b : basicBulletMap.values()) {
               g.drawImage(b.image,(int)b.x,(int)b.y,b.hitbox.width,b.hitbox.height,this);
           }
            for (BasicEnemy e : basicEnemyMap.values()) {
                g.drawImage(e.image,(int)e.x,(int)e.y,e.hitbox.width,e.hitbox.height,this);
                g.setColor(Color.WHITE);
                g.fillRect((int)e.x,(int)e.y-25,e.hitbox.width, 20);
                g.setColor(Color.RED);
                g.fillRect((int)e.x+2,(int)e.y-23,(int)(((double)e.health/e.totalHealth)*e.hitbox.width), 16);
            }
        }


    }



    public void checkClickLocation() {  // runs if player clicked, and checks if that click affects anything

        if (mainmenu) {

            if (mouseX > 0 && mouseX < SCREEN_WIDTH && mouseY > 0 && mouseY < SCREEN_HEIGHT) {

                mainmenu = false;
                level1 = true;
            }
        }

        mousePressed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // runs every time the timer finished (16ms = 60 times a second)

        if (mainmenu) {
            mainMenu();
        } else if (level1) {
            move();
            level1();
        }


        if (mousePressed) {
            checkClickLocation();
        }

        repaint();  // draws screen again

    }

    public void level1() {

        // occasionally spawn enemies (method subject to change)
        if (tick % 60 == 0) {  // true once every (ex. 600*16ms = 9600ms = 9.6s)
            makeEnemy(((int) (Math.random() * 1900)), 50, 100,false, "basic", new Dimension (100,100), getToolkit().getImage("images/basicEnemy.jpg"));
        } else if (tick % 80 == 0) {
            makeEnemy(((int) (Math.random() * 1900)), 50, 500,false, "track", new Dimension (100,100), getToolkit().getImage("images/trackEnemy.jpg"));
        }

        // player attacks
        if (attackButton && tick % 5 == 0) {
            new BasicBullet().addFriendlyBasicBullet(posX, posY, 8000, 4, 90, true, true, Toolkit.getDefaultToolkit().getImage("images/neonBullet.jpg"), this);
        }

        if (attackButton && tick % 20 == 0 && basicEnemyMap.values().toArray().length > 0) {
            new BasicBullet().addFriendlyTrackBullet(posX, posY, 4000, 14, 20,90,  true, true, Toolkit.getDefaultToolkit().getImage("images/neonBullet.jpg"), this);
        }

        updateEnemies();
        updateBullets();
        tick++;
    }

    private static Integer NextBulletID = 1;    // makes sure we don't overwrite a bullet or enemy in the map, increments by 1 everytime we add one in
    private static Integer NextEnemyID = 1;

    public void makeEnemy(double x, double y, int health, boolean dead, String name, Dimension hitbox, Image image) {
        BasicEnemy e = new BasicEnemy();
        e.x = x;
        e.y = y;
        e.health = health;
        e.totalHealth = health;
        e.dead = dead;
        e.name = name;
        e.hitbox = hitbox;
        e.image = image;

        basicEnemyMap.put(NextEnemyID, e);
        NextEnemyID++;
    }

    public void addBulletToMap(BasicBullet b) {
        basicBulletMap.put(NextBulletID, b);
        NextBulletID++;
    }

    public boolean isHit(BasicBullet b, double targetX, double targetY, Dimension targetD) {    // checks if a bullet hits a target

        return ((b.x >= targetX && b.x <= targetX + targetD.width) || (b.x + b.hitbox.width >= targetX && b.x + b.hitbox.width <= targetX + targetD.width)) &&
                ((b.y >= targetY && b.y <= targetY + targetD.height) || (b.y + b.hitbox.height >= targetY && b.y + b.hitbox.height <= targetY + targetD.height));
    }

    public void updateEnemies () {

        // iterate through every enemy in map, update them
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

        long current = System.currentTimeMillis();  // for checking if bullet lifespan is up

        // iterate through all bullets, update them
        Iterator<Map.Entry<Integer, BasicBullet>> iterator = basicBulletMap.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<Integer, BasicBullet> entry = iterator.next();

            BasicBullet b = entry.getValue();

            if (b.friendly) {   // if we shot the bullets or if they're friendly to us, check if that bullet hit any enemies
                Iterator<Map.Entry<Integer, BasicEnemy>> iterator2 = basicEnemyMap.entrySet().iterator();

                while (iterator2.hasNext()) {

                    Map.Entry<Integer, BasicEnemy> entry2 = iterator2.next();

                    BasicEnemy e = entry2.getValue();

                    boolean hit;
                    hit = isHit(b, e.x, e.y, e.hitbox);

                    if (hit) {
                        e.health -= b.damage;
                        if (e.health <= 0) {
                            e.dead = true;
                        }

                            b.death = 0;
                    }

                    if (e.dead) {    // kill enemy
                        iterator2.remove();
                    }
                }

                if (b.death < current) {
                    iterator.remove();
                }
            } else {
                boolean hit;
                hit = isHit(b, posX, posY, playerHitbox);

                if (hit || b.death < current) {
                    playerHealth = playerHealth - b.damage;
                    if (playerHealth <= 0) {
                        playerDead = true;
                    }
                    iterator.remove();
                }

                if (playerDead) {
                    // kill player
                }
            }
            if (b.friendly && basicEnemyMap.values().toArray().length > 0) {

                BasicEnemy e = (BasicEnemy) basicEnemyMap.values().toArray()[0];

                b.MoveBullet(e.x, e.y, e.hitbox, this);
            } else {
                b.MoveBullet(posX, posY, playerHitbox, this);
            }
        }
    }

    public void move() {    // simple movement system

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
        public void mousePressed(MouseEvent e) {    // if player presses the mouse button
            mouseX = e.getX();
            mouseY = e.getY();
            mousePressed = true;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {   // if player releases specific keys
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
        public void keyPressed(KeyEvent e) {    // if player presses specific keys
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