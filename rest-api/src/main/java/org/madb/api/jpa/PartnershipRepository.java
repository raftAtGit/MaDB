package org.madb.api.jpa;
/**
 * @author hirad.emamialagha
 */
import java.util.List;
import org.madb.api.model.Partnerships;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnershipRepository extends JpaRepository<Partnerships, Integer> {
    List<Partnerships> findByProjectId(Integer projectId);
}
