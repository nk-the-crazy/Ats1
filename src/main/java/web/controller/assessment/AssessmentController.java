package web.controller.assessment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import common.utils.StringUtils;
import model.assessment.Assessment;
import model.assessment.process.ProcessResponse;
import model.assessment.task.AssessmentTask;
import model.common.session.SessionData;
import model.report.assessment.AssessmentResult;
import service.api.assessment.AssessmentManager;
import service.api.assessment.AssessmentTaskManager;
import service.api.group.GroupManager;
import web.view.ModelView;

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
    @RequestMapping( value = "/test_list_user.vw")
    
    public ModelAndView getUserAssessmentsList( @AuthenticationPrincipal SessionData sData, Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
            
        try
        {
            if(sData != null)
            {
                Page<Assessment> asmtsPage = assessmentManager.getAssignedAssessments( sData.getUser().getId(), pageable );
                    
                model.addObject( "assessmentsPage", asmtsPage );
            }
            
            model.setViewName( ModelView.VIEW_ASMT_LIST_USER_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting User assigned Assessment list:", e );        
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_test_list.vw")
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
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_test_details.vw")
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
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
        
    }

    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_test_register.vw" ) 
    public String registerAssessmentView(Model model)
    {
        model.addAttribute( "groupShortList", groupManager.getGroupShortListByName( "" ));
        model.addAttribute( "categoryShortList", taskManager.getCategoryShortList( "" ) );
        
        return ModelView.VIEW_ASMT_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_test_register.do" )
    public String registerAssessment( @ModelAttribute( "assessment" ) Assessment assessment,
                                      @RequestParam( name = "participantIds" , required = false ) List<Long> participantIds,
                                      @AuthenticationPrincipal SessionData sData,
                                      Model model)
    {
        try
        {
            assessment = assessmentManager.createAssessment( assessment, sData.getUser(), participantIds);
            return "redirect:asmt_test_details.vw?assessment_id=" + assessment.getId();
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
    @RequestMapping( value = "/report_result_list.vw")
    public ModelAndView getAssessmentResultListView( @RequestParam( name = "outputType" , defaultValue = "1", required = false ) 
                                                     int outputType,
                                                     @RequestParam( name = "lastName" , defaultValue = "", required = false ) 
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
            
            if(outputType == 1)
            {
                Page<Object> resultsPage = assessmentManager.getAssessmentResults( lastName, startDateFrom,pageable );
                        
                model.addObject( "resultsPage", resultsPage );
                model.setViewName( ModelView.VIEW_REPORT_RESULT_LIST_PAGE);
            }
            else if(outputType == 2)
            {
                Page<Object> resultsPage = assessmentManager.getAssessmentResults( lastName, startDateFrom,new PageRequest(0, 100000) );
                
                return new ModelAndView( ModelView.VIEW_REPORT_RESULT_LIST_XLS, "resultsPage", resultsPage);
            }
            else if(outputType == 4)
            {
                Page<Object> resultsPage = assessmentManager.getAssessmentResults( lastName, startDateFrom,new PageRequest(0, 100000) );
                
                return new ModelAndView( ModelView.VIEW_REPORT_RESULT_LIST_PDF, "resultsPage", resultsPage);
            }
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment result list:", e );        
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/report_result_details.vw")
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
            model.setViewName( ModelView.VIEW_REPORT_RESULT_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment result details:", e );        
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
    }


}
