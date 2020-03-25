package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.madb.api.controller.exception.BadRequestException;
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.CountryRepository;
import org.madb.api.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Validated
class CountryController {

	@Autowired
	private CountryRepository countryRepository;

	@GetMapping("/countries")
	List<Country> all() {
		return countryRepository.findAll();
	}
	
	@GetMapping("/countries/{id}")
	Country findById(@PathVariable Integer id) {
	    return countryRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/countries")
	Country createCountry(@RequestBody @Valid Country newCountry) {
		if (countryRepository.existsByName(newCountry.getName()))
			throw new BadRequestException("country name already exists: " + newCountry.getName());
		
        return countryRepository.save(newCountry);
	}
	
}
