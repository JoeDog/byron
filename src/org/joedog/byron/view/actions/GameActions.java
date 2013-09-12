package org.joedog.byron.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.byron.controller.GameController;
import org.joedog.byron.engine.Engine;

public class GameActions {
  private GameController controller;

  public GameActions (GameController controller) {
    this.controller = controller;
  }

  public ActionListener getAction(String item) {
    if (item.equals("New"))
      return new NewAction(controller);
    else if (item.equals("Exit"))
      return new ExitAction(controller);
    else if (item.equals("Scores"))
      return new ScoresAction(controller);
    else if (item.equals("Pupil"))
      return new EngineAction(controller, Engine.MENACE);
    else if (item.equals("Guru"))
      return new EngineAction(controller, Engine.MINIMAX);
    else 
      return null;
  }
}


