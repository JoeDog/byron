package org.joedog.byron.util;

public class Move { 
  private int mark;
  private int square;

  public Move (int mark, int square) {
    this.mark   = mark;
    this.square = square;
  }

  public int getMark () {
    return this.mark;
  }

  public int getSquare () {
    return this.square;
  }
}
