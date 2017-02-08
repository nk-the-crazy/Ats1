package dao.api.assessment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.assessment.AssessmentTaskCategory;



public interface AssessmentTaskCategoryDAO extends  JpaRepository<AssessmentTaskCategory, Long>
{
    //********************************************
    @Query(value = "SELECT c "
            + " FROM AssessmentTaskCategory c "
            + " WHERE c.parent.id IS NULL" )
    List<AssessmentTaskCategory> getCategoryTree();

}
