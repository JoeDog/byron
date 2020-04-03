package org.joedog.byron.engine.reinforced;

import java.io.Serializable;

import java.util.Stack;

public class Bundle implements Serializable {
  private Stack<Move> bundle;
  public static final long serialVersionUID = -666L;

  /**
   * Create a new Bundle of moves
   * <p>
   * @return Bundle
   */
  public Bundle() {
    bundle = new Stack<Move>();
  }

  /**
   * Add a Move to the Bundle
   * <p>
   * @param  Move
   * @return void
   */
  public void add(Move move) {
    if (! this.contains(move)) {
      bundle.addElement(move);
    }
  }

  public void reward(Move move) {
    if (this.contains(move)) {
      Move tmp = this.get(move);
      tmp.reward();
    } else {
      move.reward();
      bundle.addElement(move);
    }
  }

  public void punish(Move move) {
    if (this.contains(move)) {
      Move tmp = this.get(move);
      tmp.punish();
    } else {
      move.punish();
      bundle.addElement(move);
    }
  }

  /**
   * Remove a Move at position index from the Bundle
   * <p>
   * @param int   the Bundle index position of the card
   * @return      Card 
   */
  public Move remove(int index) {
    Move m = (Move)bundle.remove(index);
    return m;
  }

  /** 
   * Return a reference to the card at Pack position index
   * <p>
   * @param  int   the Stack index position of the card
   * @return       Card
   */
  public Move get(int index) {
    return (Move) bundle.get(index);
  }

  public Move get(Move move) {
    for (Move m: this.getMoves()) {
      if (m.matches(move)) return m;
    }
    return null;
  }

  /**
   * Return a Stack representation of the Cards in the Pack
   * <p>
   * @param  none
   * @return Stack<Move>
   */ 
  public Stack <Move> getMoves() {
    return this.bundle;
  }

  /** 
   * Return an int representation of the size of the Bundle 
   * [same value as count()]
   * <p> 
   * @param  none
   * @return int
   */
  public int size() {
    return bundle.size();
  }

  public boolean contains(Move move) {
    for (Move m: this.getMoves()) {
      if (m.matches(move)) return true; 
    }
    return false;
  }

  /** 
   * Return an int representation of the count of the Pack 
   * [same value as size()]
   * <p> 
   * @param  none
   * @return int
   */
  public int count() {
    return bundle.size();
  }

  public String toString() {
    String ret = "";
    for (Move m: this.getMoves()) {
      ret = ret+m.toString()+"\n";
    }
    return ret;
  }
}
