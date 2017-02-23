package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.assessment.process.AssessmentProcess;


public interface ProcessDAO extends JpaRepository<AssessmentProcess, Long>
{

    // ********************************************
    @Query(value = " SELECT p " + 
                   " FROM AssessmentProcess p " + 
                   " JOIN FETCH p.responses r "+
                   " WHERE p.id = :processId ")
    AssessmentProcess getById( @Param("processId") long processId );

    // ********************************************
    @Query(value = " SELECT p " + 
                   " FROM AssessmentProcess p " + 
                   " JOIN FETCH p.assessment a "

                 + " WHERE a.id = :assessmentId "
                 + " AND p.user.id = :userId ")
    AssessmentProcess getByAssessmentAndUserId( @Param("assessmentId") long assessmentId,
                                                @Param("userId") long userId );
    
   
}
