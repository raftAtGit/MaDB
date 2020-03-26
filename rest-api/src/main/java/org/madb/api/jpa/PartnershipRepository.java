package org.madb.api.jpa;
/**
 * @author hirad.emamialagha
 */
import java.util.List;
import org.madb.api.model.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnershipRepository extends JpaRepository<Partnership, Integer> {
    List<Partnership> findByProjectId(Integer projectId);
}
