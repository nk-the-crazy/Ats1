package service.impl.assesment;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import common.exceptions.security.AccessDeniedException;
import dao.api.assessment.AssessmentDAO;
import dao.api.assessment.AssessmentTaskCategoryDAO;
import dao.api.assessment.AssessmentTaskDAO;
import dao.api.assessment.process.AssessmentProcessDAO;
import dao.api.assessment.process.AssessmentProcessTaskDAO;
import dao.api.group.GroupDAO;
import model.assessment.Assessment;
import model.assessment.options.TaskFormOptions;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.AssessmentProcessState;
import model.assessment.process.AssessmentProcessTask;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskResponse;
import model.identity.User;
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
    AssessmentProcessDAO processDAO;

    @Autowired
    AssessmentProcessTaskDAO processTaskDAO;

    @Autowired
    AssessmentTaskDAO taskDAO;

    @Autowired
    GroupDAO groupDAO;
    
    @Autowired
    AssessmentTaskCategoryDAO categoryDAO;

    
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
            AssessmentProcess process = createAssessmentProcess();
            process.setAssessment( assessment );
            
            
            for(AssessmentTask task : assessment.getTasks() )
            {
                AssessmentProcessTask processTask = new AssessmentProcessTask();
                processTask.setTaskDetails( task );
                process.addProcessTask( processTask );
            }
            
            saveAssessmentProcess( process );

            return process;
        }
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public AssessmentProcess startProcess( AssessmentProcess process , AssessmentTaskResponse taskResponse)
    {
        if(process.getState() == AssessmentProcessState.Ready.getId() )
        {
            process.setState( AssessmentProcessState.Started.getId() );
            process.setStartDate( new Date(System.currentTimeMillis()) );
            process.setEndDate( process.getStartDate() );
        }
        else
        {
            process.setEndDate( new Date(System.currentTimeMillis()) );
        }
        
        processDAO.save( process );
        
        return process;
    }
    

    
    /**************************************************
     * 
     */
    @Override
    public AssessmentProcess endProcess( AssessmentProcess process)
    {
        process.setState( AssessmentProcessState.Finished.getId() );
        process.setEndDate( new Date(System.currentTimeMillis()) );
        
        return process;
    }
    

    /**************************************************
     * 
     */
    @Override
    public AssessmentProcess createAssessmentProcess()
    {
        AssessmentProcess process = null;

        try
        {
            process = new AssessmentProcess();
        }
        catch ( Exception e )
        {
            logger.error( e.toString(), e );
        }

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
    public AssessmentProcess saveAssessmentProcess( AssessmentProcess process )
    {
        try
        {
            return processDAO.save( process );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in saving assessment process:", e );        
        }

        return null;
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


    /**************************************************
     * 
     */
    @Override
    public Assessment createAssessment( Assessment assessment, User author, List<Long> participantIds )
    {
        try
        {
            List<Long> taskIds = null;
            List<AssessmentTask> tasks = new ArrayList<AssessmentTask>();
            Page<AssessmentTask> selectedTasks = null;
            
            //------- Select Categories --------------------
            if(!assessment.getFormOptions().isAllCategories())
            {
                taskIds = taskDAO.getByCategoryIdIn( assessment.getFormOptions().getTaskCategories());
            }
            
            
            for(TaskFormOptions taskOptions : assessment.getFormOptions().getTaskOptions() )
            {
                if(taskOptions.getNumber() > 0)
                {
                    if(taskIds == null)
                        selectedTasks = taskDAO.getByModeTypeAndComplexity( taskOptions.getModeType(), 
                                                                            taskOptions.getComplexity(), 
                                                                            new PageRequest(0, taskOptions.getNumber()));
                    else
                        selectedTasks = taskDAO.getByModeTypeAndComplexity( taskOptions.getModeType(), 
                                                                            taskOptions.getComplexity(),
                                                                            taskIds,
                                                                            new PageRequest(0, taskOptions.getNumber()));
                    
                    tasks.addAll( selectedTasks.getContent() );
                }
            }
            
            assessment. setTasks( new HashSet<AssessmentTask>() ); 
            
            
            //------- Set Tasks -------------------
            if(!CollectionUtils.isEmpty( tasks ))
                assessment.setTasks( new HashSet<>(tasks) );
            
            //------- Set Participants -------------------
            if(!CollectionUtils.isEmpty( participantIds ))
                assessment.setParticipants( groupDAO.getByGroupIdIn(participantIds) );
            
            //------- Set Author -------------------
            if(!CollectionUtils.isEmpty( participantIds ))
                assessment.setAuthor( author );
            
            return assessmentDAO.save( assessment );
        }
        catch(Exception e)
        {
            logger.error( "Error generating Assessment:", e );
        }
        
        return null;
    }

  
}
