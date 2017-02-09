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
            + " ( a.startDate<=:startDate AND a.startDate<=:endDate ) AND"
            + " ( a.type=:assessmentType OR 0=:assessmentType ) " )
    Page<Assessment> getByDetails( @Param("assessmentName") String assessmentName, 
                                   @Param("startDate") Date startDate, 
                                   @Param("endDate") Date endDate, 
                                   @Param("assessmentType") short assessmentType,
                                   Pageable pageable );

}
