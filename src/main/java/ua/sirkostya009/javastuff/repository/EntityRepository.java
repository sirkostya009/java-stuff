package ua.sirkostya009.javastuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sirkostya009.javastuff.model.NamedEntity;

public interface EntityRepository extends JpaRepository<NamedEntity, Long> {
}
