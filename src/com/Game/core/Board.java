package com.Game.core;

import com.Game.Towers.*;
import com.Game.InputHandler.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//import java.nio.Buffer;

public class Board extends JPanel implements Runnable {

    boolean tested = false;
    boolean showRanges =  true;
    Tower tower;
    boolean activeWave;
    boolean towerClicked;
    Thread thread = new Thread(this);
    JButton templateButton = new JButton();
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();
    Map map = new Map();
    private Tower[][] towerMap = new Tower[22][18];
    private BufferedImage background;
    private BufferedImage vectoidImage;
    private final int CELL_SIZE=53;
    private Frame frame;
    public Vectoid [] vectoidList = new Vectoid[2];
    MouseInput mouse = new MouseInput(this);

    public Board(Frame frame){
        this.frame = frame;
        //Component textArea = new TextArea("Click here");
        thread.start();
        this.frame.addMouseListener(mouse);
    }
    private void initBoard(){

        loadImage();
        //setPreferredSize(new Dimension(h, w));
    }
    private void loadImage(){
        try{
            background = ImageIO.read(new File("resources/black_bg.jpg"));
        }catch (IOException e){
            System.out.println("Image could not be loaded");
        }

        background = scale(background, w, h);


    }

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

        if(towerClicked){


            drawRangeOnMovingTower(tower, g2);
            paintTowerClicked(g2);

        }
        if (activeWave) {
            paintVectoids(g2);

        }


    }

    public void run() {
        initBoard();
        System.out.println("im running");
        while(true)
            repaint();
        //update();

    }

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

    private void paintTowerMap(Graphics2D g2){

        for(int x = 0; x < 22; x++){
            for (int y = 0; y < 18; y++){
                if(towerMap[x][y] != null){

                    switch (towerMap[x][y].getId()) {
                        case 0 -> g2.setColor(Color.blue);
                        case 1 -> g2.setColor(Color.green);
                        case 2 -> g2.setColor(Color.magenta);
                        case 3 -> g2.setColor(Color.red);
                        default -> System.out.println("este id no existe");
                    }


                    g2.drawRect(CELL_SIZE + (x * CELL_SIZE), (CELL_SIZE) + (y* CELL_SIZE), CELL_SIZE, CELL_SIZE);
                    if(showRanges)
                        drawRange(towerMap[x][y], x, y, g2);
                }




            }
        }

    }

    private void paintGameMap(Graphics2D g2){
        g2.setColor(Color.lightGray);

        for(int x = 0; x < 22; x++){
            for (int y = 0; y < 18; y++){
                g2.setColor(Color.lightGray);
                g2.drawRect(CELL_SIZE + (x * CELL_SIZE), (CELL_SIZE) + (y* CELL_SIZE), CELL_SIZE, CELL_SIZE);


            }
        }
    }

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

    private void paintBottomMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = CELL_SIZE;
        int yInit = CELL_SIZE * 20;
        g2.drawRect(xInit, yInit, CELL_SIZE * 22, CELL_SIZE * 6);

    }

    //TODO: Bucle para generar los botones que conforman el menu de torres
    private void paintTowerMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);

        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );
        for (int i = 0; i <= 3; i++){
            switch (i) {
                case 0 -> g2.setColor(Color.blue);
                case 1 -> g2.setColor(Color.green);
                case 2 -> g2.setColor(Color.MAGENTA);
                case 3 -> g2.setColor(Color.RED);
                default -> System.out.println("este id no existe");
            }

            g2.drawRect( xInit + CELL_SIZE, yInit + CELL_SIZE, CELL_SIZE,CELL_SIZE);
            xInit = xInit + CELL_SIZE*2;

        }
        g2.setColor(Color.lightGray);

    }

    private void paintBonusMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = CELL_SIZE * 36;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );

    }

    private void paintInfoMenu(Graphics2D g2){
        g2.setColor(Color.lightGray);
        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE * 13;
        g2.drawRect( xInit, yInit, CELL_SIZE * 23,CELL_SIZE * 6 );

    }

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

    private void paintVectoids(Graphics2D g2){
        try{
            vectoidImage = ImageIO.read(new File("resources/Vectoids/BlueVectoid.png"));
        }catch (IOException e){
            System.out.println("Image could not be loaded");
        }

        for (Vectoid v : vectoidList) {
            Point p = v.getCurrentPosition();
            g2.drawImage(vectoidImage, (p.x + 1) * CELL_SIZE + (vectoidImage.getWidth() / 2), (p.y + 1) * CELL_SIZE + (vectoidImage.getHeight() / 2), null);

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
                placeTower(x, y);
                towerClicked = false;
            }else{
                System.out.println("gotta work this function still");
                //TODO: check if there's a tower in this position and call the appropiate function
            }
        }

    }

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
            tower = new BlueTower(0, 500, 5, 5, "blue");
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 27) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 28) && y<= (CELL_SIZE * 3))
        {
            tower = new GreenTower(1, 200, 5, 5, "green");
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 29) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 30) && y<= (CELL_SIZE * 3))
        {
            tower = new PurpleTower(2, 250, 5, 5, "purple");
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 31) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 32) && y<= (CELL_SIZE * 3))
        {
            tower = new RedTower(3, 300, 5, 5, "red");
            towerClicked = true;
        }

    }

    public void newWave() {

        Wave w = new Wave(this);
        w.setSpawnPoint(getSpawnPoints());
        vectoidList = new Vectoid[2];
        w.startWave(2);

        activeWave = true;

    }

    public Point[] getSpawnPoints(){
        Point s;
        Point[] spawnPoints = new Point[2];
        int i = 0;
        for(int x = 0; x < 22; x++) {
            for (int y = 0; y < 18; y++) {
                if (map.getPosition(x, y) == 2) {
                    s = new Point(x, y);
                    spawnPoints[i] = s;
                    i++;
                }

            }
        }
        return spawnPoints;
    }

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
                g2.setColor(Color.magenta);
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
            towerMap[posX-1][posY-1] = tower;
        }


    }

    public void drawRange(Tower t, int x, int y, Graphics2D g2) {

        int range =  t.getRange();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 255, 60));
        g2.fillOval((((x+1)*CELL_SIZE)+CELL_SIZE/2)-range/2, (((y+1)*CELL_SIZE)+CELL_SIZE/2)-range/2, range, range);



    }

    public void drawRangeOnMovingTower(Tower t, Graphics2D g2){
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        //Ellipse2D.Double shape = new Ellipse2D.Double(x, y, 500, 500);
        g2.setColor(Color.blue);
        g2.drawOval(x-(t.getRange()/2), y-(t.getRange()/2), t.getRange(), t.getRange());
        g2.setColor(new Color(0, 0, 255, 60));
        g2.fillOval(x-(t.getRange()/2), y-(t.getRange()/2), t.getRange(), t.getRange());

    }

}



