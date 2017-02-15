package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.assessment.process.AssessmentProcessTask;


public interface AssessmentProcessTaskDAO extends JpaRepository<AssessmentProcessTask, Long>
{
    //********************************************
    @Query(value = "SELECT t "
            + " FROM AssessmentProcessTask t "
            + " JOIN FETCH t.process p "
            + " JOIN FETCH t.taskDetails at "
            
            + " WHERE t.id = :processTaskId ")
    AssessmentProcessTask getById( @Param("processTaskId") long processTaskId);
}
