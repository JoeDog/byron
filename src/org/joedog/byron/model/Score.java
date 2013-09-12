package org.joedog.byron.model;

import java.io.Serializable; 

public class Score implements Serializable {
  private String name;
  private double score;
  static final long serialVersionUID = -3611946473283033478L;

  public Score (String name, double score) {
    this.name  = name;
    this.score = score;
  }

  public String getName() {
    return this.name;
  }

  public double getScore() {
    return this.score;
  }

  public void setName(String name) {
    this.name = name;
    return;
  }

  public void setScore(Double score) {
    this.score = score;
    return;
  }

  public int compare (Score score) {
    int x = ((Double)this.score).compareTo((Double)score.score);
    return x;
  }
}
