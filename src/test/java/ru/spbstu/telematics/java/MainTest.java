package ru.spbstu.telematics.java;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;
import org.apache.commons.io.FileUtils;

public class MainTest {
  @Before
  public void setupTest() throws Exception {
    Files.createDirectories(Paths.get("test/dir1"));
    Files.createFile(Paths.get("test/file1"));
    Files.createFile(Paths.get("test/file2"));
    Files.createFile(Paths.get("test/file3"));
  }

  @After
  public void tearDown() throws Exception {
    try (Stream<Path> walk = Files.walk(Paths.get("test"))) {
      walk.sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    }
  }

  @Test
  public void testRename() throws Exception {
    Main.mv(new String[]{"test/file1", "test/file2"});
    assertTrue("test/file1 should've been renamed to: test/file2",
               !Files.exists(Paths.get("test/file1")) &&
               Files.exists(Paths.get("test/file2")));
  }

  @Test
  public void testMoveOneToDir() throws Exception {
    Main.mv(new String[]{"test/file1", "test/dir1"});
    assertTrue("test/file1 should've been moved to: test/dir1/file1",
               Files.exists(Paths.get("test/dir1/file1")));
  }

  @Test
  public void testOneMoveToNonExistantDir() throws Exception {
    Main.mv(new String[]{"test/file1", "test/dir2/"});
    assertTrue("test/file1 should've been moved to: test/dir2/file1",
               Files.exists(Paths.get("test/dir2/file1")));
  }

  @Test
  public void testMoveMultiple() throws Exception {
    Main.mv(new String[]{"test/file1", "test/file2", "test/file3", "test/dir1"});
    assertTrue("test/file1, test/file2 and test/file3 should've been moved to: test/dir1/",
               Files.exists(Paths.get("test/dir1/file1")) &&
               Files.exists(Paths.get("test/dir1/file2")) &&
               Files.exists(Paths.get("test/dir1/file3")));
  }

  @Test
  public void testMoveMultipleToNontant() throws Exception {
    Main.mv(new String[]{"test/file1", "test/file2", "test/file3", "test/dir2"});
    assertTrue("test/file1, test/file2 and test/file3 should've been moved to: test/dir2/",
               Files.exists(Paths.get("test/dir2/file1")) &&
               Files.exists(Paths.get("test/dir2/file2")) &&
               Files.exists(Paths.get("test/dir2/file3")));
  }
}
