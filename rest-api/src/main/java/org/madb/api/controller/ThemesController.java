package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;
/**
 *
 * @author hirad.emamialagha
 */
import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.ThemeRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Themes;
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

public class ThemesController {
    @Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ThemeRepository themeRepository;

	@GetMapping("/Themes")
	List<Themes> all(@RequestParam Integer projectId) {
		return themeRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/Themes/{id}")
	Themes findById(@PathVariable Integer id) {
	    return  themeRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/Themes")
	Themes createThemes(@RequestParam Integer projectId, @RequestBody @Valid Themes newtheme) {
        return projectRepository.findById(projectId).map(theme -> {
        	newtheme.setProject(theme);
            return themeRepository.save(newtheme);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@PutMapping("/Themes/{id}")
	   Themes updateThemes(@PathVariable Integer id, @RequestBody @Valid Themes newTheme) {

	    return themeRepository.findById(id)
	    	.map(theme -> {
	    		copyThemes(newTheme, theme);
	    		return themeRepository.save(theme);
	    }).orElseThrow(() -> new NotFoundException(id));
	}
	
	@DeleteMapping("/Themes/{id}")
	void deleteThemes(@PathVariable Integer id) {
		themeRepository.deleteById(id);
	}
	
	private void copyThemes(Themes from, Themes to) {
		// never copy project ID
		to.setTheme(from.getTheme());
	}
}
