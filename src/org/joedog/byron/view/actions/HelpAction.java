package org.joedog.byron.view.actions;

import org.joedog.byron.view.Help;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelpAction implements ActionListener {

  public HelpAction() {
  }
  
  public void actionPerformed (ActionEvent e) {
    new Help("Byron Help Section", "help.html", 400, 648);
  }
}

