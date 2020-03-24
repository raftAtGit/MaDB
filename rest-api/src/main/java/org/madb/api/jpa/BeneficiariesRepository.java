package org.madb.api.jpa;

import java.util.List;

import org.madb.api.model.Beneficiaries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiariesRepository extends JpaRepository<Beneficiaries, Integer> {
	
	List<Beneficiaries> findByProjectId(Integer projectId);

}
