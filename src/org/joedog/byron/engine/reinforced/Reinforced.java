package org.joedog.byron.engine.reinforced;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.HashMap;
import java.util.Stack;

import org.joedog.byron.engine.*;
import org.joedog.util.RandomUtils;

public class Reinforced extends Engine {
  private int     count    = 0;
  private boolean training = false;
  private Bundle  turns    = new Bundle();
  private Bundle  stack    = new Bundle();
  private static  Reinforced _instance = null;
  private static  Object     mutex     = new Object();

  private Reinforced () {
    this(false);
  }

  private Reinforced (boolean training) {
    this.setTraining(training);
    if (this.training != true) {
      this.load();
    }
  }
  
  public synchronized static Reinforced getInstance() {
    if (_instance == null) {
      synchronized(mutex) {
        if (_instance == null) {
          _instance = new Reinforced(false);
        }
      }
    }
    return _instance;
  }

  public synchronized static Reinforced getInstance(boolean training) {
    if (_instance == null) {
      synchronized(mutex) {
        if (_instance == null) {
          _instance = new Reinforced(training);
        }
      }
    }
    return _instance;
  }

  public void setTraining(boolean training) {
    this.training = training;
  }

  public int getMove(String pattern){
    if (this.training) {
      return this.getTrainingMove(pattern);
    } else {
      return this.getLearnedMove(pattern);
    }
  }

  public void punish () {
    this.count += 1;
    while (turns.count() > 0) {
      Move m = turns.remove(turns.count()-1);
      this.stack.punish(m); 
    }
  }

  public void reward () {
    this.count += 1;
    while (turns.count() > 0) {
      Move m = turns.remove(turns.count()-1);
      this.stack.reward(m); 
    }
  }

  public void restore() {
  }

  public void save() {
    try {    
      FileOutputStream   file = new FileOutputStream(System.getProperty("byron.brain")); 
      ObjectOutputStream out  = new ObjectOutputStream(file); 
      out.writeObject(this.stack); 
      out.close(); 
      file.close(); 
    } catch(IOException ex) { 
      System.out.println("IOException is caught"); 
    }
  }

  public void load() {
    try {    
      FileInputStream file = new FileInputStream(System.getProperty("byron.brain")); 
      ObjectInputStream in = new ObjectInputStream(file); 
      this.stack = (Bundle)in.readObject(); 
      in.close(); 
      file.close(); 
    } catch(IOException ex) { 
      System.out.println(ex.toString()); 
    } catch(ClassNotFoundException ex) { 
      System.out.println("ClassNotFoundException is caught"); 
    }
  }

  public String toString() {
    return "Reinforced Learning engine";
  }

  private int getLearnedMove(String pattern){
    int    valu = -100000;
    int    move = -1;
    Bundle pack = new Bundle();

    if (pattern.equals("---------")) {
      // Just to make things interesting...
      return this.getTrainingMove(pattern);
    }

    for (Move m : this.stack.getMoves()) {
      if (m.matches(pattern)) {
        pack.add(m);
      }
    }      
    for (Move m : pack.getMoves()) {
      if (m.getValue() > valu) {
        valu = m.getValue();
        move = m.getMove();
      }   
    }
    if (move > 0) {
      return move;
    }
    /**
     * Something's awry - we probably don't have a training file
     */
    return this.getTrainingMove(pattern);
  }

  private int getTrainingMove(String pattern){
    while (true) {
      int move = RandomUtils.range(0,8);
      if (pattern.charAt(move) == '-') {
        this.turns.add(new Move(pattern, move+1));
        return move+1;
      }
    }
  }
}
