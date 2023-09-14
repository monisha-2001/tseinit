package com.example.Jpademo.com.zipformat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.http.HttpServletResponse;

public class ZipUtils {

    public static void zipDirectory(File directory, HttpServletResponse response) throws IOException {
        // Get the list of files and directories in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
                // Set content type and headers for the response
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment; filename=\"project.zip\"");

                // Iterate through files and directories and add them to the zip
                for (File file : files) {
                    addToZip(file, "", zipOutputStream);
                }
            }
        }
    }

    private static void addToZip(File file, String parentPath, ZipOutputStream zipOutputStream) throws IOException {
        String entryPath = parentPath + file.getName();
        if (file.isDirectory()) {
            // If it's a directory, create a zip entry for it and add its contents
            zipOutputStream.putNextEntry(new ZipEntry(entryPath + "/"));
            zipOutputStream.closeEntry();

            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    addToZip(subFile, entryPath + "/", zipOutputStream);
                }
            }
        } else {
            // If it's a file, add it to the zip
            try (FileInputStream inputStream = new FileInputStream(file)) {
                zipOutputStream.putNextEntry(new ZipEntry(entryPath));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();
            }
        }
    }
    
}
