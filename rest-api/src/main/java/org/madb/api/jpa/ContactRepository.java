package org.madb.api.jpa;

import java.util.List;

import org.madb.api.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByProjectId(Long projectId);

}
