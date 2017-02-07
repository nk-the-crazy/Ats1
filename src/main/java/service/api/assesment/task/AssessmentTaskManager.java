package service.api.assesment.task;

import model.assessment.task.AssessmentTask;

public interface AssessmentTaskManager
{

    AssessmentTask createTask( String item, int type, String comment );

}
