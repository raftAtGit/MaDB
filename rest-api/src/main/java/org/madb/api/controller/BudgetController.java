package org.madb.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.madb.api.controller.exception.NotFoundException;
import org.madb.api.jpa.BudgetRepository;
import org.madb.api.jpa.ProjectRepository;
import org.madb.api.model.Budget;
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
class BudgetController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BudgetRepository budgetRepository;

	@GetMapping("/budgets")
	List<Budget> all(@RequestParam Long projectId) {
		return budgetRepository.findByProjectId(projectId);
	}
	
	@GetMapping("/budgets/{id}")
	Budget findById(@PathVariable Long id) {
	    return budgetRepository.findById(id)
	      .orElseThrow(() -> new NotFoundException(id));
	}
	
	@PostMapping("/budgets")
	Budget createBudget(@RequestParam Long projectId, @RequestBody @Valid Budget newBudget) {
        return projectRepository.findById(projectId).map(project -> {
        	newBudget.setProject(project);
            return budgetRepository.save(newBudget);
        }).orElseThrow(() -> new NotFoundException("project not found, id: " + projectId));
	}
	
	@PutMapping("/budgets/{id}")
	Budget updateBudget(@PathVariable Long id, @RequestBody @Valid Budget newBudget) {

	    return budgetRepository.findById(id)
	    	.map(budget -> {
	    		copyBudget(newBudget, budget);
	    		return budgetRepository.save(budget);
	    }).orElseThrow(() -> new NotFoundException(id));
	}
	
	@DeleteMapping("/budgets/{id}")
	void deleteBudget(@PathVariable Long id) {
	    budgetRepository.deleteById(id);
	}
	
	
	private void copyBudget(Budget from, Budget to) {
		// never copy project ID
		to.setBudget(from.getBudget());
		to.setFinancialYear(from.getFinancialYear());
	}

}
