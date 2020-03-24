package org.madb.api.jpa;
/**
 * @author hirad.emamialagha
 */
import java.util.List;
import org.madb.api.model.Themes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Themes, Integer> {
    List<Themes> findByProjectId(Integer projectId);
}
