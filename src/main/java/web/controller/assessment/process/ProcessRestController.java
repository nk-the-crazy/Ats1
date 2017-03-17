package web.controller.assessment.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessResponseDetail;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskDetail;
import service.api.assessment.AssessmentManager;
import web.model.assessment.process.ProcessResponseDTO;


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
                    produces = "text/plain;charset=UTF-8")
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
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/response/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getResponseList( 
                       @RequestParam(value = "asmt_process_id", defaultValue = "0") 
                       long processId,
                       @RequestParam(value = "start", required = false, defaultValue = "0") 
                       int start, 
                       @RequestParam(value = "length",required = false, defaultValue = "1") 
                       int length, 
                       @RequestParam(value = "draw", required = false, defaultValue = "1") 
                       int draw , 
                       @RequestParam(value = "search", required = false, defaultValue = "") 
                       String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<Object> responsePage = assessmentManager.getProcessResponses( processId, pageable );
            
            Map<String,Object> data = new HashMap<>();
            
            data.put("data", createProcessResponseDTO(responsePage.getContent()));
            data.put("draw", draw);
            data.put("recordsTotal",responsePage.getTotalElements());
            data.put("recordsFiltered",responsePage.getTotalElements());
            
            return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
        }
        catch(Exception e) 
        {
            logger.error( " Error getting response list:", e );
            return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
    
    
    /*******************************************************
     * 
     */
    private List<ProcessResponseDTO> createProcessResponseDTO(List<Object> responses)
    {
        List<ProcessResponseDTO> dtoList =  new ArrayList<ProcessResponseDTO>();
        for(Object object:responses)
        {
            Object obj[] = (Object[])object;
            ProcessResponseDTO  dto = new ProcessResponseDTO((AssessmentTask)obj[1],
                                                             (ProcessResponse)obj[0],
                                                             (ProcessResponseDetail)obj[2],
                                                             (AssessmentTaskDetail)obj[3]);
            dtoList.add( dto );
        }
        
        return dtoList; 
    }

}
