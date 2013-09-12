package org.joedog.byron.engine.minimax;

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
            board[x][y] = -1;
            break;
          case 'O':
          case 'o':
            board[x][y] = 1;
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

