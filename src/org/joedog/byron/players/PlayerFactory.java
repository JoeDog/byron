package org.joedog.byron.players;

import org.joedog.byron.controller.GameController;

public interface PlayerFactory {

  public Player getPlayer(GameController controller, int type, int mark);
}
