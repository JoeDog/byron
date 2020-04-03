package org.joedog.byron.players;

import org.joedog.byron.controller.GameController;

public class Human extends Player {
  private final static String type = "HUMAN";
  private GameController controller;

  public Human(GameController controller, int mark) {
    super(mark);
    this.controller = controller;
  }

  public String getType () {
    return this.type;
  }

  public void start () {
  }

  public void save() {

  }

  public void finish (int status) {
  }

  public void takeTurn() {
    int position;
    boolean done = false;
    while (!done) {
      position = controller.requestMove();
      if (controller.isEmpty(position)) {
        controller.updateBoard(this.mark, position);
        done = true;
      }
    }    
  }
}
