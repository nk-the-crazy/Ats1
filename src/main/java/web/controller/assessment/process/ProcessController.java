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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.exceptions.assessment.TimeExpiredException;
import common.utils.StringUtils;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.ProcessResponse;
import model.common.session.SessionData;
import model.report.assessment.AssessmentResult;
import service.api.assessment.AssessmentManager;
import web.common.view.ModelView;


@Controller
public class ProcessController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);
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
     * 
     */
    @RequestMapping( value = "/asmt_process_init.do")
    public ModelAndView initAssessementProcess(@RequestParam( "assessment_id" ) long assessmentId , HttpSession session)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            AssessmentProcess process = assessmentManager.initProcess( assessmentId, sData.getUser() );

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
    public String startAssessementProcess( @RequestParam( name = "taskIndex" , defaultValue = "0", required = false ) int nextTaskIndex, 
                                           @ModelAttribute( "processResponse" ) ProcessResponse processResponse,
                                           HttpSession session, Model model )
    {
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            processResponse = assessmentManager.startProcess(sData.getAssessmentProcess(), processResponse, nextTaskIndex);
            
            model.addAttribute( "processResponse" , processResponse );
            
            return ModelView.VIEW_ASMT_PROCESS_START_PAGE; 
        }
        catch ( TimeExpiredException e )
        {
            model.addAttribute( "errorMessage", "message.error.assessment.time.expired");
            return ModelView.VIEW_ASMT_PROCESS_START_PAGE;
        }        
        catch(Exception e)
        {
            logger.error( " **** Error starting assessment Details:", e ); 
            model.addAttribute( "errorData", e );
        }
        
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
        
    }
    

    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_process_end.do")
    public String endAssessementProcess( @ModelAttribute( "processResponse" ) ProcessResponse processResponse,
                                         HttpSession session, Model model )
    {
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            assessmentManager.endProcess( sData.getAssessmentProcess() , processResponse);
            
            //------- Remove from session --------
            long processId = sData.getAssessmentProcess().getId();
            sData.setAssessmentProcess( null );
            //------------------------------------
            return "redirect:asmt_process_end.vw?asmt_process_id=" + processId;
            
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
    @RequestMapping("/asmt_process_end.vw")
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
    public ModelAndView getAssessmentResultListView(  @RequestParam( name = "lastName" , defaultValue = "", required = false ) 
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
            
            Page<Object> resultsPage = assessmentManager.getAssessmentResults( lastName, startDateFrom,pageable );
                    
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
            AssessmentResult result = assessmentManager.getAssessmentResult( processId);
            Page<ProcessResponse> responsesPage = assessmentManager.getProcessResponses( processId, pageable );
                    
            model.addObject( "assessmentResult", result );
            model.addObject( "responsesPage", responsesPage );
            model.setViewName( ModelView.VIEW_ASMT_PROCESS_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment process details:", e );        
            model.addObject( "errorData", e );
        }
        
        return model;
    }

    
}
