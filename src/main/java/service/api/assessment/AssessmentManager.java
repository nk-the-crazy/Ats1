package service.api.assessment;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.assessment.Assessment;
import model.assessment.process.AssessmentProcess;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskResponse;
import model.identity.User;


public interface AssessmentManager
{

    Assessment getAssessmentFullDetails( long assessmentId );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, Date startDateTo, short assessmentType,
            Pageable pageable );

    Assessment saveAssessment( Assessment assessment );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, short assessmentType,
            Pageable pageable );

 
    Page<Assessment> getAssessmentsByUserId( long userId, Pageable pageable );

    Object getAssessmentDetails( long assessmentId );

    AssessmentProcess initProcess( long assessmentId, long userId );

    Assessment createAssessment( String name, Date startDate, Date endDate, int time, int type );

    Assessment getAssessmentByIdAndUserId( long assessmentId, long userId );

    AssessmentProcess saveAssessmentProcess( AssessmentProcess process );

    AssessmentProcess createAssessmentProcess();

    Page<AssessmentTask> getAssessmentTasks( long assessmentId, Pageable pageable );

    AssessmentProcess startProcess( AssessmentProcess process, AssessmentTaskResponse taskResponse );

    AssessmentProcess endProcess( AssessmentProcess process );

    Assessment createAssessment( Assessment assessment, User author, List<Long> participantIds );

  
}
