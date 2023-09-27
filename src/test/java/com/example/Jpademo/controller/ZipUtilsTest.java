package com.example.Jpademo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.Jpademo.com.zipformat.ZipUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZipUtilsTest {

    @Test
    public void testZipDirectory(@TempDir Path tempDir) throws IOException {
        // Create a temporary directory with some files and subdirectories to zip
        File directoryToZip = tempDir.resolve("directoryToZip").toFile();
        directoryToZip.mkdir();

        // Create a few files inside the directory
        File file1 = new File(directoryToZip, "file1.txt");
        File file2 = new File(directoryToZip, "file2.txt");
        File subDirectory = new File(directoryToZip, "subdir");
        subDirectory.mkdir();
        File file3 = new File(subDirectory, "file3.txt");

        // Create some content in the files (you can replace this with your content)
        Files.write(file1.toPath(), "Content of file1".getBytes());
        Files.write(file2.toPath(), "Content of file2".getBytes());
        Files.write(file3.toPath(), "Content of file3".getBytes());

        // Prepare a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Perform the test
        ZipUtils.zipDirectory(directoryToZip, response);

        // Assertions
        assertEquals("application/zip", response.getContentType());
        assertEquals("attachment; filename=\"project.zip\"", response.getHeader("Content-Disposition"));

    }
}
