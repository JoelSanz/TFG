package com.Game.core;

import com.Game.Towers.*;
import com.Game.InputHandler.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//import java.nio.Buffer;

public class Board extends JPanel implements Runnable {

    boolean tested = false;
    Tower tower;
    boolean towerClicked;
    Thread thread = new Thread(this);
    JButton templateButton = new JButton();
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();
    Map map = new Map();
    private Tower[][] towerMap = new Tower[22][18];
    private BufferedImage background;
    private final int CELL_SIZE=53;
    private Frame frame;
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
        drawGameMap(g2);
        drawTowerMenu(g2);
        drawBottomMenu(g2);
        drawBonusMenu(g2);
        drawInfoMenu(g2);
        drawOptionsMenu(g2);
        drawTowerMap(g2);
        if(towerClicked){
            paintTowerClicked(g2);
        }


    }

    public void run() {
        initBoard();
        System.out.println("im running");
        while(true)
            repaint();
        //update();

    }
    //Function that placed towers at random no longer needed
    /*
    public void testTowerMap(int iterations) {
        for (int i = 0; i <= iterations; i++){
            boolean pass = true;
            int upperbound = 0, xPlace = 0, yPlace = 0;

            while (pass) {
                Random rand = new Random();
                upperbound = 22;
                xPlace = rand.nextInt(upperbound);
                upperbound = 18;
                yPlace = rand.nextInt(upperbound);
                if (map.getPosition(xPlace, yPlace) != 1 && towerMap[xPlace][yPlace] == null) {

                    int id = rand.nextInt(4);
                    switch (id){
                        case 0:
                            BlueTower blueTower = new BlueTower(id, 5, 5, 5, "blue");
                            towerMap[xPlace][yPlace] = blueTower;
                            break;
                        case 1:
                            RedTower redTower = new RedTower(id, 5, 5, 5, "red");
                            towerMap[xPlace][yPlace] = redTower;
                            break;
                        case 2:
                            PurpleTower purpleTowerTower = new PurpleTower(id, 5, 5, 5, "purple");
                            towerMap[xPlace][yPlace] = purpleTowerTower;
                            break;
                        case 3:
                            GreenTower gTower = new GreenTower(id, 5, 5, 5, "green");
                            towerMap[xPlace][yPlace] = gTower;
                            break;
                        default:
                            System.out.println("No se que id es este");
                    }



                    pass = false;
                }else{
                    System.out.println("Ya hay torre o es camino");
                }
            }


        }
    }
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
    private void drawTowerMap(Graphics2D g2){
        for(int x = 0; x < 22; x++){
            for (int y = 0; y < 18; y++){
                if(towerMap[x][y] != null){

                    switch (towerMap[x][y].getId()) {
                        case 0 -> g2.setColor(Color.blue);
                        case 1 -> g2.setColor(Color.green);
                        case 2 -> g2.setColor(Color.MAGENTA);
                        case 3 -> g2.setColor(Color.pink);
                        default -> System.out.println("este id no existe");
                    }

                    g2.drawRect(CELL_SIZE + (x * CELL_SIZE), (CELL_SIZE) + (y* CELL_SIZE), CELL_SIZE, CELL_SIZE);
                }



            }
        }

    }
    private void drawGameMap(Graphics2D g2){

        for(int x = 0; x < 22; x++){
            for (int y = 0; y < 18; y++){
                if(map.getPosition(x, y)==1){
                    g2.setColor(Color.RED);
                }else{
                    g2.setColor(Color.lightGray);
                }
                g2.drawRect(CELL_SIZE + (x * CELL_SIZE), (CELL_SIZE) + (y* CELL_SIZE), CELL_SIZE, CELL_SIZE);


            }
        }
    }

    private void drawBottomMenu(Graphics2D g2){
        int xInit = CELL_SIZE;
        int yInit = CELL_SIZE * 20;
        g2.drawRect(xInit, yInit, CELL_SIZE * 22, CELL_SIZE * 6);

    }

    //TODO: Bucle para generar los botones que conforman el menu de torres
    private void drawTowerMenu(Graphics2D g2){

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

    private void drawBonusMenu(Graphics2D g2){
        int xInit = CELL_SIZE * 36;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );

    }

    private void drawInfoMenu(Graphics2D g2){
        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE * 13;
        g2.drawRect( xInit, yInit, CELL_SIZE * 23,CELL_SIZE * 6 );

    }

    private void drawOptionsMenu(Graphics2D g2){
        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE * 20;
        g2.drawRect( xInit, yInit, CELL_SIZE * 23,CELL_SIZE * 6 );


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
            System.out.println("click registrado en el menu de bonuses");
        }

        //Checks if the click was made in the Options menu

        else if(x >= CELL_SIZE * 24 && y >= CELL_SIZE * 20 && x<= CELL_SIZE * 24 + (CELL_SIZE * 23) && y <= CELL_SIZE * 20 + (CELL_SIZE * 6)){
            System.out.println("Click registrado en el menu de opciones");

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

    /**
     *  Checks where in the Tower menu the click was made
     *  if it was made on a tower, places one tower of that type in a random location(will change into a fixed location in the future)
     * @param x An integer containing the x coordinate on screen of the mouse click
     * @param y An integer containing the y coordinate on screen of the mouse click
     */
    private void towerMenuClicked(int x, int y){

        if(x >= (CELL_SIZE * 25) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 26) && y<= (CELL_SIZE * 3))
        {
            tower = new BlueTower(0, 5, 5, 5, "blue");
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 27) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 28) && y<= (CELL_SIZE * 3))
        {
            tower = new GreenTower(1, 5, 5, 5, "green");
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 29) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 30) && y<= (CELL_SIZE * 3))
        {
            tower = new PurpleTower(2, 5, 5, 5, "purple");
            towerClicked = true;
        }
        else if(x >= (CELL_SIZE * 31) && y >= (CELL_SIZE * 2) && x<= (CELL_SIZE * 32) && y<= (CELL_SIZE * 3))
        {
            tower = new RedTower(3, 5, 5, 5, "red");
            towerClicked = true;
        }

    }
    //Generate Random position function no longer needed
/*


    /**
     * generates a random x, y position
     * @return An integer array containing the position generated

    private int[] generateRandomPosition(){
        boolean pass = true;
        int position[] = new int[2];
        while (pass) {
            int upperbound = 0, xPlace = 0, yPlace = 0;
            Random rand = new Random();
            upperbound = 22;
            xPlace = rand.nextInt(upperbound);
            upperbound = 18;
            yPlace = rand.nextInt(upperbound);
            if (map.getPosition(xPlace, yPlace) != 1 && towerMap[xPlace][yPlace] == null) {

                BlueTower blueTower = new BlueTower(0, 5, 5, 5, "blue");
                towerMap[xPlace][yPlace] = blueTower;
                pass = false;

            }
            position[0] = xPlace;
            position[1] = yPlace;

        }
        return position;

    }
*/
    private void paintTowerClicked(Graphics2D g2){
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        switch (tower.getTowerType()) {
            case "blue" -> {
                g2.setColor(Color.blue);
                g2.drawRect(mouseLocation.x - (CELL_SIZE / 2), mouseLocation.y - (CELL_SIZE / 2), CELL_SIZE, CELL_SIZE);
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
        if(map.getPosition(posX-1, posY-1)==1) {
            System.out.println("Can't place a tower there");
        }else{
            towerMap[posX-1][posY-1] = tower;
        }


    }
}



