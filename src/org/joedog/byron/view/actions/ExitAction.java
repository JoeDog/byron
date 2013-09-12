package org.joedog.byron.view.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.byron.controller.GameController;

public class ExitAction implements ActionListener {
  private GameController controller;

  public ExitAction (GameController controller) {
    this.controller = controller;
  }

  public void actionPerformed (ActionEvent e) {
    this.controller.exit();
  }
}

