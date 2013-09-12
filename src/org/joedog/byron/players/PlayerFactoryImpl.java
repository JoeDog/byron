package org.joedog.byron.players;

import org.joedog.byron.controller.GameController;

public class PlayerFactoryImpl implements PlayerFactory {
  
  public PlayerFactoryImpl() {
  }

  public Player getPlayer(GameController controller, int type, int mark) {
    if (type == Player.HUMAN) {
      return new Human(controller, mark);
    } 
    if (type == Player.COMPUTER) {
      return new Computer(controller, mark);
    }
    return null;
  }
}

