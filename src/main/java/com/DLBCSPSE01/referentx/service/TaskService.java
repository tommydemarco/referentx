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
        List<Task> tasksByProject = taskRepository.findByProject(project);
        return tasksByProject;
    }

    public Task getOne(int id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public void updateTask(Task task) {
        Task savedTask = taskRepository.findById(task.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        savedTask.setTaskTitle(task.getTaskTitle());
        savedTask.setTaskDescription(task.getTaskDescription());
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

