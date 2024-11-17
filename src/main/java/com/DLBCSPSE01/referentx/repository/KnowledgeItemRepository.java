package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.entity.KnowledgeItem;
import com.DLBCSPSE01.referentx.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeItemRepository extends JpaRepository<KnowledgeItem, Integer> {

    List<KnowledgeItem> findByProject(Project project);
}
