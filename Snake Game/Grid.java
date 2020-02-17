import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Grid {


    public static int[][] grid;
    private Cycle tronb;
    private int bscore;
    private Cycle tronr;
    private int rscore;

    public Grid(Cycle newTronr, Cycle newTronb) {
        tronb = newTronb;
        tronr = newTronr;
        grid = new int[1280][972];
        bscore = 0;
        rscore = 0;
    }

    public boolean tick() {
        //returns true if game should continue
        // returns false if someone loses

        tronr.yPos += 1;
        voronoi();
        int rscoreUp = rscore;
        tronr.yPos -= 1;
        tronr.yPos -= 1;
        voronoi();
        int rscoreDown = rscore;
        tronr.yPos += 1;
        tronr.xPos -= 1;
        voronoi();
        int rscoreLeft = rscore;
        tronr.xPos += 1;
        tronr.xPos += 1;
        voronoi();
        int rscoreRight = rscore;
        tronr.xPos -= 1;

        int highest = Math.max(rscoreUp, Math.max(rscoreDown, Math.max(rscoreRight, Math.max(rscoreLeft))));

        if (highest == rscoreUp) {
          tronr.setDirection(90);
        }
        if (highest == rscoreDown) {
          tronr.setDirection(270);
        }
        if (highest == rscoreLeft) {
          tronr.setDirection(180);
        }
        if (highest == rscoreRight) {
          tronr.setDirection(0);
        }

        tronr.updatePos();
        tronb.updatePos();

        grid[tronb.xPos][tronb.yPos] = 1;
        grid[tronr.xPos][tronr.yPos] = 1;

        if (willDie(tronr)) {
          return false;
        }
        if (willDie(tronb)) {
          return false;
        }
        return true;
    }

    public boolean willDie(Cycle tron) {
      if (tron.getDirection() == 0 && grid[tron.xPos+1][tron.yPos] == 1) {
        return true;
      }
      if (tron.getDirection() == 90 && grid[tron.xPos][tron.yPos+1] == 1) {
        return true;
      }
      if (tron.getDirection() == 180 && grid[tron.xPos-1][tron.yPos] == 1) {
        return true;
      }
      if (tron.getDirection() == 270 && grid[tron.xPos][tron.yPos-1] == 1) {
        return true;
      }
      return false;
    }

    public void voronoi() {
        //calculate for tronb
        int bx = tronb.xPos;
        int by = tronb.yPos;
        int newbscore = 0;
        // calculate for tronr
        int rx = tronr.xPos;
        int ry = tronr.yPos;
        int newrscore=0;

        for (int i = 0; i < 1280; i++) {
          for (int j = 0; j < 972; j++) {
            if (grid[i][j] == 0) {
              int rdistance = Math.abs(rx - i) + Math.abs(ry - j);
              int bdistance = Math.abs(bx - i) + Math.abs(by - j);
              if (rdistance > bdistance) {
                newrscore += 1;
              } else if (bdistance > rdistance) {
                newbscore += 1;
              }
            }
          }
        }

        bscore = newbscore;
        rscore = newrscore;

    }

}
