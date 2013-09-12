package org.joedog.byron.util;

public class Data { 
  private int result;
  private int time;

  public Data (int result, int time) {
    this.result = result;
    this.time   = time;
  }

  public int getResult () {
    return this.result;
  }

  public int getTime () {
    return this.time;
  }
}
