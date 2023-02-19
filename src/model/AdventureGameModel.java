package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * This class represents the model for Dungeon Adventure Game and implements its functionalities.
 * It has methods to move the player one location at a time, pick treasure if available
 * at a given location and get the state of the Game at any instant.
 */
public class AdventureGameModel implements GameModel {
  private final IDungeon dungeon;
  private final IPlayer player;
  private boolean gameOver;

  /**
   * Construct an Adventure Game object with the given arguments.
   *
   * @param rand      the Random object, can be truly random or deterministic
   * @param rows      the no. of rows in the game dungeon
   * @param cols      the no. of columns in the game dungeon
   * @param wrap      if the game dungeon needs to be wrapping, yes if true, false otherwise
   * @param interconn the degree of interconnectivity required for the dungeon
   * @param percent   the percentage of caves to be filled with treasure, between 0 and 100
   */
  public AdventureGameModel(Random rand, int rows, int cols, boolean wrap, int interconn,
                            double percent, int difficulty) {
    this.dungeon = new Dungeon(rows, cols, wrap, interconn, percent, difficulty, rand);
    this.player = new Player(this.dungeon.getStart());
    this.gameOver = false;

    this.detectSmellAtPlayerLocation();
  }

  @Override
  public IPlayer getPlayer() {
    return this.player;
  }

  @Override
  public IDungeon getDungeon() {
    return this.dungeon;
  }

  @Override
  public Location getGameStart() {
    return this.dungeon.getStart();
  }

  @Override
  public Location getGameEnd() {
    return this.dungeon.getEnd();
  }

  @Override
  public void movePlayer(Direction d) throws IllegalArgumentException {
    if (!this.player.getCurrentLocation().getPossibleDirections().contains(d)) {
      throw new IllegalArgumentException("Cannot move in the " + d.toString().charAt(0)
              + " direction from current location!");
    }

    int[] xy = this.player.getCurrentLocation().getNeighbour(d);

    int x = xy[0];
    int y = xy[1];

    Location newLocation = this.dungeon.getLocation(x, y);

    ((Player) this.player).updateLocation(newLocation);

    Location currPlayerLocation = this.player.getCurrentLocation();
    this.detectSmellAtPlayerLocation();

    if (currPlayerLocation.hasMonster()) {
      Monster monster = currPlayerLocation.getMonster();
      boolean playerGetsEaten = ((Otyugh) monster).eatPlayer();

      if (playerGetsEaten) {
        this.gameOver = true;
        ((Player) this.player).die();
      } else if (currPlayerLocation.equals(this.getGameEnd())) {
        this.gameOver = true;
      }
    }
  }

  @Override
  public void pickItem(Item i) throws IllegalArgumentException {
    if (!this.player.getCurrentLocation().getContent().contains(i)) {
      throw new IllegalArgumentException("Cannot collect " + i.getName()
              + ", item not located in the current location!");
    }

    i.pick((Player) this.player);
  }

  @Override
  public boolean shootArrow(Direction d, int distance)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.player.getCurrentLocation().getPossibleDirections().contains(d)) {
      throw new IllegalArgumentException("Cannot shoot arrow in the " + d.toString().charAt(0)
              + " direction from the current location!");
    }
    if (distance <= 0) {
      throw new IllegalArgumentException("Shooting distance invalid!");
    } else if (distance > 5) {
      throw new IllegalArgumentException("Cannot shoot arrow over a distance of "
              + distance + " caves!");
    }

    if (this.player.getArrowsLeft() == 0) {
      throw new IllegalStateException("No arrows left! Try exploring and picking arrows!");
    }

    ((Player) this.player).shootArrow();
    boolean successfulHit = this.arrowTraversal(this.player.getCurrentLocation(), d, distance);
    this.detectSmellAtPlayerLocation();

    return successfulHit;
  }

  private boolean arrowTraversal(Location currLoc, Direction d, int distance) {
    Map<Direction, Direction> entryExit = new HashMap<>();
    entryExit.put(Direction.EAST, Direction.WEST);
    entryExit.put(Direction.WEST, Direction.EAST);
    entryExit.put(Direction.NORTH, Direction.SOUTH);
    entryExit.put(Direction.SOUTH, Direction.NORTH);

    while (distance != 0) {
      int[] xy = currLoc.getNeighbour(d);
      Location nextLoc = this.dungeon.getLocation(xy[0], xy[1]);
      List<Direction> possibleDirections = new ArrayList<>(nextLoc.getPossibleDirections());
      possibleDirections.remove(entryExit.get(d));

      if (nextLoc.isCave()) {
        distance -= 1;

        if (distance == 0 && nextLoc.hasMonster()) {
          ((Otyugh) nextLoc.getMonster()).takeHit();
          return true;
        } else if (distance > 0 && possibleDirections.contains(d)) {
          currLoc = nextLoc;
        } else if (distance > 0 && !possibleDirections.contains(d)) {
          return false;
        }
      } else {
        d = possibleDirections.get(0);
        currLoc = nextLoc;
      }
    }

    return false;
  }

  private void detectSmellAtPlayerLocation() {
    Location cell = this.player.getCurrentLocation();
    int[] xy = cell.getCoordinates();
    int monsterDepthOne = this.bfsHelper(xy[0], xy[1], 1);
    int monsterDepthTwo = this.bfsHelper(xy[0], xy[1], 2);

    if (monsterDepthOne > 0 || monsterDepthTwo > 1) {
      ((Cell) cell).updateSmell(Smell.MORE_PUNGENT);
    } else if (monsterDepthTwo == 1) {
      ((Cell) cell).updateSmell(Smell.LESS_PUNGENT);
    } else {
      ((Cell) cell).updateSmell(Smell.NONE);
    }
  }

  private int bfsHelper(int row, int col, int maxHeight) {
    Set<Location> visited = new HashSet<>();
    Queue<Location> q = new LinkedList<>();
    Queue<Integer> qHeight = new LinkedList<>();

    q.add(this.dungeon.getLocation(row, col));
    qHeight.add(0);

    int monsterCount = 0;

    while (!q.isEmpty()) {
      Location cell = q.remove();
      int height = qHeight.remove();

      if (height > maxHeight) {
        return monsterCount;
      }

      if (cell.hasMonster()) {
        if (cell.getMonster().getHitsTaken() < 2) {
          monsterCount += 1;
        }
      }

      visited.add(cell);

      for (Direction d : cell.getPossibleDirections()) {
        int[] coordinates = cell.getNeighbour(d);
        int x = coordinates[0];
        int y = coordinates[1];
        Location child = this.dungeon.getLocation(x, y);

        if (!visited.contains(child)) {
          q.add(child);
          qHeight.add(height + 1);
        }
      }
    }

    return 0;
  }

  @Override
  public boolean isGameOver() {
    return this.gameOver;
  }

  @Override
  public String getGameState() {
    String[][] dungeonLayout = ((Dungeon) this.dungeon).getDungeonLayout();
    int[] playerLocation = this.player.getCurrentLocation().getCoordinates();

    int rowInLayout = 1 + playerLocation[0] * 3;
    int colInLayout = 1 + playerLocation[1] * 3;

    if (this.player.getCurrentLocation().isCave()) {
      String current = dungeonLayout[rowInLayout][colInLayout];
      dungeonLayout[rowInLayout][colInLayout] = current.charAt(0) + "P" + current.charAt(2);
    } else {
      dungeonLayout[rowInLayout][colInLayout] = " P ";
    }


    StringBuilder output = new StringBuilder();
    for (int i = 0; i < dungeonLayout.length; i++) {
      for (int j = 0; j < dungeonLayout[0].length; j++) {
        if (dungeonLayout[i][j] == null) {
          output.append("\t");
        } else {
          output.append(dungeonLayout[i][j]).append("\t");
        }
      }
      output.append("\n");
    }

    output.append("Legend to the game state:\n"
            + "P\t\t==> Player\n"
            + "(C)\t\t==> Cave with no treasure\n"
            + "{X}\t\t==> Cave with items\n"
            + "<M>\t\t==> Cave with monster\n"
            + "+\t\t==> Tunnel\n"
            + "S\t\t==> Start Cave\n"
            + "G\t\t==> End Cave\n"
            + "| or ---\t==> Paths");

    return output.toString();
  }
}
