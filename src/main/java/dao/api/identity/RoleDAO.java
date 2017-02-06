package dao.api.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import model.identity.Role;

public interface RoleDAO extends JpaRepository<Role, Long>
{

}
