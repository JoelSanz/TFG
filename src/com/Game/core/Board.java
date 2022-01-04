package com.Game.core;

import com.Game.Towers.*;
import com.Game.InputHandler.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import java.nio.Buffer;

public class Board extends JPanel implements Runnable {
    private VectoidAI ai;
    private int frameCount = 0, waitTime = 0, hp;
    boolean showRanges =  true, waveSpawnig = false, activeWave = false, towerClicked;
    Tower tower, towerSelected;
    Thread thread = new Thread(this);
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();
    Map map = new Map();
    private ArrayList<Tower> towerList = new ArrayList<Tower>(1);
    private BufferedImage background, vectoidImage;
    private final int CELL_SIZE=53;
    private Frame frame;
    Tower[][] towerMap= new Tower[22][18];
    public ArrayList<Vectoid> vectoidList = new ArrayList<Vectoid>(1);
    MouseInput mouse = new MouseInput(this);
    Wave wave;
    double money;
    String lastErrorMessage = "Game Start";

    public Board(Frame frame){
        this.frame = frame;
        //Component textArea = new TextArea("Click here");
        thread.start();
        this.frame.addMouseListener(mouse);
    }

    /**
     *
     */
    private void initBoard(){

        loadImage();
        ai = new VectoidAI(map);
        money = 200;
        hp = 5;
        //setPreferredSize(new Dimension(h, w));
    }

    /**
     *
     */
    private void loadImage(){
        try{
            background = ImageIO.read(new File("resources/black_bg.jpg"));
        }catch (IOException e){
            System.out.println("Image could not be loaded");
        }

        background = scale(background, w, h);


    }

    /**
     *
     * @param g Instance of Graphics class
     */
    @Override
    public void paintComponent(Graphics g){


        Graphics2D g2 =  (Graphics2D) g;
        g.drawImage(background, 0, 0, null);

        paintGameMap(g2);
        paintEnemyPath(g2);
        paintTowerMenu(g2);
        paintBottomMenu(g2);
        paintBonusMenu(g2);
        paintInfoMenu(g2);
        paintOptionsMenu(g2);
        paintTowerMap(g2);
        drawLaser(g2);

        if(towerClicked){
            drawRangeOnMovingTower(tower, g2);
            paintTowerClicked(g2);
        }
        if (activeWave) {
            paintVectoids(g2);

        }


    }

    /**
     *
     */
    public void run() {
        initBoard();

        while(true) {
            repaint();
            if(waveSpawnig && waitTime > 20000000 && !wave.doneSpawning()){
                spawnVectoids();
                waitTime = 0;
            }
            if(frameCount > 400000) {

                update();
                frameCount = 0;
            }
            frameCount++;
            waitTime++;
        }


    }

    /**
     *
     */
    private void update(){

        for (int i = 0; i < vectoidList.size(); i++) {
            Vectoid v = vectoidList.get(i);
            if(v != null) {
                ai.CalculateMove(v);
                for (Tower t : towerList) {
                    if (t != null) {
                        if (t.targets.contains(v)) {
                            if (!isInRange(t, v)) {
                                t.targets.remove(v);
                                t.currentTargets--;
                            }else{
                                calculateDamage(t, v);
                            }

                        } else {
                            if (isInRange(t, v) && !t.IsTargetFull()) {
                                t.targets.add(v);
                                t.currentTargets++;
                            }
                        }

                    }
                }
            }
        }
    }
    /**
     *
     * @param imageToScale BufferedImage you want to scale
     * @param dWidth Target widht desired
     * @param dHeight Target height desired
     * @return Image Scaled
     */
    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight){
        BufferedImage scaledImage = null;
        if (imageToScale !=null){
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintTowerMap(Graphics2D g2){

        for(Tower t : towerList){

                if(t != null){

                    switch (t.getTowerType()) {
                        case "blue" -> g2.setColor(new Color(0, 0, 255, 255));
                        case "red" -> g2.setColor(new Color(255, 0, 0, 255));
                        case "green" -> g2.setColor(new Color(0, 255, 0, 255));
                        case "purple" -> g2.setColor(new Color(106, 13, 173, 255));
                        default -> System.out.println("Unkown tower type");
                    }


                    g2.drawRect((t.getPosition().x * CELL_SIZE), (t.getPosition().y* CELL_SIZE), CELL_SIZE, CELL_SIZE);
                    if(showRanges)
                        drawRange(t, t.getPosition().x, t.getPosition().y, g2);

                }

        }
    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintGameMap(Graphics2D g2){
        g2.setColor(Color.lightGray);

        for(int x = 0; x < 22; x++){
            for (int y = 0; y < 18; y++){
                g2.setColor(Color.lightGray);
                g2.drawRect(CELL_SIZE + (x * CELL_SIZE), (CELL_SIZE) + (y* CELL_SIZE), CELL_SIZE, CELL_SIZE);


            }
        }
    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintEnemyPath(Graphics2D g2){
        BasicStroke stroke;
        stroke = new BasicStroke(3,2, 2);
        for(int x = 0; x < 22; x++) {
            for (int y = 0; y < 18; y++) {
                if(map.getPosition(x, y) != 0) {
                    if (map.getPosition(x, y) == 1) {

                        g2.setColor(Color.white);
                        g2.setStroke(stroke);
                    } else if (map.getPosition(x, y) == 2) {
                        g2.setColor(Color.yellow);
                        g2.setStroke(stroke);
                    } else if (map.getPosition(x, y) == 3) {
                        g2.setColor(Color.black);
                        g2.setStroke(stroke);

                    }
                    g2.drawRect(CELL_SIZE + (x * CELL_SIZE), (CELL_SIZE) + (y* CELL_SIZE), CELL_SIZE, CELL_SIZE);
                }
            }
        }
        stroke = new BasicStroke(1, 2, 2);
        g2.setStroke(stroke);

    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintBottomMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = CELL_SIZE;
        int yInit = CELL_SIZE * 20;
        g2.drawRect(xInit, yInit, CELL_SIZE * 22, CELL_SIZE * 6);

    }

    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintTowerMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);

        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );
        for (int i = 0; i <= 3; i++){
            switch (i) {
                case 0 -> g2.setColor(new Color(0, 0, 255, 255));
                case 1 -> g2.setColor(new Color(0, 255, 0, 255));
                case 2 -> g2.setColor(new Color(106, 13, 173, 255));
                case 3 -> g2.setColor(new Color(255, 0, 0, 255));
                default -> System.out.println("Unkown tower type");
            }

            g2.drawRect( xInit + CELL_SIZE, yInit + CELL_SIZE, CELL_SIZE,CELL_SIZE);
            xInit = xInit + CELL_SIZE*2;

        }
        g2.setColor(Color.lightGray);

    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintBonusMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = CELL_SIZE * 36;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );

        //Paint current money
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        g2.drawString("Current money: "+money+"€", xInit + CELL_SIZE, yInit + CELL_SIZE);

        //Paint current hp
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        g2.drawString("Current HP: "+hp, xInit + CELL_SIZE, yInit + CELL_SIZE *2);

        //Paint interest rate
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        g2.drawString("Current interest rate: "+ 0, xInit + CELL_SIZE, yInit + CELL_SIZE *3);

        //Paint last Error Message
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        g2.drawString(lastErrorMessage, xInit + CELL_SIZE, yInit + CELL_SIZE *4);

        g2.drawLine(xInit, yInit + CELL_SIZE * 5, xInit + CELL_SIZE * 11, yInit + CELL_SIZE * 5);

        if(tower != null){
            g2.drawString("Tower: "+ towerSelected.getTowerType(), xInit + CELL_SIZE, yInit + CELL_SIZE *6);
            g2.drawString("Range: " + towerSelected.getRange()/CELL_SIZE, xInit + CELL_SIZE, yInit + CELL_SIZE * 7);
            g2.drawString("Damage: "+ towerSelected.getDamage(), xInit + CELL_SIZE, yInit + CELL_SIZE *8);
            g2.drawString("Cost: "+ towerSelected.getCost(), xInit + CELL_SIZE, yInit + CELL_SIZE *9);


        }

    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintInfoMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE * 13;
        g2.drawRect( xInit, yInit, CELL_SIZE * 23,CELL_SIZE * 6 );



        //Paint tower Description
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        if(tower!=null)
            g2.drawString(towerSelected.getDescription(), xInit + CELL_SIZE, yInit + CELL_SIZE *2);


    }

    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintOptionsMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);

        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE * 20;
        g2.drawRect( xInit, yInit, CELL_SIZE * 23,CELL_SIZE * 6 );

        //Draw "Spawn vectoids" button
        g2.drawRect(xInit + CELL_SIZE, yInit + CELL_SIZE, CELL_SIZE * 5, CELL_SIZE * 2);
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        g2.drawString("Spawn Vectoids", xInit + CELL_SIZE*2, yInit + CELL_SIZE *2);

        //Draw "Show ranges" button
        g2.drawRect(xInit + CELL_SIZE * 7, yInit + CELL_SIZE, CELL_SIZE * 5, CELL_SIZE * 2);
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        if(showRanges)
            g2.drawString("Hide Ranges", xInit + CELL_SIZE * 8, yInit + CELL_SIZE * 2);
        else
            g2.drawString("Draw Ranges", xInit + CELL_SIZE * 8, yInit + CELL_SIZE * 2);


    }

    /**
     *
     * Draws all vectoids present in vectoidList
     * @param g2 Instance of Graphics2D class
     */
    private void paintVectoids(Graphics2D g2){
        try{
            vectoidImage = ImageIO.read(new File("resources/Vectoids/BlueVectoid.png"));
        }catch (IOException e){
            System.out.println("Image could not be loaded");
        }

        for (Vectoid v : vectoidList) {
            if(v != null){
                Point p = v.getCurrentPosition();
                int px, py;
                px = (p.x + 1) * CELL_SIZE + (vectoidImage.getWidth() / 2);
                py = (p.y + 1) * CELL_SIZE + (vectoidImage.getHeight() / 2);
                switch (v.getTrajectory()) {
                    case 'd' -> py = (p.y + 1) * CELL_SIZE + (vectoidImage.getHeight() / 2) + v.getPositionOffset();
                    case 'r' -> px = (p.x + 1) * CELL_SIZE + (vectoidImage.getWidth() / 2) + v.getPositionOffset();
                    case 'l' -> px =  (p.x + 1) * CELL_SIZE + (vectoidImage.getWidth() / 2) - v.getPositionOffset();
                    case 'u' -> py = (p.y + 1) * CELL_SIZE + (vectoidImage.getHeight() / 2) - v.getPositionOffset();
                    default -> System.out.println("Unkown trajectory");
                }
                g2.drawImage(vectoidImage, px, py, null);
                g2.setColor(Color.red);
                g2.fillRect(px, py - 10, vectoidImage.getWidth(), 5);
                g2.setColor(Color.green);
                g2.fillRect(px, py - 10, (int) (vectoidImage.getWidth() * v.getHpPercent()), 5);

            }
        }
    }


    /**
     * Checks which menu was clicked and calls the appropiate function
     * @param x An integer containing the x coordinate on screen of the mouse click
     * @param y An integer containing the y coordinate on screen of the mouse click
     */
    public void checkLocationClicked(int x, int y){


        //Checks if the click was made in the Tower Menu

        if(x >= CELL_SIZE * 24 && y >= CELL_SIZE && x<= CELL_SIZE * 24 + (CELL_SIZE * 11) && y <= CELL_SIZE + (CELL_SIZE * 11))
        {
            towerMenuClicked(x, y);
            paintComponent(this.getGraphics());
        }
        //Checks if the click was made in the Bonus Menu

        else  if(x >= CELL_SIZE * 36 && y >= CELL_SIZE && x<= CELL_SIZE * 36 + (CELL_SIZE * 11) && y <= CELL_SIZE + (CELL_SIZE * 11)){

            System.out.println("Click registrado en el menu de bonus");
        }

        //Checks if the click was made in the Options menu

        else if(x >= CELL_SIZE * 24 && y >= CELL_SIZE * 20 && x<= CELL_SIZE * 24 + (CELL_SIZE * 23) && y <= CELL_SIZE * 20 + (CELL_SIZE * 6)){
            optionsMenuClicked(x, y);

        }
        //Checks if the click was made in the game map
        else if(x >= CELL_SIZE && y >= CELL_SIZE && x<= CELL_SIZE * 24 && y <= CELL_SIZE * 19){
            if(this.towerClicked){
                if(money>=tower.getCost()) {
                    money = money - tower.getCost();
                    placeTower(x, y);
                }
                else{
                    lastErrorMessage = "you broke ass bitch, you got no monaaaaay";
                }
                towerClicked = false;
            }else{
               int posX = x / CELL_SIZE;
               int posY = y / CELL_SIZE;
               if(towerMap[posX][posY] != null)
                   towerSelected = towerMap[posX][posY];
                //TODO: check if there's a tower in this position and call the appropiate function
            }
        }

    }

    /**
     *
     * @param x
     * @param y
     */
    private void optionsMenuClicked(int x, int y){
        System.out.println("checking location");
        if(x >= (CELL_SIZE * 25) && y >= (CELL_SIZE * 21) && x<= (CELL_SIZE * 30) && y<= (CELL_SIZE * 23)){
            newWave();
        }

        if(x >= (CELL_SIZE * 31) && y >= (CELL_SIZE * 21) && x<= (CELL_SIZE * 36) && y<= (CELL_SIZE * 23)){
            showRanges = !showRanges;
        }
    }
    /**
     *  Checks where in the Tower menu the click was made
     *  if it was made on a tower, places one tower of that type in a random location(will change into a fixed location in the future)
     * @param x An integer containing the x coordinate on screen of the mouse click
     * @param y An integer containing the y coordinate on screen of the mouse click
     */
    private void towerMenuClicked(int x, int y){

        if(x >= (CELL_SIZE * 25) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 26) && y<= (CELL_SIZE * 3))
        {
            tower = new BlueTower();
            towerSelected = new BlueTower();
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 27) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 28) && y<= (CELL_SIZE * 3))
        {
            tower = new GreenTower();
            towerSelected = new GreenTower();
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 29) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 30) && y<= (CELL_SIZE * 3))
        {
            tower = new PurpleTower();
            towerSelected = new PurpleTower();
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 31) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 32) && y<= (CELL_SIZE * 3))
        {
            tower = new RedTower();
            towerSelected = new RedTower();
            towerClicked = true;
        }

    }

    /**
     *
     */
    public void newWave() {

        wave = new Wave(this);
        wave.setSpawnPoint(getSpawnPoints());
        wave.startWave(20);
        //spawnVectoids();
        activeWave = true;
        waveSpawnig = true;

    }

    /**
     *
     * @return
     */

    public ArrayList<Point> getSpawnPoints(){
        Point s;
        ArrayList<Point> spawnPoints = new ArrayList<Point>(1);
        int i = 0;
        for(int x = 0; x < 22; x++) {
            for (int y = 0; y < 18; y++) {
                if (map.getPosition(x, y) == 2) {
                    s = new Point(x, y);
                    spawnPoints.add(s);
                    i++;
                }

            }
        }
        return spawnPoints;
    }

    /**
     *
     * @param g2
     */

    private void paintTowerClicked(Graphics2D g2){
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        switch (tower.getTowerType()) {
            case "blue" -> {
                g2.setColor(Color.blue);
                int x = mouseLocation.x - (CELL_SIZE / 2);
                int y = mouseLocation.y - (CELL_SIZE / 2);
                g2.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                drawRange(tower, x, y, g2);

            }
            case "red" -> {
                g2.setColor(Color.red);
                g2.drawRect(mouseLocation.x - (CELL_SIZE / 2), mouseLocation.y - (CELL_SIZE / 2), CELL_SIZE, CELL_SIZE);
            }
            case "purple" -> {
                g2.setColor(new Color(106, 13, 173));
                g2.drawRect(mouseLocation.x - (CELL_SIZE / 2), mouseLocation.y - (CELL_SIZE / 2), CELL_SIZE, CELL_SIZE);
            }
            case "green" -> {
                g2.setColor(Color.green);
                g2.drawRect(mouseLocation.x - (CELL_SIZE / 2), mouseLocation.y - (CELL_SIZE / 2), CELL_SIZE, CELL_SIZE);
            }
            default -> System.out.println("Unknown tower type");
        }

    }

    /**
     * Calculates which cell was clicked and places the tower on it if possible
     * @param x An integer containing the x coordinate on screen of the mouse click
     * @param y An integer containing the y coordinate on screen of the mouse click
     */
    private void placeTower(int x, int y){

        int posX = x / CELL_SIZE;
        int posY = y / CELL_SIZE;
        if(map.getPosition(posX-1, posY-1)!=0) {
            System.out.println("Can't place a tower there");
        }else{
            tower.setPosition(new Point(posX, posY));
            towerList.add(tower);
            towerMap[posX][posY] = tower;
        }


    }

    /**
     *
     * @param t
     * @param x
     * @param y
     * @param g2
     */

    public void drawRange(Tower t, int x, int y, Graphics2D g2) {

        int range =  t.getRange();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (t.getTowerType()) {
            case "blue" -> g2.setColor(new Color(0, 0, 255, 60));
            case "red" -> g2.setColor(new Color(255, 0, 0, 60));
            case "green" -> g2.setColor(new Color(0, 255, 0, 60));
            case "purple" -> g2.setColor(new Color(106, 13, 173, 60));
            default -> System.out.println("Unkown tower type");
        }
        g2.fillOval((((x)*CELL_SIZE)+CELL_SIZE/2)-range/2, (((y)*CELL_SIZE)+CELL_SIZE/2)-range/2, range, range);




    }

    /**
     *
     * @param t
     * @param g2
     */

    public void drawRangeOnMovingTower(Tower t, Graphics2D g2){
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;



        switch (t.getTowerType()) {
            case "blue" -> g2.setColor(new Color(0, 0, 255, 60));
            case "red" -> g2.setColor(new Color(255, 0, 0, 60));
            case "green" -> g2.setColor(new Color(0, 255, 0, 60));
            case "purple" -> g2.setColor(new Color(106, 13, 173, 60));
            default -> System.out.println("Unkown tower type");
        }
        g2.drawOval(x-(t.getRange()/2), y-(t.getRange()/2), t.getRange(), t.getRange());
        g2.fillOval(x-(t.getRange()/2), y-(t.getRange()/2), t.getRange(), t.getRange());

    }

    /**
     * Checks if Vectoid v is in range of tower t
     * @param t
     * @param v
     */
    public boolean isInRange(Tower t, Vectoid v){
        boolean inRange = false;
        int vx, vy;
        Graphics2D g2;
        vx = 0;
        vy = 0;
        if(v != null) {
            vx = (v.getCurrentPosition().x * CELL_SIZE);
            vy = (v.getCurrentPosition().y * CELL_SIZE);
            switch (v.getTrajectory()) {
                case 'd' -> vy += v.getPositionOffset();
                case 'r' -> vx += v.getPositionOffset();
                case 'l' -> vx -= v.getPositionOffset();
                case 'u' -> vy -= v.getPositionOffset();
                default -> System.out.println("Unkown trajectory");
            }
        }

        //Calculates wether vectoid v is in range of tower t  by the formula (x-centerX)^2+(y-centerY)^2 < radius^2
        //Where x, y are vx, vy and centerX, centerY are t.getPosition().x or .y. radius is the range of the tower

        if(Math.pow(vx-((t.getPosition().x -1)  * CELL_SIZE), 2) + Math.pow(vy - ((t.getPosition().y -1 )* CELL_SIZE), 2) < Math.pow((t.getRange()/2), 2)) {
            inRange = true;
        }

        return inRange;
    }

    public void calculateDamage(Tower t, Vectoid v) {

        //drawLaser(t, v);
        v.setHp(v.getHp() - t.getDamage());
        if (v.getHp() <= 0) {
            vectoidList.remove(v);
            t.removeTarget(v);
            t.currentTargets--;
            repaint();
            money +=10;

            }


    }

    public void spawnVectoids(){
        ArrayList<Point> spawnPoints = getSpawnPoints();
        for(Point p : spawnPoints)
            wave.spawnVectoid(vectoidList, p);

    }

    public void drawLaser(Graphics2D g2){
        int vx, vy;
        ArrayList<Vectoid> targetList;
        for (Tower t : towerList) {
            targetList = t.getTargets();
            for (Vectoid v : targetList) {
                vx = 0;
                vy = 0;
                if (v != null) {
                    vx = (v.getCurrentPosition().x * CELL_SIZE);
                    vy = (v.getCurrentPosition().y * CELL_SIZE);
                    switch (v.getTrajectory()) {
                        case 'd' -> vy += v.getPositionOffset();
                        case 'r' -> vx += v.getPositionOffset();
                        case 'l' -> vx -= v.getPositionOffset();
                        case 'u' -> vy -= v.getPositionOffset();
                        default -> System.out.println("Unkown trajectory");
                    }
                }


                g2.setColor(Color.red);

                g2.drawLine(t.getPosition().x * CELL_SIZE + CELL_SIZE / 2, t.getPosition().y * CELL_SIZE + CELL_SIZE / 2, vx + CELL_SIZE + CELL_SIZE / 2, vy + CELL_SIZE + CELL_SIZE / 2);
                /*
                BasicStroke stroke;
                stroke = new BasicStroke(3, 2, 2);
                g2.setStroke(stroke);
                stroke = new BasicStroke(1, 2, 2);
                g2.setStroke(stroke);

                 */
            }
        }
    }
}



