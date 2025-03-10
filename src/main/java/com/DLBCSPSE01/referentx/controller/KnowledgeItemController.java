package com.DLBCSPSE01.referentx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.DLBCSPSE01.referentx.model.*;
import com.DLBCSPSE01.referentx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class KnowledgeItemController {
    private final UsersService usersService;
    private final KnowledgeItemService knowledgeItemService;
    private final ProjectService projectService;
    private final SourceService sourceService;
    private final ChapterService chapterService;

    @Autowired
    public KnowledgeItemController(UsersService usersService, KnowledgeItemService knowledgeItemService, ProjectService projectService, SourceService sourceService, ChapterService chapterService) {
        this.usersService = usersService;
        this.knowledgeItemService = knowledgeItemService;
        this.projectService = projectService;
        this.sourceService = sourceService;
        this.chapterService = chapterService;
    }

    @GetMapping("/projects/{id}/knowledge-items")
    public String listKnowledgeItems(@PathVariable("id") int id, Model model) throws JsonProcessingException {

        Users currentUser = usersService.getCurrentUser();
        Project project = projectService.getOne(id);
        List<KnowledgeItem> knowledgeItems = knowledgeItemService.getByProject(project);

        List<Object> knowledgeItemParsed = knowledgeItems.stream()
                .map(item -> new Object() {
                    public Long knowledgeItemId = item.getKnowledgeItemId();
                    public String title = item.getTitle();
                    public String content = item.getContent();
                    public String source = item.getSource() != null ? item.getSource().getTitle() : "";
                    public String chapter = item.getChapter() != null ? item.getChapter().getName() : "";
                })
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String knowledgeItemsJson = objectMapper.writeValueAsString(knowledgeItemParsed);

        model.addAttribute("knowledgeItems", knowledgeItems);
        model.addAttribute("knowledgeItemsJson", knowledgeItemsJson);
        model.addAttribute("projectId", id);
        model.addAttribute("section", "knowledge-items");
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", currentUser);

        return "knowledge-items";
    }

    @GetMapping("/projects/{id}/knowledge-items/add")
    public String addKnowledgeItem(@PathVariable("id") int id, Model model) {
        Project project = projectService.getOne(id);
        List<BaseSource> projectSources = sourceService.getByProject(project);
        List<Chapter> projectChapters = chapterService.getByProject(project);

        model.addAttribute("projectSources", projectSources);
        model.addAttribute("projectChapters", projectChapters);

        model.addAttribute("knowledgeItem", new KnowledgeItem());
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Add new knowledge item");
        model.addAttribute("formAction", "add-new");

        return "knowledge-item-form";
    }

    @PostMapping("/projects/{id}/knowledge-items/add-new")
    public String addKnowledgeItemPost(@PathVariable("id") int id, @RequestParam("selectedSourceId") int sourceId, @RequestParam("selectedChapterId") int chapterId, KnowledgeItem knowledgeItem, RedirectAttributes redirectAttributes) {
        Project project = projectService.getOne(id);
        knowledgeItem.setProject(project);

        if (sourceId == 0) {
            knowledgeItem.setSource(null);
        } else {
            BaseSource source = sourceService.getOne(sourceId);
            knowledgeItem.setSource(source);
        }

        if (chapterId == 0) {
            knowledgeItem.setChapter(null);
        } else {
            Chapter chapter = chapterService.getOne(chapterId);
            knowledgeItem.setChapter(chapter);
        }

        knowledgeItemService.addNew(knowledgeItem);
        redirectAttributes.addFlashAttribute("message", "Knowledge Item added successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/projects/" + id + "/knowledge-items";
    }

    @GetMapping("projects/{id}/knowledge-items/{knowledgeItemId}/edit")
    public String editKnowledgeItem(@PathVariable("id") int id, @PathVariable("knowledgeItemId") int knowledgeItemId, Model model) {
        KnowledgeItem knowledgeItem = knowledgeItemService.getOne(knowledgeItemId);
        Project project = projectService.getOne(id);
        List<BaseSource> projectSources = sourceService.getByProject(project);
        List<Chapter> projectChapters = chapterService.getByProject(project);

        model.addAttribute("projectSources", projectSources);
        model.addAttribute("projectChapters", projectChapters);

        model.addAttribute("knowledgeItem", knowledgeItem);
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", project.getName());
        model.addAttribute("user", usersService.getCurrentUser());
        model.addAttribute("formTitle", "Add new knowledge item");
        model.addAttribute("formAction", "add-new");

        return "knowledge-item-form";
    }

    @PostMapping("/projects/{id}/knowledge-items/edit-knowledge-item")
    public String editKnowledgeItemPost(@PathVariable("id") int id, @RequestParam("selectedSourceId") int sourceId, @RequestParam("selectedChapterId") int chapterId, KnowledgeItem knowledgeItem, RedirectAttributes redirectAttributes) {
        Project project = projectService.getOne(id);
        knowledgeItem.setProject(project);

        if (sourceId == 0) {
            knowledgeItem.setSource(null);
        } else {
            BaseSource source = sourceService.getOne(sourceId);
            knowledgeItem.setSource(source);
        }

        if (chapterId == 0) {
            knowledgeItem.setChapter(null);
        } else {
            Chapter chapter = chapterService.getOne(sourceId);
            knowledgeItem.setChapter(chapter);
        }

        redirectAttributes.addFlashAttribute("message", "Knowledge Item edited successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");

        knowledgeItemService.addNew(knowledgeItem);
        return "redirect:/projects/" + id + "/knowledge-items";
    }

    @DeleteMapping("/projects/{id}/knowledge-items/{knowledgeItemId}/delete")
    public String deleteKnowledgeItem(@PathVariable("id") int id, @PathVariable("knowledgeItemId") int knowledgeItemId, RedirectAttributes redirectAttributes) {
        KnowledgeItem knowledgeItem = knowledgeItemService.getOne(knowledgeItemId);
        knowledgeItemService.delete(knowledgeItem);
        redirectAttributes.addFlashAttribute("message", "Knowledge Item deleted successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/projects/" + id + "/knowledge-items";
    }
}









