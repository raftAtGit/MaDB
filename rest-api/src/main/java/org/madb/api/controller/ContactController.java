package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.ContactRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Contact;
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
@RequestMapping("/api")
@Validated
class ContactController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ContactRepository contactRepository;

	@GetMapping("/contacts")
	List<Contact> all(@RequestParam Integer projectId) {
		return contactRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/contacts/{id}")
	Contact findById(@PathVariable Integer id) {
	    return contactRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/contacts")
	Contact createContact(@RequestParam Integer projectId, @RequestBody @Valid Contact newContact) {
        return projectRepository.findById(projectId).map(project -> {
        	newContact.setProject(project);
            return contactRepository.save(newContact);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@PutMapping("/contacts/{id}")
	Contact updateContact(@PathVariable Integer id, @RequestBody @Valid Contact newContact) {

	    return contactRepository.findById(id)
	    	.map(contact -> {
	    		copyContact(newContact, contact);
	    		return contactRepository.save(contact);
	    }).orElseThrow(() -> new NotFoundException(id));
	}
	
	@DeleteMapping("/contacts/{id}")
	void deleteContact(@PathVariable Integer id) {
		contactRepository.deleteById(id);
	}
	
	private void copyContact(Contact from, Contact to) {
		// never copy project ID
		to.setType(from.getType());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setFunctions(from.getFunctions());
	}

}
