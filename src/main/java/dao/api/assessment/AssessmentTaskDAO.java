package dao.api.assessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.assessment.AssessmentTask;

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
            + " JOIN FETCH t.category c "
            + " JOIN FETCH t.details d "
            + " WHERE t.id=:taskId " )
    AssessmentTask getFullDetails(@Param("taskId") long taskId );


    //********************************************
    @Query(value = "SELECT c.id, t.id, t.itemName "
            + " FROM AssessmentTaskCategory c "
            + " JOIN c.tasks t "
            + " WHERE c.id=:categoryId "
            + " ORDER By t.itemName" )
    Page<AssessmentTask> getByCategoryId(@Param("categoryId") long categoryId , Pageable pageable );

}
