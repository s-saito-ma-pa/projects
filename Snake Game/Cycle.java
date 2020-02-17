import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Cycle{
    public int xPos;
    public int yPos;
    public int sideLength;
    public Color color;
    public int speed;
    public int direction; //0,90,180,270 (right, up, left, down)

    
    public Cycle() {
        xPos = 0;
        yPos = 0;
        sideLength = 0;
        color = null;
        speed = 0;
        direction = 0;
    }
    

     public Cycle(int xVal, int yVal, int dirVal, Color colVal) {
        //xVal = xPos;
        //yVal = yPos;
        xPos = xVal;
        yPos = yVal;
        //dirVal = direction;
        direction = dirVal;
        sideLength = 8;
        color = colVal;
        speed = 4; //4 pixexl every 20 milliseconds
    }

    public void draw(Graphics2D g) {
       g.setColor(color);
       g.fillRect(xPos,yPos,sideLength,sideLength);
    }

    public void updatePos(){ //0,90,180,270 (right, up, left, down)
        if(direction == 0) {
            xPos += speed;
        }
        else if(direction == 270) {
            yPos -= speed;
        }
        else if(direction == 90) {
            yPos+= speed;
        }
        else if(direction == 180) {
            xPos -= speed;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int d) {
        direction = d;
    }

    public boolean willDie(){ //0,90,180,270 (right, up, left, down)
        if (direction == 0 && !TronGame.isWhite(xPos + sideLength + 1, yPos)) { //right
            return true;
        }
        else if (direction == 180 && !TronGame.isWhite(xPos - sideLength + 1, yPos)) { //left
            return true;
        }

        else if (direction == 90 && !TronGame.isWhite(xPos, yPos + sideLength + 1)) { //up
            return true;
        }

        else if (direction == 270 && !TronGame.isWhite(xPos, yPos - sideLength + 1)) { //down
            return true;
        }
        else
          return false;

    }

   
  }
