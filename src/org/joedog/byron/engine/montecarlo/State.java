package org.joedog.byron.engine.montecarlo;

import org.joedog.byron.controller.GameController;

public class State {
  private int [][] board;
  private int      value;
  private int      available;
  private String   pattern;

  public State (String pattern) {
    this.pattern   = pattern;
    this.available = 0;
    board = new int [3][3];
    int i = 0;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        switch (pattern.charAt(i)) {
          case 'X':
          case 'x':
            board[x][y] = GameController.XWIN;
            break;
          case 'O':
          case 'o':
            board[x][y] = GameController.OWIN;
            break;
          default:
            board[x][y] = 0;
            this.available++;
            break;
        }
        i++;
      }
    }
    value = 0;
  }

  public boolean isEmpty() {
    int i = 1;
    for (int x = 0; x < 9; x++) {
      if (pattern.charAt(x)=='-') {
        i++;
      }
    }
    if (i>=9) return true;
    return false;
  }

  public void setSquare(int x, int y, int value) {
    this.board[x][y] = value;
  }

  public int getSquare(int x, int y) {
    return this.board[x][y];
  }

  public int [][] getBoard() {
    return this.board;
  }

  public String getPattern() {
    return this.pattern;
  }

  public String toString() {
    String str = "";
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        char mark;
        switch(board[c][r]) {
          case -1:
            mark = 'X';
            break;
          case  1:
            mark = 'O';
            break;
          default:
            mark = ' ';
            break;
        }
        str = str + mark;
        if (c < board.length-1) {
          str = str + "|";
        }
      }

      if (r < board[0].length-1) {
        str = str + "\n-+-+-\n";
      }
    }
    return str;
  }

  public int getStatus() {
    return this.getStatus(this.board);
  }

  public int getStatus(int [][] grid) {
    // check the rows
    for (int x = 0; x < 3; x++) {
      if ((grid[x][0]+grid[x][1]+grid[x][2] > 0) && (grid[x][0] == grid[x][1] && grid[x][1] == grid[x][2])) {
        return grid[x][0];
      }
    }
    // now check columns
    for (int y = 0; y < 3; y++) {
      if ((grid[0][y]+grid[1][y]+grid[2][y] > 0) && (grid[0][y] == grid[1][y] && grid[1][y] == grid[2][y])) {
        return grid[0][y];
      }
    }
    // check diagonally
    if (((grid[0][0]+grid[1][1]+grid[2][2]) > 0) && (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])) {
      return grid[0][0];
    }
    if (((grid[2][0]+grid[1][1]+grid[0][2]) > 0) && (grid[2][0] == grid[1][1] && grid[0][2] == grid[1][1])) {
      return grid[2][0];
    }
    /**
     * If we reached this point, then we
     * we don't have a winner. IF we don't
     * have a winner and squares marked with
     * zero, then we're still active.
     */
    for (int y = 0; y < 3; y++)
      for (int x = 0; x < 3; x++)
        if (grid[x][y] == 0) 
          return GameController.ACTIVE;   
    /**
     * If we don't have a winner and all
     * squares are marked, then it's a tie
     */
    return GameController.DRAW;
  }

  public State copy() {
    State state = new State(this.pattern);
    return state;
  }

  public int getMove(int r, int c) {
    int move = 1;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if( x == r && y == c) return move;
        move ++;
      } 
    } 
    return -1;
  }
}

