package service.api.assessment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import model.assessment.task.AssessmentTaskOption;


public interface AssessmentTaskManager
{

    AssessmentTask createTask( String itemName, String itemContent, float itemGrade, int mode, int modeType,
            int complexity );

    AssessmentTaskOption createTaskDetails( String itemOption, float grade );

    AssessmentTask saveTask( AssessmentTask entity );

    AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory category );

    List<AssessmentTaskCategory> getCategories();

    List<AssessmentTaskCategory> getCategoryTree();

    Page<AssessmentTask> getTasksByDetails( String itemName, String categoryName, short modeType, Pageable pageable );

    AssessmentTaskCategory getCategoryDetails( long categoryId );

    AssessmentTask getTaskFullDetails( long taskId );

    Page<AssessmentTask> getCategoryTasks( long categoryId, Pageable pageable );

    AssessmentTask getTaskById( long taskId );

    AssessmentTaskCategory createTaskCategory( String name, String details, int type );

    AssessmentTaskCategory getSystemCategory();

    AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory category, long parentCategoryId );

    List<AssessmentTaskCategory> getCategoryShortList( String categoryName );

    AssessmentTask saveTask( AssessmentTask task, long categoryId );
}
