package org.madb.api.jpa;

import org.madb.api.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	// TODO make case insensitive 
	boolean existsByName(String name);
	
}
