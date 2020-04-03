package org.joedog.byron.players;

import org.joedog.byron.controller.GameController;
import org.joedog.byron.engine.*;
import org.joedog.byron.engine.menace.*;
import org.joedog.byron.engine.minimax.*;
import java.util.Random;

public class Computer extends Player {
  private final static String type = "COMPUTER";
  private int map[][]    = new int[3][3];
  private boolean inplay = false;
  private Engine        engine;
  private EngineFactory factory;
  
  private GameController controller;

  public Computer(GameController controller, int mark) {
    super(mark);
    this.controller = controller;
    this.factory    = new EngineFactoryImpl();
    this.setEngine(this.controller.getIntProperty(this.engstr));
    this.map();
  }

  @Override
  public void setEngine(int engine) {
    this.engine = this.factory.getEngine(engine);
  }

  public void map () {
    int index = 1;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        map[x][y] = index;
        index++;
      }
    }
  }

  public String getType () {
    return type;
  }

  public void start () {
    this.inplay = true;
  }

  public void finish (int status) {
    if (this.inplay == false) {
      return;
    }
    if ((status == controller.XWIN) && (this.mark == controller.OSQUARE)) { 
      this.engine.punish();
    } else if ((status == controller.OWIN) && (this.mark == controller.XSQUARE)) { 
      this.engine.punish();
    } else if ((status == controller.OWIN && this.mark == controller.OSQUARE)) { 
      this.engine.reward();
    } else if ((status == controller.XWIN && this.mark == controller.XSQUARE)) { 
      this.engine.reward();
    } else {
      this.engine.restore();
    }
    this.inplay = false;
  }

  public void takeTurn() {
    int position;
    boolean done = false;
    String s = controller.getGameString(); 
    while (!done) {
      position = this.engine.getMove(s);
      if (position == -1) 
        position = getMove();
      if (controller.isEmpty(position)) {
        controller.updateBoard(this.mark, position);
        done = true;
      }
    }
  }  

  /**
   * Helper method to ensure we don't get stuck. 
   * We call this method if engine.getMove() can't
   * produce a viable move....
   */ 
  private int getMove () {
    int col;
    int row;
    Random gen1 = new Random();
    Random gen2 = new Random();
    while (true) {
      row = gen1.nextInt(3);
      col = gen2.nextInt(3);
      if (controller.isEmpty(map[row][col])){
        return map[row][col];
      }
    }
  }

  public void save() {
    this.engine.save();
  }
}
