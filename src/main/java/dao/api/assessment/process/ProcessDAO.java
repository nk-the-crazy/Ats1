package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.assessment.process.Process;


public interface ProcessDAO extends JpaRepository<Process, Long>
{

    // ********************************************
    @Query(value = " SELECT p " + 
                   " FROM Process p " + 
                   " JOIN FETCH p.responses r "+
                   " WHERE p.id = :processId ")
    Process getById( @Param("processId") long processId );

    // ********************************************
    @Query(value = " SELECT p " + 
                   " FROM Process p " + 
                   " JOIN FETCH p.assessment a "

                 + " WHERE a.id = :assessmentId ")
    Process getByAssessmentId( @Param("assessmentId") long assessmentId );

}
