package org.joedog.byron.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.beans.PropertyChangeEvent;
import org.joedog.byron.util.clock.*;
import org.joedog.byron.controller.*;

public class ScorePanel extends AbstractView {
  private double score;
  private String prefix;
  private String spacer  = new String("    ");
  private JLabel face;
  private JLabel bonus;
  private int    fontSize = 10;
  private GameController controller;
  static final long serialVersionUID = -687856439824005033L;

  public ScorePanel (GameController controller) {
    this.controller = controller;
    setup("", 0.0);
  }
  
  public ScorePanel (GameController controller, String prefix) {
    this.controller = controller;
    setup(prefix, 0.0);
  }

  public ScorePanel (GameController controller, double score) {
    this.controller = controller;
    setup("", score);
  }

  private void setup(String prefix, double score) {
    this.prefix = prefix;
    this.score  = score;
    this.face   = new JLabel();
    face.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
    face.setText(this.spacer+this.prefix+" "+this.score+this.spacer);
    face.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    this.setLayout(new BorderLayout());
    this.add(face, BorderLayout.CENTER);
    this.bonus  = new JLabel("  ");
    this.bonus.setFont(new Font("Helvetica", Font.BOLD, fontSize+2));
    this.bonus.setForeground (new Color(11, 160, 85));
    this.add(bonus, BorderLayout.EAST);
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  private void setFontSize (int fontSize) {
    this.fontSize = fontSize;
  }

  public void setScore (Double score) {
    this.score = (double)score;
    face.setText(this.spacer+this.prefix+" "+this.score+this.spacer);
  } 

  public void setBonus(Integer marks) {
    if (marks.intValue() < 7) {
      bonus.setText(" !");
    } else {
      bonus.setText("  ");
    }
    return;
  } 
  
  public double getScore () {
    return this.score;
  }

  public void modelPropertyChange(PropertyChangeEvent e) {
    if (e.getPropertyName().equals(controller.SCORE)) {
      try {
         String s = (String)e.getNewValue();
         double d = Double.valueOf(s.trim()).doubleValue();
         this.setScore(d);
      } catch (NumberFormatException nfe) {
         System.out.println("NumberFormatException: " + nfe.getMessage());
      }
    }
  }
}
