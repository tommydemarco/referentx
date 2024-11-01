package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chapterId;

    private String chapterName;

    @Length(max = 10000)
    private String chapterDescription;

    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    public Chapter() {
    }

    public Chapter(Integer chapterId, String chapterName, String chapterDescription, Project project) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.chapterDescription = chapterDescription;
        this.project = project;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public @Length(max = 10000) String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(@Length(max = 10000) String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId=" + chapterId +
                ", chapterName='" + chapterName + '\'' +
                ", project=" + project +
                '}';
    }
}

