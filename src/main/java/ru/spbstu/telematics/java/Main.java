package ru.spbstu.telematics.java;

import java.io.*;
import org.apache.commons.io.FileUtils;

public class Main {
  public static void usage() {
    System.out.println("Usage: java -jar " + System.getProperty("sun.java.command") + " SOURCE... DESTINATION");
  }

  static void move(File src, File dst) throws IOException {
    if (!src.exists()) {
      throw new FileNotFoundException("Source file does not exist: " + src);
    }

    if (src.equals(dst)) {
      throw new IOException("Source and destination paths are the same.");
    }

    if (dst.isDirectory()) {
      FileUtils.moveToDirectory(src, dst, false);
    } else {
      if (!dst.exists()) {
        dst.getParentFile().mkdirs();
      } src.renameTo(dst);
    }
  }

  static void moveTo(File src, File dst) throws IOException {
    if (!src.exists()) {
      throw new FileNotFoundException("Source file does not exist: " + src);
    }

    if (src.equals(dst)) {
      throw new IOException("Source and destination paths are the same.");
    }

    if (dst.exists() && !dst.isDirectory()) {
      throw new IOException("Destination file isn't a directory.");
    }

    FileUtils.moveToDirectory(src, dst, true);
  }

  public static void mv(String[] args) throws IOException {
    if (args.length == 2) {
      var src = new File(args[0]);
      var dst = new File(args[1]);
      if (args[1].charAt(args[1].length() - 1) == '/') {
        moveTo(src, dst);
      } else {
        move(src, dst);
      }
    } else {
      var dst = new File(args[args.length - 1]);
      for (int i = 0; i < args.length - 1; i++) {
        moveTo(new File(args[i]), dst);
      }
    }
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      usage();
    } else {
      try {
        mv (args);
      } catch (IOException e) {
        System.err.println("Error moving file or directory: " + e.getMessage());
      }
    }
  }
}
