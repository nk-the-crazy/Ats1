package web.controller.assessment;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.utils.StringUtils;
import model.assessment.Assessment;
import model.assessment.AssessmentTask;
import model.common.session.SessionData;
import service.api.assessment.AssessmentManager;
import web.common.view.ModelView;

@Controller
public class AssessmentController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);
    //---------------------------------

    @Autowired
    private AssessmentManager assessmentManager;
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/assessment_private list.vw")
    public ModelAndView getAssessmentPrivateList( HttpSession session , Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
        try
        {
            SessionData sData =  (SessionData)session.getAttribute( "sessionData" );
                    
            if(sData != null)
            {
                Page<Assessment> asmtsPage = assessmentManager.getAssessmentsByUserId( sData.getUser().getId(), pageable );
                    
                model.addObject( "assessmentsPage", asmtsPage );
            }
            
            model.setViewName( ModelView.VIEW_ASMT_LIST_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment list:", e );        
        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/assessment_list.vw")
    public ModelAndView getAssessmentList( @RequestParam( name = "assessmentName" , defaultValue = "", required = false ) 
                                            String assessmentName, 
                                            @RequestParam( name = "startDateFrom" , defaultValue = "01.01.2016", required = false ) 
                                            String startDateFromStr, 
                                            @RequestParam( name = "startDateTo" , defaultValue = "01.01.2020", required = false ) 
                                            String startDateToStr, 
                                            @RequestParam( name = "assessmentType" , defaultValue = "0", required = false ) 
                                            short assessmentType,
                                            Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
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
        }
        
        return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/assessment_details.vw")
    public ModelAndView getAssessmentDetails(@RequestParam( "assessment_id" ) long assessmentId, Pageable pageable )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
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
        }
        
        return model;
        
    }
    

    

}
