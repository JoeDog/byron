package org.joedog.byron.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.byron.controller.GameController;

public class ScoresAction implements ActionListener {
  private GameController controller;

  public ScoresAction (GameController controller) {
    this.controller = controller;
  }

  public void actionPerformed (ActionEvent e) {
    this.controller.displayScores();
  }
}

