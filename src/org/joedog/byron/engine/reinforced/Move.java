package org.joedog.byron.engine.reinforced;

import java.io.Serializable; 

public class Move implements Serializable {
  private String key;
  private int    move;
  private int    value;  
  public static final long serialVersionUID = -777L;

  public Move (String key) {
    this(key, -1, 0);
  }

  public Move(String key, int move) {
    this(key, move, 0);
  }

  public Move (String key, int move, int value) {
    this.key   = key; 
    this.move  = move;
    this.value = value;
  }

  public boolean matches(String key) {
    return this.key.equals(key);  
  }

  public boolean matches(String key, int move) {
    if (key.equals(this.key) && move == this.move) {
      return true;
    }
    return false;
  }

  /** 
   * Return boolean true if this matches Move or
   * false if it does not; the match is determined
   * by pattern and move.
   */
  public boolean matches (Move move) {
    return this.matches(move.getKey(), move.getMove());
  }

  public String getKey() {
    return this.key;
  }

  public int getMove(){
    return this.move;
  }

  public int getValue() {
    return this.value;
  }

  public void punish() {
    this.value -= 1;
  }

  public void reward() {
    this.value += 1;
  }

  public String toString() {
    return "Move("+this.key+","+this.move+","+this.value+")";
  }
}
