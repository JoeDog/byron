package org.joedog.byron.engine.montecarlo;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Thread;
import java.lang.ThreadGroup;
import org.joedog.byron.engine.*;
import org.joedog.byron.controller.GameController;

public class MonteCarlo extends Engine {
  private State  state;
  private Node   node;
  private static final int COMPUTER =  1;
  private static final int HUMAN    = -1;
  
  public MonteCarlo () {
  }

  public int getMove(String pattern) {
    int cnt  = 0;
    int move = -1;
    state    = new State(pattern);  
    node     = new Node(state);
    node.expand(this.getValidMoves(state, true));
    final long start = System.currentTimeMillis();
    ThreadGroup tg   = new ThreadGroup("Trial");
    Thread threads[] = new Thread[10];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(tg, ""+i) {
        @Override
        public void run() {
          int trials = 0;
          while (trials < 10000) {
            trial(node, true);
            trials++;
          }
        }
      };
      threads[i].start();
    } 
    while (tg.activeCount() > 0) {
      trial(node, true);
      cnt++;
    }
    if (state.getStatus() == GameController.ACTIVE) {
      Node best  = node.best(true);
      move       = best.move(this.state);
      this.state = best.getState();
      this.node  = best;
    }
    return move;
  }

  public void reward()  {}
  public void punish()  {}
  public void restore() {}

  public int trial(Node n, boolean turn) {
    int result = GameController.DRAW;

    n.visit();
    if (! n.isLeaf()) {
      result = trial(n.best(turn), !turn);
    } else {
      n.expand(this.getValidMoves(n.getState(), turn));
      if (!n.isLeaf()) {
        n = node.getChild();
        n.visit();
      }
      result = simulation(n.getState(), turn);
    } 
    if (result == GameController.OWIN) {
      n.reward();
    }
    if (result == GameController.XWIN) {
      n.punish();
    }

    return result;
  }

  public ArrayList<State> getValidMoves(State state, boolean turn) {
    String pattern  = state.getPattern();
    ArrayList moves = new ArrayList<State>();

    if (state.getStatus() == GameController.ACTIVE) {
      for (int i = 0; i < pattern.length(); i++) {
        if (pattern.charAt(i) == '-') {
          String mark = (turn==true) ? "O" : "X";
          String tmp  = pattern.substring(0,i)+mark+pattern.substring(i+1, pattern.length());
          moves.add(new State(tmp));
        }
      }
    }
    return moves;
  }

  private State applyMove(State state, Move move) {
    String tmp = state.getPattern();
    String res = new String("");
    for (int i = 1; i <= tmp.length(); i++) {
      if (i == move.getMove()) {
        if (tmp.charAt(i-1)=='-') 
          res = res+"O";
      } else {
        res = res+tmp.charAt(i-1);
      }
    } 
    return (new State(res));
  }

  private int simulation(State state, boolean turn) {
    int status = state.getStatus();

    if (status != GameController.ACTIVE) {
      return status;
    } else {
      return simulation(getRandomMove(state, turn), !turn);
    }
  }

  private State getRandomMove(State state, boolean turn) {
    Random rand = new Random(); 
    ArrayList<State> moves = this.getValidMoves(state, turn);
    int s = (moves.size() < 1) ? 1 : moves.size();
    if (turn) {
      // If we can win in one move; we'll take it.
      for (int i = 0; i < s; i++) {
        if (moves.get(i).getStatus() == GameController.OWIN) {
          return moves.get(i);
        }   
      }
    }
    if (! turn) { 
      // If they can win in one move; we're gonna assume
      // they're smart enough to make it.
      for (int i = 0; i < s; i++) {
        if (moves.get(i).getStatus() == GameController.XWIN) {
          return moves.get(i);
        }   
      }
    }
    return moves.get(rand.nextInt(s));
  }
}
