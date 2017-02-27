package web.controller.assessment.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.api.assessment.AssessmentManager;


@RestController
@RequestMapping("/rest/assessment")
public class ProcessRestController
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( ProcessRestController.class );
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
    
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/response/content", 
                    method = RequestMethod.GET, 
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public String getResponseContent( @RequestParam( "asmt_response_detail_id" ) long responseDetailId ) 
    {
        try 
        {
            return assessmentManager.getResponseContent( responseDetailId );
        }
        catch(Exception e) 
        {
            logger.error( " Error getting response content:", e );
            return null;
        }
    }    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/response/evaluate", 
                    method = RequestMethod.POST, 
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public String getEvaluateResponse(@RequestParam( "asmt_response_detail_id" ) long responseDetailId ,
                                      @RequestParam( "grade" ) float grade ,
                                      @RequestParam( "comment" ) String comment) 
    {
        try 
        {
            assessmentManager.evaluateResponse( null, grade, comment, responseDetailId );
            return "Success";
        }
        catch(Exception e) 
        {
            logger.error( " Error getting response content:", e );
            return "";
        }
    }    
    

}
