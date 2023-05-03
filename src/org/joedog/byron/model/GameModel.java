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
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (debug) System.out.print(grid[x][y]);
      }
      if (debug) System.out.println("");
    }
    if (debug) System.out.println("---");
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

  public String getTrials() {
    String tmp = (conf.getProperty("Trials") == null) ? "0" : conf.getProperty("Trials");
    try {
      if (tmp == null) {
        throw new NullPointerException("Trials is null!");
      }
    } catch (Exception e) { 
      return "0";
    }
    return tmp;
  }

  public String getEngineX() {
    String tmp = (conf.getProperty("EngineX") == null) ? "0" : conf.getProperty("EngineX");
    try {
      if (tmp == null) {
        throw new NullPointerException("Engine is null!");
      }
    } catch (Exception e) {
      return "0";
    }
    return tmp;
  }

  public String getEngineO() {
    String tmp = (conf.getProperty("EngineO") == null) ? "0" : conf.getProperty("EngineO");
    try {
      if (tmp == null) {
        throw new NullPointerException("Engine is null!");
      }
    } catch (Exception e) {
      return "0";
    }
    return tmp;
  }

  public void setEngineX(String engine) {
    conf.setProperty("EngineX", engine);
  }  

  public void setEngineO(String engine) {
    conf.setProperty("EngineO", engine);
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

  public String getTraining() {
    return conf.getProperty("Training");
  }

  public String getThoughts() {
    return conf.getProperty("Thoughts");
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
      if (grid[x][0]+grid[x][1]+grid[x][2] == GameController.XSQUARE*3) {
        firePropertyChange(GameController.PATTERN, "X", GameController.ROW[x]);
        display();
        return GameController.XSQUARE;
      }
      if (grid[x][0]+grid[x][1]+grid[x][2] == GameController.OSQUARE*3) {
        firePropertyChange(GameController.PATTERN, "X", GameController.ROW[x]);
        display();
        return GameController.OSQUARE;
      }
    }
    // now check columns
    for (int y = 0; y < 3; y++) {
      if (grid[0][y]+grid[1][y]+grid[2][y] == GameController.XSQUARE*3) {
        firePropertyChange(GameController.PATTERN, "X", GameController.COL[y]);
        display();
        return GameController.XSQUARE;
      }
      if (grid[0][y]+grid[1][y]+grid[2][y] == GameController.OSQUARE*3) {
        firePropertyChange(GameController.PATTERN, "X", GameController.COL[y]);
        display();
        return GameController.OSQUARE;
      }
    }
    // check diagonally
    if (grid[0][0]+grid[1][1]+grid[2][2] == GameController.XSQUARE*3) {
      firePropertyChange(GameController.PATTERN, "X", GameController.DIA[0]);
      display();
      return GameController.XSQUARE;
    }
    if (grid[0][0]+grid[1][1]+grid[2][2] == GameController.OSQUARE*3) {
      firePropertyChange(GameController.PATTERN, "X", GameController.DIA[0]);
      display();
      return GameController.OSQUARE;
    }
    if (grid[2][0]+grid[1][1]+grid[0][2] == GameController.XSQUARE*3) {
      firePropertyChange(GameController.PATTERN, "X", GameController.DIA[1]);
      display();
      return GameController.XSQUARE;
    }
    if (grid[2][0]+grid[1][1]+grid[0][2] == GameController.OSQUARE*3) {
      firePropertyChange(GameController.PATTERN, "X", GameController.DIA[1]);
      display();
      return GameController.OSQUARE;
    }
    // check for tie
    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        if (grid[x][y] == 0) {
          display();
          return 0;
        }
      }
    }
    firePropertyChange(GameController.PATTERN, "Y", GameController.DIA[2]);
    display();
    return GameController.DRAW;
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
