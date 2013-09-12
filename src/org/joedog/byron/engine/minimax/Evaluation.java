package org.joedog.byron.engine.minimax;

public class Evaluation {

  private int  score;
  private Move move;
  private String message;

  public Evaluation() {
    this.move  = null;
    this.score = this.minimum(); // XXX: really?
  }

  public Evaluation(int score) {
    this.move  = null;
    this.score = score;
  }

  public Evaluation(Move move, int score) {
    this.move  = move;
    this.score = score;
  } 
 
  public Move getMove() {
    return this.move;
  }

  public int getScore() {
    return this.score;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public static final int minimum() { return Integer.MIN_VALUE+1; }
  public static final int maximum() { return Integer.MAX_VALUE; }
}
