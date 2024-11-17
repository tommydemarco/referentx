package com.DLBCSPSE01.referentx.controller;

import com.DLBCSPSE01.referentx.entity.*;
import com.DLBCSPSE01.referentx.service.ChapterService;
import com.DLBCSPSE01.referentx.service.ProjectService;
import com.DLBCSPSE01.referentx.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChapterController {

    private final UsersService usersService;
    private final ChapterService chapterService;
    private final ProjectService projectService;

    @Autowired
    public ChapterController(UsersService usersService, ChapterService chapterService, ProjectService projectService) {
        this.usersService = usersService;
        this.chapterService = chapterService;
        this.projectService = projectService;
    }

    @GetMapping("/projects/{id}/chapters")
    public String chaptersPage(@PathVariable("id") int id, Model model) {

        Users currentUser = usersService.getCurrentUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Project project = projectService.getOne(id);

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Chapter> chapters = chapterService.getChaptersByProject(project);
            model.addAttribute("chapters", chapters);
        }

        model.addAttribute("projectId", id);
        model.addAttribute("section", "chapters");
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", currentUser);
        return "chapters";
    }

    @GetMapping("/projects/{id}/chapters/add")
    public String addChapter(@PathVariable("id") int id, Model model) {
        Project project = projectService.getOne(id);

        model.addAttribute("projectName", project.getName());
        model.addAttribute("chapter", new Chapter());
        model.addAttribute("projectId", id);
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Add new chapter");
        model.addAttribute("formAction", "add-new");
        return "chapter-form";
    }

    @PostMapping("/projects/{id}/chapters/add-new")
    public String addChapterPost(@PathVariable("id") int id, Chapter chapter, Model model) {
        Project project = projectService.getOne(id);
        chapter.setProject(project);
        model.addAttribute("chapter", chapter);
        Chapter saved = chapterService.addNew(chapter);

        return "redirect:/projects/" + id + "/chapters";
    }

    @GetMapping("projects/{id}/chapters/{chapterId}/edit")
    public String editChapter(@PathVariable("id") int id, @PathVariable("chapterId") int chapterId, Model model) {
        Project project = projectService.getOne(id);
        Chapter chapter = chapterService.getOne(chapterId);

        model.addAttribute("projectName", project.getName());
        model.addAttribute("chapter", chapter);
        model.addAttribute("projectId", id);
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Edit chapter");
        model.addAttribute("formAction", "edit-chapter");
        return "chapter-form";
    }

    @PostMapping("/projects/{id}/chapters/edit-chapter")
    public String editChapterPost(@PathVariable("id") int id, Chapter chapter, Model model) {

        Project project = projectService.getOne(id);
        chapter.setProject(project);

        model.addAttribute("chapter", chapter);
        Chapter saved = chapterService.addNew(chapter);

        return "redirect:/projects/" + id + "/chapters";
    }

    @DeleteMapping("/projects/{id}/chapters/{chapterId}/delete")
    public String deleteChapter(@PathVariable("id") int id, Chapter chapter, Model model) {

        chapterService.deleteChapter(chapter);
        return "redirect:/projects/" + id + "/chapters";
    }
}








