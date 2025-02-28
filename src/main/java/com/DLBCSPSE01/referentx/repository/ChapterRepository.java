package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.model.Project;
import com.DLBCSPSE01.referentx.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    List<Chapter> findByProject(Project project);
}

