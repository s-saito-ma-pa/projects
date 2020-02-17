import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.TimerTask;

 public class TronGame extends JPanel implements KeyListener, ActionListener {
    private boolean drawnCover = false;  
   private int frameCount;// used for the score
   private static String title = "Tron Light Cycles"
       + "CONTROLS: Flynn(BLUE): Arrow keys, Sark (RED): W, A, S, D";
   public Timer timer;// handles animation
   public TimerTask task;
   private static Image offScreenBuffer;// needed for double buffering graphics
   private Graphics offScreenGraphics;// needed for double buffering graphics
   //private Cycle sark = new Cycle(500,500,270,Color.RED);
   private Cycle sark = new Cycle(1270,500,270,Color.RED);
   private Cycle flynn = new Cycle(0,0,0,Color.BLUE);

   public static void main(String[] args) {
     JFrame window = new JFrame(title);
     window.setBounds(0, 0, 1280, 972);// almost 1280x1024 window
     window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     window.setResizable(false);

     TronGame game = new TronGame();
     window.getContentPane().add(game);
     window.setBackground(Color.WHITE);
     window.setVisible(true);
     game.init();
     window.addKeyListener(game);
   }


   public void init() {
     offScreenBuffer = createImage(getWidth(), getHeight());// should be 1016x736
     offScreenGraphics = offScreenBuffer.getGraphics();
     //timer = new Timer();
     timer = new Timer(20, this);
     // timer fires every 20 milliseconds.. invokes method actionPerformed()
     initRound();
   }

   public void initRound() {
     frameCount = 0; //This determines the score!
     offScreenGraphics.clearRect(0, 0, 1280, 972);
     repaint();
   }

   
   public void paint(Graphics g) {
      draw((Graphics2D) offScreenGraphics);
      g.drawImage(offScreenBuffer, 0, 0, this);
      g.setColor(Color.BLACK);
      g.setFont(new Font("Helvetica", Font.PLAIN, 30)); 
      int x = 1280/2+150;
      int y = 972/2-150;
      
      //when Flynn collides into something and dies, display its score 
      if(flynn.willDie()) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 25)); 
        g.drawString("FLYNN DIED!!!!!!!", x, y+150);
        g.drawString("YOUR SCORE IS " + frameCount, x, y+180);
        
        timer.stop();
        
     }
     //same thing for sark
     else if(sark.willDie()) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 25)); 
        g.drawString("SARK DIED!!!!!!!", x, y+150);
        g.drawString("YOUR SCORE IS " + frameCount, x, y+180);
        
        timer.stop();
        
     }
   }

   public void draw(Graphics2D g) {
    
     g.setColor(Color.BLACK);
      g.setFont(new Font("Helvetica", Font.PLAIN, 20)); 
      int x = 1280/2+150;
      int y = 972/2+150;
      
      //this will go away after 30 framecounts 
      if(frameCount < 30) { //250??
        g.drawString("WELCOME TO TRON!", x, y-30);
        g.drawString("PRESS 6 TO START, PRESS CTR + Q TO QUIT", x, y);
        g.setColor(Color.BLUE);
        g.drawString("CONTROL FLYNN (BLUE) WITH THE ARROW KEYS", x, y+30);
        g.setColor(Color.RED);
        g.drawString("CONTROL SARK (RED) WITH W, A, S, D", x, y+60);
      }
      else if (!drawnCover){
        drawnCover = true;
        g.setColor(Color.WHITE);
        g.fillRect(x, y-50, 600, 150);
      }
      
     g.setColor(flynn.color);
     g.fillRect(flynn.xPos, flynn.yPos,flynn.sideLength,flynn.sideLength);
     g.setColor(sark.color);
     g.fillRect(sark.xPos, sark.yPos, sark.sideLength,sark.sideLength);

    }

   
   public void actionPerformed(ActionEvent e) {

     flynn.updatePos();
     sark.updatePos();
     frameCount++;// update the frameCount
     repaint();// needed to refresh the animation

     if(flynn.willDie()) {
         timer.stop();
     }
     else if(sark.willDie()) {
         timer.stop();
     }
   }

   
     //handles any key pressed events and updates the Cycle's direction by setting
     //their direction to either 0,90,180 or 270 based on which key is pressed.
    
   public void keyPressed(KeyEvent e) { //0,90,180,270 (right, up, left, down)
     int keyCode = e.getKeyCode();
     //char c = e.getKeyChar();
     if (keyCode == KeyEvent.VK_LEFT) {
        flynn.setDirection(180);
     } else if (keyCode == KeyEvent.VK_RIGHT) {
         flynn.setDirection(0);
     } else if (keyCode == KeyEvent.VK_UP) {
      
         flynn.setDirection(270);
     } else if (keyCode == KeyEvent.VK_DOWN) {
         flynn.setDirection(90);
     }
       else if (keyCode == KeyEvent.VK_S) { //down
           sark.setDirection(90);
       }
       else if (keyCode == KeyEvent.VK_A) {//left
           sark.setDirection(180);
       }
       else if (keyCode == KeyEvent.VK_W) {//up
           sark.setDirection(270);
       }
       else if (keyCode == KeyEvent.VK_D){//right
           sark.setDirection(0);
       }
   }




   
    // handles any key released events ... <br>
    // starts game by the '6 Key'<br>
    // kills the game window by the 'Escape Key'
    
   public void keyReleased(KeyEvent e) {
     int keyCode = e.getKeyCode();
     if (keyCode == KeyEvent.VK_6) {
       // start the game .. if the game has not currently running
       if (!timer.isRunning()) {
         timer.start();
         initRound();
       }
     } else if (keyCode == KeyEvent.VK_ESCAPE) {
       // kill game process... close the window
       System.exit(0);
     }
     else if (keyCode == KeyEvent.VK_W)
      sark.setDirection(270);
   }

   
   public void keyTyped(KeyEvent e) {
   }

   
  // returns true if the color value of the pixel<br>
  //with coordinates (x,y) is WHITE, false otherwise<br>
  //The snakes are supposed to die when they collide into the other one or one of the walls 
    
   public static boolean isWhite(int x, int y) {
     BufferedImage bi = (BufferedImage) offScreenBuffer;
     if (bi == null)
       return true;
     try {
       int colorVal = bi.getRGB(x, y);
       return (colorVal == -1);
     } catch (Exception ex) {
       return false;
     }
   }

 }

 