package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Book")
public class Book extends BaseSource {
    private String publisher;
    private String authors;
    private String publicationPlace;

    public Book() {
        super();
    }

    public Book(Integer sourceId, Project project, String title, String year, String onlineAddress, String publisher, String authors, String publicationPlace) {
        super(sourceId, project, title, year, onlineAddress);
        this.publisher = publisher;
        this.authors = authors;
        this.publicationPlace = publicationPlace;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = this.authors;
    }

    public String getPublicationPlace() {
        return publicationPlace;
    }

    public void setPublicationPlace(String publicationPlace) {
        this.publicationPlace = publicationPlace;
    }

    @Override
    public String getSourceType() {
        return "book";
    }

    @Override
    public String toString() {
        return "Book{" +
                "publisher='" + publisher + '\'' +
                ", author='" + authors + '\'' +
                ", publicationPlace='" + publicationPlace + '\'' +
                '}';
    }
}
