package com.example.Jpademo.com.model;



import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "details")
public class Detail {

    @Id
    private ObjectId id;

    private String groupId;
    private String artifactId;
    private String name;
    private String description;
    private String packageName;
    private String buildType;
    private String language;
    private String springBootVersion;
    private String packaging;
    private String javaVersion;
    private List dependencies;
    private String customDependencies;


    public Detail() {
    }

    
    
    public Detail(ObjectId id, String groupId, String artifactId, String name, String description, String packageName,
            String buildType, String language, String springBootVersion, String packaging, String javaVersion,
            List dependencies, String customDependencies) {
        this.id = id;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.name = name;
        this.description = description;
        this.packageName = packageName;
        this.buildType = buildType;
        this.language = language;
        this.springBootVersion = springBootVersion;
        this.packaging = packaging;
        this.javaVersion = javaVersion;
        this.dependencies = dependencies;
        this.customDependencies = customDependencies;
    }



    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }
    
   public List getDependencies() {
        return dependencies;
    }

    public void setDependencies(List dependencies) {
        this.dependencies = dependencies;
    }

    public String getCustomDependencies() {
        return customDependencies;
    }

    public void setCustomDependencies(String customDependencies) {
        this.customDependencies = customDependencies;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", packageName='" + packageName + '\'' +
                ", buildType='" + buildType + '\'' +
                ", language='" + language + '\'' +
                ", springBootVersion='" + springBootVersion + '\'' +
                ", packaging='" + packaging + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", Dependencies='" + dependencies + '\'' +
                ", customDependencies='" + customDependencies + '\'' +
                '}';
    }



    
}
