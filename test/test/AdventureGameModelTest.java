package test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import model.AdventureGameModel;
import model.Direction;
import model.Location;
import model.Smell;
import model.Treasure;
import model.Weapon;
import utils.Randomizer;

/**
 * This class represents a JUnit test for AdventureGame class.
 * It tests all the functionalities of player, properties of game dungeon
 * as per requirements of the Dungeon model.
 */
public class AdventureGameModelTest {
  Random rand;

  @Before
  public void setUp() {
    this.rand = new Randomizer(42).getRandom();
  }

  @Test
  public void testPlayerStart() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    int[] start = nonWrappingGame.getGameStart().getCoordinates();
    int[] playerInitialPosition = nonWrappingGame.getPlayer().getCurrentLocation().getCoordinates();

    assertEquals(start[0], playerInitialPosition[0]);
    assertEquals(start[1], playerInitialPosition[1]);
  }

  @Test
  public void testPlayerEnd() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    int[] playerInitialPosition = nonWrappingGame.getPlayer().getCurrentLocation().getCoordinates();
    int[] end = nonWrappingGame.getGameEnd().getCoordinates();

    double inf = Double.POSITIVE_INFINITY;

    double distToEnd = breadthFirstSearch(playerInitialPosition[0], playerInitialPosition[1],
            end[0], end[1], nonWrappingGame);

    // if finite distance than player can reach
    assertTrue(distToEnd > 0 && distToEnd < inf);
  }

  @Test
  public void testPlayerMove() {
    int rows = 4;
    int cols = 4;
    int ic = 5;
    boolean wrap = true;
    int pct = 50;
    int diff = 5;
    AdventureGameModel wrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(wrappingGame.getGameState());

    wrappingGame.movePlayer(Direction.NORTH);
    int[] neighborXY = wrappingGame.getGameStart().getNeighbour(Direction.NORTH);
    assertEquals(wrappingGame.getPlayer().getCurrentLocation(),
            wrappingGame.getDungeon().getLocation(neighborXY[0], neighborXY[1]));

    wrappingGame.movePlayer(Direction.SOUTH);

    wrappingGame.movePlayer(Direction.SOUTH);
    neighborXY = wrappingGame.getGameStart().getNeighbour(Direction.SOUTH);
    assertEquals(wrappingGame.getPlayer().getCurrentLocation(),
            wrappingGame.getDungeon().getLocation(neighborXY[0], neighborXY[1]));

    wrappingGame.movePlayer(Direction.NORTH);

    wrappingGame.movePlayer(Direction.WEST);
    neighborXY = wrappingGame.getGameStart().getNeighbour(Direction.WEST);
    assertEquals(wrappingGame.getPlayer().getCurrentLocation(),
            wrappingGame.getDungeon().getLocation(neighborXY[0], neighborXY[1]));

    wrappingGame.movePlayer(Direction.EAST);

    wrappingGame.movePlayer(Direction.EAST);
    neighborXY = wrappingGame.getGameStart().getNeighbour(Direction.EAST);
    assertEquals(wrappingGame.getPlayer().getCurrentLocation(),
            wrappingGame.getDungeon().getLocation(neighborXY[0], neighborXY[1]));
  }

  @Test
  public void testPickTreasure() {
    int rows = 4;
    int cols = 4;
    int ic = 5;
    boolean wrap = true;
    int pct = 50;
    int diff = 5;
    AdventureGameModel wrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(wrappingGame.getGameState());

    wrappingGame.pickItem(Treasure.RUBY);
    assertEquals(java.util.Optional.of(1),
            java.util.Optional.of(wrappingGame.getPlayer()
                    .getTreasureCollected().get(Treasure.RUBY)));
  }

  @Test
  public void testPickArrow() {
    int rows = 4;
    int cols = 4;
    int ic = 5;
    boolean wrap = true;
    int pct = 50;
    int diff = 5;
    AdventureGameModel wrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(wrappingGame.getGameState());

    wrappingGame.pickItem(Weapon.ARROW);
    assertEquals(java.util.Optional.of(4),
            java.util.Optional.of(wrappingGame.getPlayer()
                    .getArrowsLeft()));
  }

  @Test
  public void testNonWrapping() {
    int rows = 4;
    int cols = 4;
    int ic = 5;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    int[] coordinatesBeforeMoving = nonWrappingGame.getPlayer()
            .getCurrentLocation().getCoordinates();
    try {
      nonWrappingGame.movePlayer(Direction.EAST);
    } catch (IllegalArgumentException e) {
      System.out.print("");
    }
    int[] coordinatesAfterMoving = nonWrappingGame.getPlayer()
            .getCurrentLocation().getCoordinates();

    assertEquals(coordinatesBeforeMoving[0], coordinatesAfterMoving[0]);
    assertEquals(coordinatesBeforeMoving[1], coordinatesAfterMoving[1]);
  }

  @Test
  public void testWrapping() {
    int rows = 4;
    int cols = 4;
    int ic = 5;
    boolean wrap = true;
    int pct = 50;
    int diff = 5;
    AdventureGameModel wrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);


    System.out.println(wrappingGame.getGameState());

    int[] coordinatesBeforeMoving = wrappingGame.getPlayer()
            .getCurrentLocation().getCoordinates();
    wrappingGame.movePlayer(Direction.NORTH);
    int[] coordinatesAfterMoving = wrappingGame.getPlayer()
            .getCurrentLocation().getCoordinates();

    System.out.println(coordinatesBeforeMoving[1] + " " + coordinatesAfterMoving[1]);

    assertEquals(coordinatesBeforeMoving[0] + rows - 1, coordinatesAfterMoving[0]);
    assertEquals(coordinatesBeforeMoving[1], coordinatesAfterMoving[1]);
  }

  @Test
  public void testCountOfCaves() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    // putting all cave coordinates in a set
    Set<Location> caves = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (l.isCave()) {
          caves.add(l);
        }
      }
    }

    assertEquals(caves.size(), nonWrappingGame.getDungeon().getNoOfCaves());
  }

  @Test
  public void testCountOfTunnels() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    // putting all cave coordinates in a set
    Set<Location> tunnels = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (!l.isCave()) {
          tunnels.add(l);
        }
      }
    }

    assertEquals(tunnels.size(), nonWrappingGame.getDungeon().getNoOfTunnels());
  }

  @Test
  public void testMinDistanceStartEnd() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    int[] start = nonWrappingGame.getGameStart().getCoordinates();
    int[] end = nonWrappingGame.getGameEnd().getCoordinates();

    assertTrue(breadthFirstSearch(start[0], start[1], end[0], end[1], nonWrappingGame) >= 5);
  }

  @Test
  public void testTreasurePercent() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    double noOfCaves = nonWrappingGame.getDungeon().getNoOfCaves();

    double countTreasureCaves = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (l.isCave() && l.getContent().size() != 0) {
          if (l.getContent().contains(Treasure.DIAMOND) || l.getContent().contains(Treasure.RUBY)
                  || l.getContent().contains(Treasure.SAPPHIRE)) {
            countTreasureCaves += 1;
          }
        }
      }
    }

    // delta = 0.1 to the account for using Math.ceil during treasure distribution
    assertEquals((double) pct / 100, countTreasureCaves / noOfCaves, 0.1);
  }

  @Test
  public void testEveryCaveReachable() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    // putting all cave coordinates in a set
    Set<Location> caves = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (l.isCave()) {
          caves.add(l);
        }
      }
    }

    double inf = Double.POSITIVE_INFINITY;

    // asserting that distance between any two non-same caves is finite
    for (Location caveOne : caves) {
      for (Location caveTwo : caves) {
        if (!caveOne.equals(caveTwo)) {
          int[] start = caveOne.getCoordinates();
          int[] end = caveTwo.getCoordinates();
          double minDist = breadthFirstSearch(start[0], start[1], end[0], end[1], nonWrappingGame);
          assertTrue(minDist > 0 && minDist < inf);
        }
      }
    }
  }

  private int breadthFirstSearch(int startX, int startY, int endX, int endY,
                                 AdventureGameModel game) {
    Set<Location> visited = new HashSet<>();

    Queue<Location> q = new LinkedList<>();
    Queue<Integer> qHeight = new LinkedList<>();

    q.add(game.getDungeon().getLocation(startX, startY));
    qHeight.add(0);

    while (!q.isEmpty()) {
      Location cell = q.remove();
      int height = qHeight.remove();

      if (cell.equals(game.getDungeon().getLocation(endX, endY))) {
        return height;
      }

      visited.add(cell);

      for (Direction d : cell.getPossibleDirections()) {
        int[] coordinates = cell.getNeighbour(d);
        int x = coordinates[0];
        int y = coordinates[1];
        Location child = game.getDungeon().getLocation(x, y);

        if (!visited.contains(child)) {
          q.add(child);
          qHeight.add(height + 1);
        }
      }
    }

    return 0;
  }

  @Test
  public void testOtyughAtEndCave() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    assertTrue(nonWrappingGame.getGameEnd().hasMonster());
  }

  @Test
  public void testNoOtyughAtStartCave() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    assertFalse(nonWrappingGame.getGameStart().hasMonster());
  }

  @Test
  public void testNoOtyughInTunnels() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    Set<Location> caves = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (!l.isCave()) {
          assertFalse(l.hasMonster());
        }
      }
    }
  }

  @Test
  public void testOtyughCount() {
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    int noOfOtyughs = 0;

    Set<Location> caves = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (l.isCave() && l.hasMonster()) {
          noOfOtyughs += 1;
        }
      }
    }

    assertEquals(diff, noOfOtyughs);
  }

  @Test
  public void testNoSmell() {
    Random rand = new Randomizer(33).getRandom();
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertEquals(Smell.NONE, nonWrappingGame.getPlayer().getCurrentLocation().getSmell());
  }

  @Test
  public void testLessPungentSmell() {
    Random rand = new Randomizer(33).getRandom();
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.EAST);

    assertEquals(Smell.LESS_PUNGENT, nonWrappingGame.getPlayer().getCurrentLocation().getSmell());
  }

  @Test
  public void testMorePungentSmellSinglePosition() {
    Random rand = new Randomizer(33).getRandom();
    int rows = 4;
    int cols = 4;
    int ic = 0;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.SOUTH);

    assertEquals(Smell.MORE_PUNGENT, nonWrappingGame.getPlayer().getCurrentLocation().getSmell());
  }

  @Test
  public void testMorePungentSmellDoublePosition() {
    Random rand = new Randomizer(100).getRandom();
    int rows = 4;
    int cols = 4;
    int ic = 3;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertEquals(Smell.MORE_PUNGENT, nonWrappingGame.getPlayer().getCurrentLocation().getSmell());
  }

  @Test
  public void testInitialArrowCount() {
    int rows = 4;
    int cols = 4;
    int ic = 3;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    assertEquals(3, nonWrappingGame.getPlayer().getArrowsLeft());
  }

  @Test
  public void testArrowPercent() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    double noOfLocations = rows * cols;

    double countArrowCaves = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (l.getContent().contains(Weapon.ARROW)) {
          countArrowCaves += 1;
        }
      }
    }

    // delta = 0.1 to the account for using Math.ceil during treasure distribution
    assertEquals((double) pct / 100, countArrowCaves / noOfLocations, 0.1);
  }

  @Test
  public void testArrowFoundInCavesAndTunnels() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    int noOfCaves = 0;
    int noOfTunnels = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Location l = nonWrappingGame.getDungeon().getLocation(row, col);
        if (l.getContent().contains(Weapon.ARROW))
          if (l.isCave()) {
            noOfCaves += 1;
          } else {
            noOfTunnels += 1;
          }
      }
    }

    assertTrue(noOfCaves > 0);
    assertTrue(noOfTunnels > 0);
  }

  @Test
  public void testArrowTravelThroughTunnel() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().isAlive());

    nonWrappingGame.shootArrow(Direction.NORTH, 1);
    nonWrappingGame.shootArrow(Direction.NORTH, 1);

    assertFalse(nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().isAlive());
  }

  @Test
  public void testArrowTravelThroughCaveStraight() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getDungeon().getLocation(0, 3).getMonster().isAlive());

    nonWrappingGame.shootArrow(Direction.NORTH, 2);
    nonWrappingGame.shootArrow(Direction.NORTH, 2);

    assertFalse(nonWrappingGame.getDungeon().getLocation(0, 3).getMonster().isAlive());
  }

  @Test
  public void testArrowNotTravelThroughCaveNotStraight() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getDungeon().getLocation(1, 2).getMonster().isAlive());

    nonWrappingGame.shootArrow(Direction.NORTH, 2);
    nonWrappingGame.shootArrow(Direction.NORTH, 2);
    nonWrappingGame.shootArrow(Direction.NORTH, 2);

    assertEquals(0,
            nonWrappingGame.getDungeon().getLocation(1, 2).getMonster().getHitsTaken());
  }

  @Test
  public void testArrowTravelSpecifiedDistance() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().isAlive());
    assertEquals(0,
            nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());
    assertTrue(nonWrappingGame.getDungeon().getLocation(0, 3).getMonster().isAlive());
    assertEquals(0,
            nonWrappingGame.getDungeon().getLocation(0, 3).getMonster().getHitsTaken());

    nonWrappingGame.shootArrow(Direction.NORTH, 2);
    nonWrappingGame.shootArrow(Direction.NORTH, 2);


    assertTrue(nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().isAlive());
    assertEquals(0,
            nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());
    assertFalse(nonWrappingGame.getDungeon().getLocation(0, 3).getMonster().isAlive());
    assertEquals(2,
            nonWrappingGame.getDungeon().getLocation(0, 3).getMonster().getHitsTaken());
  }

  @Test
  public void testArrowHitsMonster() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertEquals(0,
            nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());

    nonWrappingGame.shootArrow(Direction.NORTH, 1);

    assertEquals(1,
            nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());

    nonWrappingGame.shootArrow(Direction.NORTH, 1);

    assertEquals(2,
            nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().getHitsTaken());
  }

  @Test
  public void testArrowKillsMonster() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().isAlive());

    nonWrappingGame.shootArrow(Direction.NORTH, 1);
    nonWrappingGame.shootArrow(Direction.NORTH, 1);

    assertFalse(nonWrappingGame.getDungeon().getLocation(0, 2).getMonster().isAlive());
  }

  @Test
  public void testPlayerDies() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 5;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getPlayer().isAlive());

    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.EAST);

    assertFalse(nonWrappingGame.getPlayer().isAlive());
  }


  @Test
  public void testPlayerWins() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 1;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getPlayer().isAlive());

    nonWrappingGame.movePlayer(Direction.SOUTH);
    nonWrappingGame.movePlayer(Direction.SOUTH);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.shootArrow(Direction.EAST, 1);
    nonWrappingGame.shootArrow(Direction.EAST, 1);
    nonWrappingGame.movePlayer(Direction.EAST);

    assertTrue(nonWrappingGame.isGameOver());
    assertTrue(nonWrappingGame.getPlayer().isAlive());
  }

  @Test
  public void testPlayerLoses() {
    int rows = 4;
    int cols = 4;
    int ic = 2;
    boolean wrap = false;
    int pct = 50;
    int diff = 1;
    AdventureGameModel nonWrappingGame = new AdventureGameModel(this.rand,
            rows, cols, wrap, ic, pct, diff);

    System.out.println(nonWrappingGame.getGameState());

    assertTrue(nonWrappingGame.getPlayer().isAlive());

    nonWrappingGame.movePlayer(Direction.SOUTH);
    nonWrappingGame.movePlayer(Direction.SOUTH);
    nonWrappingGame.movePlayer(Direction.EAST);
    nonWrappingGame.movePlayer(Direction.NORTH);
    nonWrappingGame.movePlayer(Direction.EAST);

    assertTrue(nonWrappingGame.isGameOver());
    assertFalse(nonWrappingGame.getPlayer().isAlive());
  }
}
