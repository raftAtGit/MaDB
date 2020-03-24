package org.madb.api.jpa;
/**
 * @author hirad.emamialagha
 */
import java.util.List;
import org.madb.api.model.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Integer> {
    List<Funding> findByProjectId(Integer projectId);
}
