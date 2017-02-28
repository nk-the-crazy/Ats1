package service.impl.assesment;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import dao.api.assessment.process.ProcessResponseDetailDAO;
import dao.api.assessment.process.ProcessResponseEvaluationDAO;
import dao.api.assessment.task.AssessmentTaskCategoryDAO;
import dao.api.assessment.task.AssessmentTaskDAO;
import dao.api.group.GroupDAO;
import model.assessment.Assessment;
import model.assessment.options.TaskFormOptions;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessResponseDetail;
import model.assessment.process.ProcessResponseEvaluation;
import model.assessment.process.ProcessState;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskDetail;
import model.identity.User;
import model.report.assessment.AssessmentResult;
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
    ProcessResponseDetailDAO responseDetailDAO;

    @Autowired
    ProcessResponseEvaluationDAO evaluationDAO;

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
    public AssessmentProcess initProcess(long assessmentId, User user)
    {
        Assessment assessment = assessmentDAO.getByIdAndUserId( assessmentId, user.getId() );
        
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
            AssessmentProcess process = processDAO.getByAssessmentAndUserId( assessmentId, user.getId() );
            
            if(process == null)
            {
                process = createAssessmentProcess();
                process.setAssessment( assessment );
                process.setUser( user );
            }
            
            if(process.getState() != ProcessState.Finished.getId() )
            {
                process.setState( ProcessState.Ready.getId() );
                process.setTaskIds( taskDAO.getRandomIdByAssessmentId( assessment.getId() ) );
            }
            
            return process;
        }
    }
    
    
    /* ************************************************
     * @throws TimeExpiredException 
     * 
     */
    @Override
    public ProcessResponse startProcess( AssessmentProcess process, ProcessResponse processResponse , int nextTaskIndex) throws TimeExpiredException
    {
        ProcessResponse response = null;
        
        try
        {
            //-----------Check Valid ----------------------------
            if(process.getState() == ProcessState.Finished.getId())
            {
                throw new TimeExpiredException("Process already finished (Test passed):"+ process.getAssessment().getName());
            }
            
            if(isTimeExpired( process.getAssessment().getTime(), process.getStartDate() ))
            {
                //-----End Process if time expired -------------
                endProcess(process, null);
                //----------------------------------------------
                throw new TimeExpiredException("Time is expired for Assessment:"+ process.getAssessment().getName());
            }
            //----------------------------------------------
            
            if(process.getState() == ProcessState.Ready.getId() )
            {
                process.setStartDate( new Date(System.currentTimeMillis()) );
                process.setEndDate( process.getStartDate() );
                process.setState( ProcessState.Started.getId() );
                processDAO.save( process );
            }
            else if(process.getState() == ProcessState.Resumed.getId() )
            {
                process.setEndDate( new Date(System.currentTimeMillis()) );
                process.setState( ProcessState.Started.getId() );
                processDAO.save( process );
            }
            else
            {
                process.setEndDate( new Date(System.currentTimeMillis()) );
                processDAO.save( process );
                
                if(!CollectionUtils.isEmpty( processResponse.getDetails() ))
                {
                    // ----------Remove previous responses -----------
                    if(processResponse.getId() > 0)
                    {
                        processResponseDAO.delete( processResponse.getId() );
                    }
                    // -----------------------------------------------
                    
                    Iterator<ProcessResponseDetail> itr = processResponse.getDetails().iterator();
                    
                    while (itr.hasNext()) 
                    {
                        ProcessResponseDetail responseDetail = itr.next();
                        if(responseDetail.getTaskDetail() == null) 
                            itr.remove();
                        else
                            responseDetail.setGrade( calculateGrade( process.getCurrentTask() , 
                                                                     responseDetail.getTaskDetail().getId() ));   
                    }
                   
                    
                    processResponse.setProcessLazy( process );
                    processResponseDAO.save( processResponse );
                }
            }
            
            //*******************************************************
            response = processResponseDAO.getByProcessAndTaskId( process.getId(), getTaskIdByIndex(nextTaskIndex, process) );
            
            if(response == null)
            {
                response = new ProcessResponse(); 
            }
            
            AssessmentTask task = getTaskByIndex(nextTaskIndex, process );
            
            process.setCurrentTask( task );
            response.setTask( task ); 
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
    

    /* *********************************
     * 
     */
    @Override
    public void endProcess( AssessmentProcess process , ProcessResponse processResponse)
    {
        if(!CollectionUtils.isEmpty( processResponse.getDetails() ))
        {
            //processResponse.setProcess( process );
            //processResponseDAO.save( processResponse );
        }
        
        process.setState( ProcessState.Finished.getId() );
        process.setEndDate( new Date(System.currentTimeMillis()) );
        process = processDAO.save( process );
    }
    
    
    /**************************************************
     * 
     */
    private long getTaskIdByIndex( int taskIndex, AssessmentProcess process )
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
    private AssessmentTask getTaskByIndex( int taskIndexm, AssessmentProcess process )
    {
        return taskDAO.getByTaskId( getTaskIdByIndex(taskIndexm, process) );
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public float calculateGrade( float percentage, float grade )
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
    @Override
    public float calculateGrade( AssessmentTask task , long taskDetailId)
    {
        AssessmentTaskDetail detail = null;
        
        for(AssessmentTaskDetail sdetail: task.getDetails())
        {
            if(sdetail.getId() == taskDetailId)
            {
                detail = sdetail;
                break;
            }
        }
        
        return calculateGrade( detail == null ? 0 : detail.getItemGradeRatio(), task.getItemGrade() );
    }
    
    
    /**************************************************
     * 
     */
    private boolean isTimeExpired( int time, Date lastActiveDate )
    {
        if(lastActiveDate == null)
            return false;
        
        lastActiveDate = DateUtils.addSeconds( lastActiveDate, (time * 60) );
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
    public Page<Assessment> getAssignedAssessments( long userId, Pageable pageable )
    {
        return assessmentDAO.getAssignedByUserId( userId, pageable ); 
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
    public AssessmentResult getAssessmentResult( long processId )
    {
        return processDAO.getResultById( processId );
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Page<AssessmentProcess> getProcessList( String lastName, Date startDateFrom , Pageable pageable )
    {
        return processDAO.getAll( lastName, startDateFrom, pageable );
    }


    /**************************************************
     * 
     */
    @Override
    public Page<Object> getAssessmentResults( String lastName, Date startDateFrom , Pageable pageable )
    {
        return processDAO.getResults( lastName, startDateFrom, pageable );
    }


    /**************************************************
     * 
     */
    @Override
    public Page<ProcessResponse> getProcessResponses( long processId , Pageable pageable )
    {
        return processResponseDAO.getByProcessId( processId, pageable );
    }


    /**************************************************
     * 
     */
    @Override
    public String getResponseContent( long responseId )
    {
        return processResponseDAO.getResponseContent(responseId);
    }
    

    /**************************************************
     * 
     */
    @Override
    public void evaluateResponse( User user, float grade, String comment, long responseDetailId )
    {
        ProcessResponseDetail respDetail = responseDetailDAO.findOne( responseDetailId );
            
        if(respDetail != null)
        {
            respDetail.setGrade( grade );
            respDetail = responseDetailDAO.save( respDetail );
            
            ProcessResponseEvaluation evaluation = new ProcessResponseEvaluation();
            evaluation.setUser( user );
            evaluation.setGrade( grade );
            evaluation.setResponseDetail( respDetail );
            evaluation.setComment( comment );
            evaluationDAO.save( evaluation );
        }
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
