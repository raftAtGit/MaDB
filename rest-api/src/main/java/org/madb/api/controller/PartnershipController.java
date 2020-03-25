package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;
/**
 *
 * @author hirad.emamialagha
 */
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.PartnershipRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Partnerships;
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

public class PartnershipController {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PartnershipRepository partnershipRepository;

    @GetMapping("/partnerships")
    List<Partnerships> all(@RequestParam Integer projectId) {
            return partnershipRepository.findByProjectId(projectId);
    }

    @GetMapping("/partnerships/{id}")
    Partnerships findById(@PathVariable Integer id) {
        return  partnershipRepository.findById(id)
          .orElseThrow(() -> new NotFoundException(id));
    }

    @PostMapping("/partnerships")
    Partnerships createPartnerships(@RequestParam Integer projectId, @RequestBody @Valid Partnerships newPartnerships) {
    return projectRepository.findById(projectId).map(argPartnerships -> {
            newPartnerships.setProject(argPartnerships);
        return partnershipRepository.save(newPartnerships);
    }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
    }

    @PutMapping("/partnerships/{id}")
       Partnerships updatePartnerships(@PathVariable Integer id, @RequestBody @Valid Partnerships newPartnerships) {

        return partnershipRepository.findById(id)
            .map(argPartnerships -> {
                    copyPartnerships(newPartnerships, argPartnerships);
                    return partnershipRepository.save(argPartnerships);
        }).orElseThrow(() -> new NotFoundException(id));
    }

    @DeleteMapping("/partnerships/{id}")
    void deletePartnerships(@PathVariable Integer id) {
            partnershipRepository.deleteById(id);
    }

    private void copyPartnerships(Partnerships from, Partnerships to) {
            // never copy project ID
            to.setPartner(from.getPartner());
            to.setPartnership_type(from.getPartnership_type());
    }
}
