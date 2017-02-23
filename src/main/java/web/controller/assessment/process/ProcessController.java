package web.controller.assessment.process;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.exceptions.assessment.TimeExpiredException;
import model.assessment.process.AssessmentProcess;
import model.assessment.process.ProcessResponse;
import model.common.session.SessionData;
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
    @RequestMapping( value = "/asmt_process_init.do")
    public ModelAndView initAssessementProcess(@RequestParam( "assessment_id" ) long assessmentId , HttpSession session)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            AssessmentProcess process = assessmentManager.initProcess( assessmentId, sData.getUser().getId() );

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
    public ModelAndView endAssessementProcess( @ModelAttribute( "processResponse" ) ProcessResponse processResponse,
                                               HttpSession session )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            AssessmentProcess process = assessmentManager.endProcess( sData.getAssessmentProcess() , processResponse);

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
    
}
