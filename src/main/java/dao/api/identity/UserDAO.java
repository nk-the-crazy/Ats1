package dao.api.identity;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.identity.User;


public interface UserDAO extends JpaRepository<User, Long>
{
    //********************************************
    User findById( long id );

    //********************************************
    User findByUserName( String userName );

    
    //********************************************
    @Query(value = "SELECT u.id, u.userName, u.lastLogin, u.status ,p.firstName, p.lastName "
            + " FROM User u LEFT JOIN u.person p "
            + " WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%',:userName, '%')) AND "
            + " LOWER(p.lastName) LIKE  LOWER(CONCAT('%',:lastName, '%')) "
            + " ORDER BY u.userName")
    Page<User> findByUserNameAndLastName(@Param("userName") String userName,
                                         @Param("lastName") String lastName , 
                                         Pageable page );

    //********************************************
    @Query(value = "SELECT u "
            + " FROM User u LEFT JOIN u.person p "
            + " WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%',:userName, '%')) AND "
            + " LOWER(p.lastName) LIKE  LOWER(CONCAT('%',:lastName, '%'))")
    List<User> findByUserNameAndLastName(@Param("userName") String userName,
                                         @Param("lastName") String lastName );

    
    //********************************************
    @Query(value = "SELECT u "
            + " FROM User u "
            + " LEFT JOIN FETCH u.person p "
            + " LEFT JOIN FETCH p.address a "
            + " LEFT JOIN FETCH p.detail d "
            + " LEFT JOIN FETCH p.contact c "
            + " LEFT JOIN FETCH p.organization o "
            + " WHERE u.id=:userId")
    User getFullDetails(@Param("userId") long userId );

    
    //********************************************
    @Query(value = "SELECT u "
            + " FROM User u JOIN FETCH u.person "
            + " WHERE u.id=:userId")
    User getDetails(@Param("userId") long userId );
    
    
    
    //********************************************
    @Query(value = "SELECT u.id, u.userName, p.firstName, p.lastName, g.id"
            + " FROM User u "
            + " LEFT JOIN u.groups g "
            + " LEFT JOIN u.person p "
            + " WHERE g.id=:groupId")
    Page<User> getByGroupId(@Param("groupId") long groupId , Pageable page );
    
    
    //********************************************
    @Query(value = "SELECT r.id, u.id, u.userName, p.firstName, p.lastName "
            + " FROM User u "
            + " JOIN u.person p "
            + " RIGHT JOIN u.roles r "
            + " WHERE r.id=:roleId "
            + " ORDER BY u.userName")
    Page<User> getByRoleId(@Param("roleId") long roleId, Pageable page );
 
  
}
