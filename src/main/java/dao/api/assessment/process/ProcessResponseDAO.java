package dao.api.assessment.process;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import model.assessment.process.ProcessResponse;


public interface ProcessResponseDAO extends JpaRepository<ProcessResponse, Long>
{
    //********************************************
    @Query(value = "SELECT t "
            + " FROM ProcessResponse t "
            + " JOIN FETCH t.task tt "
            
            + " WHERE t.id=:processResponseId ")
    ProcessResponse getById( @Param("processResponseId") long processResponseId);
    
    
    //********************************************
    @Query(value = "SELECT t "
            + " FROM ProcessResponse t "
            + " LEFT JOIN t.task s " 
            + " LEFT JOIN s.details sd " 
            + " LEFT JOIN t.process p "
            + " LEFT JOIN FETCH t.details d "
            
            + " WHERE s.id=:taskId "
            + " AND p.id=:processId ")
    ProcessResponse getByProcessAndTaskId( @Param("processId") long processId, @Param("taskId") long taskId);

    
    //********************************************
    @Modifying
    @Query(value = "DELETE FROM ProcessResponseDetail rd"
            + " WHERE rd.id in ( "
            + " SELECT prd.id "
            + " FROM ProcessResponse pr "
            + " JOIN pr.details prd "
            
            + " WHERE pr.id=:responseId ) ")
    void removeResponseDetails( @Param("responseId") long responseId);
    
    
    
    // ********************************************
    @Query(value = " SELECT r, tsk, rd "
                 +  " FROM AssessmentProcess p "  
                 +  " JOIN p.responses r "
                 +  " JOIN r.task tsk "
                 +  " JOIN r.details rd "
                 
                 +  " WHERE p.id=:processId "
                 +  " ORDER BY r.id ")
    Page<ProcessResponse> getByProcessId( @Param("processId") long processId , Pageable pageable );

    
    // ********************************************
    @Query(value = " SELECT rd.itemResponse "
                 +  " FROM ProcessResponseDetail rd "  
             
                 +  " WHERE rd.id=:responseDetailsId ")
    String getResponseContent( @Param("responseDetailsId") long responseDetailsId);

}