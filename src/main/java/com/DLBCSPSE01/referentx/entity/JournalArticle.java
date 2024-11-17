package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("JournalArticle")
public class JournalArticle extends BaseSource {
    private String periodical;
    private String authors;
    private String pageRange;
    private Long issueNumber;
    private Long volume;
    private String articleNumber;

    public JournalArticle() {
        super();
    }

    public JournalArticle(Integer sourceId, Project project, String title, String year, String onlineAddress, String periodical, String authors, String pageRange, Long issueNumber, Long volume, String articleNumber) {
        super(sourceId, project, title, year, onlineAddress);
        this.periodical = periodical;
        this.authors = authors;
        this.pageRange = pageRange;
        this.issueNumber = issueNumber;
        this.volume = volume;
        this.articleNumber = articleNumber;
    }

    public String getPeriodical() {
        return periodical;
    }

    public void setPeriodical(String periodical) {
        this.periodical = periodical;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPageRange() {
        return pageRange;
    }

    public void setPageRange(String pageRange) {
        this.pageRange = pageRange;
    }

    public Long getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Long issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    @Override
    public String getSourceType() {
        return "journal-article";
    }

    @Override
    public String toString() {
        return "JournalArticle{" +
                "periodical='" + periodical + '\'' +
                ", author='" + authors + '\'' +
                ", pageRange='" + pageRange + '\'' +
                ", issueNumber=" + issueNumber +
                ", volume=" + volume +
                ", articleNumber='" + articleNumber + '\'' +
                '}';
    }
}
