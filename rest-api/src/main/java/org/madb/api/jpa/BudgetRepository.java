package org.madb.api.jpa;

import java.util.List;

import org.madb.api.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
	
	List<Budget> findByProjectId(Long projectId);

}
