package dao.api.assessment.process;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.assessment.process.AssessmentProcess;
import model.report.assessment.AssessmentResult;


public interface ProcessDAO extends JpaRepository<AssessmentProcess, Long>
{

    // ********************************************
    
    @Query(value = " SELECT p " + 
                   " FROM AssessmentProcess p " + 
                   " JOIN FETCH p.responses r "+
                   " WHERE p.id = :processId " )
    AssessmentProcess getById( @Param("processId") long processId );

    // ********************************************
    @Query(value = " SELECT p " + 
                   " FROM AssessmentProcess p " + 
                   " JOIN FETCH p.assessment a "

                 + " WHERE a.id = :assessmentId "
                 + " AND p.user.id = :userId ")
    AssessmentProcess getByAssessmentAndUserId( @Param("assessmentId") long assessmentId,
                                                @Param("userId") long userId );
    
   
    // ********************************************
    @Query(value = " SELECT new model.report.assessment.AssessmentResult(" 
                 +  " p, (SELECT COUNT(tsk.id) from a.tasks tsk), COUNT(DISTINCT r.id), "
                 +  " SUM(CASE WHEN rd.grade>0 THEN 1 ELSE 0 END), SUM(rd.grade)) "
                 +  " FROM AssessmentProcess p "  
                 +  " JOIN p.assessment a "
                 +  " JOIN p.responses r "
                 +  " JOIN r.details rd "
                 +  " WHERE p.id = :processId "
                 +  " GROUP BY p.id, a.id ")
    AssessmentResult getResultById( @Param("processId") long processId );
    
   
    // ********************************************
    @Query(value = " SELECT p, a, usr, prn, (SELECT COUNT(tsk.id) from a.tasks tsk), COUNT(DISTINCT r.id), "
                 +  " SUM(CASE WHEN rd.grade>0 THEN 1 ELSE 0 END), SUM(rd.grade) "
                 +  " FROM AssessmentProcess p "  
                 +  " JOIN p.assessment a "
                 +  " JOIN p.responses r "
                 +  " JOIN r.details rd "
                 +  " JOIN p.user usr "
                 +  " JOIN usr.person prn "
                 +  " WHERE LOWER(prn.lastName) LIKE LOWER(CONCAT('%',:lastName, '%')) AND "
                 +  " p.startDate>=:startDateFrom "
                 +  " GROUP BY usr.id, prn.id, p.id, a.id "
                 +  " ORDER BY p.startDate DESC " , 
         countQuery = " SELECT count(p.id) "
                         +  " FROM AssessmentProcess p "  
                         +  " JOIN p.assessment a "
                         +  " JOIN p.responses r "
                         +  " JOIN r.details rd "
                         +  " JOIN p.user usr "
                         +  " JOIN usr.person prn "
                         +  " WHERE LOWER(prn.lastName) LIKE LOWER(CONCAT('%',:lastName, '%')) AND "
                         +  " p.startDate>=:startDateFrom "
                         +  " GROUP BY usr.id, prn.id, p.id, a.id ")
    Page<Object> getResults( @Param("lastName") String lastName ,
                             @Param("startDateFrom") Date startDateFrom, 
                             Pageable pageable );
    
   
}
