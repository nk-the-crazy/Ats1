package service.impl.assesment;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import common.exceptions.assessment.TimeExpiredException;
import common.exceptions.security.AccessDeniedException;
import dao.api.assessment.AssessmentDAO;
import dao.api.assessment.process.ProcessDAO;
import dao.api.assessment.process.ProcessResponseDAO;
import dao.api.assessment.task.AssessmentTaskCategoryDAO;
import dao.api.assessment.task.AssessmentTaskDAO;
import dao.api.group.GroupDAO;
import model.assessment.Assessment;
import model.assessment.options.TaskFormOptions;
import model.assessment.process.Process;
import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessResponseDetail;
import model.assessment.process.ProcessState;
import model.assessment.task.AssessmentTask;
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
    ProcessDAO processDAO;

    @Autowired
    ProcessResponseDAO processResponseDAO;

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
    public Process initProcess(long assessmentId, long userId)
    {
        Assessment assessment = assessmentDAO.getByIdAndUserId( assessmentId, userId );
        
        if(assessment == null)
        {
            throw new AccessDeniedException("User is not permitted to take assessment.");
        }
        else if(assessment.getStatus() != 1 )
        {
            throw new AccessDeniedException("Assessment is ended or disabled");
        }
        else
        {
            Process process = processDAO.getByAssessmentId( assessmentId );
            
            if(process == null)
            {
                process = createAssessmentProcess();
                process.setAssessment( assessment );
            }
            
            if(process.getState() != ProcessState.Finished.getId() )
            {
                process.setState( ProcessState.Ready.getId() );
                process.setTaskIds( taskDAO.getRandomIdByAssessmentId( assessment.getId() ) );
                return process;
            }
            else
            {
                throw new AccessDeniedException("Assessment Test is not allowed."); 
            }
        }
    }
    
    
    /**
     * @throws TimeExpiredException ************************************************
     * 
     */
    @Override
    public ProcessResponse startProcess( Process process, ProcessResponse processResponse , int nextTaskIndex) throws TimeExpiredException
    {
        ProcessResponse response = null;
        
        try
        {
            if(isTimeExpired( process.getAssessment().getTime(), process.getEndDate() ))
            {
                throw new TimeExpiredException("Time is expired for Assessment:"+ process.getAssessment().getName());
            }

            //*******************************************************
            if(process.getState() == ProcessState.Ready.getId() )
            {
                process.setState( ProcessState.Started.getId() );
            }
            else
            {
                process.setEndDate( new Date(System.currentTimeMillis()) );
                processDAO.save( process );
                
                processResponse.setProcess( process );
                
                if(!CollectionUtils.isEmpty( processResponse.getDetails() ))
                {
                    processResponseDAO.save( processResponse );
                }
            }
            
            //*******************************************************
            response = processResponseDAO.getByProcessAndTaskId( process.getId(), getTaskIdByIndex(nextTaskIndex, process) );
            
            if(response == null)
            {
                response = new ProcessResponse(); 
            }
            
            response.setTask( getTaskByIndex(nextTaskIndex, process ) ); 
        }
        catch ( TimeExpiredException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch(Exception e)
        {
            logger.error( e.toString(), e );
        }
        
        return response;

    }
    

    /**************************************************
     * 
     */
    @Override
    public Process endProcess( Process process , ProcessResponse processResponse)
    {
        process.setState( ProcessState.Finished.getId() );
        process.setEndDate( new Date(System.currentTimeMillis()) );
        processResponse.setProcess( process );
        processResponseDAO.save( processResponse );
        
        return processDAO.save( process );
    }
    
    
    /**************************************************
     * 
     */
    private long getTaskIdByIndex( int taskIndex, Process process )
    {
        if(taskIndex >= process.getTaskIds().size())
        {
            taskIndex = process.getTaskIds().size() - 1;
            return process.getTaskIds().get( taskIndex ); 
        }
        else
            return process.getTaskIds().get(taskIndex);
    }
    
    
    /**************************************************
     * 
     */
    private AssessmentTask getTaskByIndex( int taskIndexm, Process process )
    {
        return taskDAO.getByTaskId( getTaskIdByIndex(taskIndexm, process) );
    }
    
    
    /**************************************************
     * 
     */
    private float calculateGrade( float percentage, float grade )
    {
        if(percentage == 0 || grade == 0 )
            return 0;
        else
        {
            return (percentage*grade)/100;
        }
    }
    
    
    /**************************************************
     * 
     */
    private boolean isTimeExpired( int time, Date lastActiveDate )
    {
        lastActiveDate = DateUtils.addMinutes( lastActiveDate, time );
        Date currentDate = new Date(System.currentTimeMillis());
        
        if( lastActiveDate.getTime() < currentDate.getTime())
            return true;
        else 
            return false;
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Process createAssessmentProcess()
    {
        Process process = null;

        try
        {
            process = new Process();
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
    public Process saveAssessmentProcess( Process process )
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
            if( !CollectionUtils.isEmpty( tasks ))
                assessment.setTasks( new HashSet<>(tasks) );
            
            //------- Set Participants -------------------
            if (!CollectionUtils.isEmpty( participantIds ))
                assessment.setParticipants( groupDAO.getByGroupIdIn(participantIds) );
            
            //------- Set Author -------------------
            if( author != null )
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
