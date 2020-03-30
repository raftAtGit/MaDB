package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;

/**
 *
 * @author hirad.emamialagha
 */
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.CountryRepository;
import org.madb.api.jpa.ProjectCountryRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Country;
import org.madb.api.model.ProjectCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated

public class ProjectCountryController {
	
    @Autowired
    private ProjectCountryRepository projectCountryRepository;
    
    @Autowired
	private ProjectRepository projectRepository;

    @Autowired
	private CountryRepository countryRepository;

	@GetMapping("/project_countries")
	List<ProjectCountry> all(@RequestParam Integer projectId) {
		return projectCountryRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/project_countries/{id}")
	ProjectCountry findById(@PathVariable Integer id) {
	    return projectCountryRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/project_countries")
	ProjectCountry createProjectCountry(@RequestParam Integer projectId, @RequestBody @Valid ProjectCountry newProjectCountry) {
		Country country = countryRepository.findById(newProjectCountry.getCountry().getId())
				.orElseThrow(() -> new NotFoundException("country not found, id: " + newProjectCountry.getCountry().getId()));
		newProjectCountry.setCountry(country);
		
		return projectRepository.findById(projectId).map(project -> {
        	newProjectCountry.setProject(project);
            return projectCountryRepository.save(newProjectCountry);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@DeleteMapping("/project_countries/{id}")
	Response deleteProjectCountry(@PathVariable Integer id) {
		if (!projectCountryRepository.existsById(id))
			throw new NotFoundException(id);
		
		projectCountryRepository.deleteById(id);
		return Response.OK;
	}
	
}
