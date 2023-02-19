package mock;

import java.io.IOException;

import controller.GuiGameFeatures;
import view.GameView;

/**
 * This class represent s a mock view for JUnit testing.
 */
public class MockGameView implements GameView {
  private final Appendable logger;

  public MockGameView(Appendable l) {
    this.logger = l;
  }

  @Override
  public void setFeatures(GuiGameFeatures f) {
    try {
      this.logger.append("added action listeners\n");
      this.logger.append("added keyboard listener\n");
      this.logger.append("added mouse listener\n");
    } catch (IOException ioe) {
      // do nothing
    }
  }

  @Override
  public void resetFocus() {
    try {
      this.logger.append("focus was reset\n");
    } catch (IOException ioe) {
      // do nothing
    }
  }

  @Override
  public void delete() {
    try {
      this.logger.append("set invisible and disposed\n");
    } catch (IOException ioe) {
      // do nothing
    }
  }

  @Override
  public void refresh() {
    try {
      this.logger.append("repaint was called\n");
    } catch (IOException ioe) {
      // do nothing
    }

  }

  @Override
  public void showMessage(String err, String title, int messageType) {
    try {
      this.logger.append("showed message dialog\n");
    } catch (IOException ioe) {
      // do nothing
    }
  }
}
