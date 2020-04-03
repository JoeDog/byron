package org.joedog.byron.engine;

import org.joedog.byron.engine.menace.*;
import org.joedog.byron.engine.minimax.*;
import org.joedog.byron.engine.montecarlo.*;
import org.joedog.byron.engine.reinforced.*;


public class EngineFactoryImpl implements EngineFactory {

  public EngineFactoryImpl () {
  }

  private String getCallingMethodName() {
    StackTraceElement callingFrame = Thread.currentThread().getStackTrace()[4];
    return callingFrame.getMethodName();
  }

  public Engine getEngine (int type) {
    if (type == Engine.MENACE) {
      //System.out.println("MENACE ENGINE");
      return Menace.getInstance(); 
    } 
    if (type == Engine.MINIMAX) {
      //System.out.println("MINIMAX ENGINE");
      return MiniMax.getInstance();
    }
    if (type == Engine.MONTECARLO) {
      //System.out.println("MONTE CARLO ENGINE");
      return MonteCarlo.getInstance();
    }
    if (type == Engine.REINFORCED) {
      //System.out.println("REINFORCED ENGINE");
      return Reinforced.getInstance();
    }
    if (type == Engine.TRAINING) {
      //System.out.println("TRAINING ENGINE");
      return Reinforced.getInstance(true);
    }
    return null;
  }
}
