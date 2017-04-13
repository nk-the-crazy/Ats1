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
import common.utils.security.SecurityUtils;
import dao.api.assessment.AssessmentDAO;
import dao.api.assessment.process.ProcessDAO;
import dao.api.assessment.process.ProcessResponseDAO;
import dao.api.assessment.process.ProcessResponseDetailDAO;
import dao.api.assessment.process.ProcessResponseEvaluationDAO;
import dao.api.assessment.task.AssessmentTaskCategoryDAO;
import dao.api.assessment.task.AssessmentTaskDAO;
import dao.api.group.GroupDAO;
import dao.api.identity.UserDAO;
import model.assessment.Assessment;
import model.assessment.EvaluationMethod;
import model.assessment.options.TaskFormOptions;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessResponseDetail;
import model.assessment.process.ProcessResponseRate;
import model.assessment.process.ProcessResponseStatus;
import model.assessment.process.ProcessSession;
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
    UserDAO userDAO;

    @Autowired
    ProcessResponseDAO responseDAO;

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

    
    /* *************************************************
     * Init Assessment process
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
                process = createAssessmentProcess(assessment, user);
            }
            
            
            if(process.getState() != ProcessState.Finished.getId() )
            {
                if(process.getState() == ProcessState.Started.getId())
                {
                    process.setState( ProcessState.Resumed.getId() );
                }
            }
            
            return process;
        }
    }
    
    
    /* ************************************************
     * Start Assessment process
     * @throws TimeExpiredException 
     */
    @Override
    public ProcessSession startProcess(AssessmentProcess process, int taskIndex , String entryCode) throws TimeExpiredException
    {

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
                endProcess(process.getId());
                //----------------------------------------------
                throw new TimeExpiredException("Time is expired for Assessment:"+ process.getAssessment().getName());
            }
            //----------------------------------------------
            
            if(process.getState() == ProcessState.Ready.getId() )
            {
                process.setStartDate( new Date(System.currentTimeMillis()) );
                process.setEndDate( process.getStartDate() );
                process.setState( ProcessState.Started.getId() );
            }
            else if(process.getState() == ProcessState.Resumed.getId() )
            {
                process.setEndDate( new Date(System.currentTimeMillis()) );
            }
             
             
            process.setState( ProcessState.Started.getId() );
            process = processDAO.save( process );
            
            ProcessSession pSession = createProcessSession(process);
            pSession.setProcessResponse( getProcessResponseDetails( process.getId(),pSession.getTaskIdByIndex( taskIndex )) );
            
            return pSession;

        }
        catch ( TimeExpiredException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch ( AccessDeniedException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch(Exception e)
        {
            logger.error( e.toString(), e );
        }
        
        return null;
    }
    
    
    

    /* ************************************************
     * Start Assessment process
     * @throws TimeExpiredException 
     */
    @Override
    public ProcessSession rollProcess(ProcessSession processSession, ProcessResponse processResponse, int taskIndex) throws TimeExpiredException
    {
        try
        {
            int status = submitProcessResponse(processSession, processResponse);
            
            //Get next task
            processResponse = getProcessResponseDetails( processSession.getProcessId(), processSession.getTaskIdByIndex( taskIndex )); 
            processResponse.setPrevResponseStatus( status );
            processSession.setProcessResponse( processResponse );
        
        }
        catch ( TimeExpiredException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch ( AccessDeniedException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch(Exception e)
        {
            logger.error( e.toString(), e );
        }
        
        return processSession; 
     }
    
    
    /* ************************************************
     * Save and submit process responses
     * @throws TimeExpiredException 
     */
    @Override
    public Integer submitProcessResponse(ProcessSession processSession, ProcessResponse processResponse) throws TimeExpiredException
    {
        try
        {
            
            if(isTimeExpired( processSession.getAssessmentTime(), processSession.getStartDate() ))
            {
                //-----End Process if time expired -------------
                endProcess(processSession);
                //----------------------------------------------
                throw new TimeExpiredException("Time is expired for Assessment:"+ processSession.getAssessmentName());
            }
            
            if(processResponse != null && processResponse.getTask() != null && !CollectionUtils.isEmpty( processResponse.getDetails() ))
            {
                //------Remove Empty responses----
                Iterator<ProcessResponseDetail> itr = processResponse.getDetails().iterator();
                while (itr.hasNext()) 
                {
                    ProcessResponseDetail responseDetail = itr.next();
                    if(responseDetail.getTaskDetail() == null) 
                        itr.remove();
                }
                //---------------------------------
                AssessmentProcess process = processDAO.findOne( processSession.getProcessId() );
                process.setEndDate( new Date(System.currentTimeMillis()) );
                processDAO.save( process );
                //---------------------------------
                processResponse.setProcessLazy( process );
                processResponse.setStatus( ProcessResponseStatus.Responded.getId());
                processResponse.setGrade( calculateGrade(processSession , processResponse) );
                
                responseDAO.save( processResponse );
                
                return ProcessResponseStatus.Responded.getId();
            }
        }
        catch ( TimeExpiredException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch ( AccessDeniedException e )
        {
            logger.error( e.toString(), e );
            
            throw e;
        }
        catch(Exception e)
        {
            logger.error( e.toString(), e );
        }
        
        return ProcessResponseStatus.NoResponse.getId(); 
     }
    

    /* *********************************
     * 
     */
    @Override
    public void endProcess( ProcessSession processSession)
    {
        try
        {
            if(processSession == null )
            {
                throw new Exception();
            }

            
            endProcess( processSession.getProcessId());
        }
        catch(Exception e)
        {
            logger.error( "Error ending process session:" ,e );
        }
    }
    
    
    

    /* *********************************
     * 
     */
    @Override
    public void endProcess( long processId )
    {
        try
        {
            AssessmentProcess process = processDAO.getById( processId);
            
            if(process.getState() == ProcessState.Started.getId() || process.getState() == ProcessState.Resumed.getId())
            {
                process.setEndDate( new Date(System.currentTimeMillis()) );
            }
            
            process.setState( ProcessState.Finished.getId() );
            process = processDAO.save( process );
        }
        catch(Exception e)
        {
            logger.error( "Error ending process session:" ,e );
        }
    }
    
   
    /* *************************************************
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
    
    
    /* ************************************************
     * Start process session
     * @throws TimeExpiredException 
     */
    private ProcessSession createProcessSession( AssessmentProcess process )
    {
        ProcessSession processSession = new ProcessSession();
        
        processSession.setAssessmentId( process.getAssessment().getId() );
        processSession.setStartDate( process.getStartDate() );
        processSession.setEndDate( process.getEndDate() );
        processSession.setAssessmentName( process.getAssessment().getName() );
        processSession.setProcessId( process.getId() );
        processSession.setAssessmentTime(process.getAssessment().getTime());
        processSession.setEvaluationMethod( process.getAssessment().getEvaluationMethod() );
        
        for(ProcessResponse response: process.getResponses())
        {
            processSession.addTaskDetails( response.getTask().getId(), response.getStatus() );
        }
        
        return processSession;
    }
    
    
    /* ************************************************
     * Calculate grade based
     *  
     */
    private float calculateGrade( ProcessSession processSession, ProcessResponse processResponse )
    {
        AssessmentTask task = taskDAO.getByTaskId( processResponse.getTask().getId());
        
        float grade = 0;
        int trueCount = 0;
        int responseTrueCount = 0;
        
        
        for(AssessmentTaskDetail taskDetails:task.getDetails())
        {
            //--------------------------------------
            if(taskDetails.getItemGradeRatio() > 0) 
                trueCount++ ;
            //--------------------------------------
            
            for(ProcessResponseDetail dets:processResponse.getDetails())
            {
                if(dets.getTaskDetail() != null && taskDetails.getId() == dets.getTaskDetail().getId())
                {
                    if( taskDetails.getItemGradeRatio() > 0 )
                    {
                        responseTrueCount++;
                    }
                    
                    grade = getGradeRatio(grade , task.getItemGrade(), taskDetails.getItemGradeRatio());
                    
                    break;
                }  
            }
        }
        
        if( processSession.getEvaluationMethod() == EvaluationMethod.TrueAnswers.getId())
        {
            if(responseTrueCount == trueCount )
                return task.getItemGrade();
            else
                return 0;
        }
        
        
        return grade;
    }
    
    
    /* ************************************************
     * Calculate grade based
     *  
     */
    private float getGradeRatio(float totalGrade, float grade, float ratio)
    {
        totalGrade += (grade*ratio)/100;
        
        if(totalGrade < 0)
            return 0;
        else
            return totalGrade;
    }
    
   
    /**************************************************
     * 
     */
    @Override
    public AssessmentProcess createAssessmentProcess(Assessment assessment, User user )
    {
        AssessmentProcess process = null;

        try
        {
            process = createAssessmentProcess();
            process.setAssessment( assessment );
            process.setUser( user );
            process.setState( ProcessState.Ready.getId() );
            
            Page<AssessmentTask> tasksPage = taskDAO.getRandomIdByAssessmentId( assessment.getId(), 
                                                     new PageRequest(0,assessment.getTaskCount()) );
            
            for(AssessmentTask task: tasksPage.getContent())
            {
                ProcessResponse response = new ProcessResponse();
                response.setTask( task ); 
                process.addProcessResponse( response );
            }

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
            //-------- Generate Entry Code ---------
            assessment.setEntryCode( Integer.toString( SecurityUtils.generateShortRandom()));
            
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
    public Assessment getAssessment( long assessmentId )
    {
        return assessmentDAO.findOne( assessmentId ) ;  
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
    public AssessmentResult getAssessmentResultDetail( long processId )
    {
        return processDAO.getResultDetailsById( processId );
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
    public Page<Object> getProcessResponses( long processId , Pageable pageable )
    {
        return responseDAO.getByProcessId( processId, pageable );
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public ProcessResponse getProcessResponseDetails( long processId , long taskId)
    {
        ProcessResponse pres = responseDAO.getDetailsByProcessAndTaskId( processId, taskId );
        
        if(pres.getTask() != null)
        {
            if(pres.getTask().getDetails().isEmpty())
            {
                pres.getTask().getDetails().get( 0 ).getId();
            }
        }
        return pres;
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public User getProcessUserDetails( long userId )
    {
        return userDAO.getDetails( userId );
    }
    

    /**************************************************
     * 
     */
    @Override
    public String getResponseContent( long responseDetailId )
    {
        return responseDAO.getResponseContent(responseDetailId);
    }
    

    /**************************************************
     * 
     */
    @Override
    public void evaluateResponse( User user, float grade, String comment, long responseId )
    {
        ProcessResponse response = responseDAO.findOne( responseId );
            
        if(response != null)
        {
            response.setGrade( grade );
            response = responseDAO.save( response );
            
            ProcessResponseRate evaluation = new ProcessResponseRate();
            evaluation.setUser( user );
            evaluation.setGrade( grade );
            evaluation.setResponse( response );
            evaluation.setComment( comment );
            evaluationDAO.save( evaluation );
        }
    }
    

    /**************************************************
     * 
     */
    @Override
    public Page<Object> getAssessmentTasks( long assessmentId, Pageable pageable )
    {
        return taskDAO.getByAssessmentId( assessmentId, pageable );
    }
    
    

    /**************************************************
     * 
     */
    @Override
    public void removeTask( long assessmentId, long taskId )
    {
        Assessment asmt = assessmentDAO.findOne( assessmentId );
        
        if(asmt != null)
        {
            asmt.removeTask( taskDAO.findOne( taskId )  );
            asmt = assessmentDAO.saveAndFlush( asmt );          
        }
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public void addTasks( long assessmentId, List<Long> taskIds )
    {
        Assessment asmt = assessmentDAO.findOne( assessmentId );
        
        if(asmt != null)
        {
            for(long taskId : taskIds)
            {
                AssessmentTask task = taskDAO.findOne( taskId );
                
                if(!asmt.getTasks().contains( task ))
                {
                    asmt.addTask( task );
                }
            }
        } 
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
            
            //-------- Generate Entry Code ---------
            assessment.setEntryCode( Integer.toString( SecurityUtils.generateShortRandom()));
            
            return assessmentDAO.save( assessment );
        }
        catch(Exception e)
        {
            logger.error( "Error generating Assessment:", e );
        }
        
        return null;
    }


}
