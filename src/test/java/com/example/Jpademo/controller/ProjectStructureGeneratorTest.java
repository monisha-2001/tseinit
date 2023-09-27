package com.example.Jpademo.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.Jpademo.com.zipformat.ProjectStructureGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.example.Jpademo.com.model.Detail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ProjectStructureGeneratorTest {

    @Test
    public void testCreateProjectStructure(@TempDir Path tempDir) {
        // Prepare test data: create a temporary directory and a Detail object
        File rootDirectory = tempDir.toFile();
        Detail details = createSampleDetail(); // You should create a sample Detail object here

        // Perform the test
        ProjectStructureGenerator.createProjectStructure(rootDirectory, details);

        // Assertions: You can add assertions to verify the structure of the temporary directory
        File srcMainJava = new File(rootDirectory, "src/main/java/" + details.getGroupId() + "/" + details.getArtifactId());
        assertTrue(srcMainJava.exists());
        assertTrue(srcMainJava.isDirectory());

        // Add more assertions as needed to verify the generated structure
    }

    // Create a sample Detail object for testing
    private Detail createSampleDetail() {
        Detail details = new Detail();
        details.setGroupId("com.example");
        details.setArtifactId("sample");
        details.setName("SampleProject");
        details.setBuildType("maven");
        details.setPackaging("jar");
        details.setSpringBootVersion("2.5.4");
        details.setJavaVersion("11");
        details.setDependencies(Arrays.asList("spring-boot-starter-data-jpa", "spring-boot-starter-web"));
        details.setCustomDependencies("your-custom-dependency");
        details.setDescription("Sample Spring Boot Project");
        return details;
    }
}

