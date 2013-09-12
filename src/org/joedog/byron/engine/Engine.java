package org.joedog.byron.engine;

public abstract class Engine {
  public static final int MENACE  = 1;
  public static final int MINIMAX = 2;

  public Engine () {
  }

  public abstract void reward();
  public abstract void punish();
  public abstract void restore();
  public abstract int  getMove(String pattern); 
}
