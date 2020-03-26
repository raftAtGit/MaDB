package org.madb.api.jpa;

import java.util.List;

import org.madb.api.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
	
	List<Beneficiary> findByProjectId(Integer projectId);

}
