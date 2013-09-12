package org.joedog.byron.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.StringBuilder;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;
import org.joedog.byron.util.clock.*;
import org.joedog.byron.controller.*;

public class LivesPanel extends AbstractView {
  private GameController controller;
  private int lives[] = {1, 1, 1, 1, 1};
  private JLabel face;

  static final long serialVersionUID = -876543492884005033L;

  public LivesPanel (GameController controller) {
    this.controller = controller;
    this.face   = new JLabel();
    face.setFont(new Font("Helvetica", Font.PLAIN, 10));
    face.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    this.setLayout(new BorderLayout());
    this.add(face, BorderLayout.CENTER);
    this.display();
  }

  private void display () {
    StringBuilder sb = new StringBuilder(" ");
    for (int i = 0; i < lives.length; i++) {
      if (lives[i] == 1) sb.append("x ");
      else sb.append("o ");
    }
    face.setText(sb.toString());
  }
  
  public void modelPropertyChange(PropertyChangeEvent e) {
    //if (controller.isGameOver()) return;
    if (e.getPropertyName().equals(controller.LIVES)) {
      if (e.getNewValue().equals("5")) {
        for (int i = 0; i < 5; i++)
          lives[i] = 1;
      }
      if (e.getNewValue().equals("4")) {
        int x = 4;
        for (int i = 0; i < x; i++) 
          lives[i] = 1; 
        for (int i = x; i < 5; i++)
          lives[i] = 0;
      }
      if (e.getNewValue().equals("3")) {
        int x = 3;
        for (int i = 0; i < x; i++) 
          lives[i] = 1; 
        for (int i = x; i < 5; i++)
          lives[i] = 0;
      }
      if (e.getNewValue().equals("2")) {
        int x = 2;
        for (int i = 0; i < x; i++) 
          lives[i] = 1; 
        for (int i = x; i < 5; i++)
          lives[i] = 0;
      }
      if (e.getNewValue().equals("1")) {
        int x = 1;
        for (int i = 0; i < x; i++) 
          lives[i] = 1; 
        for (int i = x; i < 5; i++)
          lives[i] = 0;
      }
      if (e.getNewValue().equals("0")) {
        for (int i = 0; i < 5; i++)
          lives[i] = 0;
      }
      display();
    }
  }

}
