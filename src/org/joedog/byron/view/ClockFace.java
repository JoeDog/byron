package org.joedog.byron.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Date;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.text.SimpleDateFormat; 
import java.beans.PropertyChangeEvent;
import org.joedog.byron.util.clock.*;
import org.joedog.byron.controller.*;

public class ClockFace extends AbstractView implements ClockMonitor {
  private Clock  clock;
  private String prefix = new String(""); 
  private String spacer = new String("    "); 
  private JLabel face   = new JLabel(this.spacer+"00:00:00"+this.spacer, JLabel.RIGHT);
  private long   start;
  private long   expire;
  private long   ticks;
  private int    type   = Clock.CLOCK;
  private GameController controller;
  static final long serialVersionUID = -652351492884005033L;
 
  private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

  public ClockFace (GameController controller) {
    this.clock      = new Clock();
    this.start      = clock.getStartTime();
    this.controller = controller;
    this.setLayout(new BorderLayout());
    face.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    face.setFont(new Font("Helvetica", Font.PLAIN, 10));
    clock.addClockMonitor(this);
    this.add(face, BorderLayout.CENTER);
    face.setVerticalTextPosition(JLabel.BOTTOM);
  }

  public void set (long expire) {
    this.type   = Clock.TIMER;
    this.expire = expire;
  }

  public void start () {
    clock.start();
  }

  public void stop () {
    clock.stop();
  }

  public void setPrefix(String prefix) {
    this.prefix = "    "+prefix+" ";
  }

  public void tick (Clock clock) {
    setTime();
  }

  public Long getTimeElapsed  () { 
    return new Long(ticks);
  }
 
  public Long getTimeRemaining () {
    if ((expire - ticks) <= 0)  {
      return new Long(0);
    }
    return new Long(expire-ticks);
  }

  private void setTime () {
    switch (this.type) {
      case Clock.CLOCK:
        countUp();
        break;
      case Clock.TIMER:
        countDown();
        break;
      default:
        countUp();
        break;
    }
    return;
  }

  public void countUp () {
    Date date = new Date(0,0,0,0,0,0);
    ticks     = (clock.getCurrentTime() - clock.getStartTime()) / 1000;
    Long l    = new Long(ticks);
    date.setSeconds(l.intValue());
    face.setText(this.spacer+this.prefix+sdf.format(date)+this.spacer);
  }

  private void countDown () {
    Date date = new Date(0,0,0,0,0,0);
    ticks     = (clock.getCurrentTime() - clock.getStartTime()) / 1000;
    Long l    = new Long(expire-ticks);
    if (l.intValue() <= 0) {
      face.setText(this.spacer+this.prefix+sdf.format(new Date(0,0,0,0,0,0))+"    ");
      clock.stop();
    }
    date.setSeconds(l.intValue());
    face.setText(this.spacer+this.prefix+sdf.format(date)+this.spacer);
  }

  public void modelPropertyChange(PropertyChangeEvent e) {
    // AbstractView
  }
}
