package com.DLBCSPSE01.referentx.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chapterId;

    private String name;

    @Length(max = 10000)
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    public Chapter() {
    }

    public Chapter(Integer chapterId, String name, String description, Project project) {
        this.chapterId = chapterId;
        this.name = name;
        this.description = description;
        this.project = project;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public @Length(max = 10000) String getDescription() {
        return description;
    }

    public void setDescription(@Length(max = 10000) String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId=" + chapterId +
                ", name='" + name + '\'' +
                ", project=" + project +
                '}';
    }
}

