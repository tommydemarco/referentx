package com.DLBCSPSE01.referentx.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "source_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sourceId;

    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    private String title;
    private String year;
    private String onlineAddress;

    public BaseSource() {
    }

    public BaseSource(Integer sourceId, Project project, String title, String year, String onlineAddress) {
        this.sourceId = sourceId;
        this.project = project;
        this.title = title;
        this.year = year;
        this.onlineAddress = onlineAddress;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOnlineAddress() {
        return onlineAddress;
    }

    public void setOnlineAddress(String onlineAddress) {
        this.onlineAddress = onlineAddress;
    }

    public abstract String getSourceType();

    @Override
    public String toString() {
        return "BaseSource{" +
                "sourceId=" + sourceId +
                ", project=" + project +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", onlineAddress='" + onlineAddress + '\'' +
                '}';
    }
}