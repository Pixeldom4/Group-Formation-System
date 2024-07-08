package Entities;

import java.util.HashSet;

public class Project implements ProjectInterface {
    private int projectId;
    private String projectTitle;
    private double projectBudget;
    private String projectDescription;
    private HashSet<String> projectTags;
    private float[] embedding;

    // Constructor
    public Project(int projectId, String projectTitle, double projectBudget, String projectDescription, HashSet<String> projectTags) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectBudget = projectBudget;
        this.projectDescription = projectDescription;
        this.projectTags = projectTags;
    }

    // Getters and Setters
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public double getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(double projectBudget) {
        this.projectBudget = projectBudget;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public HashSet<String> getProjectTags() {
        return projectTags;
    }

    public void setProjectTags(HashSet<String> projectTags) {
        this.projectTags = projectTags;
    }

}
