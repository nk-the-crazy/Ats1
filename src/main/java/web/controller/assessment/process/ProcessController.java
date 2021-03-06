package web.controller.assessment.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.exceptions.assessment.TimeExpiredException;
import common.exceptions.security.AccessDeniedException;
import common.utils.StringUtils;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.ProcessSession;
import model.common.session.SessionData;
import model.identity.User;
import model.report.assessment.AssessmentResult;
import service.api.assessment.AssessmentManager;
import web.view.ModelView;


@Controller
public class ProcessController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);
    private static final String ACTIVE_PROCESS  = "activeProcess";
    private static final String PROCESS_SESSION = "processSession";
    //---------------------------------
    
    @Autowired
    private AssessmentManager assessmentManager;
    
   
    /*******************************************************
     * 
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) 
    {
        SimpleDateFormat dateFormat = StringUtils.getShortDateFormat();
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    
    /*******************************************************
     *  Initialize Assessment Process
     */
    @RequestMapping( value = "/test_process_init.do")
    public ModelAndView initAssessementProcess(@RequestParam( "assessment_id" ) long assessmentId , 
                                               @AuthenticationPrincipal SessionData userSession,
                                               HttpSession session)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            AssessmentProcess process = assessmentManager.initProcess( assessmentId, userSession.getUser() );

            session.setAttribute(ACTIVE_PROCESS, process );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_INIT_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error initializing assessment :", e ); 
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );
        }
        
        return model;
        
    }


    /*******************************************************
     * Start Assessment process
     */
    @RequestMapping( value = "/test_process_start.do")
    public String startAssessementProcess( @RequestParam( name = "entryCode", defaultValue = "", required = false ) String entryCode,
                                           @RequestParam( name = "taskIndex", defaultValue = "0", required = false ) int taskIndex, 
                                           @RequestParam( name = "assessment_id", defaultValue = "0", required = false ) long assessmentId, 
                                           HttpSession session, Model model,
                                           @AuthenticationPrincipal SessionData userSession)
    {
        try
        {
            AssessmentProcess activeProcess = (AssessmentProcess)session.getAttribute( "activeProcess" );
            
            if(activeProcess == null)
                activeProcess = assessmentManager.initProcess( assessmentId, userSession.getUser() );
            
            ProcessSession pSession = assessmentManager.startProcess(activeProcess, taskIndex , entryCode);
            session.removeAttribute( ACTIVE_PROCESS );
            session.setAttribute(PROCESS_SESSION, pSession );
            
            return ModelView.VIEW_ASMT_PROCESS_START_PAGE; 
        }
        catch ( TimeExpiredException e )
        {
            model.addAttribute( "errorData", "message.error.assessment.time.expired");
            return ModelView.VIEW_SYSTEM_ERROR_PAGE;
        }   
        catch ( AccessDeniedException e )
        {
            model.addAttribute( "errorMessage", "message.error.assessment.invalid.code");
            return "forward:/test_process_init.do?assessment_id="+assessmentId;
        }
        catch(Exception e)
        {
            logger.error( " **** Error starting assessment Details:", e ); 
            model.addAttribute( "errorData", "message.error.system");
            model.addAttribute( "errorDetails", e.toString() );
        }
        
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
        
    }
    

    /*******************************************************
     * 
     */
    @RequestMapping( value = "/test_process_end.do")
    public String endAssessementProcess( HttpSession session, Model model )
    {
        try
        {
            ProcessSession processSession = (ProcessSession)session.getAttribute( PROCESS_SESSION );
            assessmentManager.endProcess( processSession);
            
            //------- Remove from session --------
            session.removeAttribute( ACTIVE_PROCESS );
            session.removeAttribute( PROCESS_SESSION );
            //------------------------------------
            return "redirect:test_process_end.vw?asmt_process_id=" + processSession.getProcessId();
            
        }
        catch(Exception e)
        {
            logger.error( " **** Error ending assessment Details:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
            return ModelView.VIEW_SYSTEM_ERROR_PAGE;
        }

    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/test_process_end.vw")
    public String endAssessementProcessView(@RequestParam( name = "asmt_process_id") long processId, Model model)
    {
        Object process = assessmentManager.getAssessmentResult( processId );
        model.addAttribute( "assessmentResult", process );
        
        return ModelView.VIEW_ASMT_PROCESS_END_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_process_list.vw")
    public ModelAndView getAssessmentResultListView( @RequestParam( name = "lastName" , defaultValue = "", required = false ) 
                                                     String lastName, 
                                                     @RequestParam( name = "startDateFrom" , defaultValue = "01.01.2016", required = false ) 
                                                     String startDateFromStr, 
                                                     @RequestParam( name = "startDateTo" , defaultValue = "01.01.2020", required = false ) 
                                                     String startDateToStr,
                                                     Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
            
        try
        {
            Date startDateFrom = StringUtils.stringToDate( startDateFromStr );
            //Date startDateTo = StringUtils.stringToDate( startDateToStr );
            
            Page<AssessmentProcess> resultsPage = assessmentManager.getProcessList( lastName, startDateFrom,pageable );
                    
            model.addObject( "resultsPage", resultsPage );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_LIST_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment process list:", e );        
            model.addObject( "errorData", e );
        }
        
        return model;
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_process_details.vw")
    public ModelAndView getAssessmentResultDetailsView( @RequestParam( name = "asmt_process_id" ) long processId,
                                                        Pageable pageable)
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
            
        try
        {
            AssessmentResult result = assessmentManager.getAssessmentResultDetail( processId);
            User userDetails = assessmentManager.getProcessUserDetails(result.getUserId());
                    
            model.addObject( "assessmentResult", result );
            model.addObject( "userDetails", userDetails );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment process details:", e );        
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_response_rate.mvw")
    public ModelAndView getResponseEvaluationView(@RequestParam( "asmt_response_id" ) long responseId)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            model.setViewName(ModelView.VIEW_ASMT_RESPONSE_RATE_MD_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting ResponseEvaluationView :", e ); 
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
        
    }

    
}
