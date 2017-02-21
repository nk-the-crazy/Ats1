package service.api.assessment;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.assessment.Assessment;
import model.assessment.process.Process;
import model.assessment.process.ProcessResponse;
import model.assessment.task.AssessmentTask;
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

    Process initProcess( long assessmentId, long userId );
    
    ProcessResponse startProcess( Process assessmentProcess, ProcessResponse processResponse, int nextTaskIndex );
    
    Process endProcess( Process process );

    Assessment createAssessment( String name, Date startDate, Date endDate, int time, int type );

    Assessment getAssessmentByIdAndUserId( long assessmentId, long userId );

    Process saveAssessmentProcess( Process process );

    Process createAssessmentProcess();

    Page<AssessmentTask> getAssessmentTasks( long assessmentId, Pageable pageable );

    Assessment createAssessment( Assessment assessment, User author, List<Long> participantIds );

    
}
