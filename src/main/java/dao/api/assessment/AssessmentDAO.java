package dao.api.assessment;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.assessment.Assessment;


public interface AssessmentDAO extends JpaRepository<Assessment, Long>
{

    //********************************************
    @Query(value = "SELECT a "
            + " FROM Assessment a "
            
            + " WHERE LOWER(a.name) LIKE LOWER(CONCAT('%',:assessmentName, '%')) AND "
            + " ( a.startDate>=:startDateFrom AND a.startDate<=:startDateTo ) AND"
            + " ( a.type=:assessmentType OR 0=:assessmentType ) "
            
            + " ORDER BY a.startDate DESC " )
    Page<Assessment> getByDetails( @Param("assessmentName") String assessmentName, 
                                   @Param("startDateFrom") Date startDateFrom, 
                                   @Param("startDateTo") Date startDateTo, 
                                   @Param("assessmentType") short assessmentType,
                                   Pageable pageable );
    
    
    // ********************************************
    @Query(value = " SELECT  DISTINCT usr, prn, a "
                 +  " FROM Assessment a "  
                 +  " JOIN a.participants p "
                 +  " JOIN p.users usr "
                 +  " JOIN usr.person prn "
                 +  " WHERE a.id = :assessmentId "
                 +  " ORDER BY prn.lastName " )
    List<Object> getUserDetails( @Param("assessmentId") long assessmentId );

    //********************************************
    @Query(value = "SELECT a "
            + " FROM Assessment a "
            
            + " WHERE LOWER(a.name) LIKE LOWER(CONCAT('%',:assessmentName, '%')) AND "
            + " ( a.startDate>=:startDateFrom ) AND"
            + " ( a.type=:assessmentType OR 0=:assessmentType ) "
            
            + " ORDER BY a.startDate DESC " )
    Page<Assessment> getByDetails( @Param("assessmentName") String assessmentName, 
                                   @Param("startDateFrom") Date startDateFrom, 
                                   @Param("assessmentType") short assessmentType,
                                   Pageable pageable );

    
    //********************************************
    @Query(value = "SELECT DISTINCT a "
            + " FROM Assessment a "
            + " JOIN a.participants p "
            + " WHERE p.id in "
            + " ( SELECT g.id FROM UserGroup g JOIN g.users u "
            + "   WHERE u.id = :userId ) "
            + " ORDER BY a.startDate DESC " )
    Page<Assessment> getByUserId( @Param("userId") long userId, Pageable pageable );

    
    //********************************************
    @Query(value = "SELECT DISTINCT a , ps "
            + " FROM Assessment a "
            + " LEFT JOIN a.processes ps WITH ps.user.id=:userId "
            + " JOIN a.participants p "
            + " WHERE p.id in "
            + " ( SELECT g.id FROM UserGroup g JOIN g.users u "
            + "   WHERE u.id = :userId) "
            + " ORDER BY a.startDate DESC " )
    Page<Assessment> getAssignedByUserId( @Param("userId") long userId, Pageable pageable );

    
    //********************************************
    @Query(value = "SELECT a "
            + " FROM Assessment a "
            + " LEFT JOIN FETCH a.author  at "
            + " LEFT JOIN FETCH a.participants p "

            + " WHERE a.id=:assessmentId " )
    Assessment getFullDetails( @Param("assessmentId") long assessmentId );

    
     //********************************************
    @Query(value = "SELECT DISTINCT a "
            + " FROM Assessment a "
            + " JOIN a.participants p "
            + " JOIN FETCH a.tasks t "
            + " JOIN FETCH t.details o "
            
            + " WHERE p.id in "
            + " ( SELECT g.id FROM UserGroup g JOIN g.users u "
            + "   WHERE u.id = :userId ) AND "
            + " a.id=:assessmentId " )
    Assessment getByIdAndUserId( @Param("assessmentId") long assessmentId , @Param("userId") long userId);


}
