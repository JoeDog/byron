package org.joedog.byron.engine.montecarlo;

public class Move {
  private int move;
  private int player;

  public Move (int move) {
    this.move   = move;
    this.player = 1;
  }

  public Move (int move, int player) {
    this.move   = move;
    this.player = player;
  }

  // XXX: handle errors!!
  public boolean execute(State state) {
    int i = 1;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (i == this.move) {
          state.setSquare(x, y, player);
        }
        i++;  
      } 
    } 
    return true;
  }

  // XXX: handle errors!!
  public boolean undo(State state) {
    int i = 1;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (i == this.move) {
          state.setSquare(x, y, 0);
        }
        i++;  
      } 
    } 
    return true;
  }

  public void setMove(int move) {
    this.move = move;
  }
 
  public int getMove() {
    return this.move;
  }
}
