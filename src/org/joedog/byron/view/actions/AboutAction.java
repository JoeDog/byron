package org.joedog.byron.view.actions;

import org.joedog.byron.view.Help;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutAction implements ActionListener {

  public AboutAction() {
  }

  public void actionPerformed (ActionEvent e) {
    new Help("About This Software", "about.html", 300, 240);
  }
}

