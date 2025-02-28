package com.DLBCSPSE01.referentx.controller;

import com.DLBCSPSE01.referentx.model.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.DLBCSPSE01.referentx.model.TaskStatus.*;

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
    public String listTasks(@PathVariable("id") int id, Model model) {

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

        model.addAttribute("isProjectOwner", currentUser.getUserId() == project.getOwner().getUserId());
        model.addAttribute("projectId", id);
        model.addAttribute("section", "tasks");
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", currentUser);

        return "tasks";
    }

    @GetMapping("/projects/{id}/tasks/add")
    public String addTask(@PathVariable("id") int id, Model model) {
        Project project = projectService.getOne(id);
        Users owner = project.getOwner();
        List<Users> projectCollaborators = project.getCollaborators();
        List<Users> availableAssignees = new ArrayList<>();
        availableAssignees.add(owner);
        availableAssignees.addAll(projectCollaborators);
        model.addAttribute("availableAssignees", availableAssignees);

        model.addAttribute("task", new Task());
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Add new task");
        model.addAttribute("formAction", "add-new");

        return "task-form";
    }

    @PostMapping("/projects/{id}/tasks/add-new")
    public String addTaskPost(@PathVariable("id") int id, @RequestParam("selectedAssigneeId") int assigneeId, Task task, RedirectAttributes redirectAttributes) {
        Project project = projectService.getOne(id);
        Users currentUser = usersService.getCurrentUser();

        task.setProject(project);
        task.setStatus(TODO);
        task.setCreator(currentUser);

        if (assigneeId == 0) {
            task.setAssignee(null);
        } else {
            Users assignee = usersService.getOne(assigneeId);
            task.setAssignee(assignee);
        }

        taskService.addNew(task);
        redirectAttributes.addFlashAttribute("message", "Task added successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");

        return "redirect:/projects/" + id + "/tasks";
    }

    @GetMapping("projects/{id}/tasks/{taskId}/edit")
    public String editTask(@PathVariable("id") int id, @PathVariable("taskId") int taskId, Model model) {
        Project project = projectService.getOne(id);
        Users owner = project.getOwner();
        List<Users> projectCollaborators = project.getCollaborators();
        List<Users> availableAssignees = new ArrayList<>();
        availableAssignees.add(owner);
        availableAssignees.addAll(projectCollaborators);
        model.addAttribute("availableAssignees", availableAssignees);

        Task task = taskService.getOne(taskId);
        model.addAttribute("task", task);
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Edit task");
        model.addAttribute("formAction", "edit-task");
        return "task-form";
    }

    @PostMapping("/projects/{id}/tasks/edit-task")
    public String editTaskPost(@PathVariable("id") int id, @RequestParam("selectedAssigneeId") int assigneeId, Task task, RedirectAttributes redirectAttributes) {
        Project project = projectService.getOne(id);
        task.setProject(project);

        if (assigneeId == 0) {
            task.setAssignee(null);
        } else {
            Users assignee = usersService.getOne(assigneeId);
            task.setAssignee(assignee);
        }

        taskService.edit(task);
        redirectAttributes.addFlashAttribute("message", "Task edited successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/projects/" + id + "/tasks";
    }

    @DeleteMapping("/projects/{id}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable("id") int id, Task task, RedirectAttributes redirectAttributes) {
        taskService.delete(task);
        redirectAttributes.addFlashAttribute("message", "Task deleted successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/projects/" + id + "/tasks";
    }

    @PostMapping("/projects/{id}/tasks/{taskId}/update-status")
    public String updateStatus(@PathVariable Integer id, @PathVariable Integer taskId, @RequestParam("status") String statusValue, RedirectAttributes redirectAttributes) {
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
        redirectAttributes.addFlashAttribute("message", "Status updated successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");

        return "redirect:/projects/" + id + "/tasks";
    }
}








