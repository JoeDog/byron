package org.joedog.byron;

import org.joedog.byron.controller.*;
import org.joedog.byron.model.*;
import org.joedog.byron.players.*;
import org.joedog.byron.view.*;
import org.joedog.byron.view.actions.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.WindowConstants;

/**
 * @author Jeffrey Fulmer
 */
public class Main {
  private GameController controller;
  private GameActions    actions;
  private MenuView       menu;
  private GameView       gameBoard;
  private GameModel      gameModel;
  private ScoreModel     scoreModel;
  private Splash         splash;
  private PlayerFactory  factory;
  private Player         player1;
  private Player         player2;

  public Main() {
    this.splash     = new Splash();
    this.controller = new GameController();
    splash.setMessage("Game controller");
    this.gameBoard  = new GameView(controller);
    splash.setMessage("Game board");
    this.gameModel  = new GameModel();
    splash.setMessage("Game data");
    this.scoreModel = new ScoreModel();
    this.actions    = new GameActions(controller);
    this.menu       = new MenuView(actions);
    splash.setMessage("Players");
    this.factory    = new PlayerFactoryImpl();
    this.player1    = factory.getPlayer(controller, Player.HUMAN,    controller.XSQUARE);
    splash.setMessage("M.E.N.A.C.E. engine");
    this.player2    = factory.getPlayer(controller, Player.COMPUTER, controller.OSQUARE);
    splash.setMessage("MiniMax engine");

    controller.addView(gameBoard);
    controller.addModel(gameModel);
    controller.addModel(scoreModel);
    splash.close();
    
    JFrame    main = new JFrame("Byron");
    Dimension dim  = Toolkit.getDefaultToolkit().getScreenSize();
    main.getContentPane().add(gameBoard, BorderLayout.CENTER);
    main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    main.setJMenuBar(menu);
    main.pack();
    int w = main.getSize().width;
    int h = main.getSize().height;
    int x = (dim.width-w)/2;
    int y = (dim.height-h)/2;
    main.setLocation(x, y);
    main.setVisible(true);
    for ( ;; ) {
      while(controller.alive){this.play();}
    }
  }

  public synchronized void play () {
    int turn         = 0;
    int status       = GameController.ACTIVE;
    Player[] players = { player2, player1 };
    players[0].start();
    players[1].start();
    while (true) {
      status = controller.gameStatus(); 
      if (status > 0) break;
      if ((players[turn%2].getType()).equals("HUMAN")) 
        controller.setStatus("Your turn...");
      else {
        controller.setStatus("My turn...");
        players[turn%2].setEngine(controller.getEngine());
      }
      players[turn%2].takeTurn(); 
      turn++;
    }
    players[0].finish(status);
    players[1].finish(status);
    return;
  }
 
  public static void main(String[] args) {
    Main main = new Main();
  }
}

