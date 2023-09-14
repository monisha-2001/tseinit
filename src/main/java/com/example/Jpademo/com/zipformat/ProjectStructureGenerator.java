package com.example.Jpademo.com.zipformat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.example.Jpademo.com.model.Detail;

public class ProjectStructureGenerator {

    public static void createProjectStructure(File rootDirectory, Detail details) {

        // Create directories based on the structure you want
        File mavenFolder = new File(rootDirectory, ".mvn/wrapper");
        mavenFolder.mkdirs();
        // createMavenFloder(mavenFolder);

        File srcMainJava = new File(rootDirectory,
                "src/main/java/" + details.getGroupId() + "/" + details.getArtifactId());
        srcMainJava.mkdirs();

        File srcMainResources = new File(rootDirectory, "src/main/resources");
        srcMainResources.mkdirs();


        // Create a sample Java file (you can generate it based on user input)
        File mavenFile = new File(mavenFolder,"maven-wrapper.properties");
        createMavenFloder(mavenFile);

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

    private static void createSampleJavaFile(File javaFile, Detail details) {
        // Create a sample Java file content (you can modify it as needed)
        String javaCode = "package " + details.getGroupId() + "." + details.getArtifactId() + ";\n\n" +
                "import org.springframework.boot.SpringApplication;\n" +
                "import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n" +
                "@SpringBootApplication\n" +
                "public class " + details.getName() + "Application" + "{\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tSpringApplication.run(" + details.getName() + "Application.class, args);\n" +
                "\t}\n" +
                "}";

        try (PrintWriter writer = new PrintWriter(new FileWriter(javaFile))) {
            writer.write(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatDependencies(List<String> dependencies) {
    StringBuilder formattedDependencies = new StringBuilder();
    
    for (String dependency : dependencies) {
        formattedDependencies.append(dependency).append("\n");
    }
    
    return formattedDependencies.toString();
}


    private static void createSamplePomXml(File pomXml, Detail details) {
        // Create a sample pom.xml content (you can modify it as needed)
        String formattedDependencies = formatDependencies(details.getDependencies());

        String pomXmlContent =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"+
                    "\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n"+
                    "\t<modelVersion>4.0.0</modelVersion>\n"+
                    "\t<parent>\n"+
                        "\t\t<groupId>org.springframework.boot</groupId>\n"+
                        "\t\t<artifactId>spring-boot-starter-parent</artifactId>\n"+
                        "\t\t<version>"+details.getSpringBootVersion()+"</version>\n"+
                        "\t\t<relativePath/> <!-- lookup parent from repository -->\n"+
                    "\t</parent>\n"+
                    "\t<groupId>"+details.getGroupId()+"</groupId>\n"+
                    "\t<artifactId>"+details.getArtifactId()+"</artifactId>\n"+
                    "\t<version>0.0.1-SNAPSHOT</version>\n"+
                    "\t<name>"+details.getName()+"</name>\n"+
                    "\t<description>"+details.getDescription()+"</description>\n"+
                    "\t<properties>\n"+
                        "\t\t<java.version>"+details.getJavaVersion()+"</java.version>\n"+
                    "\t</properties>\n"+
                    "\t<dependencies>\n"+
                    "\t<dependency>\n"+
                        "\t\t<groupId>org.springframework.boot</groupId>\n"+
                        "\t\t<artifactId>spring-boot-starter</artifactId>\n"+
                   "\t</dependency>\n"+
                   formattedDependencies +
                    "\t\t"+details.getCustomDependencies()+"\n"+
                  "\t<dependency>\n"+
                    "\t\t<groupId>org.springframework.boot</groupId>\n"+
                    "\t\t<artifactId>spring-boot-starter-test</artifactId>\n"+
                    "\t\t<scope>test</scope>\n"+
                  "\t</dependency>\n"+
                    
                    "\t</dependencies>\n"+

                    "\t<build>\n"+
                        "\t\t<plugins>\n"+
                        "\t\t\t<plugin>\n"+
                            "\t\t\t\t<groupId>org.springframework.boot</groupId>\n"+
                            "\t\t\t\t<artifactId>spring-boot-maven-plugin</artifactId>\n"+
                        "\t\t\t</plugin>\n"+
                        "\t\t</plugins>\n"+
                    "\t</build>\n"+

                "</project>";


        try (PrintWriter writer = new PrintWriter(new FileWriter(pomXml))) {
            writer.write(pomXmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSampleJavaTestFile(File javaFile, Detail details) {
        // Create a sample Java file content (you can modify it as needed)
        String javaCode = "package " + details.getGroupId() + "." + details.getArtifactId() + ";\n\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n" +

                "@SpringBootTest\n" +
                "class " + details.getName() + "ApplicationTests {\n" +

                "\t@Test\n" +
                "\tvoid contextLoads() {\n" +

                "\t}\n" +
                "}\n";

        try (PrintWriter writer = new PrintWriter(new FileWriter(javaFile))) {
            writer.write(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMavenFloder(File mavenFile) {

        String propertiesContent = 
                    "# or more contributor license agreements.  See the NOTICE file\n"+
                    "# distributed with this work for additional information\n"+
                    "# regarding copyright ownership.  The ASF licenses this file\n"+
                    "# to you under the Apache License, Version 2.0 (the\n"+
                    "# \"License\"); you may not use this file except in compliance\n"+
                    "# with the License.  You may obtain a copy of the License at\n"+
                    "#\n"+
                    "#   https://www.apache.org/licenses/LICENSE-2.0\n"+
                    "#\n"+
                    "# Unless required by applicable law or agreed to in writing,\n"+
                    "# software distributed under the License is distributed on an\n"+
                    "# \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n"+
                    "# KIND, either express or implied.  See the License for the\n"+
                    "# specific language governing permissions and limitations\n"+
                    "# under the License.\n"+
                    "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.7/apache-maven-3.8.7-bin.zip\n"+
                    "wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.1.1/maven-wrapper-3.1.1.jar";
        

        try (PrintWriter writer = new PrintWriter(new FileWriter(mavenFile))) {
            writer.write(propertiesContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
