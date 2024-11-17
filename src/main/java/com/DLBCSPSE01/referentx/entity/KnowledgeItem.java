package com.DLBCSPSE01.referentx.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
public class KnowledgeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long knowledgeItemId;

    private String title;

    @Length
    private String content;

    @ManyToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    @ManyToOne(optional = true)
    @JoinColumn(name = "sourceId", referencedColumnName = "sourceId")
    private BaseSource source;

    @ManyToOne(optional = true)
    @JoinColumn(name = "chapterId", referencedColumnName = "chapterId")
    private Chapter chapter;

    public KnowledgeItem() {
    }

    public KnowledgeItem(Long knowledgeItemId, String title, String content, Project project, BaseSource source, Chapter chapter) {
        this.knowledgeItemId = knowledgeItemId;
        this.title = title;
        this.content = content;
        this.project = project;
        this.source = source;
        this.chapter = chapter;
    }

    public Long getKnowledgeItemId() {
        return knowledgeItemId;
    }

    public void setKnowledgeItemId(Long knowledgeItemId) {
        this.knowledgeItemId = knowledgeItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @Length String getContent() {
        return content;
    }

    public void setContent(@Length String content) {
        this.content = content;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BaseSource getSource() {
        return source;
    }

    public void setSource(BaseSource source) {
        this.source = source;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "KnowledgeItem{" +
                "knowledgeItemId=" + knowledgeItemId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", project=" + project +
                ", source=" + source +
                ", chapter=" + chapter +
                '}';
    }
}

