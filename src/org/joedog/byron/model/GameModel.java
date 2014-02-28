package org.joedog.byron.model;

import org.joedog.byron.controller.*;
import org.joedog.byron.util.*;

public class GameModel extends AbstractModel {
  private Configuration conf  = null;
  private int[][]    grid     = new int[3][3];
  private boolean    debug    = false;

  public GameModel () {
    reset();
    conf = Configuration.getInstance();
    System.out.println("GOT CONF");
  }

  public void reset() {
    int index = 0;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        grid[x][y] = GameController.EMPTY;
        index++;
      }
    }
  }

  public void display() {
    if (!debug) return; 
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        System.out.print(grid[x][y]);
      }
      System.out.println("");
    }
    System.out.println("---");
  }

  public String getGameString() {
    String str = new String();
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (grid[x][y] == GameController.EMPTY) {
          str += "-";
        }
        if (grid[x][y] == GameController.XSQUARE) {
          str += "X";
        }
        if (grid[x][y] == GameController.OSQUARE) {
          str += "O";
        }
      }      
    }
    return str;
  }

  public String getEngine() {
    String tmp = (conf.getProperty("Engine") == null) ? "0" : conf.getProperty("Engine");
    try {
    if (tmp == null) {
      throw new NullPointerException("Engine is null!");
    }
    } catch (Exception e) { 
      return "0";
    }
    return tmp;
  }

  public void setEngine(String engine) {
    conf.setProperty("Engine", engine);
  }

  public String getMainX() {
    return conf.getProperty("MainX");
  }

  public void setMainX(String X) {
    conf.setProperty("MainX", X);
  }

  public String getMainY() {
    return conf.getProperty("MainY");
  }

  public void setMainY(String Y) {
    conf.setProperty("MainY", Y);
  }

  public int getMarkTotal() {
    int total = 0;

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (! empty(x, y)) {
          total++;
        }
      }
    }
    return total;
  }

  public boolean getIsEmpty (Integer position) {
    switch (position.intValue()) {
      case 1:
        return empty(0,0);
      case 2:
        return empty(0,1);
      case 3:
        return empty(0,2);
      case 4:
        return empty(1,0);
      case 5:
        return empty(1,1);
      case 6:
        return empty(1,2);
      case 7:
        return empty(2,0);
      case 8:
        return empty(2,1);
      case 9:
        return empty(2,2);
      default:
        return false;
    } 
  }

  public int getGameStatus () {
    // check the rows 
    for (int x = 0; x < 3; x++) {
      if ((grid[x][0] + grid[x][1] + grid[x][2] > 0) && (grid[x][0] == grid[x][1] && grid[x][1] == grid[x][2])) {
        firePropertyChange(GameController.PATTERN, "X", GameController.ROW[x]);
        display();
        return grid[x][0];
      }
    }
    // now check columns
    for (int y = 0; y < 3; y++) {
      if ((grid[0][y] + grid[1][y] + grid[2][y] > 0) && (grid[0][y] == grid[1][y] && grid[1][y] == grid[2][y])) {
        firePropertyChange(GameController.PATTERN, "X", GameController.COL[y]);
        display();
        return grid[0][y];
      }
    }
    // check diagonally
    if (((grid[0][0]+grid[1][1]+grid[2][2]) > 0) && (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])) {
      firePropertyChange(GameController.PATTERN, "X", GameController.DIA[0]);
      display();
      return grid[0][0];
    }
    if (((grid[2][0]+grid[1][1]+grid[0][2]) > 0) && (grid[2][0] == grid[1][1] && grid[0][2] == grid[1][1])) {
      firePropertyChange(GameController.PATTERN, "X", GameController.DIA[1]);
      display();
      return grid[2][0];
    }
    // check for tie
    for (int y = 0; y < 3; y++)
      for (int x = 0; x < 3; x++)
        if (grid[x][y] == 0) 
          return 0;
    return 3;
  }

  public void setSquare (Move move) {
    int old;
    int mark   = move.getMark();
    int pos    = move.getSquare();
    int index  = 1;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (pos == index) {
          old = grid[x][y];
          grid[x][y] = mark;
        }
        index++;
      }
    }
    display();
  }

  public int getPiece(char value) {
    if (value=='X') {
      return GameController.XSQUARE;
    } else {
      return GameController.OSQUARE;
    }
  }

  private boolean empty (int row, int col) {
    return grid[row][col] == GameController.EMPTY;
  }

  public void save() {
    conf.save();
  }
}
