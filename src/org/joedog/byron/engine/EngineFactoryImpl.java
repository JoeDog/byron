package org.joedog.byron.engine;

import org.joedog.byron.engine.menace.*;
import org.joedog.byron.engine.minimax.*;

public class EngineFactoryImpl implements EngineFactory {

  public EngineFactoryImpl () {
  }

  public Engine getEngine (int type) {
    if (type == Engine.MENACE) {
      return new Menace(); 
    } 
    if (type == Engine.MINIMAX) {
      return new MiniMax();
    }
    return null;
  }
}
