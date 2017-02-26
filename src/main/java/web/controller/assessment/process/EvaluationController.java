package web.controller.assessment.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web.common.view.ModelView;

@Controller
public class EvaluationController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);
    //---------------------------------
    
    @Autowired
    //private AssessmentManager assessmentManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_response_evaluation.vw")
    public ModelAndView getResponseEvaluationView(@RequestParam( "asmt_response_detail_id" ) long responseDetailId)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            model.setViewName( ModelView.VIEW_ASMT_RESPONSE_EVALUATION_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting ResponseEvaluationView :", e ); 
            model.addObject( "errorData", e );
        }
        
        return model;
        
    }
}
