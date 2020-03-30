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
import org.madb.api.model.Theme;
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

public class ThemeController {
    @Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ThemeRepository themeRepository;

	@GetMapping("/themes")
	List<Theme> all(@RequestParam Integer projectId) {
		return themeRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/themes/{id}")
	Theme findById(@PathVariable Integer id) {
	    return  themeRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/themes")
	Theme createThemes(@RequestParam Integer projectId, @RequestBody @Valid Theme newtheme) {
        return projectRepository.findById(projectId).map(theme -> {
        	newtheme.setProject(theme);
            return themeRepository.save(newtheme);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@PutMapping("/themes/{id}")
	   Theme updateThemes(@PathVariable Integer id, @RequestBody @Valid Theme newTheme) {

	    return themeRepository.findById(id)
	    	.map(theme -> {
	    		copyThemes(newTheme, theme);
	    		return themeRepository.save(theme);
	    }).orElseThrow(() -> new NotFoundException(id));
	}
	
	@DeleteMapping("/themes/{id}")
	Response deleteThemes(@PathVariable Integer id) {
		if (!themeRepository.existsById(id))
			throw new NotFoundException(id);
		
		themeRepository.deleteById(id);
		return Response.OK;
	}
	
	private void copyThemes(Theme from, Theme to) {
		// never copy project ID
		to.setTheme(from.getTheme());
	}
}
