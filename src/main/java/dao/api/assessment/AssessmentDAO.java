package dao.api.assessment;

import java.util.Date;

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
    @Query(value = "SELECT a "
            + " FROM Assessment a "
            + " JOIN a.participants p "
            + " WHERE p.id in "
            + " ( select g.id ) "
            
            + " WHERE a."
            + " ( a.startDate>=:startDateFrom ) AND"
            + " ( a.type=:assessmentType OR 0=:assessmentType ) "
            
            + " ORDER BY a.startDate DESC " )
    Page<Assessment> getByUserId( @Param("assessmentName") String assessmentName, 
                                   @Param("startDateFrom") Date startDateFrom, 
                                   @Param("assessmentType") short assessmentType,
                                   Pageable pageable );

    //********************************************
    @Query(value = "SELECT a "
            + " FROM Assessment a "
            + " LEFT JOIN FETCH a.details ad "
            + " LEFT JOIN FETCH a.author  at "
            + " LEFT JOIN FETCH a.managers m "
            + " LEFT JOIN FETCH a.participants p "

            + " WHERE a.id=:assessmentId " )
    Assessment getFullDetails( @Param("assessmentId") long assessmentId );

}
