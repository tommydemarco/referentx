package com.DLBCSPSE01.referentx.service;

import com.DLBCSPSE01.referentx.entity.*;
import com.DLBCSPSE01.referentx.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public Chapter addNew(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public Chapter edit(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public List<Chapter> getByProject(Project project) {
        return chapterRepository.findByProject(project);
    }

    public Chapter getOne(int id) {
        return chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Chapter not found"));
    }

    public void delete(Chapter chapter) {
        chapterRepository.delete(chapter);
    }
}

