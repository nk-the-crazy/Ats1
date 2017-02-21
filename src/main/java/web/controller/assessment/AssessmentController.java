package web.controller.assessment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.utils.StringUtils;
import model.assessment.Assessment;
import model.assessment.process.Process;
import model.assessment.process.ProcessResponse;
import model.assessment.task.AssessmentTask;
import model.common.session.SessionData;
import service.api.assessment.AssessmentManager;
import service.api.assessment.AssessmentTaskManager;
import service.api.group.GroupManager;
import web.common.view.ModelView;

@Controller
public class AssessmentController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);
    //---------------------------------

    @Autowired
    private AssessmentManager assessmentManager;
    
    @Autowired
    private GroupManager groupManager;
    
    @Autowired
    private AssessmentTaskManager taskManager;
    
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
     * 
     */
    @RequestMapping( value = "/asmt_list_user.vw")
    public ModelAndView getUserAssessmentsList( HttpSession session , Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
            
        try
        {
            SessionData sData =  (SessionData)session.getAttribute( "sessionData" );
                    
            if(sData != null)
            {
                Page<Assessment> asmtsPage = assessmentManager.getAssessmentsByUserId( sData.getUser().getId(), pageable );
                    
                model.addObject( "assessmentsPage", asmtsPage );
            }
            
            model.setViewName( ModelView.VIEW_ASMT_LIST_USER_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting User assigned Assessment list:", e );        
            model.addObject( "errorData", e );
        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_list.vw")
    public ModelAndView getAssessmentList(  @RequestParam( name = "assessmentName" , defaultValue = "", required = false ) 
                                            String assessmentName, 
                                            @RequestParam( name = "startDateFrom" , defaultValue = "01.01.2016", required = false ) 
                                            String startDateFromStr, 
                                            @RequestParam( name = "startDateTo" , defaultValue = "01.01.2020", required = false ) 
                                            String startDateToStr, 
                                            @RequestParam( name = "assessmentType" , defaultValue = "0", required = false ) 
                                            short assessmentType,
                                            Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
            
        try
        {
            Date startDateFrom = StringUtils.stringToDate( startDateFromStr );
            //Date startDateTo = StringUtils.stringToDate( startDateToStr );
            
            Page<Assessment> asmtsPage = assessmentManager.getAssessmentsByDetails( assessmentName, startDateFrom, assessmentType, pageable );
                    
            model.addObject( "assessmentsPage", asmtsPage );
            model.setViewName( ModelView.VIEW_ASMT_LIST_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment list:", e );        
            model.addObject( "errorData", e );
        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_details.vw")
    public ModelAndView getAssessmentDetails(@RequestParam( "assessment_id" ) long assessmentId, Pageable pageable )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            Assessment asmtDetails = assessmentManager.getAssessmentFullDetails( assessmentId );
            Page<AssessmentTask> assessmentTasks = assessmentManager.getAssessmentTasks( assessmentId, pageable );


            model.addObject( "assessmentDetails", asmtDetails );
            model.addObject( "assessmentTasks", assessmentTasks );
            model.setViewName( ModelView.VIEW_ASMT_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting assessment Details:", e );        
            model.addObject( "errorData", e );
        }
        
        return model;
        
    }
    

    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_process_init.do")
    public ModelAndView initAssessementProcess(@RequestParam( "assessment_id" ) long assessmentId , HttpSession session)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            Process process = assessmentManager.initProcess( assessmentId, sData.getUser().getId() );

            sData.setAssessmentProcess( process );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_INIT_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error initializing assessment :", e ); 
            model.addObject( "errorData", e );
        }
        
        return model;
        
    }
    


    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_process_start.do")
    public ModelAndView startAssessementProcess( @RequestParam( name = "taskIndex" , defaultValue = "0", required = false ) int nextTaskIndex, 
                                                 @ModelAttribute( "processTask" ) ProcessResponse processResponse,
                                                 HttpSession session )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            processResponse = assessmentManager.startProcess(sData.getAssessmentProcess(), processResponse, nextTaskIndex);
            model.addObject( "processResponse" , processResponse );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_START_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error starting assessment Details:", e ); 
            model.addObject( "errorData", e );
        }
        
        return model;
        
    }
    

    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_process_end.do")
    public ModelAndView endAssessementProcess( HttpSession session )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            Process process = assessmentManager.endProcess( sData.getAssessmentProcess());

            //------- Remove from session --------
            sData.setAssessmentProcess( null );
            //------------------------------------
            model.addObject( "assessmentProcess", process );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_END_PAGE); 
            
        }
        catch(Exception e)
        {
            logger.error( " **** Error ending assessment Details:", e ); 
            model.addObject( "errorData", e );
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_register.vw")
    public String registerAssessmentView(Model model)
    {
        model.addAttribute( "groupShortList", groupManager.getGroupShortListByName( "" ));
        model.addAttribute( "categoryShortList", taskManager.getCategoryShortList( "" ) );
        
        return ModelView.VIEW_ASMT_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_register.do")
    public String registerAssessment( @ModelAttribute( "assessment" ) Assessment assessment,
                                @RequestParam( name = "participantIds" , required = false ) List<Long> participantIds,
                                Model model, HttpSession session )
    {
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" ); 
            assessment = assessmentManager.createAssessment( assessment, sData.getUser(), participantIds);
            return "redirect:asmt_details.vw?assessment_id=" + assessment.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering assessment:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return registerAssessmentView(model);
    }
    
    

    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_results.vw")
    public String assessmentResultsView(Model model)
    {
        return ModelView.VIEW_ASMT_RESULTS_PAGE;
    }
    
    

    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_results_user.vw")
    public String assessmentUserResultsView(Model model)
    {
        return ModelView.VIEW_ASMT_RESULTS_USER_PAGE;
    }
    
    
}
