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
  private Engine        engine1;
  private Engine        engine2;
  private Engine        engine3;
  private EngineFactory factory;
  
  private GameController controller;

  public Computer(GameController controller, int mark) {
    super(mark);
    this.controller = controller;
    this.factory    = new EngineFactoryImpl();
    this.ENGINE     = this.controller.getIntProperty("Engine");
    this.engine1    = this.factory.getEngine(Engine.MENACE);
    this.engine2    = this.factory.getEngine(Engine.MINIMAX);
    this.engine3    = this.factory.getEngine(Engine.MONTECARLO);
    this.map();
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
    Engine[] engines = {this.engine1, this.engine2, this.engine3};
    if (this.inplay == false) {
      return;
    }
    if ((status == controller.XWIN) && (this.mark == controller.OSQUARE)) { 
      engines[this.ENGINE].punish();
    } else if ((status == controller.OWIN && this.mark == controller.OSQUARE)) { 
      engines[this.ENGINE].reward();
    } else {
      engines[this.ENGINE].restore();
    }
    this.inplay = false;
  }

  public void takeTurn() {
    int position;
    boolean done = false;
    Engine[] engines = {this.engine1, this.engine2, this.engine3};
    String s = controller.getGameString();
    while (!done) {
      position = engines[this.ENGINE].getMove(s);
      if (position == -1) 
        position = getMove();
      if (controller.isEmpty(position)) {
        controller.updateBoard(this.mark, position);
        done = true;
      }
    }
  }  

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
}
