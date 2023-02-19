package test;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Random;

import controller.GameController;
import controller.TextBasedGameController;
import model.AdventureGameModel;
import model.Direction;
import model.GameModel;
import model.Treasure;
import utils.Randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * This class represents a JUnit test for Text-based Controller class.
 * It tests all the functionalities of the controller, valid and invalid inputs,
 * as per requirements of the Dungeon controller.
 */
public class TextBasedGameControllerTest {
  Random rand;

  @Before
  public void setUp() {
    this.rand = new Randomizer(42).getRandom();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    GameModel m = null;
    StringReader in = new StringReader("M");
    StringBuffer out = new StringBuffer();

    TextBasedGameController c = new TextBasedGameController(in, out, m);
    c.playGame();
  }

  @Test
  public void testInvalidCommand() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("bogus q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Unknown game action entered - bogus"));
  }

  @Test
  public void testInvalidMoveDirection() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("m bogus q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Not a valid direction! Try again."));
  }

  @Test
  public void testInvalidPickItem() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("p bogus q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Not a valid item! Try again."));
  }

  @Test
  public void testInvalidShootDirection() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("s bogus 1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Not a valid direction! Try again"));
  }

  @Test
  public void testInvalidShootDistance() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("s n bogus q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Not a valid integer distance! Try again."));
  }

  @Test
  public void testMoveWrongDirection() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    System.out.println(m.getGameState());
    StringReader in = new StringReader("m w q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Cannot move in the W direction from current location!"));
  }

  @Test
  public void testPickWrongItem() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    System.out.println(m.getGameStart().getContent());

    StringReader in = new StringReader("p diamond q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Cannot collect diamond, item not "
            + "located in the current location!"));
  }

  @Test
  public void testShootWrongDirection() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("s w 1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Cannot shoot arrow in the W"
            + " direction from the current location!"));
  }

  @Test
  public void testShootNegativeDistance() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("s n -1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Shooting distance invalid!"));
  }

  @Test
  public void testShootZeroDistance() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("s n 0 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Shooting distance invalid!"));
  }

  @Test
  public void testShootOutOfBoundsDistance() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("s n 7 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Cannot shoot arrow over a distance of 7 caves!"));
  }

  @Test
  public void testQAsCommand() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);
    StringReader in = new StringReader("q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Quitting game..."));
  }

  @Test
  public void testValidMove() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(m.getGameStart(), m.getPlayer().getCurrentLocation());

    StringReader in = new StringReader("m n q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    int[] xy = m.getGameStart().getNeighbour(Direction.NORTH);
    assertEquals(m.getDungeon().getLocation(xy[0], xy[1]), m.getPlayer().getCurrentLocation());
  }

  @Test
  public void testValidPickTreasure() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertFalse(m.getPlayer().getTreasureCollected().containsKey(Treasure.SAPPHIRE));

    StringReader in = new StringReader("p sapphire q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(m.getPlayer().getTreasureCollected().containsKey(Treasure.SAPPHIRE));
  }

  @Test
  public void testValidPickArrow() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(3, m.getPlayer().getArrowsLeft());

    StringReader in = new StringReader("p arrow q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertEquals(4, m.getPlayer().getArrowsLeft());
  }

  @Test
  public void testValidShoot() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(3, m.getPlayer().getArrowsLeft());

    StringReader in = new StringReader("s n 1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));

    assertEquals(2, m.getPlayer().getArrowsLeft());
  }

  @Test
  public void testValidShootInjuresMonster() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(3, m.getPlayer().getArrowsLeft());

    StringReader in = new StringReader("s n 1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));

    assertEquals(2, m.getPlayer().getArrowsLeft());
    assertEquals(1, m.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());
    assertTrue(m.getDungeon().getLocation(0, 2).getMonster().isAlive());
  }

  @Test
  public void testValidShootKillsMonster() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertTrue(m.getDungeon().getLocation(0, 2).getMonster().isAlive());

    StringReader in = new StringReader("s n 1 s n 1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));
    assertTrue(out.toString().contains("You hear a great howl in the distance"));

    assertEquals(1, m.getPlayer().getArrowsLeft());
    assertEquals(2, m.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());
    assertFalse(m.getDungeon().getLocation(0, 2).getMonster().isAlive());
  }

  @Test
  public void testValidShootTravelsFreelyTunnels() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertTrue(m.getDungeon().getLocation(2, 2).getMonster().isAlive());

    StringReader in = new StringReader("s s 1 s s 1 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));
    assertTrue(out.toString().contains("You hear a great howl in the distance"));

    assertEquals(1, m.getPlayer().getArrowsLeft());
    assertEquals(2, m.getDungeon().getLocation(2, 2).getMonster().getHitsTaken());
    assertFalse(m.getDungeon().getLocation(2, 2).getMonster().isAlive());
  }

  @Test
  public void testValidShootCrossesStraightCave() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertTrue(m.getDungeon().getLocation(0, 2).getMonster().isAlive());
    assertTrue(m.getDungeon().getLocation(0, 3).getMonster().isAlive());

    StringReader in = new StringReader("s n 2 s n 2 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));
    assertTrue(out.toString().contains("You hear a great howl in the distance"));

    assertEquals(1, m.getPlayer().getArrowsLeft());
    assertEquals(0, m.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());
    assertTrue(m.getDungeon().getLocation(0, 2).getMonster().isAlive());
    assertEquals(2, m.getDungeon().getLocation(0, 3).getMonster().getHitsTaken());
    assertFalse(m.getDungeon().getLocation(0, 3).getMonster().isAlive());
  }

  @Test
  public void testValidShootNotCrossesCurvedCave() {
    int rows = 4;
    int cols = 4;
    int ic = 4;
    boolean wrap = true;
    int pct = 50;
    int diff = 5;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    System.out.println(m.getGameState());

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertTrue(m.getDungeon().getLocation(1, 0).getMonster().isAlive());
    assertTrue(m.getDungeon().getLocation(3, 2).getMonster().isAlive());

    StringReader in = new StringReader("m n s e 2 s e 2 q");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));
    assertFalse(out.toString().contains("You hear a great howl in the distance"));

    assertEquals(1, m.getPlayer().getArrowsLeft());
    assertEquals(0, m.getDungeon().getLocation(1, 0).getMonster().getHitsTaken());
    assertTrue(m.getDungeon().getLocation(1, 0).getMonster().isAlive());
    assertEquals(0, m.getDungeon().getLocation(3, 2).getMonster().getHitsTaken());
    assertTrue(m.getDungeon().getLocation(3, 2).getMonster().isAlive());
  }

  @Test
  public void testPlayerWins() {
    int rows = 3;
    int cols = 3;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 2;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    System.out.println(m.getGameState());

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertTrue(m.getDungeon().getLocation(2, 1).getMonster().isAlive());

    StringReader in = new StringReader("s n 1 s n 1 m n m e m e m s m s m w");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("You shoot an arrow into the darkness"));
    assertTrue(out.toString().contains("You hear a great howl in the distance"));
    assertTrue(out.toString().contains("Bravo! Player wins!"));

    assertEquals(1, m.getPlayer().getArrowsLeft());
    assertEquals(2, m.getDungeon().getLocation(2, 1).getMonster().getHitsTaken());
    assertFalse(m.getDungeon().getLocation(2, 1).getMonster().isAlive());
    assertTrue(m.isGameOver());
    assertTrue(m.getPlayer().isAlive());
  }

  @Test
  public void testPlayerLoses() {
    int rows = 3;
    int cols = 3;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 2;

    GameModel m = new AdventureGameModel(this.rand, rows, cols, wrap, ic, pct, diff);

    System.out.println(m.getGameState());

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertTrue(m.getDungeon().getLocation(2, 1).getMonster().isAlive());

    StringReader in = new StringReader("m n m e m e m s m s m w");
    StringBuffer out = new StringBuffer();

    GameController c = new TextBasedGameController(in, out, m);
    c.playGame();

    assertTrue(out.toString().contains("Chomp, chomp, chomp, you are "
            + "eaten by an un-slayed Otyugh!"));

    assertEquals(3, m.getPlayer().getArrowsLeft());
    assertEquals(0, m.getDungeon().getLocation(2, 1).getMonster().getHitsTaken());
    assertTrue(m.getDungeon().getLocation(2, 1).getMonster().isAlive());
    assertTrue(m.isGameOver());
    assertFalse(m.getPlayer().isAlive());
  }
}