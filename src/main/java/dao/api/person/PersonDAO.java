package dao.api.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.person.Person;

public interface PersonDAO  extends JpaRepository<Person, Long>
{
    //********************************************
    Person findById(long id);
    
    //********************************************
    @Query("SELECT MAX(p.id) FROM Person p")
    Integer getMaxId();
    
    
    //********************************************
    @Query(value = "SELECT p "
            + " FROM Person p "
            + " JOIN FETCH p.address a "
            + " JOIN FETCH p.contact c "
            + " JOIN FETCH p.organization o "
            + " WHERE p.id=:personId")
    Person getPersonFullDetails(@Param("personId") long personId );


    //********************************************
    @Query(value = "SELECT o.id, u.id, u.userName, p.id, p.firstName, p.lastName "
            + " FROM Organization o "
            + " JOIN o.personList p "
            + " JOIN p.users u "
            + " WHERE o.id=:organizationId")
    Page<Person> getByOrganizationId(@Param("organizationId") long organizationId, Pageable pageable );

}
