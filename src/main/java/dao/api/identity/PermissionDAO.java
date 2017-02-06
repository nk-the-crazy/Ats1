package dao.api.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import model.identity.Permission;

public interface PermissionDAO extends JpaRepository<Permission, Long>
{

}
