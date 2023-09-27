package com.example.Jpademo.com.zipformat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import com.example.Jpademo.com.model.Detail;

public class ProjectStructureGenerator {

    public static void createProjectStructure(File rootDirectory, Detail details) {

    String camelCaseName = toCamelCase(details.getName());


        // Create directories based on the structure you want

        File mavenFolder = new File(rootDirectory, ".mvn/wrapper");
        File gradleFolder = new File(rootDirectory, "gradle/wrapper");

        // Create directories based on the build type
        if ("maven".equals(details.getBuildType())) {
            mavenFolder.mkdirs();
        } else if ("gradle".equals(details.getBuildType())) {
            gradleFolder.mkdirs();
        }


        // File mavenFolder = new File(rootDirectory, ".mvn/wrapper");
        // mavenFolder.mkdirs();
        // createMavenFloder(mavenFolder);

        File srcMainJava = new File(rootDirectory,
                "src/main/java/" + details.getGroupId() + "/" + details.getArtifactId());
        srcMainJava.mkdirs();



        File srcMainResources = new File(rootDirectory, "src/main/resources");
        srcMainResources.mkdirs();


        // Create a sample Java file (you can generate it based on user input)
        // File mavenFile = new File(mavenFolder,"maven-wrapper.properties");
        // createMavenFloder(mavenFile);
        if ("maven".equals(details.getBuildType())) {
            File mavenFile = new File(mavenFolder, "maven-wrapper.properties");
            createMavenWrapperFile(mavenFile);
        } else if ("gradle".equals(details.getBuildType())) {
            File gradleFile = new File(gradleFolder, "gradle-wrapper.properties");
            createGradleWrapperFile(gradleFile);

            File settingGradle = new File(rootDirectory, "settings.gradle.kts");
            createSettingGradle(settingGradle,details);
        }



        File javaFile = new File(srcMainJava, camelCaseName + "Application.java");
        createSampleJavaFile(javaFile, details);



        // Check if packaging is "war" and generate ServletInitializer.java if true
        if ("war".equals(details.getPackaging())) {
            File servletInitializerFile = new File(srcMainJava, "ServletInitializer.java");
            createServletInitializerFile(servletInitializerFile, details);

            // Generate 'static' and 'templates' folders
            File staticFolder = new File(srcMainResources, "static");
            File templatesFolder = new File(srcMainResources, "templates");

            staticFolder.mkdirs();
            templatesFolder.mkdirs();

        }

        // Create a sample pom.xml (you can generate it based on user input)

        if ("maven".equals(details.getBuildType())) {
            // Generate Maven-related files and folders
            File pomXml = new File(rootDirectory, "pom.xml");
            createSamplePomXml(pomXml, details);
        } else if ("gradle".equals(details.getBuildType())) {
            // Generate Gradle-related files and folders
            File buildGradleKtsFile = new File(rootDirectory, "build.gradle.kts");
            createGradleBuildFile(buildGradleKtsFile, details);
        }

        // File pomXml = new File(rootDirectory, "pom.xml");
        // createSamplePomXml(pomXml, details);

        
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

        File javaTestFile = new File(srcTestJava, camelCaseName + "ApplicationTests.java");
        createSampleJavaTestFile(javaTestFile, details);

    }
    
    private static String toCamelCase(String input) {
        StringBuilder camelCaseString = new StringBuilder();
    
        boolean capitalizeNext = true; // Start with capitalizing the first character
    
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c) || c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    camelCaseString.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    camelCaseString.append(Character.toLowerCase(c));
                }
            }
        }
    
        // Ensure the first character is in uppercase
        if (camelCaseString.length() > 0) {
            char firstChar = camelCaseString.charAt(0);
            if (Character.isLowerCase(firstChar)) {
                camelCaseString.setCharAt(0, Character.toUpperCase(firstChar));
            }
        }
    
        return camelCaseString.toString();
    }
    
    
    

    private static void createSampleJavaFile(File javaFile, Detail details) {

        String camelCaseName = toCamelCase(details.getName());
        // Create a sample Java file content (you can modify it as needed)
        String javaCode = "package " + details.getGroupId() + "." + details.getArtifactId() + ";\n\n" +
                "import org.springframework.boot.SpringApplication;\n" +
                "import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n" +
                "@SpringBootApplication\n" +
                "public class " + camelCaseName + "Application" + "{\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tSpringApplication.run(" + camelCaseName + "Application.class, args);\n" +
                "\t}\n" +
                "}";

        try (PrintWriter writer = new PrintWriter(new FileWriter(javaFile))) {
            writer.write(javaCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createServletInitializerFile(File servletInitializerFile, Detail details) {
        // Create a sample ServletInitializer file content
        String servletInitializerCode = "package " + details.getGroupId() + "." + details.getArtifactId() + ";\n\n" +
                "import org.springframework.boot.builder.SpringApplicationBuilder;\n" +
                "import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;\n\n" +
                "public class ServletInitializer extends SpringBootServletInitializer {\n\n" +
                "  @Override\n" +
                "  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {\n" +
                "   \treturn application.sources(" + toCamelCase(details.getName()) + "Application.class);\n" +
                "  }\n" +
                "}\n";
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(servletInitializerFile))) {
            writer.write(servletInitializerCode);
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

    // Method to create Gradle build.gradle.kts file
    private static void createGradleBuildFile(File buildGradleKtsFile, Detail details) {
        String formattedDependencies = formatDependencies(details.getDependencies());

        String buildGradleKtsContent =
                "plugins {\n" +
                "  java\n" +
                "  id(\"org.springframework.boot\") version \"" + details.getSpringBootVersion() + "\"\n" +
                "  id(\"io.spring.dependency-management\") version \"1.1.3\"\n" +
                "}\n\n" +
                "group = \"" + details.getGroupId() + "\"\n" +
                "version = \"0.0.1-SNAPSHOT\"\n\n" +
                "java {\n" +
                "  sourceCompatibility = JavaVersion.VERSION_" + details.getJavaVersion() + "\n" +
                "}\n\n" +
                "repositories {\n" +
                "  mavenCentral()\n" +
                "}\n\n" +
                "dependencies {\n" +
                "  implementation(\"org.springframework.boot:spring-boot-starter\")\n" +
                     formattedDependencies +
                    "\t\t"+details.getCustomDependencies()+"\n"+
                "  testImplementation(\"org.springframework.boot:spring-boot-starter-test\")\n" +
                "}\n\n" +
                "tasks.withType<Test> {\n" +
                "  useJUnitPlatform()\n" +
                "}\n";

        try (PrintWriter writer = new PrintWriter(new FileWriter(buildGradleKtsFile))) {
            writer.write(buildGradleKtsContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSampleJavaTestFile(File javaFile, Detail details) {
        String camelCaseName = toCamelCase(details.getName());

        // Create a sample Java file content (you can modify it as needed)
        String javaCode = "package " + details.getGroupId() + "." + details.getArtifactId() + ";\n\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n" +

                "@SpringBootTest\n" +
                "class " + camelCaseName + "ApplicationTests {\n" +

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

    private static void createMavenWrapperFile(File mavenFile) {

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

    // Method to create Gradle wrapper properties file
    private static void createGradleWrapperFile(File gradleFile) {
        String propertiesContent = 
            "distributionBase=GRADLE_USER_HOME\n"+
            "distributionPath=wrapper/dists\n"+
            "distributionUrl=https\\://services.gradle.org/distributions/gradle-8.2.1-bin.zip\n"+
            "networkTimeout=10000\n"+
            "validateDistributionUrl=true\n"+
            "zipStoreBase=GRADLE_USER_HOME\n"+
            "zipStorePath=wrapper/dists";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(gradleFile))) {
            writer.write(propertiesContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSettingGradle(File gradleFile,Detail details) {
        String propertiesContent = "rootProject.name =\""+details.getName()+"\"";

        try (PrintWriter writer = new PrintWriter(new FileWriter(gradleFile))) {
            writer.write(propertiesContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
