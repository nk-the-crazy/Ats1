package dao.api.assessment.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.assessment.task.AssessmentTaskCategory;



public interface AssessmentTaskCategoryDAO extends  JpaRepository<AssessmentTaskCategory, Long>
{
    //********************************************
    @Query(value = "SELECT c "
            + " FROM AssessmentTaskCategory c "
            + " WHERE c.parent.id IS NULL" )
    List<AssessmentTaskCategory> getCategoryTree();

    //********************************************
    @Query(value = "SELECT c "
            + " FROM AssessmentTaskCategory c "
            + " WHERE c.type=1" )
   List<AssessmentTaskCategory> getSystemCategory();
    
    
    //********************************************
    @Query(value = "SELECT c.id, c.name "
            + " FROM AssessmentTaskCategory c "
            + " WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:categoryName, '%')) "
            + " ORDER BY c.name")
    List<AssessmentTaskCategory> getShortListByCategoryName(@Param("categoryName") String categoryName );

}
