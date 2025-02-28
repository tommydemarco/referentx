package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.model.Project;
import com.DLBCSPSE01.referentx.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProject(Project project);
}

