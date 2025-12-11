
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private static final long serialVerion =11 ;
    private boolean play =false;
    private int score=0;
    private int totalBricks ;
    private Timer timer ;
    private int delay= 8;
    private int playerX=310;
    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private MapGenerator map ;

    private Clip c;

    private int player2X = -100;
    private Image gameBackground;

    private int level = 1;
    private int ballXdir2 = -1;
    private int ballYdir2 = -2;
    private int ballposX2 = 150;
    private int ballposY2 = 350;
    private boolean secondBall = false;
    private boolean secondPlayer = false;
    private int playerX2 = 310;
    private Image paddleImage;


    /////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////

    public Gameplay(){
        int rows = 3 + (int)(Math.random() * 3);
        int cols = 5 + (int)(Math.random() * 6);

        map = new MapGenerator(rows, cols);
        totalBricks = rows * cols;

        //  map=new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer =new Timer(delay,this);
        timer.start();

        gameBackground=new ImageIcon(getClass().getResource("BrickphotoBack.png")).getImage();
        paddleImage = new ImageIcon(getClass().getResource("Brickphotopaddle.png")).getImage();

    }

    /////////////////////////////////////////////////////////////////////////////////////
      public void playMusic(){
        try {
            AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(getClass().getResource("hitsound.wav"));
            Clip clip =AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch (Exception e){
              e.printStackTrace();
        }

      }

      public void stopMusic(){
        if (c!=null&&c.isRunning())
           c.stop();

      }/*
    public void addBluePlayer() {
        player2X = 310;
        repaint();
    }*/
    public void addBluePlayer() {
        secondPlayer = true;
    }



    ////////////////////////////////////////////////////////////////////////////////////////

    public void paint(Graphics g){
        //background
      //  g.setColor(Color.BLACK);
      //  g.fillRect(1,1,692,592);
      g.drawImage(gameBackground,0,0,getWidth(),getHeight(),this);


        //drawing map
        map.draw((Graphics2D) g);

  /*   //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
*/
     //Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("seril",Font.BOLD,25));
        g.drawString(""+score,590,30);

     //paddle
       // g.setColor(Color.GREEN);
       // g.fillRect(playerX+20,550,100,10);
        g.drawImage(paddleImage, playerX+20, 550, 100, 10, this);
    //addplayer
      /*  if (player2X>=0){
            g.setColor(Color.BLUE);
            g.fillRect(player2X,580,100,8);
        }*/
       if (secondPlayer){
          // g.setColor(Color.blue);
          // g.fillRect(playerX2-150, 550, 100, 10);
           g.drawImage(paddleImage, playerX2-150, 550, 100, 8, this);
       }



     //ball1
        g.setColor(Color.GREEN);
        g.fillOval(ballposX,ballposY,20,20);
       //ball2
        if (secondBall) {
            g.setColor(Color.ORANGE);
            g.fillOval(ballposX2, ballposY2, 20, 20);
        }


        if (totalBricks<=0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("seril",1,30));
            g.drawString("Level"+(level+1),260,300);

            Timer levelTimer=new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    level++;
                    totalBricks=28;
                    map=new MapGenerator(4,7);
                    ballXdir= (ballXdir>0)? ballXdir+1 : ballXdir-1 ;
                    ballYdir= (ballYdir>0)? ballYdir+1 : ballYdir-1 ;
                    if (level>=2){
                        secondBall=true;
                        ballXdir2=ballXdir ;
                        ballYdir2=ballYdir ;
                    }
                    play=true;
                    ((Timer)e.getSource()).stop();
                    repaint();
                }
            });
            levelTimer.setRepeats(false);
            levelTimer.start();
           // g.setFont(new Font("seril",Font.BOLD,30));
           // g.drawString("pressEnter to Restart",230,350);
        }
        if (ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            saveScore();
            g.setColor(Color.RED);
            g.setFont(new Font("seril",1,30));
            g.drawString("GameOver ,Scores"+ score,190,300);

            g.setFont(new Font("seril",1,20));
            g.drawString("pressEnter to Restart",230,350);
        }
        g.dispose();

    }

     public void actionPerformed(ActionEvent e){
        timer.start();
        if (play){
            if (new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir= -ballYdir;
                ///////
                playMusic();
            }
            A: for (int i=0;i<map.map.length;i++){
                for (int j=0;j<map.map[0].length;j++){
                    if (map.map[i][j]>0){
                        int brickX=j*map.brickwidth+80;
                        int brickY=i*map.brickheight+50;
                        int brickwidth=map.brickwidth;
                        int brickheight=map.brickheight;

                        Rectangle rect=new Rectangle(brickX,brickY,brickwidth,brickheight);
                        Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;
                        if (ballRect.intersects(brickRect)){
                            map.setBrickvalue(0,i,j);
                            totalBricks--;
                            score+=5;
                            /////////////////////////
                            playMusic();
                            /////////////////////////////
                            if (ballposX+19<=brickRect.x|| ballposX+1>=brickRect.x+brickRect.width){
                                ballXdir= -ballXdir;
                            }else {
                                ballYdir= -ballYdir;
                            }
                            break A;


                        }
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if (ballposX<0){
                ballXdir= -ballXdir;
            }
            if (ballposY<0){
                ballYdir= -ballYdir;
            }
            if (ballposX>670){
                ballXdir= -ballXdir;
            }
         if (secondBall){
             ballposX2 += ballXdir2;
             ballposY2 += ballYdir2;
             if (new Rectangle(ballposX2, ballposY2, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                 ballYdir2 = -ballYdir2;
             }
             // تصادم الكرة الثانية مع الطوب
             A: for (int i=0;i<map.map.length;i++) {
                 for (int j = 0; j < map.map[0].length; j++) {
                     if (map.map[i][j] > 0) {
                         int brickX = j * map.brickwidth + 80;
                         int brickY = i * map.brickheight + 50;
                         int brickwidth = map.brickwidth;
                         int brickheight = map.brickheight;

                         Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
                         Rectangle ballRect2 = new Rectangle(ballposX2, ballposY2, 20, 20);

                         if (ballRect2.intersects(rect)) {
                             map.setBrickvalue(0, i, j);
                             totalBricks--;
                             score += 5;

                             if (ballposX2 + 19 <= rect.x || ballposX2 + 1 >= rect.x + rect.width) {
                                 ballXdir2 = -ballXdir2;
                             } else {
                                 ballYdir2 = -ballYdir2;
                             }
                             break A;
                         }
                     }
                 }

             }

             if (ballposX2 < 0) ballXdir2 = -ballXdir2;
             if (ballposY2 < 0) ballYdir2 = -ballYdir2;
             if (ballposX2 > 670) ballXdir2 = -ballXdir2;

         }



        }
        repaint();
    }




    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (playerX >=600){
                playerX=600;
            }else {
                moveRight();
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            if (playerX <10){
                playerX=10;
            }else {
                moveLeft();
            }
       }
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
           if (!play){
               play=true;
               ballposX=120;
               ballposY=350;
               ballXdir= -1;
               ballYdir= -2;
               playerX=310;
               score=0;
               int rows = 3 + (int)(Math.random() * 3);
               int cols = 5 + (int)(Math.random() * 6);

               map = new MapGenerator(rows, cols);
               totalBricks = rows * cols;


               repaint();
           }
        }
        if (secondPlayer) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                if (playerX2 >= 600) playerX2 = 600;
                else playerX2 += 20;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                if (playerX2 <= 10) playerX2 = 10;
                else playerX2 -= 20;
            }
        }




    }
    public void moveRight() {
      play=true;
      playerX+=20;
    }
    public void moveLeft() {
        play=true;
        playerX-=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public void saveScore() {
        try {
            FileWriter writer = new FileWriter("score.txt", true);
            writer.write(score + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
