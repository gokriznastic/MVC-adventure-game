package test;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import model.Dungeon;
import utils.Randomizer;

/**
 * This class represents a JUnit test for Dungeon class.
 * It tests for Dungeon creation when given invalid arguments.
 */
public class DungeonTest {
  Random rand;

  @Before
  public void setUp() {
    this.rand = new Randomizer(42).getRandom();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRows() {
    Dungeon dungeon = new Dungeon(0, 5, true, 5,
            20, 5, this.rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidColumns() {
    Dungeon dungeon = new Dungeon(5, -2, true, 5,
            20, 5, this.rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    Dungeon dungeon = new Dungeon(5, -2, true, -1,
            20, 5, this.rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPercent() {
    Dungeon dungeon = new Dungeon(5, -2, true, -1,
            150, 5, this.rand);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDifficulty() {
    Dungeon dungeon = new Dungeon(5, -2, true, -1,
            150, -5, this.rand);
  }
}