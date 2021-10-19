import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Board extends JPanel {
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int h = device.getDisplayMode().getHeight();
    int w = device.getDisplayMode().getWidth();
    Map map = new Map();
    private BufferedImage background;
    private int CELL_SIZE=40;


    public Board(){
        initBoard();
    }
    private void initBoard(){
        loadImage();


        setPreferredSize(new Dimension(1920, 1080));
    }
    private void loadImage(){
        try{
            background = ImageIO.read(new File("resources/black_bg.jpg"));
        }catch (IOException e){
            System.out.println("Image could not be loaded");
        }

        background = scale( background, 1920, 1080);


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
        g2.drawRect(xInit, yInit, CELL_SIZE * 22, h - CELL_SIZE * 30);

    }

    private void drawTowerMenu(Graphics2D g2){
        int xInit = CELL_SIZE * 24 ;
        int yInit = CELL_SIZE;
        g2.drawRect( xInit, yInit, CELL_SIZE * 11,CELL_SIZE * 11 );
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

}
