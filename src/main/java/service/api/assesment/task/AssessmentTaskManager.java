package service.api.assesment.task;

import java.util.List;

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import model.assessment.task.AssessmentTaskDetails;


public interface AssessmentTaskManager
{

    AssessmentTask createTask( String itemName, String itemText, int mode, int modeType, int complexity );

    AssessmentTaskCategory createTaskCategory( String name, String details );

    AssessmentTaskDetails createTaskDetails( String itemDetails, float grade );

    AssessmentTask saveTask( AssessmentTask entity );

    AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory entity );

    List<AssessmentTaskCategory> getCategories();

    List<AssessmentTaskCategory> getCategoryTree();

}
