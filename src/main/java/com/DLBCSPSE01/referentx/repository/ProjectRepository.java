package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.entity.Project;
import com.DLBCSPSE01.referentx.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByOwner(Users user);

    List<Project> findByCollaborators(Users user);
}

