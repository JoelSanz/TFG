package com.Game.core;

import com.Game.InputHandler.MouseInput;
import com.Game.Towers.BlueTower;
import com.Game.Towers.GreenTower;
import com.Game.Towers.PurpleTower;
import com.Game.Towers.RedTower;
import com.Game.Vectoids.BlueVectoid;
import com.Game.Vectoids.GreenVectoid;
import com.Game.Vectoids.RedVectoid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//import java.nio.Buffer;

public class Board extends JPanel implements Runnable {
    private VectoidAI ai;
    private int frameCount = 0, waitTime = 0, hp, interestCost = 100, reward;
    boolean showRanges =  true, waveSpawnig = false, activeWave = false, towerClicked, nextWavePrev = true, heatMapActive;
    Tower tower, towerSelected;
    Thread thread = new Thread(this);
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();
    Map map = new Map();
    private ArrayList<Tower> towerList = new ArrayList<Tower>(1);
    private BufferedImage background, vectoidImage, blueTower1, blueTower2, blueTower3, redTower1, redTower2 ,redTower3, greenTower1, greenTower2, greenTower3, purpleTower1, purpleTower2,
            purpleTower3, interestBonus, hpBonus, blueVectoid, greenVectoid, redVectoid, purpleVectoid;
    private int cellSize, fontSize;
    private Frame frame;
    Tower[][] towerMap= new Tower[22][18];
    int [][] heatMap = new int[22][18];
    public ArrayList<Vectoid> vectoidList = new ArrayList<Vectoid>(1);
    public ArrayList<Color> palette = new ArrayList<Color>(6);
    MouseInput mouse = new MouseInput(this);
    Wave wave;
    double money;
    String lastInfoMessage = "Game Start";
    double interestRate = 3;
    DPSChart dpsMeter;
    int waveCounter = 0;
    Vectoid vectoidNextWave, vectoidThisWave;

    public Board(Frame frame){
        switch(w){
            case 2560:
                cellSize = 53;
                fontSize = 25;
                break;
            case 1920:
                cellSize = 40;
                fontSize = 19;
                break;
            case 1680:
                cellSize = 35;
                fontSize = 17;
                break;
            case 1600:
                cellSize = 33;
                fontSize = 16;
                break;
            case 1280:
                cellSize = 27;
                fontSize = 13;
                break;

        }
        this.frame = frame;
        heatMapActive = false;
        this.setLayout(null);
        this.dpsMeter = new DPSChart("Damage this Round", cellSize);
        this.add(dpsMeter.getPane());
        //this.remove(dpsMeter.getPane());
        thread.start();
        this.frame.addMouseListener(mouse);



    }

    /**
     *
     */
    private void initBoard(){

        loadImage();
        ai = new VectoidAI(map, cellSize);
        money = 275;
        hp = 25;
        reward = 3;
        vectoidNextWave = new GreenVectoid(600, greenVectoid);
        vectoidThisWave = vectoidNextWave;
        initPalette();
        //setPreferredSize(new Dimension(h, w));
    }

    /**
     *
     */
    private void loadImage(){
        try{
            background = ImageIO.read(new File("resources/black_bg.jpg"));
            blueTower1 = ImageIO.read(new File("resources/TowerSprites/BlueTower1.png"));
            blueTower2 = ImageIO.read(new File("resources/TowerSprites/BlueTower2.png"));
            blueTower3 = ImageIO.read(new File("resources/TowerSprites/BlueTower3.png"));
            greenTower1 = ImageIO.read(new File("resources/TowerSprites/GreenTower1.png"));
            greenTower2 = ImageIO.read(new File("resources/TowerSprites/GreenTower2.png"));
            greenTower3 = ImageIO.read(new File("resources/TowerSprites/GreenTower3.png"));
            redTower1 = ImageIO.read(new File("resources/TowerSprites/RedTower1.png"));
            redTower2 = ImageIO.read(new File("resources/TowerSprites/RedTower2.png"));
            redTower3 = ImageIO.read(new File("resources/TowerSprites/RedTower3.png"));
            purpleTower1 = ImageIO.read(new File("resources/TowerSprites/RedTower1.png"));
            purpleTower2 = ImageIO.read(new File("resources/TowerSprites/RedTower1.png"));
            purpleTower3 = ImageIO.read(new File("resources/TowerSprites/RedTower1.png"));
            interestBonus = ImageIO.read(new File("resources/Bonus/interestBonus.png"));
            hpBonus = ImageIO.read(new File("resources/Bonus/hpBonus.png"));
            blueVectoid  = ImageIO.read(new File("resources/Vectoids/BlueVectoid.png"));
            greenVectoid  = ImageIO.read(new File("resources/Vectoids/GreenVectoid.png"));
            redVectoid  = ImageIO.read(new File("resources/Vectoids/RedVectoid.png"));

        }catch (IOException e){
            System.out.println("Image could not be loaded");
        }
        blueTower1 = scale(blueTower1, cellSize, cellSize);
        blueTower2 = scale(blueTower2, cellSize, cellSize);
        blueTower3 = scale(blueTower3, cellSize, cellSize);
        redTower1 = scale(redTower1, cellSize, cellSize);
        redTower2 = scale(redTower2, cellSize, cellSize);
        redTower3 = scale(redTower3, cellSize, cellSize);
        greenTower1 = scale(greenTower1, cellSize, cellSize);
        greenTower2 = scale(greenTower2, cellSize, cellSize);
        greenTower3 = scale(greenTower3, cellSize, cellSize);
        purpleTower1 = scale(purpleTower1, cellSize, cellSize);
        purpleTower2 = scale(purpleTower2, cellSize, cellSize);
        purpleTower3 = scale(purpleTower3, cellSize, cellSize);
        interestBonus = scale(interestBonus, cellSize, cellSize);
        hpBonus = scale(hpBonus, cellSize, cellSize);
        blueVectoid = scale(blueVectoid, cellSize, cellSize);
        greenVectoid = scale(greenVectoid, cellSize, cellSize);
        redVectoid = scale(redVectoid, cellSize, cellSize);



        background = scale(background, w, h);


    }

    /**
     *
     * @param g Instance of Graphics class
     */
    public void paintComponent(Graphics g){


        Graphics2D g2 =  (Graphics2D) g;
        g.drawImage(background, 0, 0, null);
        g2.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));

        paintGameMap(g2);
        paintEnemyPath(g2);
        paintTowerMenu(g2);
        paintChartMenu(g2);
        paintBonusMenu(g2);
        paintInfoMenu(g2);
        paintOptionsMenu(g2);
        paintTowerMap(g2);
        drawLaser(g2);

        if(towerClicked){
            drawRangeOnMovingTower(towerSelected, g2);
            paintTowerClicked(g2);
        }
        if (activeWave) {
            paintVectoids(g2);

        }


    }

    /**
     *
     */
    @Override
    public void run() {
        initBoard();

        while(true) {
            repaint();

            if(waveSpawnig && waitTime > 20000000 && !wave.doneSpawning()){
                spawnVectoids();
                waitTime = 0;
            }
            if(frameCount > 1500000) {

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

        Vectoid[] vectoidArray = new Vectoid[vectoidList.size()];
        vectoidList.toArray(vectoidArray);

        for (Vectoid v : vectoidArray) {
            if(v != null) {
                if (ai.hasArrived(v)) {
                    hp--;
                    deleteVectoid(v);
                    v.setArrived(true);
                }else{
                    ai.CalculateMove(v);
                }


                for (Tower t : towerList) {
                    if (t != null) {
                        if (t.targets.contains(v)) {
                            if (!isInRange(t, v)) {
                                t.targets.remove(v);
                                t.currentTargets--;
                            }else{
                                calculateDamage(t, v);
                                if (v.getHp() <= 0) {
                                    heatMap[v.getCurrentPosition().x][v.getCurrentPosition().y]++;
                                    deleteVectoid(v);
                                    repaint();
                                    money += reward + (reward * (interestRate/100));
                                    break;
                                }
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
        if(activeWave && vectoidList.isEmpty()){
            activeWave = false;
            lastInfoMessage = "Congratulations you finished the wave!";
            vectoidThisWave = vectoidNextWave;
            reward++;


        }
        vectoidList.clear();
        for(Vectoid v : vectoidArray){
            if(v.getHp()>0 && !v.isArrived())
                vectoidList.add(v);
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

                    g2.drawImage(t.getSprite(), t.getPosition().x * cellSize, t.getPosition().y * cellSize, null);

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
                g2.drawRect(cellSize + (x * cellSize), (cellSize) + (y* cellSize), cellSize, cellSize);


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
        int th = 1;
        for(int x = 0; x < 22; x++) {
            for (int y = 0; y < 18; y++) {
                if(map.getPosition(x, y) != 0) {
                    if (map.getPosition(x, y) == 1) {

                        g2.setColor(new Color(255, 255, 255, 80));
                        g2.fillRect(cellSize + (x * cellSize), (cellSize) + (y* cellSize), cellSize, cellSize);
                        g2.setColor(Color.white);
                        g2.setStroke(stroke);
                    } else if (map.getPosition(x, y) == 2) {

                        g2.setColor(new Color(255, 255, 0, 80));
                        g2.fillRect(cellSize + (x * cellSize), (cellSize) + (y* cellSize), cellSize, cellSize);
                        g2.setColor(Color.yellow);
                        g2.setStroke(stroke);
                    } else if (map.getPosition(x, y) == 3) {

                        g2.setColor(new Color(255, 0, 0, 80));
                        g2.fillRect(cellSize + (x * cellSize), (cellSize) + (y* cellSize), cellSize, cellSize);
                        g2.setColor(Color.red);
                        g2.setStroke(stroke);

                    }
                    g2.drawRect(cellSize + (x * cellSize), (cellSize) + (y* cellSize), cellSize, cellSize);

                }
            }
        }

        if(heatMapActive) {

            th = calculateHeatMapThreshold(heatMap, palette);
            int value = 0;
            for (int i = 0; i<=palette.size(); i++){
                for (int x = 0; x < 22; x++) {
                    for (int y = 0; y < 18; y++) {
                        value = heatMap[x][y];
                        if(value<=th*(i+1) && value>th*i && value!=0) {
                            if(i==0)
                                g2.setColor(palette.get(i));
                            else
                                g2.setColor(palette.get(i-1));
                            g2.fillRect(cellSize + (x * cellSize), (cellSize) + (y * cellSize), cellSize, cellSize);
                        }else if(i == palette.size() && value>th*(i)){
                            g2.setColor(palette.get(i-1));
                            g2.fillRect(cellSize + (x * cellSize), (cellSize) + (y * cellSize), cellSize, cellSize);
                        }
                    }
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
    private void paintChartMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = cellSize;
        int yInit = cellSize * 20;
        g2.drawRect(xInit, yInit, cellSize * 22, cellSize * 6);

    }

    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintTowerMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        BufferedImage blue, green, red, purple;
        int xInit = cellSize * 24 ;
        int yInit = cellSize;
        g2.drawRect( xInit, yInit, cellSize * 11, cellSize * 11 );
        blue = blueTower1;
        green = greenTower1;
        red = redTower1;
        purple = purpleTower1;
        for(int j = 0; j < 3; j++) {
            for (int i = 0; i <= 3; i++) {
                switch (i) {
                    case 0 -> g2.drawImage(green, xInit + cellSize, yInit + cellSize, null);
                    case 1 -> g2.drawImage(blue, xInit + cellSize, yInit + cellSize, null);
                    case 2 -> g2.drawImage(red, xInit + cellSize, yInit + cellSize, null);
                    case 3 -> g2.drawImage(purple, xInit + cellSize, yInit + cellSize, null);
                    default -> System.out.println("Unkown tower type");
                }
                xInit = xInit + cellSize * 2;

            }
            switch (j){

                case 0:
                    blue = blueTower2;
                    green = greenTower2;
                    red = redTower2;
                    purple = purpleTower2;
                    break;
                case 1 :
                    blue = blueTower3;
                    green = greenTower3;
                    red = redTower3;
                    purple = purpleTower3;
                    break;

            }
            xInit =  cellSize * 24 ;
            yInit = yInit + cellSize * 2;
        }

        xInit = cellSize * 24 ;

        g2.setColor(Color.lightGray);
        g2.drawLine(xInit, yInit + cellSize, xInit +(cellSize * 11), yInit + cellSize);

        //Draw interest rate Bonus

        g2.drawImage(interestBonus, xInit + cellSize, yInit + (cellSize * 2), null);

        //Draw Hp Bonus

        g2.drawImage(hpBonus, xInit + cellSize * 3, yInit + (cellSize * 2), null);


    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintBonusMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = cellSize * 36;
        int yInit = cellSize;
        g2.drawRect( xInit, yInit, cellSize * 11, cellSize * 11 );

        //Paint current money

        g2.drawString("Current money: "+(int) money+"€", xInit + cellSize, yInit + cellSize);

        //Paint current hp

        g2.drawString("Current HP: "+hp, xInit + cellSize, yInit + cellSize *2);

        //Paint interest rate

        g2.drawString("Current interest rate: "+ (int) interestRate + "%", xInit + cellSize, yInit + cellSize *3);

        //Paint last Error Message

        g2.drawString(lastInfoMessage, xInit + cellSize, yInit + cellSize *4);

        g2.drawLine(xInit, yInit + cellSize * 5, xInit + cellSize * 11, yInit + cellSize * 5);

        //displays info of the selected tower
        if(towerSelected != null){
            g2.drawString("Tower: "+ towerSelected.getTowerType(), xInit + cellSize, yInit + cellSize *6);
            g2.drawString("Range: " + towerSelected.getRange()/ cellSize, xInit + cellSize, yInit + cellSize * 7);
            g2.drawString("Damage: "+ towerSelected.getDamage(), xInit + cellSize, yInit + cellSize *8);
            g2.drawString("Cost: "+ towerSelected.getCost(), xInit + cellSize, yInit + cellSize *9);
            g2.drawRect(xInit + cellSize*5+cellSize/3, yInit + (cellSize * 5)+ cellSize/2, cellSize *3, cellSize);
            g2.drawString("Sell Tower", xInit + cellSize*6-cellSize/4, yInit + (cellSize * 6) +cellSize/6);
            g2.drawRect(xInit + cellSize*5+cellSize/3, yInit + (cellSize * 7)+ cellSize/2, cellSize *3, cellSize);
            g2.drawString("Upgrade",xInit + cellSize*6-cellSize/10, yInit + (cellSize * 8) +cellSize/6);


        }

    }
    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintInfoMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = cellSize * 24 ;
        int yInit = cellSize * 13;
        g2.drawRect( xInit, yInit, cellSize * 23, cellSize * 6 );



        //Paint tower Description

        if(tower!=null)
            g2.drawString(towerSelected.getDescription(), xInit + cellSize, yInit + cellSize);

        if(vectoidNextWave != null) {
            g2.drawImage(vectoidThisWave.getSprite(), xInit + cellSize, yInit + cellSize * 2, null);
            g2.drawString(vectoidThisWave.getHp() + " HP", xInit + cellSize + vectoidThisWave.getSprite().getWidth() + cellSize /2, yInit + cellSize * 2 +vectoidThisWave.getSprite().getHeight()/2);
        }


    }

    /**
     *
     * @param g2 Instance of Graphics2D class
     */
    private void paintOptionsMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);

        int xInit = cellSize * 24 ;
        int yInit = cellSize * 20;
        g2.drawRect( xInit, yInit, cellSize * 23, cellSize * 6 );

        //Draw "Spawn vectoids" button
        g2.drawRect(xInit + cellSize, yInit + cellSize/2, cellSize * 5, cellSize * 2);

        g2.drawString("Spawn Vectoids", xInit + cellSize *2, yInit + cellSize + cellSize/2);

        //Draw "Show ranges" button
        g2.drawRect(xInit + cellSize * 7, yInit + cellSize /2, cellSize * 5, cellSize * 2);

        if(showRanges)
            g2.drawString("Hide Ranges", xInit + cellSize * 8, yInit + cellSize + cellSize/2);
        else
            g2.drawString("Draw Ranges", xInit + cellSize * 8, yInit + cellSize + cellSize/2);

        g2.drawRect(xInit + cellSize * 13, yInit + cellSize/2, cellSize * 4, cellSize * 2);
        g2.drawString("Exit Game", xInit + cellSize * 14, yInit + cellSize + cellSize/2);;

        g2.drawRect(xInit + cellSize, yInit + cellSize * 3, cellSize * 5, cellSize * 2);
        if(heatMapActive)
            g2.drawString("hide Death heat map", xInit + cellSize + cellSize/2, yInit + cellSize * 4);
        else
            g2.drawString("show Death heat map", xInit + cellSize + cellSize/2, yInit + cellSize * 4);

    }

    /**
     *
     * Draws all vectoids present in vectoidList
     * @param g2 Instance of Graphics2D class
     */
    private void paintVectoids(Graphics2D g2){

        Vectoid[] vectoidArray = new Vectoid[vectoidList.size()];
        vectoidList.toArray(vectoidArray);
        for (Vectoid v : vectoidArray) {
            vectoidImage = scale(v.getSprite(), cellSize /2, cellSize /2 );
            if(v != null){
                Point p = v.getCurrentPosition();
                int px, py;
                px = (p.x + 1) * cellSize + (vectoidImage.getWidth() / 2);
                py = (p.y + 1) * cellSize + (vectoidImage.getHeight() / 2);
                switch (v.getTrajectory()) {
                    case 'd' -> py = (p.y + 1) * cellSize + (vectoidImage.getHeight() / 2) + v.getPositionOffset();
                    case 'r' -> px = (p.x + 1) * cellSize + (vectoidImage.getWidth() / 2) + v.getPositionOffset();
                    case 'l' -> px =  (p.x + 1) * cellSize + (vectoidImage.getWidth() / 2) - v.getPositionOffset();
                    case 'u' -> py = (p.y + 1) * cellSize + (vectoidImage.getHeight() / 2) - v.getPositionOffset();
                    default -> System.out.println("Unkown trajectory on painting vectoids");
                }
                g2.drawImage(vectoidImage, px, py, null);
                //Draw Hp bar
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

        if(x >= cellSize * 24 && y >= cellSize && x<= cellSize * 24 + (cellSize * 11) && y <= cellSize + (cellSize * 11))
        {
            towerMenuClicked(x, y);
            paintComponent(this.getGraphics());
        }
        //Checks if the click was made in the Bonus Menu

        else  if(x >= cellSize * 36 && y >= cellSize && x<= cellSize * 36 + (cellSize * 11) && y <= cellSize + (cellSize * 11)){

            bonusMenuClicked(x, y);
        }

        //Checks if the click was made in the Options menu

        else if(x >= cellSize * 24 && y >= cellSize * 20 && x<= cellSize * 24 + (cellSize * 23) && y <= cellSize * 20 + (cellSize * 6)){
            optionsMenuClicked(x, y);

        }
        //Checks if the click was made in the game map
        else if(x >= cellSize && y >= cellSize && x<= cellSize * 24 && y <= cellSize * 19){
            if(this.towerClicked){
                if(money>=towerSelected.getCost()) {
                    if(placeTower(x, y)) {
                        money = money - towerSelected.getCost();
                        towerSelected = null;
                    }

                }
                else{
                    lastInfoMessage = "you broke ass bitch, you got no monaaaaay";
                }
                towerClicked = false;
            }else{
               int posX = x / cellSize;
               int posY = y / cellSize;
               if(towerMap[posX][posY] != null) {
                   towerSelected = towerMap[posX][posY];
                   drawTowerChart(towerSelected);
               }else{
                   drawGameChart();
               }
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
        if(x >= (cellSize * 25) && y >= (cellSize * 20 + cellSize/2) && x<= (cellSize * 30) && y<= (cellSize * 22 + cellSize/2)){
            if(!activeWave)
                newWave();
            else
                lastInfoMessage = "Wave still in progress";
        }

        if(x >= (cellSize * 31) && y >= (cellSize * 20 + cellSize/2) && x<= (cellSize * 36) && y<= (cellSize * 22 + cellSize/2)){
            showRanges = !showRanges;

        }
        if(x >= (cellSize * 37) && y >= (cellSize * 20 + cellSize/2) && x<= (cellSize * 41) && y<= (cellSize * 22 + cellSize/2)){
            System.exit(0); // stop program
            frame.dispose(); // close window

        }
        if(x >= (cellSize * 25) && y >= (cellSize * 22 + cellSize/2) && x<= (cellSize * 30) && y<= (cellSize * 25)){
            heatMapActive = !heatMapActive;
        }
    }
    /**
     *  Checks where in the Tower menu the click was made
     *  if it was made on a tower, places one tower of that type in a random location(will change into a fixed location in the future)
     * @param x An integer containing the x coordinate on screen of the mouse click
     * @param y An integer containing the y coordinate on screen of the mouse click
     */
    private void towerMenuClicked(int x, int y){

        if(x >= (cellSize * 25) && y >= (cellSize * 2) && x<= (cellSize * 26) && y<= (cellSize * 3))
        {
            //tower = new GreenTower(cellSize, greenTower1);
            towerSelected = new GreenTower(cellSize, greenTower1);
            towerClicked = true;

        }
        else if(x >= (cellSize * 27) && y >= (cellSize * 2) && x<= (cellSize * 28) && y<= (cellSize * 3))
        {
            //tower = new BlueTower(cellSize, blueTower1);
            towerSelected = new BlueTower(cellSize, blueTower1);
            towerClicked = true;
        }
        else if(x >= (cellSize * 29) && y >= (cellSize * 2) && x<= (cellSize * 30) && y<= (cellSize * 3))
        {
            //tower = new RedTower(cellSize, redTower1);
            towerSelected = new RedTower(cellSize, redTower1);
            towerClicked = true;

        }else if(x >= (cellSize * 31) && y >= (cellSize * 2) && x<= (cellSize * 32) && y<= (cellSize * 4))
        {
            //tower = new PurpleTower(cellSize, purpleTower1);
            towerSelected = new PurpleTower(cellSize, purpleTower1);
            towerClicked = true;

        }else if(x >= (cellSize * 25) && y >= (cellSize * 4) && x<= (cellSize * 26) && y<= (cellSize * 5))
        {
            //tower = new GreenTower(cellSize, greenTower2);
            towerSelected = new GreenTower(cellSize, greenTower2);
            towerClicked = true;

        }
        else if(x >= (cellSize * 27) && y >= (cellSize * 4) && x<= (cellSize * 28) && y<= (cellSize * 5))
        {

            //tower = new BlueTower(cellSize, blueTower2);
            towerSelected = new BlueTower(cellSize, blueTower2);
            towerClicked = true;
        }
        else if(x >= (cellSize * 29) && y >= (cellSize * 4) && x<= (cellSize * 30) && y<= (cellSize * 5))
        {
            //tower = new RedTower(cellSize, redTower2);
            towerSelected = new RedTower(cellSize, redTower2);
            towerClicked = true;

        }
        else if(x >= (cellSize * 31) && y >= (cellSize * 4) && x<= (cellSize * 32) && y<= (cellSize * 5))
        {
            //tower = new PurpleTower(cellSize, purpleTower2);
            towerSelected = new PurpleTower(cellSize, purpleTower2);
            towerClicked = true;

        }else if(x >= (cellSize * 25) && y >= (cellSize * 6) && x<= (cellSize * 26) && y<= (cellSize * 7))
        {
            //tower = new GreenTower(cellSize, greenTower3);
            towerSelected = new GreenTower(cellSize, greenTower3);
            towerClicked = true;

        }
        else if(x >= (cellSize * 27) && y >= (cellSize * 6) && x<= (cellSize * 28) && y<= (cellSize * 7))
        {
            //tower = new BlueTower(cellSize, blueTower3);
            towerSelected = new BlueTower(cellSize, blueTower3);
            towerClicked = true;
        }
        else if(x >= (cellSize * 29) && y >= (cellSize * 6) && x<= (cellSize * 30) && y<= (cellSize * 7))
        {
            //tower = new RedTower(cellSize, redTower3);
            towerSelected = new RedTower(cellSize, redTower3);
            towerClicked = true;

        }
        else if(x >= (cellSize * 31) && y >= (cellSize * 6) && x<= (cellSize * 32) && y<= (cellSize * 7))
        {
            //tower = new PurpleTower(cellSize, purpleTower3);
            towerSelected = new PurpleTower(cellSize, purpleTower3);
            towerClicked = true;

        }else if(x >= (cellSize * 25) && y >= (cellSize * 9) && x<= (cellSize * 26) && y<= (cellSize * 10)){

            if(money>=interestCost) {
                money = money - interestCost;
                incBonusRate();
            }else
                lastInfoMessage = "You are broke";
        }else if(x >= (cellSize * 27) && y >= (cellSize * 9) && x<= (cellSize * 28) && y<= (cellSize * 10)){
            if(money >= 50){
                money = money - 50;
                hp = hp + 5;
            }else
                lastInfoMessage = "you are broke";
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
        waveCounter++;
        double hpMod = vectoidThisWave.getHp() + (vectoidThisWave.getHp() * 0.2);

        switch((int)(waveCounter % 4.0)){
            case 0 -> vectoidNextWave = new GreenVectoid((int)hpMod, greenVectoid);
            case 1, 3 -> vectoidNextWave = new RedVectoid((int)hpMod, redVectoid);
            case 2 -> vectoidNextWave = new BlueVectoid((int)hpMod, blueVectoid);
            //case 3 -> vectoidNextWave = new RedVectoid((int)hpMod, redVectoid);

        }
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
        int x = mouseLocation.x - (cellSize / 2);
        int y = mouseLocation.y - (cellSize / 2);
        g2.drawImage(towerSelected.getSprite(), x, y, null);


    }

    /**
     * Calculates which cell was clicked and places the tower on it if possible
     * @param x An integer containing the x coordinate on screen of the mouse click
     * @param y An integer containing the y coordinate on screen of the mouse click
     */
    private boolean placeTower(int x, int y){

        boolean placed = false;
        int posX = x / cellSize;
        int posY = y / cellSize;
        if(map.getPosition(posX-1, posY-1)!=0) {
            lastInfoMessage = "Can't place the tower on the path";
        }else if(towerMap[posX][posY] != null){
            lastInfoMessage = "There's a tower there already!";

        }else{
            placed = true;
            towerSelected.setPosition(new Point(posX, posY));
            towerList.add(towerSelected);
            towerMap[posX][posY] = towerSelected;
            towerSelected.setDpsChart(new DPSChart("Tower damage", cellSize));
            this.add(towerSelected.getDpsChart().getPane());
        }
        return placed;
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
        g2.fillOval((((x)* cellSize)+ cellSize /2)-range/2, (((y)* cellSize)+ cellSize /2)-range/2, range, range);




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
            vx = (v.getCurrentPosition().x * cellSize);
            vy = (v.getCurrentPosition().y * cellSize);
            switch (v.getTrajectory()) {
                case 'd' -> vy += v.getPositionOffset();
                case 'r' -> vx += v.getPositionOffset();
                case 'l' -> vx -= v.getPositionOffset();
                case 'u' -> vy -= v.getPositionOffset();
                default -> System.out.println("Unkown trajectory on range funcion");
            }
        }

        //Calculates wether vectoid v is in range of tower t  by the formula (x-centerX)^2+(y-centerY)^2 < radius^2
        //Where x, y are vx, vy and centerX, centerY are t.getPosition().x or .y radius is the range of the tower

        if(Math.pow(vx-((t.getPosition().x -1)  * cellSize), 2) + Math.pow(vy - ((t.getPosition().y -1 )* cellSize), 2) < Math.pow((t.getRange()/2), 2)) {
            inRange = true;
        }

        return inRange;
    }

    public void calculateDamage(Tower t, Vectoid v) {

        //drawLaser(t, v);
        double dmgMod = 1.0;
        if(t.delay == 0) {
            switch (t.getTowerType()){
                case "green":
                    if(v.getVectoidType() == "red")
                        dmgMod = 1.5;
                    break;
                case "red":
                    if(v.getVectoidType() == "green")
                        dmgMod = 1.5;
                    break;
                case "purple":
                    if(v.getVectoidType() == "blue")
                        dmgMod = 1.5;
                    break;
                case "blue":
                    if(v.getVectoidType() == "purple")
                        dmgMod = 1.5;
                    break;
                default:
                    lastInfoMessage = "Unkown Tower type";
                    break;


            }

            v.setHp(v.getHp() -(int) round(t.getDamage()*dmgMod, 0));
            dpsMeter.addData(t.getDamage());
            t.getDpsChart().addData(t.getDamage());
            //Afegeixo informacio al fitxer
            t.delay = t.maxDelay;
        }else{
            t.delay--;
        }


    }

    public void spawnVectoids(){
        ArrayList<Point> spawnPoints = getSpawnPoints();
        for(Point p : spawnPoints)
            wave.spawnVectoid(vectoidList, p, waveCounter, blueVectoid, vectoidThisWave);

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
                    vx = (v.getCurrentPosition().x * cellSize);
                    vy = (v.getCurrentPosition().y * cellSize);
                    switch (v.getTrajectory()) {
                        case 'd' -> vy += v.getPositionOffset();
                        case 'r' -> vx += v.getPositionOffset();
                        case 'l' -> vx -= v.getPositionOffset();
                        case 'u' -> vy -= v.getPositionOffset();
                        default -> System.out.println("Unkown trajectory on lazer painting");
                    }
                }


                g2.setColor(Color.red);

                g2.drawLine(t.getPosition().x * cellSize + cellSize / 2, t.getPosition().y * cellSize + cellSize / 2, vx + cellSize + cellSize / 2, vy + cellSize + cellSize / 2);
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
    public void drawTowerChart(Tower t){
        dpsMeter.getPane().setVisible(false);

        t.getDpsChart().setVisible(true);
        t.getDpsChart().getPane().setBounds(cellSize, cellSize * 20, cellSize * 22, cellSize * 6);

        frame.getContentPane().validate();
    }

    public void drawGameChart(){
        dpsMeter.getPane().setVisible(true);
    }
    public void incBonusRate(){
        interestRate += 0.1;
    }

    public void deleteVectoid(Vectoid v){
        for(Tower t : towerList){
            if(t.getTargets().lastIndexOf(v) >= 0){
                t.removeTarget(v);
                t.currentTargets--;
            }
        }
        vectoidList.remove(v);
    }

    public int calculateHeatMapThreshold(int[][] heatMap, ArrayList<Color> palette){

        int [][] auxMap = heatMap.clone();
        int [] aux;
        double highest = 0.0,lowest = 0.0;
        for (int i = 0; i < heatMap.length; i++){
            aux = auxMap[i].clone();
            Arrays.sort(aux);
            if(highest < aux[aux.length-1])
                highest = aux[aux.length-1];
            if(lowest > aux[0])
                lowest = aux[0];
        }
        System.out.println("highest kill number: " + highest + "Lowest kill number: "+ lowest);
        if(highest > 0) {
            if (highest > palette.size())
                return (int) (highest / palette.size());
            else
                return (int) (palette.size() / highest);
        }else
            return 0;
    }

    public void initPalette(){
        this.palette.add(0, new Color(0, 255, 0));
        this.palette.add(1, new Color(20,100,20));
        this.palette.add(2, new Color(255, 255, 0));
        this.palette.add(3, new Color(255, 140, 0));
        this.palette.add(4, new Color(205, 92, 92));
        this.palette.add(5, new Color(255, 0, 0));
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void bonusMenuClicked(int x, int y) {
        int xInit = cellSize * 36;
        int yInit = cellSize;
        if(x >= xInit + cellSize*5+cellSize/3 && y >= yInit + (cellSize * 5)+ cellSize/2 && x<= (xInit + cellSize*8+cellSize/3) && y <= yInit + (cellSize * 6)+ cellSize/2){
            //sell tower
            lastInfoMessage="tower sold!";
            sellTower(towerSelected);
        }else if(x >= xInit + cellSize*5+cellSize/3 && y >= yInit + (cellSize * 7)+ cellSize/2 && x<= xInit + cellSize*8+cellSize/3 && y <= yInit + (cellSize * 8)+ cellSize/2){
            //upgrade
            lastInfoMessage = "tower upgraded";
        }
/*
        g2.drawRect(xInit + cellSize*5+cellSize/3, yInit + (cellSize * 5)+ cellSize/2, cellSize *3, cellSize);
        g2.drawString("Sell Tower", xInit + cellSize*6-cellSize/4, yInit + (cellSize * 6) +cellSize/6);
        g2.drawRect(xInit + cellSize*5+cellSize/3, yInit + (cellSize * 7)+ cellSize/2, cellSize *3, cellSize);
        */
    }

    private void sellTower(Tower t){
        this.money = this.money + t.getCost();
        towerMap[t.getPosition().x][t.getPosition().y] = null;
        towerList.remove(t);
        towerSelected = null;
    }
}



