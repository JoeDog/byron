package org.joedog.byron.util;

public class Data { 
  private int result;
  private int time;
  private int marks;

  public Data (int result, int time) {
    this.result = result;
    this.time   = time;
    this.marks  = 9;
  }

  public Data (int result, int time, int marks) {
    this.result = result;
    this.time   = time;
    this.marks  = marks;
  }

  public int getResult () {
    return this.result;
  }

  public int getTime () {
    return this.time;
  }

  public int getMarks() {
    return this.marks;
  }
}
