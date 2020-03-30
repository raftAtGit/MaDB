package org.madb.api.jpa;

import java.util.List;

import org.madb.api.model.ProjectCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCountryRepository extends JpaRepository<ProjectCountry, Integer> {
    List<ProjectCountry> findByProjectId(Integer projectId);
}
