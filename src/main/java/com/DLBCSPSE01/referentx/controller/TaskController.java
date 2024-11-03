package com.DLBCSPSE01.referentx.controller;

import com.DLBCSPSE01.referentx.entity.*;
import com.DLBCSPSE01.referentx.service.ProjectService;
import com.DLBCSPSE01.referentx.service.TaskService;
import com.DLBCSPSE01.referentx.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.DLBCSPSE01.referentx.entity.TaskStatus.*;

@Controller
public class TaskController {

    private final UsersService usersService;
    private final TaskService taskService;
    private final ProjectService projectService;

    @Autowired
    public TaskController(UsersService usersService, TaskService taskService, ProjectService projectService) {
        this.usersService = usersService;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/projects/{id}/tasks")
    public String tasksPage(@PathVariable("id") int id, Model model) {

        Users currentUser = usersService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Project project = projectService.getOne(id);

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Task> tasks = taskService.getByProject(project);
            List<Task> todoTasks = new ArrayList<Task>();
            List<Task> inProgressTasks = new ArrayList<Task>();
            List<Task> doneTasks = new ArrayList<Task>();

            for (Task task : tasks) {
                switch (task.getStatus()) {
                    case TODO:
                        todoTasks.add(task);
                        break;
                    case PROGRESS:
                        inProgressTasks.add(task);
                        break;
                    case DONE:
                        doneTasks.add(task);
                        break;
                }
            }

            model.addAttribute("todoTasks", todoTasks);
            model.addAttribute("inProgressTasks", inProgressTasks);
            model.addAttribute("doneTasks", doneTasks);
        }

        model.addAttribute("projectId", id);
        model.addAttribute("section", "tasks");
        model.addAttribute("projectName", project.getProjectName());
        model.addAttribute("user", currentUser);

        return "tasks";
    }

    @GetMapping("/projects/{id}/tasks/add")
    public String addTask(@PathVariable("id") int id, Model model) {
        Project project = projectService.getOne(id);
        Users projectOwner = project.getProjectOwner();
        List<Users> projectCollaborators = project.getProjectCollaborators();
        List<Users> availableAssignees = new ArrayList<>();
        availableAssignees.add(projectOwner);
        availableAssignees.addAll(projectCollaborators);
        model.addAttribute("availableAssignees", availableAssignees);

        model.addAttribute("task", new Task());
        model.addAttribute("projectId", id);
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Add new task");
        model.addAttribute("formAction", "add-new");

        return "task-form";
    }

    @PostMapping("/projects/{id}/tasks/add-new")
    public String addTaskPost(@PathVariable("id") int id, @RequestParam("selectedAssigneeId") int assigneeId, Task task, Model model) {
        Project project = projectService.getOne(id);

        task.setProject(project);
        task.setStatus(TODO);

        if (assigneeId == 0) {
            task.setAssignee(null);
        } else {
            Users assignee = usersService.getOne(assigneeId);
            System.out.println("ASSIGNEEEE: " + assignee);
            task.setAssignee(assignee);
        }

        model.addAttribute("task", task);

        Task saved = taskService.addNew(task);
        return "redirect:/projects/" + id + "/tasks";
    }

    @GetMapping("projects/{id}/tasks/{taskId}/edit")
    public String editTask(@PathVariable("id") int id, @PathVariable("taskId") int taskId, Model model) {
        Project project = projectService.getOne(id);
        Users projectOwner = project.getProjectOwner();
        List<Users> projectCollaborators = project.getProjectCollaborators();
        List<Users> availableAssignees = new ArrayList<>();
        availableAssignees.add(projectOwner);
        availableAssignees.addAll(projectCollaborators);
        model.addAttribute("availableAssignees", availableAssignees);

        Task task = taskService.getOne(taskId);
        model.addAttribute("task", task);
        model.addAttribute("projectId", id);
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Edit task");
        model.addAttribute("formAction", "edit-task");
        return "task-form";
    }

    @PostMapping("/projects/{id}/tasks/edit-task")
    public String editTaskPost(@PathVariable("id") int id, @RequestParam("selectedAssigneeId") int assigneeId, Task task) {

        Project project = projectService.getOne(id);
        task.setProject(project);

        if (assigneeId == 0) {
            task.setAssignee(null);
        } else {
            Users assignee = usersService.getOne(assigneeId);
            System.out.println("ASSIGNEEEE: " + assignee);
            task.setAssignee(assignee);
        }

        taskService.updateTask(task);

        return "redirect:/projects/" + id + "/tasks";
    }

    @DeleteMapping("/projects/{id}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable("id") int id, Task task, Model model) {

        taskService.deleteTask(task);
        return "redirect:/projects/" + id + "/tasks";
    }

    @PostMapping("/projects/{id}/tasks/{taskId}/update-status")
    public String updateStatus(@PathVariable Integer id, @PathVariable Integer taskId, @RequestParam("status") String statusValue) {
        TaskStatus status;

        switch (statusValue.toLowerCase()) {
            case "todo":
                status = TODO;
                break;
            case "progress":
                status = PROGRESS;
                break;
            case "done":
                status = DONE;
                break;
            default:
                throw new IllegalArgumentException("Invalid status value: " + statusValue);
        }

        taskService.updateStatus(taskId, status);

        return "redirect:/projects/" + id + "/tasks";
    }
}








