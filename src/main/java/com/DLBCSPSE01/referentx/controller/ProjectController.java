package com.DLBCSPSE01.referentx.controller;

import com.DLBCSPSE01.referentx.entity.*;
import com.DLBCSPSE01.referentx.service.ProjectService;
import com.DLBCSPSE01.referentx.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {

    private final UsersService usersService;
    private final ProjectService projectService;

    @Autowired
    public ProjectController(UsersService usersService, ProjectService projectService) {
        this.usersService = usersService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/projects/";
    }

    @GetMapping("/projects/")
    public String projectsPage(@RequestParam(value = "type", required = false) String type, Model model) {

        Users currentUser = usersService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (type != null && type.equals("collaborations")) {
                List<Project> projects = projectService.getByCollaborator(currentUser);
                model.addAttribute("projects", projects);
                model.addAttribute("ownProjects", false);
            } else {
                List<Project> projects = projectService.getByOwner(currentUser);
                model.addAttribute("projects", projects);
                model.addAttribute("ownProjects", true);
            }
        }

        model.addAttribute("user", currentUser);

        return "projects";
    }

    @GetMapping("/projects/add")
    public String addProject(Model model) {
        Users owner = usersService.getCurrentUser();

        List<Users> allUsers = usersService.getAll();
        List<Users> availableCollaborators = allUsers.stream()
                .filter(item -> !item.equals(owner))
                .collect(Collectors.toList());

        model.addAttribute("project", new Project());
        model.addAttribute("user", owner);
        model.addAttribute("availableCollaborators", availableCollaborators);
        model.addAttribute("currentCollaborators", new ArrayList<>());
        model.addAttribute("formTitle", "Add new project");
        model.addAttribute("formAction", "add-new");
        return "project-form";
    }

    @PostMapping("/projects/add-new")
    public String addProjectPost(@RequestParam("collaboratorId") List<Integer> collaboratorIds, Project project, Model model) {
        List<Users> selectedCollaborators = usersService.getAllById(collaboratorIds);
        project.setCollaborators(selectedCollaborators);

        Users user = usersService.getCurrentUser();
        if (user != null) {
            project.setOwner(user);
        }
        model.addAttribute("project", project);
        Project saved = projectService.addNew(project);
        return "redirect:/projects/";
    }

    @GetMapping("projects/{id}/edit")
    public String editProject(@PathVariable("id") int id, Model model) {
        Project project = projectService.getOne(id);
        Users owner = project.getOwner();
        Users currentUser = usersService.getCurrentUser();

        List<Users> allUsers = usersService.getAll();
        List<Users> availableCollaborators = allUsers.stream()
                .filter(item -> !item.equals(owner))
                .collect(Collectors.toList());


        model.addAttribute("project", project);
        model.addAttribute("user", currentUser);
        model.addAttribute("availableCollaborators", availableCollaborators);
        model.addAttribute("currentCollaborators", project.getCollaborators());
        model.addAttribute("formTitle", "Edit project");
        model.addAttribute("formAction", "edit-project");
        return "project-form";
    }

    @PostMapping("/projects/edit-project")
    public String editProjectPost(@RequestParam("collaboratorId") List<Integer> collaboratorIds, Project project, Model model) {
        List<Users> selectedCollaborators = usersService.getAllById(collaboratorIds);
        project.setCollaborators(selectedCollaborators);

        Users user = usersService.getCurrentUser();
        if (user != null) {
            project.setOwner(user);
        }

        model.addAttribute("project", project);
        Project saved = projectService.addNew(project);
        return "redirect:/projects/";
    }
}








