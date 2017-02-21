package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.assessment.process.ProcessResponse;


public interface ProcessResponseDAO extends JpaRepository<ProcessResponse, Long>
{
    //********************************************
    @Query(value = "SELECT t "
            + " FROM ProcessResponse t "
            + " JOIN FETCH t.process p "
            + " JOIN FETCH t.task at "
            
            + " WHERE t.id = :processResponseId ")
    ProcessResponse getById( @Param("processResponseId") long processResponseId);
    
    
    //********************************************
    @Query(value = "SELECT t "
            + " FROM ProcessResponse t "
            + " JOIN FETCH t.process p "
            + " JOIN FETCH t.task at "
            
            + " WHERE at.id = :taskId ")
    ProcessResponse getByTaskId( @Param("taskId") long taskId);
}
