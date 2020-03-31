package org.madb.api.controller;

import java.util.List;

import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Project;
import org.madb.api.model.Project.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated
class AdminController {

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/admin/all_projects")
	List<Project> all() {
		return projectRepository.findAll();
	}
	
	@GetMapping("/admin/pending_projects")
	List<Project> allPending() {
		return projectRepository.findByStatus(Project.Status.PENDING);
	}

	@PutMapping("/admin/approve_project/{id}")
	Response approveProject(@PathVariable Integer id) {
		
	    return projectRepository.findById(id)
	    	.map(project -> {
				project.setStatus(Status.APPROVED);
				projectRepository.save(project);
	    		return new Response("OK");
	    	})
	    .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PutMapping("/admin/reject_project/{id}")
	Response rejectProject(@PathVariable Integer id) {
		
	    return projectRepository.findById(id)
	    	.map(project -> {
				project.setStatus(Status.REJECTED);
				projectRepository.save(project);
	    		return Response.OK;
	    	})
	    .orElseThrow(() -> new NotFoundException(id));
	}
}
