package utc.cntt2.k61.pollsappserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc.cntt2.k61.pollsappserver.domain.Choice;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
