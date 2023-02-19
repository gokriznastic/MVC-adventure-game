package utils;

import java.util.Random;

/**
 * This class represents a Randomizer which helps in dungeon generation.
 */
public class Randomizer {
  private final Random rand;

  /**
   * Construct a true Random object.
   */
  public Randomizer() {
    this.rand = new Random();
  }

  /**
   * Construct a deterministic Random object using a given seed value.
   *
   * @param seedValue the seed value for the Random object as integer
   */
  public Randomizer(int seedValue) {
    this.rand = new Random();
    this.rand.setSeed(seedValue);
  }

  /**
   * Method to get the initialized Random object.
   *
   * @return  the Random object
   */
  public Random getRandom() {
    return this.rand;
  }
}
