package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Command executor class is to run external tools.
 * You can execute commands from command line or shell on the OS where test is currently being executed
 *
 * For example:
 *
 * {@code adb push hosts /etc/hosts} - pushes the hosts file to the /etc/hosts directory on android emulator
 * {@code xcrun simctl shutdown booted} - shuts down all booted iOS simulators
 * {@code cat /etc/hosts} - returns content of the hosts file from the /etc directory on local host
 */
public class CommandExecutor {

  /**
   * Executes command on local host.
   *
   * @param command    the command
   * @param readOutput boolean "true" if you want to see command results in the output, "false" if you don't
   * @return the string "success" if the execution is successful or error if the execution is failed
   */
  public static String executeCommand(String command, boolean readOutput) {
    try {
      Process p = Runtime.getRuntime().exec(command);
      if (readOutput) {
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
          sb.append(line).append("\n");
        }
        return sb.toString();
      } else {
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        System.out.println(command);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Success!";
  }
}
