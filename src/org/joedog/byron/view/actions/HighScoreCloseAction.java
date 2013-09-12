package org.joedog.byron.view.actions;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.joedog.byron.controller.GameController;
import org.joedog.byron.model.HighScoreTableModel;

public class HighScoreCloseAction implements ActionListener {
  private JFrame frame;
  private HighScoreTableModel model;
  public final static String NAME = "Okay";

  public HighScoreCloseAction (JFrame frame, HighScoreTableModel model) {
    this.frame = frame;
    this.model = model;
  }

  public void actionPerformed (ActionEvent e) {
    this.model.save();
    this.frame.setVisible(false); 
    this.frame.dispose();
  }
}

