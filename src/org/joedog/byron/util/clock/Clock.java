package org.joedog.byron.util.clock;

import java.util.ArrayList;

public class Clock implements Runnable {
  private Thread  timer = null;
  private int     nap;
  private long    now; 
  private long    last;
  private long    start;
  private long    count;

  public final static int CLOCK = 1; // counts down
  public final static int TIMER = 2; // counts down
  
  private ArrayList <ClockMonitor> timers; 

  public Clock () {
    this.nap    = 1;
    this.timers = new ArrayList<ClockMonitor>();
  }
 
  public Clock (int nap) {
    this.nap    = nap;
    this.timers = new ArrayList<ClockMonitor>();
  } 

  public long getCurrentTime() {
    return this.now;
  }

  public long getStartTime () {
    return this.start;
  }

  public long getTimeSinceLastTick() {
    return (now - last);
  }

  public long getCurrentTickCount() {
    return count;
  }

  public void start () {
    if (timer == null) {
      timer = new Thread(this);
    }
    timer.start();
    start = last  = now = System.currentTimeMillis();
    return;
 } 

  public void stop () {
    if (timer != null) {
      timer.stop();
    }
    timer = null;
    return;
  }

  public void run () {
    while (timer != null) {
      long left; 

      now = System.currentTimeMillis();
      count++;

      for (ClockMonitor monitor: timers) {
        monitor.tick(this);
      }

      last = now;
      left = nap - System.currentTimeMillis() + now;      
      if (left > 0 ){
        try {
          Thread.sleep(left);
        } catch(InterruptedException e) {}
      }
    }
    timer = null;
  }

  public void addClockMonitor (ClockMonitor monitor) {
    this.timers.add(monitor);
  }  
 
  public void removeClockMonitor (ClockMonitor monitor) {
    this.timers.remove(monitor);
  } 

}
