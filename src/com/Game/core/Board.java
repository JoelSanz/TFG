package com.Game.core;

import com.Game.Towers.*;
import com.Game.InputHandler.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//import java.nio.Buffer;

public class Board extends JPanel implements Runnable {

    Thread thread = new Thread(this);
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();
    Map map = new Map();
    private Tower[][] towerMap = new Tower[22][18];
    private BufferedImage background;
    private final int CELL_SIZE=53;
    private Frame frame;
    MouseInput mouse = new MouseInput();

    public Board(Frame frame){
        this.frame = frame;
        //Component textArea = new TextArea("Click here");
        initBoard();
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
        testTowerMap(5);
        drawTowerMap(g2);
        //thread.start();

    }

    private void testTowerMap(int iterations) {
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
                            BlueTower blueTower = new BlueTower(id, 5, 5, 5);
                            towerMap[xPlace][yPlace] = blueTower.getTower();
                            break;
                        case 1:
                            RedTower redTower = new RedTower(id, 5, 5, 5);
                            towerMap[xPlace][yPlace] = redTower.getTower();
                            break;
                        case 2:
                            PurpleTower purpleTowerTower = new PurpleTower(id, 5, 5, 5);
                            towerMap[xPlace][yPlace] = purpleTowerTower.getTower();
                            break;
                        case 3:
                            GreenTower gTower = new GreenTower(id, 5, 5, 5);
                            towerMap[xPlace][yPlace] = gTower.getTower();
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

                    switch (towerMap[x][y].getId()){
                        case 0:
                            g2.setColor(Color.blue);
                            break;
                        case 1:
                            g2.setColor(Color.yellow);
                            break;
                        case 2:
                            g2.setColor(Color.MAGENTA);
                            break;
                        case 3:
                            g2.setColor(Color.GREEN);
                            break;
                        default:
                            System.out.println("este id no existe");
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

    private void drawTowerMenu(Graphics2D g2){
        MouseEvent e = mouse.getEvent();
        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );
        if (e != null) {
            if (e.getLocationOnScreen().x >= xInit && e.getLocationOnScreen().x <= xInit + CELL_SIZE * 11 && e.getLocationOnScreen().y >= yInit && e.getLocationOnScreen().y <= yInit + CELL_SIZE * 11) {
                System.out.println("Estas en el menu de torre");
            }
        }
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

    @Override
    public void run() {


    }
}
