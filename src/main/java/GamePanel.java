/*
This class deals with:

All the drawing
Character aspects
frame count
all scenes (menu, lv1)
key input
updating bullet & enemy classes

 */

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;


public class GamePanel extends JPanel implements ActionListener {

    //static
    static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width-5;      // gets any screen width
    static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height-27; // gets any screen height
    static final int UNIT_SIZE = 50;    // not sure atm
    static final int DELAY = 16;    // delay between frames. 16ms = ~60fps

    //changable stats
    int PLAYER_SPEED;   // player speed

    //images
    Image menuImage = getToolkit().getImage("images/menu.jpg");
    Image menu_sImage = getToolkit().getImage("images/menu_s.jpg");
    Image menu_oImage = getToolkit().getImage("images/menu_o.jpg");
    Image menu_lImage = getToolkit().getImage("images/menu_l.jpg");
    Image menu_qImage = getToolkit().getImage("images/menu_q.jpg");
    Image menu_o_menuImage = getToolkit().getImage("images/menu_o_menu.jpg");
    Image menu_o_menu_bImage = getToolkit().getImage("images/menu_o_menu_b.jpg");
    Image menu_l_menuImage = getToolkit().getImage("images/menu_l_menu.jpg");
    Image menu_l_menu_bImage = getToolkit().getImage("images/menu_l_menu_b.jpg");
    Image backgroundImage = getToolkit().getImage("images/background.jpg");
    Image characterImage = getToolkit().getImage("images/character.jpg");

    //sounds
    File menuMusic;         // menu music
    Clip menuClip;

    //main
    int posX;   // character x
    int posY;   // character y
    Dimension playerHitbox; // player hitbox in dimension (width, height)
    Dimension playerHitboxCollision;
    int posXCollision;
    int posYCollision;
    int playerHealth; // player health
    int playerTotalHealth;  // for healthbar
    boolean playerDead; // if player died
    int mouseX; // mouse x position
    int mouseY; // mouse y position
    boolean tempintropaused;
    boolean sceneswap;  // swaps scene
    boolean introclip;
    boolean mainmenu;   // game state menu
    int menuscreen = 0;     // changes screen in menu depending on what user clicks (ex. options)
    boolean level1;     // game state lv1
    Timer timer;    // timer for everything (with the DELAY from above)
    Random random;
    double rotatescreen;
    boolean rotateL;
    boolean rotateR;
    boolean movingL;    // moving left
    boolean movingR;    // moving right
    boolean movingU;    // moving up
    boolean movingD;    // moving down
    boolean attackButton;   // if pressing attack button
    boolean mousePressed;   // if mouse was pressed
    boolean mouseMoved;
    long tick;              // goes up by 1 every DELAY time passed, 16ms = 60 times a second
    int TEMPBULLETCHECK;

    final JFXPanel VFXPanel = new JFXPanel();   ///// all for displaying mp4
    JPanel videoPanel = this;

    File video_source = new File("video/boko intro.mp4");
    Media m = new Media(video_source.toURI().toString());
    MediaPlayer player = new MediaPlayer(m);
    MediaView viewer = new MediaView(player);

    StackPane root = new StackPane();
    Scene scene = new Scene(root);  /////

    Map<Integer, BasicEnemy> levelLayout = new HashMap<>();     // stores when all the enemys are going to appear
    Map<Integer, BasicBullet> basicBulletMap = new HashMap<>(); // holds all bullets on screen in a hashmap
    Map<Integer, BasicEnemy> basicEnemyMap = new HashMap<>();   // holds all enemies on screen in a hashmap

    DecimalFormat numberFormat = new DecimalFormat("#.00"); // if used, will lock decimals to 2 decimal place


    public void refreshStats() {    // refreshes all changeable variables

        //changable stats
        PLAYER_SPEED = 8;

        //sounds
        menuMusic = new File("music/menu.wav");

        //main
        posX = UNIT_SIZE*10;
        posY = UNIT_SIZE*12;
        playerHitbox = new Dimension (UNIT_SIZE, UNIT_SIZE);
        posXCollision = UNIT_SIZE*10;
        posYCollision = UNIT_SIZE*12;
        playerHitboxCollision = new Dimension (UNIT_SIZE/4, UNIT_SIZE/4);
        playerHealth = 25;
        playerTotalHealth = playerHealth;
        playerDead = false;
        level1 = false;
        tempintropaused = false;
        rotatescreen = 0;
        sceneswap = false;
        introclip = true;
        mainmenu = false;
        rotateL = false;
        rotateR = false;
        movingL = false;
        movingR = false;
        movingU = false;
        movingD = false;
        mousePressed = false;
        mouseMoved = false;
        tick = 0;
        TEMPBULLETCHECK = 0;
    }

    GamePanel() {   // deals with odd stuff
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MyMousePressAdapter());
        this.addMouseMotionListener(new MyMouseMoveAdapter());
        startGame();
    }

    public void startGame() {   // sets up game for the first time

        timer = new Timer(DELAY, this); // create the timer
        timer.start();

        getVideo();
        refreshStats(); // reset all variables
        //setupMusic();   // sets up music

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

        if (movingD && menuscreen == 0) {
            menuscreen = 1;
        } else if (movingD && menuscreen == 1) {
            menuscreen = 2;
        } else if (movingD && menuscreen == 2) {
            menuscreen = 3;
        } else if (movingD && menuscreen == 3) {
            menuscreen = 4;
        } else if (movingD && menuscreen == 4) {
            menuscreen = 1;
        } else if (movingU && menuscreen == 0) {
            menuscreen = 1;
        } else if (movingU && menuscreen == 1) {
            menuscreen = 4;
        } else if (movingU && menuscreen == 4) {
            menuscreen = 3;
        } else if (movingU && menuscreen == 3) {
            menuscreen = 2;
        } else if (movingU && menuscreen == 2) {
            menuscreen = 1;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (mainmenu) { // everything in main menu scene

            switch (menuscreen) {

                case 0: // menu screen
                    g.drawImage(menuImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    //g.setFont( new Font("Dirty Roma",Font.PLAIN, 170));
                    break;
                case 1: // hover over start
                    g.drawImage(menu_sImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 2: // hover over options
                    g.drawImage(menu_oImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 3: // hover over leaderboard
                    g.drawImage(menu_lImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 4: // hover over quit
                    g.drawImage(menu_qImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 5: // clicked options
                    g.drawImage(menu_o_menuImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 6: // clicked options hover over back
                    g.drawImage(menu_o_menu_bImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 7: // clicked leaderboard
                    g.drawImage(menu_l_menuImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;
                case 8: // clicked leaderboard hover over back
                    g.drawImage(menu_l_menu_bImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                    break;

            }
        } else if (level1) {    // everything in level1 scene
            Graphics2D levelrotation = (Graphics2D)g;
            AffineTransform oldlevelrotation = levelrotation.getTransform();

            levelrotation.rotate(rotatescreen, (float)SCREEN_WIDTH/2, SCREEN_HEIGHT);

            g.drawImage(backgroundImage,-1000,-1000,SCREEN_WIDTH+3000,SCREEN_HEIGHT+3000,this);

           for (BasicBullet b : basicBulletMap.values()) {
               if (b.rotationSensitive) {
                   Graphics2D g2d = (Graphics2D)g;

                   AffineTransform old = g2d.getTransform();
                   g2d.rotate(b.trajectory, b.x+((double)b.hitbox.width/2), b.y+((double)b.hitbox.height/2));
                   g.drawImage(b.image, (int) b.x, (int) b.y, b.hitbox.width, b.hitbox.height, this);
                   g2d.setTransform(old);
               } else {
                   g.drawImage(b.image, (int) b.x, (int) b.y, b.hitbox.width, b.hitbox.height, this);
               }
           }
            for (BasicEnemy e : basicEnemyMap.values()) {
                g.drawImage(e.image,(int)e.x,(int)e.y,e.hitbox.width,e.hitbox.height,this);
                g.setColor(Color.WHITE);
                g.fillRect((int)e.x,(int)e.y-25,e.hitbox.width, 20);
                g.setColor(Color.RED);
                g.fillRect((int)e.x+2,(int)e.y-23,(int)(((double)e.health/e.totalHealth)*e.hitbox.width), 16);
            }

            g.setColor(Color.RED);
            g.fillRect(posXCollision,posYCollision,playerHitboxCollision.width,playerHitboxCollision.height);

            levelrotation.setTransform(oldlevelrotation);

            g.drawImage(characterImage,posX,posY, playerHitbox.width,playerHitbox.height, this);
            g.setColor(Color.WHITE);
            g.fillRect(posX,posY-25,playerHitbox.width, 20);
            g.setColor(Color.RED);
            g.fillRect(posX,posY-23,(int)(((double)playerHealth/playerTotalHealth)*playerHitbox.width), 16);
        }


    }



    public void checkClickLocation() {  // runs if player clicked, and checks if that click affects anything

        if (mainmenu) {

            if (menuscreen < 5) {
                if (mouseX > 1200 && mouseX < SCREEN_WIDTH - 10 && mouseY > 359 && mouseY < SCREEN_HEIGHT - 530) {

                    sceneswap = true;
                } else if (mouseX > 1100 && mouseX < SCREEN_WIDTH - 60 && mouseY > 530 && mouseY < SCREEN_HEIGHT - 378) {

                    menuscreen = 5;
                } else if (mouseX > 1000 && mouseX < SCREEN_WIDTH - 120 && mouseY > 679 && mouseY < SCREEN_HEIGHT - 208) {

                    menuscreen = 7;
                } else if (mouseX > 900 && mouseX < SCREEN_WIDTH - 180 && mouseY > 850 && mouseY < SCREEN_HEIGHT - 63) {

                    //quit
                }
            } else if (menuscreen <= 6) {
                if (mouseX > 7 && mouseX < 320 && mouseY > 7 && mouseY < 150) {
                    menuscreen = 0;
                }
            } else if (menuscreen <= 8) {
                if (mouseX > 7 && mouseX < 320 && mouseY > 7 && mouseY < 150) {
                    menuscreen = 0;
                }
            }
        }

        mousePressed = false;
    }

    public void checkMouseMoveLocation() {  // runs if player clicked, and checks if that click affects anything

        if (mainmenu) {
            if (menuscreen < 5) {
                if (mouseX > 1200 && mouseX < SCREEN_WIDTH - 10 && mouseY > 359 && mouseY < SCREEN_HEIGHT - 530) {
                    menuscreen = 1;
                } else if (mouseX > 1100 && mouseX < SCREEN_WIDTH - 60 && mouseY > 530 && mouseY < SCREEN_HEIGHT - 378) {
                    menuscreen = 2;
                } else if (mouseX > 1000 && mouseX < SCREEN_WIDTH - 120 && mouseY > 679 && mouseY < SCREEN_HEIGHT - 208) {
                    menuscreen = 3;
                } else if (mouseX > 900 && mouseX < SCREEN_WIDTH - 180 && mouseY > 850 && mouseY < SCREEN_HEIGHT - 63) {
                    menuscreen = 4;
                } else {
                    menuscreen = 0;
                }
            } else if (menuscreen <= 6) {
                if (mouseX > 7 && mouseX < 320 && mouseY > 7 && mouseY < 150) {
                    menuscreen = 6;
                } else {
                    menuscreen = 5;
                }
            } else if (menuscreen <= 8) {
                if (mouseX > 7 && mouseX < 320 && mouseY > 7 && mouseY < 150) {
                    menuscreen = 8;
                } else {
                    menuscreen = 7;
                }
            }
        }

        mouseMoved = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {    // runs every time the timer finished (16ms = 60 times a second)

        if (introclip) {
            if (player.getCurrentTime().toMillis() >= 88000) {
                sceneswap = true;
            }
        } else if (mainmenu) {
            mainMenu();
        } else if (level1) {
            move();
            level1();
        }

        if (sceneswap) {
            swapScenes();
        }

        if (mousePressed) {
            checkClickLocation();
        } else if (mouseMoved) {
            checkMouseMoveLocation();
        }

        repaint();  // draws screen again

    }

    public void inputEnemiesIntoMap() {

        levelLayout.put(60, new BasicEnemy(800, -70, 500,false, "swooper", new Dimension (100,100), getToolkit().getImage("images/trackEnemy.jpg")));
        levelLayout.put(30, new BasicEnemy(800, 500, 4500,false, "basic", new Dimension (200,200), null));
        levelLayout.put(29, new BasicEnemy(800, 500, 200,false, "boss", new Dimension (200,200), getToolkit().getImage("images/basicEnemy.jpg")));
        levelLayout.put(78, new BasicEnemy(1400, 50, 4500,false, "track", new Dimension (200,200), getToolkit().getImage("images/bossEnemy.jpg")));

       // levelLayout.put(400, makeEnemy(((int) (Math.random() * 1900)), 50, 4500,false, tick, "boss", new Dimension (200,200), getToolkit().getImage("images/bossEnemy.jpg")));


    }

    public void level1() {

       if (levelLayout.containsKey((int)tick)) {

          BasicEnemy e = levelLayout.get((int)tick);

          e.tickWhenCreated = tick;

          addEnemyToMap(e);
       }

        shootPlayerBullets();

        updateEnemies();
        updateBullets();
        tick++;
    }

    public void shootPlayerBullets() {

        // player attacks
        if (attackButton && tick % 10 == 0) {
            //  new BasicBullet().friendlyBasicBullet(posX, posY, 8000, 4, 90, true, true, Toolkit.getDefaultToolkit().getImage("images/neonBullet.jpg"), this);

            switch (TEMPBULLETCHECK) {
                case 0:
                    //new BasicBullet().EverythingBullet(posX, posY, 4000, 5, 200, 0, .2, 5, 270, 0, true, false, true, false, new Dimension(20, 20), getToolkit().getImage("images/neonBullet.jpg"), this);
                    new BasicBullet().slowdownBullet(posX, posY, 12000, 5, 270, 200, 180, 2, getToolkit().getImage("images/neonBullet.jpg"), this);
                    break;
                case 1:
                    new BasicBullet().EverythingBullet(posX, posY, 4000, 5, 10, 0, .2, 4, 270, 0, true, false, true, false, new Dimension(20, 20), getToolkit().getImage("images/neonBullet.jpg"), this);
                    break;
                case 2:
                    new BasicBullet().EverythingBullet(posX, posY, 4000, 5, 10, 0, .05, 2, 270, 0, false, true, true, false, new Dimension(20, 20), getToolkit().getImage("images/neonBullet.jpg"), this);
                    break;
                case 3:
                    new BasicBullet().EverythingBullet(posX, posY, 40000, 1, 10, 0, 1, 1, 270, .1, false, false, true, false, new Dimension(20, 20), getToolkit().getImage("images/neonBullet.jpg"), this);
                    break;
                case 4:
                    new BasicBullet().EverythingBullet(posX, posY, 40000, 5, 10, 0, .2, 1, 270, 0, true, true, true, true, new Dimension(20, 20), getToolkit().getImage("images/neonBullet.jpg"), this);
                    break;
            }
        }

    }

    private static Integer NextBulletID = 1;    // makes sure we don't overwrite a bullet or enemy in the map, increments by 1 everytime we add one in
    private static Integer NextEnemyID = 1;

    public void addBulletToMap(BasicBullet b) {
        basicBulletMap.put(NextBulletID, b);
        NextBulletID++;
    }

    public void addEnemyToMap(BasicEnemy e) {
        basicEnemyMap.put(NextEnemyID, e);
        NextEnemyID++;
    }

    public boolean isHit(BasicBullet b, double targetX, double targetY, Dimension targetD) {    // checks if a bullet hits a target

        return ((b.x <= targetX && b.x+b.hitbox.width >= targetX) || (b.x+b.hitbox.width >= targetX+targetD.width && b.x <= targetX+targetD.width)) &&
                ((b.y <= targetY && b.y+b.hitbox.height >= targetY) || (b.y+b.hitbox.height >= targetY+targetD.height && b.y <= targetY+targetD.height));
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
                } else if (e.name.equalsIgnoreCase("swooper")) {
                    basicBulletMap = e.swooper(tick, basicBulletMap, this);
                } else if (e.name.equalsIgnoreCase("boss")) {
                    basicBulletMap = e.boss(tick, basicBulletMap, this);
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
                hit = isHit(b, posXCollision, posYCollision, playerHitboxCollision);

                if (b.death < current || hit) {
                    if (hit) {
                        playerHealth = playerHealth - b.damage;
                        if (playerHealth <= 0) {
                            playerDead = true;
                        }
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
                b.MoveBullet(posXCollision, posYCollision, playerHitboxCollision, this);
            }
        }
    }

    public void move() {    // simple movement system

        if (movingL) {  // move player horizontally
            posX -= PLAYER_SPEED;
        }
        if (movingR) {
            posX += PLAYER_SPEED;
        }
        if (movingU) {  // move player vertically
            posY -= PLAYER_SPEED;
        }
        if (movingD) {
            posY += PLAYER_SPEED;
        }

        if (rotateL) {
            rotatescreen -= 0.01;
        }
        if (rotateR) {
            rotatescreen += 0.01;
        }

        System.out.println(posX);
        posXCollision = (int)((posX+playerHitbox.height/2-SCREEN_WIDTH/2)*Math.cos(-rotatescreen)-(posY+playerHitbox.height/2-SCREEN_HEIGHT)*Math.sin(-rotatescreen)) + SCREEN_WIDTH/2 - playerHitboxCollision.width/2;
        posYCollision = (int)((posX+playerHitbox.height/2-SCREEN_WIDTH/2)*Math.sin(-rotatescreen)+(posY+playerHitbox.height/2-SCREEN_HEIGHT)*Math.cos(-rotatescreen)) + SCREEN_HEIGHT - playerHitboxCollision.height/2;

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

    public void swapScenes() {

        if (introclip) {
            introclip = false;
            mainmenu = true;
            player.stop();
            videoPanel.remove(0);
            setupMusic();
        }
        else if (mainmenu) {
            mainmenu = false;
            level1 = true;
            inputEnemiesIntoMap();
        }

        sceneswap = false;
    }

    private void getVideo(){

        // add video to stackpane
        root.getChildren().add(viewer);

        VFXPanel.setScene(scene);
        player.play();
        videoPanel.setLayout(new BorderLayout());
        videoPanel.add(VFXPanel, BorderLayout.CENTER);
    }

    public class MyMousePressAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {    // if player presses the mouse button
            mouseX = e.getX();
            mouseY = e.getY();
            mousePressed = true;
        }
    }

    public class MyMouseMoveAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mouseMoved = true;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {   // if player releases specific keys
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                case KeyEvent.VK_J:
                    movingL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                case KeyEvent.VK_L:
                    movingR = false;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                case KeyEvent.VK_I:
                    movingU = false;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                case KeyEvent.VK_K:
                    movingD = false;
                    break;
                case KeyEvent.VK_Q:
                case KeyEvent.VK_U:
                    rotateL = false;
                    break;
                case KeyEvent.VK_E:
                case KeyEvent.VK_O:
                    rotateR = false;
                    break;
                case KeyEvent.VK_SPACE:
                    attackButton = false;
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {    // if player presses specific keys
            boolean tempintropausechecker = tempintropaused;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                case KeyEvent.VK_J:
                    movingL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                case KeyEvent.VK_L:
                    movingR = true;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                case KeyEvent.VK_I:
                    movingU = true;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                case KeyEvent.VK_K:
                    movingD = true;
                    break;
                case KeyEvent.VK_Q:
                case KeyEvent.VK_U:
                    rotateL = true;
                    break;
                case KeyEvent.VK_E:
                case KeyEvent.VK_O:
                    rotateR = true;
                    break;
                case KeyEvent.VK_SPACE:
                    attackButton = true;
                    break;
                case KeyEvent.VK_1:
                    TEMPBULLETCHECK = 0;
                    break;
                case KeyEvent.VK_2:
                    TEMPBULLETCHECK = 1;
                    break;
                case KeyEvent.VK_3:
                    TEMPBULLETCHECK = 2;
                    break;
                case KeyEvent.VK_4:
                    TEMPBULLETCHECK = 3;
                    break;
                case KeyEvent.VK_5:
                    TEMPBULLETCHECK = 4;
                    break;
                case KeyEvent.VK_P:
                    if (introclip && !tempintropaused) {
                        tempintropaused = true;
                        player.pause();
                    } else if (introclip && tempintropaused) {
                        tempintropaused = false;
                        player.play();
                    }
            }
            if (introclip && tempintropausechecker == tempintropaused) {
                sceneswap = true;
            }
        }
    }
}