package utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The type Sync pipe.
 * This class is used by {@link CommandExecutor} if you don't want to see the output of the command
 */
class SyncPipe implements Runnable {

  private final OutputStream out_;
  private final InputStream in_;

  /**
   * Instantiates a new Sync pipe.
   *
   * @param inputStream the input stream
   * @param outputStream the output stream
   */
  public SyncPipe(InputStream inputStream, OutputStream outputStream) {
    in_ = inputStream;
    out_ = outputStream;
  }

  public void run() {
    try {
      final byte[] buffer = new byte[1024];
      for (int length; (length = in_.read(buffer)) != -1; ) {
        out_.write(buffer, 0, length);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}