package org.joedog.byron.engine.minimax;

import java.util.ArrayList;
import org.joedog.byron.engine.*;

public class MiniMax extends Engine {
  private int    ply;
  private State  state;
  private static final int COMPUTER  =  1;
  private static final int HUMAN     = -1;
  private static MiniMax   _instance = null;
  private static Object    mutex     = new Object();
  
  public MiniMax () {
    this.ply = 5;
  }

  public synchronized static MiniMax getInstance() {
    if (_instance == null) {
      synchronized(mutex) {
        if (_instance == null) {
          _instance = new MiniMax();
        }
      }
    }
    return _instance;
  }

  public int getMove(String pattern) {
    state            = new State(pattern);  
    Evaluation eval  = first(state);
    if (eval != null) {
      return (eval.getMove()).getMove();
    }
    eval = quickie(state);
    if (eval != null) {
      return (eval.getMove()).getMove();
    } 
    eval = max(state, this.ply);
    return (eval.getMove()).getMove();
  }

  public void reward()  {}
  public void punish()  {}
  public void restore() {}

  private Evaluation max(State state, int ply) {
    Evaluation      best  = null;
    ArrayList<Move> moves = getValidMoves(state, COMPUTER);
    int             count = moves.size();
    int             tops  = Evaluation.minimum();

    for (Move move : moves) {
      Evaluation eval; 
      State temp = applyMove(state, move);
      //move.execute(state); //XXX: not applying the player???
      if (ply <= 0 || count == 1) {
        eval = new Evaluation(move, evaluate(state));
        //return new Evaluation(move, evaluate(state));
      } else {
        eval = mini(state, ply -1);
      }
      //move.undo(state);
      if (eval.getScore() > tops) {
        tops = eval.getScore();
        best = eval;
      }
    }
    return best;
  }

  private Evaluation mini(State state, int ply) {
    Evaluation      best  = null;
    ArrayList<Move> moves = getValidMoves(state, HUMAN);
    int             count = moves.size();  
    int             tops  = Evaluation.maximum();
    
    for (Move move : moves) {
      Evaluation eval;
      State temp = applyMove(state, move);
      if (count == 1) {
        eval = new Evaluation(move, evaluate(temp));
        //return new Evaluation(move, evaluate(temp));
      } else {
        eval = max(temp, ply - 1);
      }
      if (eval.getScore() < tops) {
        tops = eval.getScore();
        best = eval;
      }
    }  
    return best;
  }

  private Evaluation first(State state) {
    if(state.isEmpty()) {
      return new Evaluation(new Move((int)(Math.random() * 9)), 100);
    } 
    return null;
  }

  private Evaluation quickie(State state) {
    // check for an obvious winner...
    int value = 0;
    int a, b, c = 0;
    Evaluation eval = null;
    for (int x = 0; x < 3; x++) {
      a = state.getSquare(x, 0);
      b = state.getSquare(x, 1);
      c = state.getSquare(x, 2);
      value = count(a, b, c);   
      if (value == 2) {
        eval = new Evaluation(new Move(state.getMove(x, which(a, b, c))), 100);
      }
      if (value == -2) {
        if (eval == null || eval.getScore() < 10) {
          eval = new Evaluation(new Move(state.getMove(x, which(a, b, c))), -100);
        }
      }
    } 
    for (int y = 0; y < 3; y++) {
      a = state.getSquare(0, y);
      b = state.getSquare(1, y);
      c = state.getSquare(2, y);
      value = count(a, b, c);   
      if (value == 2) {
        eval =  new Evaluation(new Move(state.getMove(which(a, b, c), y)), 100);
      }
      if (value == -2) {
        if (eval == null || eval.getScore() < 10) {
          eval = new Evaluation(new Move(state.getMove(which(a, b, c), y)), -100);
        }
      }
    }
   
    a = state.getSquare(0, 0);
    b = state.getSquare(1, 1);
    c = state.getSquare(2, 2);
    value = count(a, b, c);
    if (value == 2) {
      if (a == 0) eval = new Evaluation(new Move(state.getMove(0, 0)), 100);
      if (b == 0) eval = new Evaluation(new Move(state.getMove(1, 1)), 100);
      if (c == 0) eval = new Evaluation(new Move(state.getMove(2, 2)), 100);
    }
    if (value == -2) {
      if (eval == null || eval.getScore() < 10) {
        if (a == 0) eval = new Evaluation(new Move(state.getMove(0, 0)), -100);
        if (b == 0) eval = new Evaluation(new Move(state.getMove(1, 1)), -100);
        if (c == 0) eval = new Evaluation(new Move(state.getMove(2, 2)), -100);
      }
    }
    a = state.getSquare(2,0); 
    b = state.getSquare(1,1); 
    c = state.getSquare(0,2);
    value = count(a, b, c);
    if (value == 2) {
      if (a == 0) eval = new Evaluation(new Move(state.getMove(2, 0)), 100);
      if (b == 0) eval = new Evaluation(new Move(state.getMove(1, 1)), 100);
      if (c == 0) eval = new Evaluation(new Move(state.getMove(0, 2)), 100);
    }
    if (value == -2) {
      if (eval == null || eval.getScore() < 10) {
        if (a == 0) eval = new Evaluation(new Move(state.getMove(2, 0)), -100);
        if (b == 0) eval = new Evaluation(new Move(state.getMove(1, 1)), -100);
        if (c == 0) eval = new Evaluation(new Move(state.getMove(0, 2)), -100);
      }
    }
    return eval;
  }

  private int count(int a, int b, int c) {
    return (a + b + c); 
  }

  private int which(int a, int b, int c) {
    if (a == 0) return 0;
    if (b == 0) return 1;
    if (c == 0) return 2;
    return -1;
  }

  private int evaluate(State state) {
    int value = 0;
    // check the rows 
    for (int x = 0; x < 3; x++) {
      value += score(state.getSquare(x, 0), state.getSquare(x, 1), state.getSquare(x, 2));
      if (value > 10)  {
        return value;
      }
    }
    // now check columns
    for (int y = 0; y < 3; y++) {
      value += score(state.getSquare(0, y), state.getSquare(1, y), state.getSquare(2, y));
      if (value > 10) {
        return value;
      }
    }
    // check diagonally
    value += score(state.getSquare(0,0), state.getSquare(1,1), state.getSquare(2,2));
    value += score(state.getSquare(2,0), state.getSquare(1,1), state.getSquare(0,2));
    return value;
  }

  private int score(int a, int b, int c) {
    int     value = 0;
    int     row[] = {a, b, c};
    boolean open  = false;
    boolean one   = false;
    
    // check for winner...
    if (row[0] != 0 && row[0] == row[1] && row[1] == row[2]){
      return (100*row[0]);
    }
 
    for (int i = 0; i < 3; i++) {
      if (row[i] == 0) open = true;
      else if (one && row[i] != value) return 0;
      else {
        one   = true;
        value = row[i];
      }
    } 
    return value;
  }

  private ArrayList<Move> getValidMoves(State state, int player) {
    String pattern = state.getPattern();
    ArrayList<Move>  moves = new ArrayList<Move>();
    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i)=='-') {
        moves.add(new Move(i+1, player));
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

  public void save() { }

  public String toString() {
    return "MiniMax engine";
  }
}
