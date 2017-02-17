package dao.api.identity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.identity.Role;

public interface RoleDAO extends JpaRepository<Role, Long>
{
    //********************************************
    @Query(value = "SELECT r "
            + " FROM Role r "
            + " WHERE LOWER(r.name) LIKE LOWER(CONCAT('%',:roleName, '%')) "
            + " ORDER BY r.id")
    Page<Role> findByRoleName(@Param("roleName") String roleName, Pageable page );
    
    
    @Query(value = "SELECT r.id, r.name "
            + " FROM Role r "
            + " WHERE LOWER(r.name) LIKE LOWER(CONCAT('%',:roleName, '%')) "
            + " ORDER BY r.id")
    List<Role> getShortListByRoleName(@Param("roleName") String roleName );
    
}
