package dao.api.assessment.task;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.assessment.task.AssessmentTask;

public interface AssessmentTaskDAO extends JpaRepository<AssessmentTask, Long>
{
    
    //********************************************
    @Query(value = "SELECT t.id, t.itemName, t.complexity ,t.mode, t.modeType ,t.status, c.id, c.name "
            + " FROM AssessmentTask t "
            + " INNER JOIN t.category c "
            + " WHERE LOWER(t.itemName) LIKE LOWER(CONCAT('%',:itemName, '%')) AND "
            + " LOWER(c.name) LIKE  LOWER(CONCAT('%',:categoryName, '%')) AND "
            + " ( t.modeType=:modeType OR 0=:modeType )"
            + " ORDER BY t.itemName")
    Page<AssessmentTask> findByDetails(@Param("itemName") String itemName,
                                       @Param("categoryName") String categoryName , 
                                       @Param("modeType") short modeType , 
                                       Pageable pageable );
    

    //********************************************
    @Query(value = "SELECT t.id, t.itemName, t.complexity ,t.mode, t.modeType ,t.status, c.name "
            + " FROM AssessmentTask t "
            + " INNER JOIN t.category c "
            + " WHERE LOWER(t.itemName) LIKE LOWER(CONCAT('%',:itemName, '%')) AND "
            + " LOWER(c.name) LIKE  LOWER(CONCAT('%',:categoryName, '%')) "
            + " ORDER BY t.itemName")
    Page<AssessmentTask> findByItemNameAndCategory(@Param("itemName") String itemName,
                                         @Param("categoryName") String categoryName , 
                                         Pageable pageable );


    //********************************************
    @Query(value = "SELECT t "
            + " FROM AssessmentTask t "
            + " LEFT JOIN FETCH t.category c "
            + " LEFT JOIN FETCH t.details d "
            + " WHERE t.id=:taskId " )
    AssessmentTask getFullDetails(@Param("taskId") long taskId );

    
    //********************************************
    @Query(value = "SELECT t "
            + " FROM AssessmentTask t "
            + " LEFT JOIN FETCH t.details d "
            + " WHERE t.id=:taskId " )
    AssessmentTask getByTaskId(@Param("taskId") long taskId );


    //********************************************
    @Query(value = "SELECT c.id, t.id, t.itemName "
            + " FROM AssessmentTaskCategory c "
            + " JOIN c.tasks t "
            + " WHERE c.id=:categoryId "
            + " ORDER By t.itemName" )
    Page<AssessmentTask> getByCategoryId(@Param("categoryId") long categoryId , Pageable pageable );

    
    //********************************************
    @Query(value = "SELECT t "
            + " FROM AssessmentTask t "
            + " WHERE t.complexity=:complexity "
            + " AND t.modeType=:modeType "
            + " AND t.status=1 "
            + " ORDER BY RAND()" )
    Page<AssessmentTask> getByModeTypeAndComplexity(@Param("modeType") short modeType,
                                                    @Param("complexity") short complexity, 
                                                    Pageable pageable );

    
    //********************************************
    @Query(value = "SELECT t "
            + " FROM AssessmentTask t "
            + " WHERE t.complexity=:complexity "
            + " AND t.modeType=:modeType "
            + " AND t.status=1 "
            + " AND t.id IN :ids "
            + " ORDER BY RAND()" )
    Page<AssessmentTask> getByModeTypeAndComplexity(@Param("modeType") short modeType,
                                                    @Param("complexity") short complexity,
                                                    @Param("ids") List<Long> taskIds,
                                                    Pageable pageable );

    

    
    //********************************************
    @Query(value = "SELECT a.id, t.id, t.itemName, t.modeType "
            + " FROM Assessment a "
            + " JOIN a.tasks t "
            + " WHERE a.id=:assessmentId "
            + " ORDER By t.itemName" )
    Page<AssessmentTask> getByAssessmentId(@Param("assessmentId") long assessmentId , Pageable pageable );

    
    //********************************************
    @Query(value = "SELECT t.id  "
            + " FROM Assessment a "
            + " JOIN a.tasks t "
            + " WHERE a.id=:assessmentId "
            + " ORDER BY RAND()" )
    List<Long> getRandomIdByAssessmentId(@Param("assessmentId") long assessmentId);

    
   //********************************************
    @Query( value = "SELECT t.id "
            + " FROM AssessmentTask t "
            + " JOIN t.category c "
            + " WHERE c.id IN :ids" )
    List<Long> getByCategoryIdIn(@Param("ids") List<Long> taskCategories );

}
