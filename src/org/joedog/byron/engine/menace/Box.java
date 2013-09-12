package org.joedog.byron.engine.menace;

import java.util.ArrayList;

public class Box {
  private final static int BEAD[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
  private String    label;
  private Bead      bead;
  private ArrayList <Bead> list;
  private ArrayList <Integer>history;

  public Box (String label) {
    this.label   = label;
    this.list    = new ArrayList<Bead>();
    this.fillBox();
  }

  public int getBead (int index, int[][] table) {
    if (list.size() < 1) {
      this.fillBox();
    }
    int rand  = (int)(Math.random() * list.size());
    this.bead = (list.get(rand));
    int  pos  = bead.getId();
    list.remove(rand);
    return BEAD[table[index][pos]]; 
  }

  public void reward () {
    if (this.bead != null) {
      this.list.add(this.bead);
      this.list.add(this.bead);
      this.list.add(this.bead);
      for (int i = 0; i < list.size(); i++) {
        Bead b = list.get(i);
      } 
    }
    this.bead = null;
  }
  
  public void punish () {
    /**
     * Place holder. 
     * The bead was already popped. No need to remove it.
     */
  }

  public void restore () {
    if (this.bead != null) {
      this.list.add(this.bead);
    }
    this.bead = null;
  }

  private void fillBox () {
    for (int i = 0; i < this.label.length(); i++) {
      if (this.label.charAt(i)=='-') {
        list.add(new Bead(i));
      }
    }  
  }

  /**
  public static void main(String[] args) {
    Box box = new Box("-x--o----");
    box.getBeads();
  } */
}
