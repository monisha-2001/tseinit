package com.example.Jpademo.controller;

import com.example.Jpademo.com.zipformat.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilsTest {

    @Test
    public void testCreateTempDirectory(@TempDir File tempDir) throws IOException {
        // Perform the test by using the @TempDir annotation to get a temporary directory

        // Call the method being tested
        File tempDirectory = FileUtils.createTempDirectory();

        // Assertions
        assertNotNull(tempDirectory);
        assertTrue(tempDirectory.isDirectory());
    }

}
