package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    private String projectName;

    @Length(max = 10000)
    private String projectDescription;

    private String projectCitationStyle;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "userId")
    private Users projectOwner;

    @ManyToMany
    @JoinTable(
            name = "project_collaborators",
            joinColumns = @JoinColumn(name = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<Users> projectCollaborators;

    public Project() {
    }

    public Project(Integer projectId, String projectName, String projectDescription, String projectCitationStyle, Users projectOwner, List<Users> projectCollaborators) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectCitationStyle = projectCitationStyle;
        this.projectOwner = projectOwner;
        this.projectCollaborators = projectCollaborators;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public @Length(max = 10000) String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(@Length(max = 10000) String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectCitationStyle() {
        return projectCitationStyle;
    }

    public void setProjectCitationStyle(String projectCitationStyle) {
        this.projectCitationStyle = projectCitationStyle;
    }

    public Users getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Users projectOwner) {
        this.projectOwner = projectOwner;
    }

    public List<Users> getProjectCollaborators() {
        return projectCollaborators;
    }

    public void setProjectCollaborators(List<Users> projectCollaborators) {
        this.projectCollaborators = projectCollaborators;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectCitationStyle='" + projectCitationStyle + '\'' +
                ", projectOwner=" + projectOwner +
                ", projectCollaborators=" + projectCollaborators +
                '}';
    }
}

