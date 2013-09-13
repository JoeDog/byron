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
  private String prefItems[]  = new String [] {"M.E.N.A.C.E", "Minimax"};
  private String helpItems[]  = new String [] {"Help", "About"};
  char   fileShorts[] = {'N', 'S', 'X'};
  private JMenu  fileMenu; 
  private JMenu  prefMenu;
  private JMenu  helpMenu;
  private GameActions actions;
  static final long serialVersionUID = -333243492884001234L;
  
  public MenuView (GameActions actions) {
    this.actions  = actions;    
    this.fileMenu = new JMenu("File");
    this.prefMenu = new JMenu("Preferences");
    this.helpMenu = new JMenu("Help");
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
    // Pref Menus
    for (int i = 0; i < prefItems.length; i++) {
      prefMenu.add(item = new JRadioButtonMenuItem(prefItems[i], (i==0)?true:false)); 
      group.add(item);
      item.addActionListener(actions.getAction(prefItems[i]));
    }

    // Help Menu
    JMenuItem meti;
    for (int i = 0; i < helpItems.length; i++) {
      meti = new JMenuItem(helpItems[i]);
      helpMenu.add(meti);
      meti.addActionListener(actions.getAction(helpItems[i]));
    } 
    this.add(fileMenu);
    this.add(prefMenu);
    this.add(helpMenu);
  } 
}
