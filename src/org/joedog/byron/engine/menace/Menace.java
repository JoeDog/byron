package org.joedog.byron.engine.menace;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.*;
import org.joedog.byron.engine.*;

public class Menace extends Engine {
  private HashMap boxes;
  private ArrayList <Box>refs;
  private int table[][] = {
    {0, 1, 2, 3, 4, 5, 6, 7, 8}, // normal board
    {6, 3, 0, 7, 4, 1, 8, 5, 2}, // 90 degees right
    {2, 5, 8, 1, 4, 7, 0, 3, 6}, // 90 degrees left
    {8, 7, 6, 5, 4, 3, 2, 1, 0}, // 180 degree rotation.
    {2, 1, 0, 5, 4, 3, 8, 7, 6}, // flipped left to right
    {6, 7, 8, 3, 4, 5, 0, 1, 2}, // flipped top to bottom
    {2, 1, 6, 5, 4, 3, 8, 7, 0}, // 180 degree rotation, flipped horizontally 
    {8, 5, 2, 7, 4, 1, 6, 3, 0}, // 90 degrees left,  flipped vertically
    {0, 3, 6, 1, 4, 7, 2, 5, 8}  // 90 degrees right, flipped vertically
  }; // see: http://www.adit.co.uk/html/transformations.html

  public Menace () {
    this.boxes = getBoxes("org/joedog/byron/engine/menace/positions.properties");
    this.refs  = new ArrayList<Box>();
  }

  /**
   * flip pattern as per the table above, then 
   * pass the table to the corresponding box so
   * that we return the proper bead.
   */ 
  public int getMove(String pattern){
    String s[] = new String[9];
    char   c[] = new char[pattern.length()];
    for (int i = 0; i < pattern.length(); i++) {
      c[i] = pattern.charAt(i);
    }
    for (int x = 0; x < 9; x++) {
      s[x] = new String("");
      for (int y = 0; y < 9; y++) {
        s[x] = s[x]+Character.toString(c[table[x][y]]);
      } 
    }
    for (int i = 0; i < 9; i++) {
      if (this.boxes.containsKey(s[i])) {
        Box b = (Box)this.boxes.get(s[i]);
        if (b==null) {
          return -1;
        }
        this.refs.add(b);
        return b.getBead(i, table);
      }
    }
    return -1; 
  }

  private HashMap getBoxes(String filename) {
    Positions pos = new Positions(filename);
    String    s[] = pos.getPositions();
    HashMap <String, Box>map = new HashMap<String, Box>();

    for (int i = 0; i < s.length; i++) {  
      map.put(new String(s[i]), new Box(s[i]));
    }
    return map;
  } 

  public void punish () {
    for (int i = 0; i < this.refs.size(); i ++){
      this.refs.get(i).punish();
    }
  }

  public void reward () {
    for (int i = 0; i < this.refs.size(); i ++){
      this.refs.get(i).reward();
    }
  }

  public void restore() {
    for (int i = 0; i < this.refs.size(); i ++){
      this.refs.get(i).restore();
    }
  }

}
