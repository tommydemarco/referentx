package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.model.BaseSource;
import com.DLBCSPSE01.referentx.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRepository extends JpaRepository<BaseSource, Integer> {
    List<BaseSource> findByProject(Project project);
}