package com.DLBCSPSE01.referentx.service;

import com.DLBCSPSE01.referentx.entity.*;
import com.DLBCSPSE01.referentx.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addNew(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getByProject(Project project) {
        return taskRepository.findByProject(project);
    }

    public Task getOne(int id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public void edit(Task task) {
        Task savedTask = taskRepository.findById(task.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        savedTask.setTitle(task.getTitle());
        savedTask.setDescription(task.getDescription());
        savedTask.setDueDate(task.getDueDate());
        savedTask.setAssignee(task.getAssignee());

        taskRepository.save(savedTask);
    }

    public void updateStatus(Integer id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        task.setStatus(status);
        taskRepository.save(task);
    }
}

