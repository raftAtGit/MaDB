package org.madb.api.jpa;
/**
 * @author hirad.emamialagha
 */
import java.util.List;
import org.madb.api.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    List<Theme> findByProjectId(Integer projectId);
}
