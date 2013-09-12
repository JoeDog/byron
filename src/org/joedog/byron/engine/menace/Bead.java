package org.joedog.byron.engine.menace;

public class Bead {
  private int id;

  public Bead () {
  }

  public Bead (int id) {
    if (id > 9) {
      this.id = -1;
    } else {
      this.id = id;
    }
  }

  /**
   * Sets the Bead id with an int
   *
   * @param id sets the Bead id
   */
  public void setId (int id) {
    this.id = id;
  }

  /**
   * Returns int Bead.id
   *
   * @returns int getId
   */
  public int getId () {
    if (this.id < 0) {
      return 1;
    }
    return this.id;
  }
}
