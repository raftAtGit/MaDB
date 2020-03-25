package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;
/**
 *
 * @author hirad.emamialagha
 */
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.FundingRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Funding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated

public class FundingController {
    @Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private FundingRepository fundingRepository;

	@GetMapping("/funding")
	List<Funding> all(@RequestParam Integer projectId) {
		return fundingRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/funding/{id}")
	Funding findById(@PathVariable Integer id) {
	    return fundingRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/funding")
	Funding createFunding(@RequestParam Integer projectId, @RequestBody @Valid Funding newFunding) {
        return projectRepository.findById(projectId).map(project -> {
        	newFunding.setProject(project);
            return fundingRepository.save(newFunding);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@PutMapping("/funding/{id}")
	   Funding updateFunding(@PathVariable Integer id, @RequestBody @Valid Funding newFunding) {

	    return fundingRepository.findById(id)
	    	.map(funding -> {
	    		copyFunding(newFunding, funding);
	    		return fundingRepository.save(funding);
	    }).orElseThrow(() -> new NotFoundException(id));
	}
	
	@DeleteMapping("/funding/{id}")
	void deleteFunding(@PathVariable Integer id) {
		fundingRepository.deleteById(id);
	}
	
	private void copyFunding(Funding from, Funding to) {
		// never copy project ID
		to.setFounding_source(from.getFounding_source());
                to.setUser(from.getUser());
	}
}
