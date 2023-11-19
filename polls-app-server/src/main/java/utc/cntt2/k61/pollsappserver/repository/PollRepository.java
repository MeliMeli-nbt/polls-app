package utc.cntt2.k61.pollsappserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc.cntt2.k61.pollsappserver.domain.Poll;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    long countByCreatedBy(Long id);

    Page<Poll> findByCreatedBy(Long id, Pageable pageable);

    List<Poll> findByIdIn(List<Long> pollIds, Sort sort);
}
