package com.DLBCSPSE01.referentx.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    private String title;

    @Length(max = 10000)
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "assignee", referencedColumnName = "userId")
    private Users assignee;

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "userId")
    private Users creator;

    private TaskStatus status;

    public Task() {
    }

    public Task(Integer taskId, String title, String description, Project project, Date dueDate, Users assignee, Users creator, TaskStatus status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.project = project;
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.creator = creator;
        this.status = status;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @Length(max = 10000) String getDescription() {
        return description;
    }

    public void setDescription(@Length(max = 10000) String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Users getAssignee() {
        return assignee;
    }

    public void setAssignee(Users assignee) {
        this.assignee = assignee;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

