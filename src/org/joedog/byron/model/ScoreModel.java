package org.joedog.byron.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.security.*;
import org.joedog.byron.controller.*;
import org.joedog.byron.util.*;

public class ScoreModel extends AbstractModel {
  private double  xscore;
  private double  oscore;
  private double  newlife;
  private int     lives;
  public  File    data;

  public ScoreModel () {
    AccessController.doPrivileged(new PrivilegedAction<Object>() {
      public Object run() {
        String path = System.getProperty("user.home") + File.separator + ".byron.score";
        data   = new File(path);
        return null;
      }
    });
    this.refresh();
  }

  public void refresh () {
    this.xscore  = 0.0;
    this.oscore  = 0.0;
    this.newlife = 0.0;
    this.lives   = 5;
    firePropertyChange(GameController.LIVES, GameController.LIVES, new String(""+this.lives));
    firePropertyChange(GameController.SCORE, GameController.SCORE, new String(""+0.0));
  } 

  public void setScore (Data data) {
    double score;
    if (data.getResult() == GameController.XWIN) {
      score         = (data.getTime()*0.25);
      this.xscore  += score;
      this.newlife += score;
      if ((this.newlife >= 25) && (this.lives < 5)) {
        this.lives++;
        this.newlife = 0.0;
        firePropertyChange(GameController.LIVES, GameController.LIVES, new String(""+this.lives));
      }
    }
    if (data.getResult() == GameController.OWIN) {
      this.xscore -= 1;
      this.lives--;
      firePropertyChange(GameController.LIVES, GameController.LIVES, new String(""+this.lives));
    } 
  }

  public double getScore() {
    return this.xscore;
  }

  public int getLives() {
    if (this.lives < 0) 
      return 0;
    else 
      return this.lives;
  }
}
