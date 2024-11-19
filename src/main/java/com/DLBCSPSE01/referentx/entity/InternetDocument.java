package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("InternetDocument")
public class InternetDocument extends BaseSource {
    private String organization;
    private String authors;
    private String accessedAt;

    public InternetDocument() {
        super();
    }

    public InternetDocument(Integer sourceId, Project project, String title, String year, String onlineAddress, String organization, String authors, String accessedAt) {
        super(sourceId, project, title, year, onlineAddress);
        this.organization = organization;
        this.authors = authors;
        this.accessedAt = accessedAt;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(String accessedAt) {
        this.accessedAt = accessedAt;
    }

    @Override
    public String getSourceType() {
        return "internet-document";
    }

    @Override
    public String toString() {
        return "InternetDocument{" +
                "organization='" + organization + '\'' +
                ", authors='" + authors + '\'' +
                ", accessedAt='" + accessedAt + '\'' +
                '}';
    }
}
