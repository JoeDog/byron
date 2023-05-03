package org.joedog.byron;

import org.joedog.byron.controller.*;
import org.joedog.byron.model.*;
import org.joedog.byron.players.*;
import org.joedog.byron.engine.Engine;
import org.joedog.byron.view.*;
import org.joedog.byron.view.actions.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
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
  private JFrame         main;
  private int            trials;

  public Main() {
    this.splash     = new Splash();
    this.controller = new GameController();
    splash.setMessage("Game controller");
    this.gameBoard  = new GameView(controller);
    splash.setMessage("Game board");
    this.gameModel  = new GameModel();
    splash.setMessage("Game data");
    this.scoreModel = new ScoreModel();
    splash.setMessage("Players");
    this.factory    = new PlayerFactoryImpl();
    splash.setMessage("M.E.N.A.C.E. engine");
    splash.setMessage("MiniMax engine");
    splash.setMessage("Monte Carlo engine");
    splash.setMessage("Reinforced Learing engine");

    controller.addModel(gameModel);
    controller.addModel(scoreModel);
    controller.addView(gameBoard);
    this.actions = new GameActions(controller);
    splash.close();
    
    main = new JFrame("Byron");
    main.getContentPane().add(gameBoard, BorderLayout.CENTER);
    main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    menu = new MenuView(controller, actions);
    main.setJMenuBar(menu);
    main.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e) {
        //We'll snag and save these properties when we exit
        System.getProperties().put("main.X", ""+main.getX());
        System.getProperties().put("main.Y", ""+main.getY());
      }
    });
    Dimension dim  = Toolkit.getDefaultToolkit().getScreenSize();
    int x = controller.getIntProperty("MainX");
    int y = controller.getIntProperty("MainY");
    if (x == 0 && y == 0) {
      int w = main.getSize().width;
      int h = main.getSize().height;
      x = (dim.width-w)/2;
      y = (dim.height-h)/2;
    }
    main.pack();
    main.setLocation(x, y);
    main.setVisible(true);
    if (controller.getBooleanProperty("Training") == true) {
      this.player1 = factory.getPlayer(controller, Player.COMPUTER, controller.XSQUARE);
      this.player1.setEngine(Engine.TRAINING);
      this.trials  = controller.getIntProperty("Trials");
    } else {
      this.player1 = factory.getPlayer(controller, Player.HUMAN,    controller.XSQUARE);
    }
    this.player2 = factory.getPlayer(controller, Player.COMPUTER, controller.OSQUARE);

    /**
     * Set number of thoughts for the Monte Carlo engine;
     * Byron will add a thought every fifteen games inside 
     * the GameController.
     */
    if (controller.getProperty("Thoughts") != null && controller.getProperty("Thoughts").length() > 0) {
      System.getProperties().put("byron.thoughts", controller.getProperty("Thoughts"));
    } else {
      System.getProperties().put("byron.thoughts", "1");
    }

    for ( ;; ) {
      while(controller.alive){
        this.play();
      }
    }
  }

  public synchronized void play () {
    int turn         = 0;
    int status       = controller.gameStatus();
    Player[] players = { 
      player2, 
      player1 
    };
    if (status == GameController.ACTIVE) {
      players[0].start();
      players[1].start();
    }
    do {
      status = controller.gameStatus(); 
      if (status != GameController.ACTIVE) break;
      if ((players[turn%2].getType()).equals("HUMAN")) {
        controller.setStatus("Your turn...");
      } else {
        controller.setStatus("My turn...");
        players[turn%2].setEngine(controller.getIntProperty("EngineO"));
      }
      players[turn%2].takeTurn(); 
      turn++;
    } while (status == GameController.ACTIVE);
    players[0].finish(status);
    players[1].finish(status);
    if (controller.getBooleanProperty("Training") == true) {
      this.trials--;
      if (this.trials > 0) {
        controller.newMatch();
      } else {
        players[1].save();
        System.exit(0);
      }
    }
    return;
  }
 
  public static void main(String[] args) {
    Main main = new Main();
  }
}

