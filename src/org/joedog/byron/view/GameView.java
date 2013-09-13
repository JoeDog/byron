package org.joedog.byron.view;

import org.joedog.byron.controller.*;
import org.joedog.byron.util.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GameView extends AbstractView implements MouseListener {
  private Square[][] square = new Square[3][3];
  private JPanel     main   = new JPanel();
  private JPanel     bottom = new JPanel();
  private JPanel     buttons= new JPanel();
  private JLabel[]   spacer = new JLabel[3];
  private StatusBar  status = new StatusBar(); 
  private Action     resetAction = new ResetAction();
  private GameController controller;
  private ClockFace   clock;
  private ScorePanel  score;
  private LivesPanel  lives;

  static final long serialVersionUID = -196491492884005033L;

  public GameView (GameController controller) {
    this.controller = controller;
    this.clock      = new ClockFace(controller);
    this.score      = new ScorePanel(controller);
    this.lives      = new LivesPanel(controller);
    this.setPreferredSize(new Dimension(280,280));
    this.setBackground(Color.lightGray);
    this.setup();
    resetAction.setEnabled(true);
    status.add(clock);
    status.add(score);
    status.add(lives);
    clock.set(10);
    clock.start();
    score.setScore(0.0);
    controller.addView(clock);
    controller.addView(score);
    controller.addView(lives);
  }

  public void setStatus (String message) {
    status.setMessage(message);
  }

  public void reset () {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        square[x][y].setWinner(0);
        square[x][y].setValue(controller.EMPTY);
        square[x][y].setColor(Color.BLACK);
      }
    } 
    resetAction.setEnabled(false);
    score.setBonus(9);
    clock.stop();
    clock.start();
  }

  private void setup() {
    int id = 0;
    BorderLayout bl = new BorderLayout();
    GridLayout   gl = new GridLayout(3, 3);
    FlowLayout   fl = new FlowLayout();
    for (int i=0; i < 3; i++)
      spacer[i] = new JLabel("    ");
    gl.setVgap(1);
    gl.setHgap(1);
    main.setLayout(gl);
    main.setBackground(Color.GRAY);
    buttons.setLayout(fl);
    buttons.setBackground(Color.lightGray);
    buttons.add(new JButton(resetAction));
    bottom.setLayout(new BorderLayout());
    bottom.setBackground(Color.lightGray);
    bottom.add(buttons, java.awt.BorderLayout.CENTER);
    bottom.add(status,  java.awt.BorderLayout.SOUTH);
    this.setLayout(bl);
    this.add(spacer[0], BorderLayout.NORTH);
    this.add(spacer[1], BorderLayout.EAST);
    this.add(spacer[2], BorderLayout.WEST);
    this.add(main,      BorderLayout.CENTER);
    this.add(bottom,    BorderLayout.SOUTH);
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        id++;
        square[x][y] = new Square(id);
        main.add(square[x][y]);
        square[x][y].addMouseListener(this);
      }
    }
  }

  public void display() {
    System.out.println("Square values:");
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        System.out.print(square[x][y].getValue());
      } System.out.println("");
    } System.out.println("---");
  }

  public void mousePressed (MouseEvent e) {
    int id;
    controller.gameStatus();
    if (controller.isGameOver()) {
      controller.newGame();
      return;
    }
    if (e.getSource() instanceof Square) {
      id = ((Square)e.getSource()).getId();
      controller.selectSquare(id);
    }
    //display();
  }
  public void mouseReleased (MouseEvent e) {}
  public void mouseEntered (MouseEvent e) {}
  public void mouseExited (MouseEvent e) {}
  public void mouseClicked (MouseEvent e) { }

  public void setWinner (Integer p) {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        square[x][y].setWinner(p.intValue());
      }
    } 
  }

  public void setBoard (Move move) {
    int mark   = move.getMark();
    int pos    = move.getSquare();
    int index  = 1;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (pos == index) {
          square[x][y].setValue(mark);
          square[x][y].repaint();
          return;
        }
        index++;
      }
    }
  }
  
  public void modelPropertyChange(PropertyChangeEvent e) {
    if (controller.isGameOver()) return;
    if (e.getNewValue() == null) return;
    if (e.getPropertyName().equals(controller.PATTERN)) {
      Color green = new Color(11, 160, 85);
      if (e.getNewValue().equals("X")) return;
      if (e.getNewValue().equals("A")) {
        for (int i = 0; i < 3; i++) {
          square[0][i].setColor(green);
          square[0][i].repaint();
        }
      }
      if (e.getNewValue().equals("B")) {
        for (int i = 0; i < 3; i++) {
          square[1][i].setColor(green);
          square[1][i].repaint();
        }
      }
      if (e.getNewValue().equals("C")) {
        for (int i = 0; i < 3; i++ ) {
          square[2][i].setColor(green);
          square[2][i].repaint();
        }
      }
      if (e.getNewValue().equals("D")) {
        for (int i = 0; i < 3; i++) {
          square[i][0].setColor(green);
          square[i][0].repaint();
        }
      }
      if (e.getNewValue().equals("E")) {
        for (int i = 0; i < 3; i++) {
          square[i][1].setColor(green);
          square[i][1].repaint();
        }
      }
      if (e.getNewValue().equals("F")) {
        for (int i = 0; i < 3; i++) {
          square[i][2].setColor(green);
          square[i][2].repaint();
        }
      }
      if (e.getNewValue().equals("G")) {
        square[0][0].setColor(green);
        square[0][0].repaint();
        square[1][1].setColor(green);
        square[1][1].repaint();
        square[2][2].setColor(green);
        square[2][2].repaint();
      }
      if (e.getNewValue().equals("H")) {
        square[0][2].setColor(green);
        square[0][2].repaint();
        square[1][1].setColor(green);
        square[1][1].repaint();
        square[2][0].setColor(green);
        square[2][0].repaint();
      }
    }
  }

  private class ResetAction extends AbstractAction {
    static final long serialVersionUID = -222243492884215094L; 

    public ResetAction() {
      putValue(NAME, "Reset");
      putValue(SHORT_DESCRIPTION, "Reset Game");
      putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_ENTER));
    }
    public void actionPerformed(ActionEvent ae) {
      controller.gameStatus();
      if (controller.isGameOver()) {
        controller.newGame();
        return;
      }
    }
  }

  private class Square extends Canvas {
    private int   id;
    private int   wr     = 0;
    private int   hr     = 0;
    private int   winner = 0;
    private int   value  = 0;
    private Color color  = Color.BLACK;
    static final long serialVersionUID = -200243434234218974L; 

    public Square(int id) {
      this.id = id;
      this.wr = -8 + (int)(Math.random()*2);
      this.hr =  2 + (int)(Math.random()*6);
      this.setBackground(Color.LIGHT_GRAY);
    }

    public synchronized void setValue(int value) {
      this.value = value;
      repaint();
    }

    public int getValue() {
      return this.value;
    }

    public int getId() {
      return this.id;
    }

    public void setWinner (int winner) {
      this.winner = winner;
      repaint();
      resetAction.setEnabled(true);
    }

    public void setColor (Color color) {
      if (this.value == 0) {
        this.color = Color.BLACK;
      } else {
        this.color = color;
      }
    }

    public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      int w   = this.getWidth();
      int h   = this.getHeight();
      Font font = new Font("Helvetica", Font.BOLD, 36);
      g2.setFont(font);
      g2.setColor(color);
      if (value == 1) {
        g2.drawString("X", (w/2)+this.wr, (h/2)+this.hr);
      }
      if (value == 2) {
        g2.drawString("0", (w/2)+this.wr, (h/2)+this.hr);
      }
    }
  }
}
