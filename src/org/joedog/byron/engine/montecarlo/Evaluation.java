package org.joedog.byron.engine.montecarlo;

import java.util.ArrayList;

public class Evaluation {

  private int  score;
  private Move move;
  private String message;

  public Evaluation() {
    this.move  = null;
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
}
