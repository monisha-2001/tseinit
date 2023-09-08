package com.example.Jpademo.com.zipformat;

import com.example.Jpademo.com.model.Detail;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ProjectGenerationImpl implements ProjectGenerationService {

    @Override
    public void generateProject(Detail details, HttpServletResponse response) throws IOException {
        // Create a temporary directory to store project files
        File tempDirectory = createTempDirectory();

        // Create the folder structure
        createProjectStructure(tempDirectory, details);

        // Zip the temporary directory and send it as a response
        zipDirectory(tempDirectory, response);
    }

    private File createTempDirectory() throws IOException {
        // Create a temporary directory
        return Files.createTempDirectory("project").toFile();
    }

    private void createProjectStructure(File rootDirectory, Detail details) {
        // Create directories based on the structure you want
        File srcMainJava = new File(rootDirectory,
                "src/main/java/" + details.getGroupId() + "/" + details.getArtifactId());
        srcMainJava.mkdirs();

        File srcMainResources = new File(rootDirectory, "src/main/resources");
        srcMainResources.mkdirs();

        // Create a sample Java file (you can generate it based on user input)
        File javaFile = new File(srcMainJava, details.getName() + "Application.java");
        createSampleJavaFile(javaFile, details);

        // Create a sample pom.xml (you can generate it based on user input)
        File pomXml = new File(rootDirectory, "pom.xml");
        createSamplePomXml(pomXml, details);

        // Create a sample application.properties (you can generate it based on user
        // input)
        File applProp = new File(srcMainResources, "application.properties");
        try {
            if (applProp.createNewFile()) {
                System.out.println("File created successfully: " + applProp.getAbsolutePath());
            } else {
                System.out.println("File already exists: " + applProp.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        // create a test floder
        File srcTestJava = new File(rootDirectory,
                "src/test/java/" + details.getGroupId() + "/" + details.getArtifactId());
        srcTestJava.mkdirs();

        File javaTestFile = new File(srcTestJava, details.getName() + "ApplicationTests.java");
        createSampleJavaTestFile(javaTestFile, details);

    }

    private void createSampleJavaFile(File javaFile, Detail details) {
        // Create a sample Java file content (you can modify it as needed)
        String javaCode = "package" + details.getGroupId() + "." + details.getArtifactId() + ";\n\n" +
                "import org.springframework.boot.SpringApplication;\n" +
                "import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n" +
                "@SpringBootApplication\n" +
                "public class " + details.getName() + "Application" + "{\n" +
                "public static void main(String[] args) {\n" +
                "SpringApplication.run(" + details.getName() + ".class, args);\n" +
                "}\n" +
                "}";

        try (PrintWriter writer = new PrintWriter(new FileWriter(javaFile))) {
            writer.write(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSamplePomXml(File pomXml, Detail details) {
        // Create a sample pom.xml content (you can modify it as needed)
        String pomXmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "    <groupId>" + details.getGroupId() + "</groupId>\n" +
                "    <artifactId>" + details.getArtifactId() + "</artifactId>\n" +
                "<version>0.0.1-SNAPSHOT</version>\n"+
                "<name>"+details.getName()+"</name>\n"+
                "<description>"+details.getDescription()+"</description>\n"+
                "<properties>\n"+
                  "<java.version>"+details.getSpringBootVersion()+"</java.version>\n"+
                "</properties>\n"+
                "<dependencies>\n"+
                  details.getCustomDependencies()+"\n"+
                "<dependencies>\n"+

                "</project>";

        try (PrintWriter writer = new PrintWriter(new FileWriter(pomXml))) {
            writer.write(pomXmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zipDirectory(File directory, HttpServletResponse response) throws IOException {
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

    private void createSampleJavaTestFile(File javaFile, Detail details) {
        // Create a sample Java file content (you can modify it as needed)
        String javaCode = "package com.example.demo\n\n;" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n" +

                "@SpringBootTest\n" +
                "class " + details.getName() + "ApplicationTests {\n" +

                "@Test\n" +
                "void contextLoads() {\n" +

                "}\n" +
                "}\n";

        try (PrintWriter writer = new PrintWriter(new FileWriter(javaFile))) {
            writer.write(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToZip(File file, String parentPath, ZipOutputStream zipOutputStream) throws IOException {
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
