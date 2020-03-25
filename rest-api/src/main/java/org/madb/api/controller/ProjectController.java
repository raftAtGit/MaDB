package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.madb.api.controller.exception.BadRequestException;
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated
class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/projects")
	List<Project> all() {
		return projectRepository.findAll();
	}
	
	@GetMapping("/projects/{id}")
	Project findById(@PathVariable Integer id) {
	    return projectRepository.findById(id)
	    .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/projects")
	Project createProject(@RequestBody @Valid Project newProject) {
		if (projectRepository.existsByProjectId(newProject.getProjectId()))
			throw new BadRequestException("projectId already exists: " + newProject.getProjectId());
		return projectRepository.save(newProject);
	}
	
	@PutMapping("/projects/{id}")
	Project updateProject(@PathVariable Integer id, @RequestBody @Valid Project newProject) {

	    return projectRepository.findById(id)
	    	.map(project -> {
	    		copyProject(newProject, project);
	    		return projectRepository.save(project);
	    	})
	    .orElseThrow(() -> new NotFoundException(id));
	}
	
	private void copyProject(Project from, Project to) {
		// skip project.status
		to.setProjectId(from.getProjectId());
		to.setName(from.getName());
		to.setCountry(from.getCountry());
		to.setStartDate(from.getStartDate());
		to.setEndDate(from.getEndDate());
		to.setBudget(from.getBudget());
		to.setDescription(from.getDescription());
		to.setObservations(from.getObservations());
	}

}
