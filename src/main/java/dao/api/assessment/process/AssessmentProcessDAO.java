package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.assessment.process.AssessmentProcess;


public interface AssessmentProcessDAO extends JpaRepository<AssessmentProcess, Long>
{
    
    //********************************************
      @Query(value = "SELECT p "
              + " FROM AssessmentProcess p "
              + " JOIN FETCH p.tasks t "
              
              + "  WHERE p.id = :processId ")
      AssessmentProcess getById( @Param("processId") long processId);
}
