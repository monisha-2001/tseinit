package com.example.Jpademo.com.zipformat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static File createTempDirectory() throws IOException {
        // Create a temporary directory
        return Files.createTempDirectory("project").toFile();
    }
    
}
