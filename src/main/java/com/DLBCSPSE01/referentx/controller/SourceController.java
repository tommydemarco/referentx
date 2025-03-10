package com.DLBCSPSE01.referentx.controller;

import com.DLBCSPSE01.referentx.model.*;
import com.DLBCSPSE01.referentx.service.ProjectService;
import com.DLBCSPSE01.referentx.service.SourceService;
import com.DLBCSPSE01.referentx.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SourceController {

    private final UsersService usersService;
    private final SourceService sourceService;
    private final ProjectService projectService;

    @Autowired
    public SourceController(UsersService usersService, SourceService sourceService, ProjectService projectService) {
        this.usersService = usersService;
        this.sourceService = sourceService;
        this.projectService = projectService;
    }

    @GetMapping("/projects/{id}/sources")
    public String listSources(@PathVariable("id") int id, Model model) {

        Users currentUser = usersService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Project project = projectService.getOne(id);

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<BaseSource> sources = sourceService.getByProject(project);
            model.addAttribute("sources", sources);
        }

        model.addAttribute("projectId", id);
        model.addAttribute("section", "sources");
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", currentUser);
        return "sources";
    }

    @GetMapping("/projects/{id}/sources/add/{sourceType}")
    public String addSource(@PathVariable("id") int id, @PathVariable("sourceType") String sourceType, Model model) {
        Project project = projectService.getOne(id);
        BaseSource source;
        switch (sourceType.toLowerCase()) {
            case "book":
                source = new Book();
                break;
            case "journal-article":
                source = new JournalArticle();
                break;
            case "internet-document":
                source = new InternetDocument();
                break;
            default:
                throw new IllegalArgumentException("Invalid source type value");
        }

        model.addAttribute("source", source);
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", project.getName());
        model.addAttribute("sourceType", sourceType);
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Add new " + sourceType.replace('-', ' '));
        model.addAttribute("formAction", "add-new");

        return "source-form";
    }

    @PostMapping("/projects/{id}/sources/add-new/book")
    public String addBookPost(@PathVariable("id") int projectId, Book book, RedirectAttributes redirectAttributes) {
        saveNewSource(book, projectId, redirectAttributes);
        return "redirect:/projects/" + projectId + "/sources";
    }

    @PostMapping("/projects/{id}/sources/add-new/journal-article")
    public String addJournalArticlePost(@PathVariable("id") int projectId, JournalArticle journalArticle, RedirectAttributes redirectAttributes) {
        saveNewSource(journalArticle, projectId, redirectAttributes);
        return "redirect:/projects/" + projectId + "/sources";
    }

    @PostMapping("/projects/{id}/sources/add-new/internet-document")
    public String addInternetDocumentPost(@PathVariable("id") int projectId, InternetDocument internetDocument, RedirectAttributes redirectAttributes) {
        saveNewSource(internetDocument, projectId, redirectAttributes);
        return "redirect:/projects/" + projectId + "/sources";
    }

    @GetMapping("projects/{id}/sources/{sourceId}/edit")
    public String editSource(@PathVariable("id") int id, @PathVariable("sourceId") int sourceId, Model model) {
        BaseSource source = sourceService.getOne(sourceId);
        Project project = projectService.getOne(id);

        model.addAttribute("source", source);
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", project.getName());
        model.addAttribute("sourceType", source.getSourceType());
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Edit source");
        model.addAttribute("formAction", "edit-source");
        return "source-form";
    }

    @PostMapping("/projects/{id}/sources/edit-source/book")
    public String editBookPost(@PathVariable("id") int projectId, Book book, RedirectAttributes redirectAttributes) {
        editSource(book, projectId, redirectAttributes);
        return "redirect:/projects/" + projectId + "/sources";
    }

    @PostMapping("/projects/{id}/sources/edit-source/journal-article")
    public String editJournalArticlePost(@PathVariable("id") int projectId, JournalArticle journalArticle, RedirectAttributes redirectAttributes) {
        editSource(journalArticle, projectId, redirectAttributes);
        return "redirect:/projects/" + projectId + "/sources";
    }

    @PostMapping("/projects/{id}/sources/edit-source/internet-document")
    public String editInternetDocumentPost(@PathVariable("id") int projectId, InternetDocument internetDocument, RedirectAttributes redirectAttributes) {
        editSource(internetDocument, projectId, redirectAttributes);
        return "redirect:/projects/" + projectId + "/sources";
    }

    @DeleteMapping("/projects/{id}/sources/{sourceId}/delete")
    public String deleteSource(@PathVariable("id") int id, @PathVariable("sourceId") int sourceId, RedirectAttributes redirectAttributes) {
        BaseSource source = sourceService.getOne(sourceId);
        sourceService.delete(source);
        redirectAttributes.addFlashAttribute("message", "Source deleted successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/projects/" + id + "/sources";
    }

    public void saveNewSource(BaseSource source, int projectId, RedirectAttributes redirectAttributes) {
        Project project = projectService.getOne(projectId);
        source.setProject(project);
        redirectAttributes.addFlashAttribute("message", "Source added successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        sourceService.addNew(source);
    }

    public void editSource(BaseSource source, int projectId, RedirectAttributes redirectAttributes) {
        Project project = projectService.getOne(projectId);
        source.setProject(project);
        redirectAttributes.addFlashAttribute("message", "Source edited successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        sourceService.edit(source);
    }
}








