package service.api.assessment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import model.assessment.task.AssessmentTaskDetail;
import model.assessment.task.AssessmentTaskInfo;


public interface AssessmentTaskManager
{

    AssessmentTask createTask( String itemName, String itemContent, float itemGrade, int mode, int modeType,
            int complexity );

    AssessmentTaskDetail createTaskDetails( String itemDetail, float itemGrade );

    AssessmentTask saveTask( AssessmentTask entity );

    AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory category );

    List<AssessmentTaskCategory> getCategories();

    List<AssessmentTaskCategory> getCategoryTree();

    Page<Object> getTasksByDetails( String itemContent, String categoryName, short modeType, Pageable pageable );

    AssessmentTaskCategory getCategoryDetails( long categoryId );

    AssessmentTask getTaskFullDetails( long taskId );

    Page<AssessmentTask> getCategoryTasks( long categoryId, Pageable pageable );

    AssessmentTask getTaskById( long taskId );

    AssessmentTaskCategory createTaskCategory( String name, String details, int type );

    AssessmentTaskCategory getSystemCategory();

    AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory category, long parentCategoryId );

    List<AssessmentTaskCategory> getCategoryShortList( String categoryName );

    AssessmentTask saveTask( AssessmentTask task, long categoryId );

    void importTasks( MultipartFile file );

    boolean removeCategory( long categoryId );

    AssessmentTaskInfo createTaskInfo( String description );
}
