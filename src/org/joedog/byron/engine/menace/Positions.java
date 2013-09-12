package org.joedog.byron.engine.menace;

import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Properties;

public class Positions {
  private static final char SEPARATOR = '.';
  private String[] positions;

  static final long serialVersionUID = -197491492884005033L;

  public Positions (String filename) {
    PositionProperties p = getPositions(filename);
    positions = p.getProperties("box");
  }

  public String [] getPositions() {
    return this.positions;
  }

  public PositionProperties getPositions (String filename) {
    PositionProperties p = new PositionProperties();
    try {
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filename);
      p.load(stream); 
    } catch(Exception e) {
      System.err.println("Unable to locate resource file: "+filename);
      System.exit(1);
    }
    return p;
  } 

  public class PositionProperties extends Properties {
    static final long serialVersionUID = -193991466684652013L;

    public String[] getProperties(String key) {
      int  i      = 1;
      List list   = new LinkedList();
      String pkey = key + ".0";
      String pval = getProperty(pkey, "").trim();

      if (pval.length() == 0) {
        i++;
        pkey = key + ".1";
        pval = getProperty(pkey, "").trim();
        if (pval.length() == 0) {
          return new String[0];
        }
      }

      for (; pval.length() != 0; i++) {
        list.add(pval);
        pkey = key + "." + i;
        pval = getProperty(pkey, "").trim();
      }
      Object[] obj   = list.toArray();
      int length     = obj.length;
      String[] props = new String[length];

      for (i = 0; i < length; i++) {
        props[i] = (String)obj[i];
      }
      return props;
    }
  }
}
