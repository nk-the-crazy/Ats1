package web.controller.assessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.api.assessment.AssessmentManager;


@RestController
@RequestMapping("/rest/assessment")
public class AssessmentRestController
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( AssessmentRestController.class );
    // ---------------------------------


    @Autowired
    private AssessmentManager assessmentManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/process/start")
    public String assessmentProcessStart() 
    {
        try 
        {
            return "";
            //return personManager.generateCode();
        }
        catch(Exception e) 
        {
            logger.error( " Assessment Process Start RestController Error:", e );
            return null;
        }
    }    
   
    /*******************************************************
     * 
     */
    @RequestMapping("/process/end")
    public String assessmentProcessEnd() 
    {
        try 
        {
            assessmentManager.endProcess( null , null );
            return "";
        }
        catch(Exception e) 
        {
            logger.error( " Assessment Process End RestController Error:", e );
            return null;
        }
    }    
}
