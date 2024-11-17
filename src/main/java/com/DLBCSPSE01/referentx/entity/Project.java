package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String name;

    private String description;

    private String citationStyle;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "userId")
    private Users owner;

    @ManyToMany
    @JoinTable(
            name = "collaborators",
            joinColumns = @JoinColumn(name = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<Users> collaborators;

    public Project() {
    }

    public Project(Long projectId, String name, String description, String citationStyle, Users owner, List<Users> collaborators) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.citationStyle = citationStyle;
        this.owner = owner;
        this.collaborators = collaborators;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public String getCitationStyle() {
        return citationStyle;
    }

    public void setCitationStyle(String citationStyle) {
        this.citationStyle = citationStyle;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public List<Users> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Users> collaborators) {
        this.collaborators = collaborators;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", citationStyle='" + citationStyle + '\'' +
                ", owner=" + owner +
                ", collaborators=" + collaborators +
                '}';
    }
}

