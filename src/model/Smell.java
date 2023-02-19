package model;

/**
 * This enum class represents intensities of Smell that can be detected while
 * moving in the game dungeon.
 */
public enum Smell {
  NONE("no"),
  LESS_PUNGENT("slightly pungent"),
  MORE_PUNGENT("very pungent");

  private String smell;

  Smell(String smell) {
    this.smell = smell;
  }

  @Override
  public String toString() {
    return this.smell;
  }
}
