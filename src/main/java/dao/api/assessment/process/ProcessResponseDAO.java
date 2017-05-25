package dao.api.assessment.process;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.assessment.process.ProcessResponse;


public interface ProcessResponseDAO extends JpaRepository<ProcessResponse, Long>
{
    
    //********************************************
    @Query(value = "SELECT r "
            + " FROM ProcessResponse r "
            + " LEFT JOIN FETCH r.details rd "
            
            + " WHERE r.task.id=:taskId "
            + " AND   r.process.id=:processId ")
    ProcessResponse getDetailsByProcessAndTaskId( @Param("processId") long processId, 
                                                  @Param("taskId") long taskId);

    
  
    // ********************************************
    @Query(value = " SELECT r, tsk, rd, rdt "
                 +  " FROM AssessmentProcess p "  
                 +  " JOIN p.responses r "
                 +  " JOIN r.task tsk "
                 +  " JOIN r.details rd "
                 +  " JOIN rd.taskDetail rdt "
                 
                 +  " WHERE p.id=:processId "
                 +  " ORDER BY r.id ")
    Page<Object> getByProcessId( @Param("processId") long processId , Pageable pageable );

    

    // ********************************************
    @Query(value = " SELECT r, tsk "
                 +  " FROM AssessmentProcess p "  
                 +  " JOIN p.responses r "
                 +  " JOIN r.task tsk "
                 
                 +  " WHERE p.id=:processId "
                 +  " AND  r.grade > 0 "
                 +  " ORDER BY r.id ")
    Page<Object> getWrongByProcessId( @Param("processId") long processId , Pageable pageable );

    
    // ********************************************
    @Query(value = " SELECT rd.itemResponse "
                 +  " FROM ProcessResponseDetail rd "  
             
                 +  " WHERE rd.id=:responseDetailsId ")
    String getResponseContent( @Param("responseDetailsId") long responseDetailsId);

}
