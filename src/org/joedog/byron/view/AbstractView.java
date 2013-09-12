package org.joedog.byron.view;

import javax.swing.JPanel;
import java.beans.PropertyChangeEvent;

public abstract class AbstractView extends JPanel {
  public abstract void modelPropertyChange(PropertyChangeEvent evt);
}
