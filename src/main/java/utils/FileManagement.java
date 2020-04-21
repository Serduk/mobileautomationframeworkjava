package utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The File management service class.
 */
public class FileManagement {

  /**
   * Search for application files with the .app and .apk extensions.
   *
   * @param root                   the path to the base directory with all apps
   * @param listOfApplicationFiles the list of application files
   */
  public static void searchForAppFiles(File root, List<File> listOfApplicationFiles) {
    if (root == null || listOfApplicationFiles == null) {
      return;
    }
    searchForFileType(root, listOfApplicationFiles, ".app");
    searchForFileType(root, listOfApplicationFiles, ".apk");
  }

  /**
   * Cleans given directory with an option to exclude given file during cleanup
   *
   * @param filePath                   the path to the base directory to clean
   * @param fileNameToSkip name of the file to skip during the clean
   */
  public static void cleanFolderAndSkipGivenFile(String filePath, String fileNameToSkip) {
    if (filePath == null || fileNameToSkip == null) {
      return;
    }
    File dirContent = Paths.get(filePath).toFile();
    for (File file : Objects.requireNonNull(dirContent.listFiles())) {
      if (file.isFile() && !file.getName().equals(fileNameToSkip)) {
        file.delete();
      }
    }

  }

  private static void searchForFileType(File root, List<File> listOfFilesToFill, String fileType) {
    if (root == null || listOfFilesToFill == null) {
      return;
    }
    if (root.isDirectory() && root.getName().endsWith(fileType)) {
      listOfFilesToFill.add(root);
    } else if (root.isDirectory()) {
      for (File file : root.listFiles()) {
        searchForFileType(file, listOfFilesToFill,fileType);
      }
    } else if (!root.isDirectory() && root.getName().endsWith(fileType)){
      listOfFilesToFill.add(root);
    }
  }

}
