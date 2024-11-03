package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.entity.Project;
import com.DLBCSPSE01.referentx.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    List<Chapter> findByProject(Project project);
}

