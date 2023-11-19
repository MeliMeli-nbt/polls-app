package utc.cntt2.k61.pollsappserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc.cntt2.k61.pollsappserver.domain.Role;
import utc.cntt2.k61.pollsappserver.domain.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
