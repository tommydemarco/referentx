package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.model.Project;
import com.DLBCSPSE01.referentx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByOwner(Users user);

    List<Project> findByCollaborators(Users user);
}

