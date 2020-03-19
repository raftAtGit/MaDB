package org.madb.api.controller;

import java.util.List;

import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class AdminController {

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/admin/pending_projects")
	List<Project> allPending() {
		return projectRepository.findByStatus(Project.Status.PENDING);
	}

	
	static class Response {
		
	}
}
