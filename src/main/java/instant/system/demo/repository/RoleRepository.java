package instant.system.demo.repository;

import instant.system.demo.model.Role;
import instant.system.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String n);
}
