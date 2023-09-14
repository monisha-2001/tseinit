package com.example.Jpademo.com.zipformat;

import com.example.Jpademo.com.model.Detail;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class ProjectGenerationImpl implements ProjectGenerationService {

    @Override
    public void generateProject(Detail details, HttpServletResponse response) throws IOException {
        // Create a temporary directory to store project files
        File tempDirectory = FileUtils.createTempDirectory();

        // Create the folder structure
        ProjectStructureGenerator.createProjectStructure(tempDirectory, details);

        // Zip the temporary directory and send it as a response
        ZipUtils.zipDirectory(tempDirectory, response);
    } 
}
