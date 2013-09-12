package org.joedog.byron.controller;

import org.joedog.byron.view.*;
import org.joedog.byron.util.*;
import org.joedog.byron.engine.Engine;

public class GameController extends AbstractController {
  public boolean alive = true;
  public final static int ACTIVE   = 0;
  public final static int XWIN     = 1;
  public final static int OWIN     = 2;
  public final static int DRAW     = 3;
  public static final int SQUARE[] = 
  {
    1, 2, 3, // row one
    4, 5, 6, // row two
    7, 8, 9  // row three
  };
  public static final String PATTERN   = "X";     // holds the winning pattern
  public static final String LIVES     = "LIVES";
  public static final String SCORE     = "SCORE";
  public static final String ROW[]     = 
  {
    "A", // first row across
    "B", // second row across
    "C"  // third row across
  };
  public static final String COL[]     = 
  { 
    "D", // first column down
    "E", // second column down
    "F"  // third column down
  };
  public static final String DIA[]     = 
  {
    "G", // diagonal from 1 to 9
    "H"  // diagonal from 3 to 7
  };
  public static final int  EMPTY     = 0;
  public static final int  XSQUARE   = 1;
  public static final int  OSQUARE   = 2;
  
  private boolean over      = false;
  private boolean added     = false;
  private boolean waiting   = false;
  private int     square    = -1;
  private int     position  = -1;
  private int     engine    = Engine.MENACE;

  public GameController () {
  }

  public synchronized void newGame () {
    this.over     = false;
    this.square   = -1;
    this.position = -1;
    runModelMethod("reset");
    runViewMethod("reset");
    this.alive    = true;
  }
  
  // NewAction
  public synchronized void newMatch() {
    runModelMethod("reset");
    runModelMethod("refresh");
    runViewMethod("reset");
    this.over     = false;
    this.added    = false;
    this.square   = -1;
    this.position = -1;
    this.alive    = true;
  } 

  // ExitAction
  public void exit() {
    setStatus("Shutting down...");
    try {
      Thread.sleep(200);
    } catch (Exception e) {
       e.printStackTrace();
    }
    System.exit(0);
  }

  // ScoresAction
  public void displayScores() {
    int    lives  = ((Integer)getModelProperty("Lives")).intValue();
    double score  = (!this.added)?(Double)getModelProperty("Score"):-1.0;
    HighScorePanel hs = new HighScorePanel();
    if (lives > 0) {
      hs.display(-1.0);
    } else {
      hs.display(score);
      this.added = true;
    }
    return;
  }

  public void selectSquare (int square) {
    this.waiting = false; 
    this.square  = square; 
  }

  public int requestMove () {
    this.waiting = true;
    while (waiting && !over) {
      try {
        Thread.sleep(100);
      } catch (Exception e) {
         e.printStackTrace();
      }
    }
    return this.square;
  }

  public void setStatus (String status) {
    setViewProperty("Status", status);
  }

  public void setEngine (int engine) {
    this.engine = engine;
  }

  public synchronized void updateBoard (int mark, int square) {
    setModelProperty("Square", new Move(mark, square));
    setViewProperty("Board", new Move(mark, square));
  }

  public boolean isEmpty (int square) {
    return ((Boolean)getModelProperty("IsEmpty", square)).booleanValue();
  }

  public String getGameString () {
    return (String)getModelProperty("GameString");
  }

  public int getEngine() {
    return this.engine;
  }

  public boolean isGameOver() {
    return this.over;
  }

  public synchronized int gameStatus () {
    int lives   = 5;
    int status  = ((Integer)getModelProperty("GameStatus")).intValue();
    switch (status) {
      case ACTIVE:
        this.over = false;
        break;
      case XWIN: 
        if (!this.over) {
          Long l   = (Long)getViewProperty("TimeRemaining");
          int time = l.intValue();
          setModelProperty("Score", new Data(this.XWIN, time));
          setViewProperty("Winner", this.XWIN);
          setViewProperty("Status", "X Wins!");
          setViewProperty("Score",  (Double)getModelProperty("Score"));
        }
        this.over = true;
        break;
      case OWIN: 
        if (!this.over) {
          setModelProperty("Score", new Data(this.OWIN, 0));
          setViewProperty("Winner", this.OWIN);
          setViewProperty("Status", "O Wins!");
          setViewProperty("Score",  (Double)getModelProperty("Score"));
          lives = ((Integer)getModelProperty("Lives")).intValue();
          if (lives <= 0) {
            setViewProperty("Status", "GAME OVER!");
            this.displayScores();
          }
        }
        over = true;
        break;
      case 3:
        setViewProperty("Status", "Sister kisser");
        over = true;
        break;
    } 
    if (lives <= 0) this.alive = false;
    return status;
  }
}
