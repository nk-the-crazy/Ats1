package web.controller.assessment.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import common.exceptions.assessment.TimeExpiredException;
import common.exceptions.security.AccessDeniedException;
import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessResponseDetail;
import model.assessment.process.ProcessSession;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskDetail;
import service.api.assessment.AssessmentManager;
import web.model.assessment.process.ProcessResponseDTO;
import web.model.assessment.process.ProcessResponseDataDTO;
import web.model.assessment.process.ProcessResponseDetailDTO;
import web.model.assessment.task.TaskDTO;
import web.model.assessment.task.TaskDetailDTO;


@RestController
@RequestMapping("/rest/assessment")
public class ProcessRestController
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( ProcessRestController.class );
    //private static final String ACTIVE_PROCESS  = "activeProcess";
    private static final String PROCESS_SESSION = "processSession";    
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
    public String assessmentProcessEnd(HttpSession session) 
    {
        try 
        {
            ProcessSession pSession = (ProcessSession)session.getAttribute( PROCESS_SESSION );
            assessmentManager.endProcess( pSession );
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
                       @RequestParam(value = "asmt_process_id", defaultValue = "0")  long processId,
                       @RequestParam(value = "start", required = false, defaultValue = "0") int start, 
                       @RequestParam(value = "length",required = false, defaultValue = "1") int length, 
                       @RequestParam(value = "draw", required = false, defaultValue = "1")  int draw , 
                       @RequestParam(value = "search", required = false, defaultValue = "") String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<Object> responsePage = assessmentManager.getProcessResponses( processId, pageable );
            
            Map<String,Object> data = new HashMap<>();
            
            data.put("data", createProcessResponseListDTO(responsePage.getContent()));
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
    @RequestMapping( value = "/response/wlist", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getResponseWList( 
                       @RequestParam(value = "asmt_process_id", defaultValue = "0")  long processId,
                       @RequestParam(value = "start", required = false, defaultValue = "0") int start, 
                       @RequestParam(value = "length",required = false, defaultValue = "1") int length, 
                       @RequestParam(value = "draw", required = false, defaultValue = "1")  int draw , 
                       @RequestParam(value = "search", required = false, defaultValue = "") String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<Object> responsePage = assessmentManager.getProcessWrongResponses( processId, pageable );
            
            Map<String,Object> data = new HashMap<>();
            
            data.put("data", createProcessResponseListDTO(responsePage.getContent()));
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
    private List<ProcessResponseDataDTO> createProcessResponseListDTO(List<Object> responses)
    {
        List<ProcessResponseDataDTO> dtoList =  new ArrayList<ProcessResponseDataDTO>();
        for(Object object:responses)
        {
            Object obj[] = (Object[])object;
            ProcessResponseDataDTO  dto = new ProcessResponseDataDTO((AssessmentTask)obj[1],
                                                             (ProcessResponse)obj[0], null, null);
            dtoList.add( dto );
        }
        
        return dtoList; 
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/process/task", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProcessResponseDTO> getProcessResponse( @RequestParam( name = "taskIndex" , defaultValue = "0", required = false ) int taskIndex, 
                                                   @ModelAttribute( "processResponse" ) ProcessResponse processResponse,
                                                   HttpSession session) 
    {
        try 
        {

            ProcessSession pSession = (ProcessSession)session.getAttribute( PROCESS_SESSION );
            pSession = assessmentManager.rollProcess(pSession, processResponse, taskIndex);
            
            return new ResponseEntity<ProcessResponseDTO>(createProcessResponseDTO(pSession.getProcessResponse()), HttpStatus.OK);
        }
        catch(TimeExpiredException e) 
        {
            logger.error( " Time expired for process:", e );
            return new ResponseEntity<ProcessResponseDTO>(HttpStatus.GATEWAY_TIMEOUT); //504
        }
        catch ( AccessDeniedException e )
        {
            logger.error( " Access Denied:", e );
            return new ResponseEntity<ProcessResponseDTO>(HttpStatus.FORBIDDEN); //403
        }
        catch(Exception e) 
        {
            logger.error( " Error getting process task details:", e );
            return new ResponseEntity<ProcessResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }
    
    
    /*******************************************************
     * 
     */
    private ProcessResponseDTO createProcessResponseDTO(ProcessResponse processResponse)
    {
        ProcessResponseDTO responseDTO = new ProcessResponseDTO();
        
        //------------ responseDetail ----------------
        
        for(ProcessResponseDetail resDetail:processResponse.getDetails())
        {
            ProcessResponseDetailDTO resDetailDTO = new ProcessResponseDetailDTO();
            
            resDetailDTO.setId( resDetail.getId() );
            resDetailDTO.setItemResponse( resDetail.getItemResponse() );
            
            if(resDetail.getTaskDetail() != null)
            {
                resDetailDTO.setTaskDetailId( resDetail.getTaskDetail().getId() );
            }
            
            responseDTO.addDetail( resDetailDTO );
        }
        //--------------------------------------------
        
        //------------ TaskDTO ----------------
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId( processResponse.getTask().getId() );
        taskDTO.setItemContent( processResponse.getTask().getItemContent() );
        taskDTO.setModeType( processResponse.getTask().getModeType() );
        
        for(AssessmentTaskDetail taskDetail:processResponse.getTask().getDetails())
        {
            TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
            taskDetailDTO.setId( taskDetail.getId() );
            taskDetailDTO.setItemDetail( taskDetail.getItemDetail() );
            
            taskDTO.addTaskDetail( taskDetailDTO );
        }
        //-------------------------------------
        
        
        responseDTO.setTask( taskDTO );
        responseDTO.setId( processResponse.getId() );
        responseDTO.setStatus( processResponse.getStatus() );
        responseDTO.setPrevResponseStatus( processResponse.getPrevResponseStatus() );
        
        return responseDTO; 
    }

}
