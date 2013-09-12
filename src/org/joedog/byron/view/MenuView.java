package org.joedog.byron.view;

import java.awt.Toolkit;
import javax.swing.KeyStroke;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import org.joedog.byron.view.actions.*;

public class MenuView extends JMenuBar {
  private String fileItems[]  = new String [] {"New", "Scores", "Exit"};
  private String prefItems[]  = new String [] {"Pupil", "Guru"};
  char   fileShorts[] = {'N', 'S', 'X'};
  private JMenu  fileMenu; 
  private JMenu  prefMenu;
  private GameActions actions;
  static final long serialVersionUID = -333243492884001234L;
  
  public MenuView (GameActions actions) {
    this.actions  = actions;    
    this.fileMenu = new JMenu("File");
    this.prefMenu = new JMenu("Preferences");
    this.setup();
  }
 
  public void setup () {
    // File Menu
    for (int i = 0; i < fileItems.length; i++) {
      JMenuItem item = new JMenuItem(fileItems[i]);
      item.setAccelerator(
        KeyStroke.getKeyStroke(fileShorts[i], Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)
      );
      item.addActionListener(actions.getAction(fileItems[i]));
      fileMenu.add(item);
    }
    
    JMenuItem   item; 
    ButtonGroup group = new ButtonGroup();
    for (int i = 0; i < prefItems.length; i++) {
      prefMenu.add(item = new JRadioButtonMenuItem(prefItems[i], (i==0)?true:false)); 
      group.add(item);
      item.addActionListener(actions.getAction(prefItems[i]));
    }
    this.add(fileMenu);
    this.add(prefMenu);
  } 
}
