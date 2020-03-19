package org.madb.api.jpa;

import java.util.List;

import org.madb.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	boolean existsByProjectId(String projectId);
	
	List<Project> findByStatus(Project.Status status);
	
	
}
