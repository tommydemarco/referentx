package com.DLBCSPSE01.referentx.service;

import com.DLBCSPSE01.referentx.model.*;
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

    public Project edit(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getByOwner(Users user) {
        return projectRepository.findByOwner(user);
    }

    public List<Project> getByCollaborator(Users user) {
        return projectRepository.findByCollaborators(user);
    }

    public Project getOne(int id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }
}

