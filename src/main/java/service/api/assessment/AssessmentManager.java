package service.api.assessment;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.assessment.Assessment;
import model.assessment.process.AssessmentProcess;
import model.assessment.task.AssessmentTask;

public interface AssessmentManager
{

    Assessment getAssessmentFullDetails( long assessmentId );

    Assessment createAssessment( String name, Date startDate, Date endDate, int type );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, Date startDateTo, short assessmentType,
            Pageable pageable );

    Assessment saveAssessment( Assessment assessment );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, short assessmentType,
            Pageable pageable );

    Page<AssessmentTask> getAssessmentTasks( long assessmentId, Pageable pageable );

    Page<Assessment> getAssessmentsByUserId( long userId, Pageable pageable );

    Object getAssessmentDetails( long assessmentId );

    AssessmentProcess initProcess( long assessmentId, long userId );

  
}
