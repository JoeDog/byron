package org.joedog.byron.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.byron.controller.GameController;

public class EngineAction implements ActionListener {
  private GameController controller;
  private int engine;

  public EngineAction (GameController controller, int engine) {
    this.controller = controller;
    this.engine     = engine;
  }

  public void actionPerformed (ActionEvent e) {
    this.controller.setEngine(engine);
  }
}

