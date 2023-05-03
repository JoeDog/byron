package org.joedog.byron.controller;

import org.joedog.byron.view.*;
import org.joedog.byron.util.*;
import org.joedog.byron.engine.Engine;

public class GameController extends AbstractController {
  public boolean alive = true;
  public final static int ACTIVE   =  0;
  public final static int XWIN     =  1;
  public final static int OWIN     = -1;
  public final static int DRAW     =  3;
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
    "H", // diagonal from 3 to 7
    "I"  // draw
  };
  public static final int  EMPTY     =  0;
  public static final int  XSQUARE   =  1;
  public static final int  OSQUARE   = -1;
  
  private boolean over      = false;
  private boolean added     = false;
  private boolean waiting   = false;
  private int     square    = -1;
  private int     position  = -1;
  private int     counter   = 0;
  private int     engine;

  public GameController () {
    //System.getProperties().put("byron.thoughts", "5");  
  }

  public synchronized void newGame () {
    this.over     = false;
    this.square   = -1;
    this.position = -1;
    this.counter  += 1;
    runModelMethod("reset");
    runViewMethod("reset");
    this.alive    = true;
    if (counter % 15 == 0) {
      // this is here to make Monte Carlo increasingly smarter
      System.getProperties().put("byron.thoughts", ""+this.addThought());  
    }
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
    this.engine   = this.getIntProperty("Engine");
    if (this.getProperty("Thoughts") != null) {
      System.getProperties().put("byron.thoughts", this.getProperty("Thoughts"));
    } else {
      System.getProperties().put("byron.thoughts", "5");
    }
  } 

  public String getProperty(String property) {
    String str = (String)getModelProperty(property);
    if (str == null) {
      return "";
    } else {
      return str;
    }
  }

  public int getIntProperty(String property) {
    String tmp = (String)getModelProperty(property);
    try {
      if (tmp != null && tmp.length() > 0) {
        return Integer.parseInt(tmp);
      }
    } catch (final NumberFormatException nfe) {
      return 0;
    }
    return 0;
  }

  public boolean getBooleanProperty(String property) {
    String tmp = (String)getModelProperty(property);
    if (tmp == null) return false;
    if (tmp.equals("true")) {
      return true;
    }
    return false;
  }

  // ExitAction
  public void exit() {
    setStatus("Shutting down...");
    this.setModelProperty("MainX", System.getProperty("main.X"));
    this.setModelProperty("MainY", System.getProperty("main.Y"));
    runModelMethod("save");
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

  public void setEngineO (int engine) {
    this.engine = engine;
    setModelProperty("EngineO", ""+engine);
  }

  public synchronized void updateBoard (int mark, int square) {
    setViewProperty("Board", new Move(mark, square));
    setModelProperty("Square", new Move(mark, square));
  }

  public boolean isEmpty (int square) {
    return ((Boolean)getModelProperty("IsEmpty", square)).booleanValue();
  }

  public String getGameString () {
    return (String)getModelProperty("GameString");
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
          Long l    = (Long)getViewProperty("TimeRemaining");
          int time  = l.intValue();
          int marks = ((Integer)getModelProperty("MarkTotal")).intValue();
          setModelProperty("Score", new Data(this.XWIN, time, marks));
          setViewProperty("Winner", this.XWIN);
          setViewProperty("Status", "X Wins!");
          setViewProperty("Score",  (Double)getModelProperty("Score"));
          setViewProperty("Bonus",  marks);
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
      case DRAW:
        setViewProperty("Status", "Sister kisser");
        over = true;
        break;
    } 
    if (lives <= 0) this.alive = false;
    return status;
  }

  private int addThought() {
    String tmp = (String)System.getProperty("byron.thoughts");
    try {
      if (tmp != null && tmp.length() > 0) {
        return (Integer.parseInt(tmp)+1);
      }
    } catch (final NumberFormatException nfe) {
      return 1;
    }
    return 1;
  }
}
