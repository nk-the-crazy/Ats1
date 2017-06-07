package service.api.assessment;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import common.exceptions.assessment.TimeExpiredException;
import model.assessment.Assessment;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessSession;
import model.identity.User;
import model.report.assessment.AssessmentResult;



public interface AssessmentManager
{

    Assessment getAssessmentFullDetails( long assessmentId );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, Date startDateTo, short assessmentType,
            Pageable pageable );

    Assessment saveAssessment( Assessment assessment );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, short assessmentType,
            Pageable pageable );

 
    Page<Assessment> getAssessmentsByUserId( long userId, Pageable pageable );

    AssessmentProcess initProcess( long assessmentId, User user );
    
    ProcessSession rollProcess( ProcessSession processSession, ProcessResponse processResponse, int taskIndex )
                    throws TimeExpiredException;

    ProcessSession startProcess( AssessmentProcess process, int taskIndex, String entryCode )
                    throws TimeExpiredException;

    void endProcess( ProcessSession processSession );
    
    Integer submitProcessResponse( ProcessSession processSession, ProcessResponse processResponse )
                    throws TimeExpiredException;

    Assessment createAssessment( String name, Date startDate, Date endDate, int time, int type );

    Assessment getAssessmentByIdAndUserId( long assessmentId, long userId );

    AssessmentProcess saveAssessmentProcess( AssessmentProcess process );

    AssessmentProcess createAssessmentProcess();

    Page<Object> getAssessmentTasks( long assessmentId, Pageable pageable );

    Assessment createAssessment( Assessment assessment, User author, List<Long> participantIds );

    Page<Assessment> getAssignedAssessments( long userId, Pageable pageable );

    AssessmentResult getAssessmentResult( long processId );

    Page<Object> getAssessmentResults( String lastName, Date startDateFrom, Pageable pageable );

    Page<Object> getProcessResponses( long processId, Pageable pageable );

    String getResponseContent( long responseDetailId );

    Page<AssessmentProcess> getProcessList( String lastName, Date startDateFrom, Pageable pageable );

    void evaluateResponse( User user, float grade, String comment, long responseId );

    AssessmentResult getAssessmentResultDetail( long processId );

    User getProcessUserDetails( long userId );

    void removeTask( long assessmentId, long taskId );

    void addTasks( long assessmentId, List<Long> taskIds );

    Assessment getAssessment( long assessmentId );

    AssessmentProcess createAssessmentProcess( Assessment assessment, User user );

    ProcessResponse getProcessResponseDetails( long processId, long taskId );

    void endProcess( long processId );

    Page<Object> getProcessWrongResponses( long processId, Pageable pageable );


}
