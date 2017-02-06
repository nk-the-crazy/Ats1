package dao.api.group;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.group.UserGroup;


public interface GroupDAO extends JpaRepository<UserGroup, Long>
{

    //********************************************
    @Query(value = " SELECT g.id,g.name,g.status,g.type,g.created,g.details,count(u.id) "
                 + " FROM UserGroup g LEFT JOIN g.users u"
                 + " GROUP BY g.id,g.name,g.type,g.details "
                 + " HAVING LOWER(g.name) LIKE LOWER(CONCAT('%',:groupName, '%'))")
    Page<UserGroup> findByName(@Param("groupName") String groupName, Pageable pageable );
    

    //********************************************
    @Query(value = "SELECT g "
            + " FROM UserGroup g "
            + " WHERE LOWER(g.name) LIKE LOWER(CONCAT('%',:groupName, '%'))")
    List<UserGroup> findByName(@Param("groupName") String groupName);

    
    //********************************************
    @Query(value = "SELECT g "
            + " FROM UserGroup g "
            + " LEFT JOIN FETCH g.users u"
            + " LEFT JOIN FETCH u.person p"
            + " WHERE g.id=:groupId ")
    UserGroup getFullDetails(@Param("groupId") long groupId);
    
    
    //********************************************
    @Query(value = "SELECT u.id, g.id, g.name, g.details "
            + " FROM UserGroup g "
            + " JOIN g.users u "
            + " WHERE u.id=:userId")
    Page<UserGroup> getByUserId(@Param("userId") long userId , Pageable page );


}
