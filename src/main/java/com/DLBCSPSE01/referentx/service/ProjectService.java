package com.DLBCSPSE01.referentx.service;

import com.DLBCSPSE01.referentx.entity.*;
import com.DLBCSPSE01.referentx.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addNew(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getProjectsByOwner(Users user) {

        List<Project> projectsByOwner = projectRepository.findByProjectOwner(user);
        return projectsByOwner;
    }

    public Project getOne(int id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }
}

