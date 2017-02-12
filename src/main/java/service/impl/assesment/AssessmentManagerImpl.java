package service.impl.assesment;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.exceptions.security.AccessDeniedException;
import dao.api.assessment.AssessmentDAO;
import dao.api.assessment.AssessmentTaskDAO;
import model.assessment.Assessment;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.AssessmentProcessState;
import model.assessment.task.AssessmentTask;
import service.api.assessment.AssessmentManager;


@Service("assessmentManagerService")
@Transactional
public class AssessmentManagerImpl implements AssessmentManager
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( AssessmentManagerImpl.class );
    // ---------------------------------

    @Autowired
    AssessmentDAO assessmentDAO;

    @Autowired
    AssessmentTaskDAO taskDAO;

    
    /**************************************************
     * 
     */
    @Override
    public AssessmentProcess initProcess(long assessmentId, long userId)
    {
        Assessment assessment = assessmentDAO.getByIdAndUserId( assessmentId, userId );
        
        if(assessment == null)
        {
            throw new AccessDeniedException("User is not permitted to take assessment.");
        }
        else
        {
            AssessmentProcess process = new AssessmentProcess();
            
            process.setAssessment( assessment );
            //process.setTaskIdList( taskDAO.getRandomIdByAssessmentId( assessment.getId() ) );
            //process.setState( AssessmentProcessState.Ready.getId() );
            //process.setCurrentTaskIndex( 0 );
            
            return process;
        }
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public AssessmentProcess startProcess(AssessmentProcess process)
    {
        int currentTaskIndex  = process.getCurrentTaskIndex();
        
        if(currentTaskIndex == 0)
        {
            process.setState( AssessmentProcessState.Started.getId() );
            process.setTimeElapsed( 0 );
        }
        else if(( currentTaskIndex + 1 ) == process.getTaskIdList().size() )
        {
            return process;
        }
        
        AssessmentTask currentTask = taskDAO.findOne( process.getTaskIdList().get( currentTaskIndex ) );
        process.setCurrentTask( currentTask );
        process.setCurrentTaskIndex( currentTaskIndex + 1 );
        
        return process;
    }
    

    /**************************************************
     * 
     */
    @Override
    public Assessment createAssessment( String name, Date startDate, Date endDate, int time ,int type )
    {
        Assessment assessment = null;

        try
        {
            assessment = new Assessment();
            assessment.setName( name );
            assessment.setType( type ); 
            assessment.setStartDate( startDate );
            assessment.setTime( time );
            assessment.setEndDate( endDate );
        }
        catch ( Exception e )
        {
            logger.error( e.toString(), e );
        }

        return assessment;

    }
    
    /**************************************************
     * 
     */
    @Override
    public Assessment saveAssessment( Assessment assessment )
    {
        try
        {
            return assessmentDAO.save( assessment );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in saving assessment:", e );        
        }

        return null;
    }
    

    /**************************************************
     * 
     */
    @Override
    public Assessment getAssessmentFullDetails( long assessmentId )
    {
        return assessmentDAO.getFullDetails( assessmentId );
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Object getAssessmentDetails( long assessmentId )
    {
        return assessmentDAO.getDetails( assessmentId );
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Assessment getAssessmentByIdAndUserId( long assessmentId, long userId )
    {
        return assessmentDAO.getByIdAndUserId( assessmentId, userId );
    }
    

    /**************************************************
     * 
     */
    @Override
    public Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom, Date startDateTo,
            short assessmentType, Pageable pageable )
    {
        return assessmentDAO.getByDetails(assessmentName, startDateFrom, startDateTo,assessmentType, pageable);
    }
    

    /**************************************************
     * 
     */
    @Override
    public Page<Assessment> getAssessmentsByUserId( long userId, Pageable pageable )
    {
        return assessmentDAO.getByUserId( userId, pageable ); 
    }
    

    /**************************************************
     * 
     */
    @Override
    public Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDateFrom , short assessmentType, Pageable pageable )
    {
        return assessmentDAO.getByDetails(assessmentName, startDateFrom, assessmentType, pageable);
    }
    
    

    /**************************************************
     * 
     */
    @Override
    public Page<AssessmentTask> getAssessmentTasks( long assessmentId, Pageable pageable )
    {
        return taskDAO.getByAssessmentId( assessmentId, pageable );
    }
    
    

}
