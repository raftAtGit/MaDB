package org.madb.api.jpa;

import java.util.List;
import java.util.Optional;

import org.madb.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	boolean existsByProjectId(String projectId);
	
	Optional<Project> findByProjectId(String projectId);
	
	List<Project> findByStatus(Project.Status status);
	
	List<Project> findByStatusNot(Project.Status status);
	
}
