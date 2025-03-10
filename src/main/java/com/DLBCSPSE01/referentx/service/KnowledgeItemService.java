package com.DLBCSPSE01.referentx.service;

import com.DLBCSPSE01.referentx.model.*;
import com.DLBCSPSE01.referentx.repository.KnowledgeItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeItemService {

    private final KnowledgeItemRepository knowledgeItemRepository;

    public KnowledgeItemService(KnowledgeItemRepository knowledgeItemRepository) {
        this.knowledgeItemRepository = knowledgeItemRepository;
    }

    public KnowledgeItem addNew(KnowledgeItem knowledgeItem) {
        return knowledgeItemRepository.save(knowledgeItem);
    }

    public KnowledgeItem edit(KnowledgeItem knowledgeItem) {
        return knowledgeItemRepository.save(knowledgeItem);
    }

    public List<KnowledgeItem> getByProject(Project project) {
        return knowledgeItemRepository.findByProject(project);
    }

    public KnowledgeItem getOne(int id) {
        return knowledgeItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Knowledge Item not found"));
    }

    public void delete(KnowledgeItem knowledgeItem) {
        knowledgeItemRepository.delete(knowledgeItem);
    }
}


