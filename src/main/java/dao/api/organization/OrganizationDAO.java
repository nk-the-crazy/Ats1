package dao.api.organization;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.organization.Organization;


public interface OrganizationDAO extends JpaRepository<Organization, Long>
{
    Organization findById(long id);
    
    @Query("SELECT MAX(o.id) FROM Organization o")
    Integer getMaxId();
    
    //********************************************
    @Query(value = " SELECT ogz "
                 + " FROM Organization ogz "
                 + " LEFT JOIN ogz.details ogzd "
                 + " WHERE LOWER(ogz.name) LIKE LOWER(CONCAT('%',:organizationName, '%'))"
                 + " ORDER BY ogz.name ")
    Page<Organization> findByName(@Param("organizationName") String organizationName, Pageable pageable );
    

    //********************************************
    @Query(value = "SELECT ogz "
            + " FROM Organization ogz "
            + " WHERE LOWER(ogz.name) LIKE LOWER(CONCAT('%',:organizationName, '%'))")
    List<Organization> findByName(@Param("organizationName") String organizationName);
    
    
    //********************************************
    @Query(value = "SELECT ogz "
            + " FROM Organization ogz "
            + " LEFT JOIN FETCH ogz.address a "
            + " LEFT JOIN FETCH ogz.details d "
            + " LEFT JOIN FETCH ogz.contact c "
            + " WHERE ogz.id=:organizationId ")
    Organization getFullDetails(@Param("organizationId") long organizationId);

}
