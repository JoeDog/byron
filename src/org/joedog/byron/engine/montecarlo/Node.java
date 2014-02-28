package org.joedog.byron.engine.montecarlo;

import java.util.ArrayList;
import java.util.Random;

public class Node {
  private State   state;
  private int     visits;
  private int     score;
  private Random  rand; 
  private boolean done;
  ArrayList<Node> next;

  public Node (State state) {
    this.state  = state;
    this.next   = null;
    this.visits = 0;
    this.score  = 0;
    this.rand   = new Random();
    this.done   = false;
  }

  public synchronized void expand (ArrayList<State> moves) {
    if (! done) {
      done = true;
      next = new ArrayList<Node>();
      for (State s : moves) {
        next.add(new Node(s));
      }
    }
  }

  public Node best (boolean turn) {
    int t = (turn) ? 1 : -1;
    float bias;
    float tare; 
    float max   = -Float.MAX_VALUE * t;
    int   index = 0;
    float f = 1;

    for (int i = 0; i < next.size(); i++) {
      Node n = next.get(i);
      float nscore = (float) n.getScore() / ((float) (n.visits() + Float.MIN_VALUE));
      bias = 2 * f * (float) (Math.sqrt(Math.log((float) this.visits()) / ((float) n.visits() + Float.MIN_VALUE)));
      tare = Float.MIN_VALUE * rand.nextInt(next.size() * next.size());
      float bscore = nscore + tare + (bias * t);
      if (bscore * t > max * t) {
        max   = bscore;
        index = i;
      }
    }
    return next.get(index);
  }

  public int move(State s) {
    String a = this.state.getPattern();
    String b = s.getPattern();
    int move = 0;
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) != b.charAt(i))
        move = i +1;
    }
    return move;
  }

  public State getState() {
    return this.state;
  }

  public int getScore() {
    return this.score;
  }

  public boolean isLeaf() {
    return (next == null || next.isEmpty());
  }

  public synchronized void visit() {
    this.visits++;
  }

  public synchronized int visits() {
    return this.visits;
  }

  public Node getChild() {
    int i = rand.nextInt(next.size());
    return next.get(i);
  }

  public void reward() {
    this.score += 1;
  }

  public void punish() {
    this.score -= 1;
  }
}
