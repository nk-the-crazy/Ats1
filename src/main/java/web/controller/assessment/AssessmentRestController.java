package web.controller.assessment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @RequestMapping( value = "/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getResponseList( 
                       @RequestParam(value = "start", required = false, defaultValue = "") 
                       int start, 
                       @RequestParam(value = "length",required = false, defaultValue = "") 
                       int length, 
                       @RequestParam(value = "draw", required = false, defaultValue = "") 
                       int draw , 
                       @RequestParam(value = "search", required = false, defaultValue = "") 
                       String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<Object> assessmentPage =  assessmentManager.getAssessmentResults( "", null, pageable );
            
            Map<String,Object> data = new HashMap<>();
            
            data.put("data", assessmentPage.getContent());
            data.put("draw", draw);
            data.put("recordsTotal",assessmentPage.getTotalElements());
            data.put("recordsFiltered",assessmentPage.getTotalElements());
            
            return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
        }
        catch(Exception e) 
        {
            logger.error( " Error getting response list:", e );
            return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
    
}
