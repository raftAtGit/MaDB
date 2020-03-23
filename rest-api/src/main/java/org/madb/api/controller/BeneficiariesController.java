package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;
/**
 *
 * @author hirad.emamialagha
 */
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.BeneficiariesRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Beneficiaries;
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

public class BeneficiariesController {
    @Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BeneficiariesRepository beneficiariesRepository;

	@GetMapping("/Beneficiaries")
	List<Beneficiaries> all(@RequestParam Integer projectId) {
		return beneficiariesRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/Beneficiaries/{id}")
	Beneficiaries findById(@PathVariable Integer id) {
	    return  beneficiariesRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/Beneficiaries")
	Beneficiaries createBeneficiaries(@RequestParam Integer projectId, @RequestBody @Valid Beneficiaries newbeneficiary) {
        return projectRepository.findById(projectId).map(beneficiary -> {
        	newbeneficiary.setProject(beneficiary);
            return beneficiariesRepository.save(newbeneficiary);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@PutMapping("/Beneficiaries/{id}")
	   Beneficiaries updateBeneficiaries(@PathVariable Integer id, @RequestBody @Valid Beneficiaries newBeneficiaries) {

	    return beneficiariesRepository.findById(id)
	    	.map(beneficiary -> {
	    		copyBeneficiaries(newBeneficiaries, beneficiary);
	    		return beneficiariesRepository.save(beneficiary);
	    }).orElseThrow(() -> new NotFoundException(id));
	}
	
	@DeleteMapping("/Beneficiaries/{id}")
	void deleteBeneficiaries(@PathVariable Integer id) {
		beneficiariesRepository.deleteById(id);
	}
	
	private void copyBeneficiaries(Beneficiaries from, Beneficiaries to) {
		// never copy project ID
		to.setFinancial_year(from.getFinancial_year());
                to.setGender(from.getGender());
                to.setNumber_of_beneficiaries(from.getNumber_of_beneficiaries());
	}
}
