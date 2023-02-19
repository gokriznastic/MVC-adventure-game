package mock;

import java.io.IOException;

import model.Direction;
import model.GameModel;
import model.IDungeon;
import model.IPlayer;
import model.Item;
import model.Location;

/**
 * This class represent s a mock model for JUnit testing.
 */
public class MockGameModel implements GameModel {
  private final Appendable logger;

  public MockGameModel(Appendable l) {
    this.logger = l;
  }

  @Override
  public void movePlayer(Direction d) {
    try {
      if (d == Direction.NORTH) {
        this.logger.append("player moved north\n");
      } else if (d == Direction.SOUTH) {
        this.logger.append("player moved south\n");
      } else if (d == Direction.EAST) {
        this.logger.append("player moved east\n");
      } else if (d == Direction.WEST) {
        this.logger.append("player moved west\n");
      }
    } catch (IOException ioe) {
      // do nothing
    }
  }

  @Override
  public void pickItem(Item i) {
    try {
      this.logger.append("player picked ").append(i.getName()).append("\n");
    } catch (IOException ioe) {
      // do nothing
    }
  }

  @Override
  public boolean shootArrow(Direction d, int distance) {
    if (distance <= 0 || distance > 5) {
      throw new IllegalArgumentException();
    }

    try {
      if (d == Direction.NORTH) {
        this.logger.append("player shoots north over a distance of ")
                .append(String.valueOf(distance)).append("\n");
      } else if (d == Direction.SOUTH) {
        this.logger.append("player shoots south over a distance of ")
                .append(String.valueOf(distance)).append("\n");
      } else if (d == Direction.EAST) {
        this.logger.append("player shoots east over a distance of ")
                .append(String.valueOf(distance)).append("\n");
      } else if (d == Direction.WEST) {
        this.logger.append("player shoots west over a distance of ")
                .append(String.valueOf(distance)).append("\n");
      }
    } catch (IOException ioe) {
      // do nothing
    }

    return false;
  }

  @Override
  public IPlayer getPlayer() {
    try {
      this.logger.append("return player object").append("\n");
    } catch (IOException ioe) {
      // do nothing
    }

    return null;
  }

  @Override
  public IDungeon getDungeon() {
    try {
      this.logger.append("return dungeon object").append("\n");
    } catch (IOException ioe) {
      // do nothing
    }

    return null;
  }

  @Override
  public Location getGameStart() {
    try {
      this.logger.append("return start location object").append("\n");
    } catch (IOException ioe) {
      // do nothing
    }

    return null;
  }

  @Override
  public Location getGameEnd() {
    try {
      this.logger.append("return end location object").append("\n");
    } catch (IOException ioe) {
      // do nothing
    }

    return null;
  }

  @Override
  public boolean isGameOver() {
    try {
      this.logger.append("check game over condition").append("\n");
    } catch (IOException ioe) {
      // do nothing
    }

    return false;
  }

  @Override
  public String getGameState() {
    return null;
  }
}
