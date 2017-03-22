package web.controller.assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import web.model.assessment.task.TaskDTO;


@RestController
@RequestMapping("/rest/assessment/test")
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
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/task/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getTaskList( 
                       @RequestParam(value = "assessment_id", defaultValue = "0") long assessmentId,
                       @RequestParam(value = "start", defaultValue = "") int start, 
                       @RequestParam(value = "length", defaultValue = "") int length, 
                       @RequestParam(value = "draw", defaultValue = "") int draw , 
                       @RequestParam(value = "search", defaultValue = "") String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<Object> tasksPage = assessmentManager.getAssessmentTasks( assessmentId, pageable );
            
            Map<String,Object> data = new HashMap<>();
            
            data.put("data", createTaskDTO(tasksPage.getContent()));
            data.put("draw", draw);
            data.put("recordsTotal",tasksPage.getTotalElements());
            data.put("recordsFiltered",tasksPage.getTotalElements());
            
            return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
        }
        catch(Exception e) 
        {
            logger.error( " Error getting task list:", e );
            return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
    
    
    /*******************************************************
     * 
     */
    private List<TaskDTO> createTaskDTO(List<Object> taskObjects)
    {
        List<TaskDTO>  taskDTOList = new ArrayList<TaskDTO>();
        
        for(Object task:taskObjects)
        {
            Object[] obj = (Object[])task;
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId( (long)obj[1] );
            taskDTO.setItemName( (String)obj[2] );
            taskDTO.setItemContent( (String)obj[3] );
            taskDTO.setModeType( (short)obj[4] );
            
            taskDTOList.add( taskDTO );
        }
        
        return taskDTOList;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/task/remove",
                    method = RequestMethod.GET, 
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public String removeTaskFromAssessment(@RequestParam(value = "assessment_id") long assessmentId,
                                           @RequestParam(value = "asmt_task_id") long taskId) 
    {
        try 
        {
            assessmentManager.removeTask(assessmentId , taskId);
            return "";
        }
        catch(Exception e) 
        {
            logger.error( " Error removing task from assessment:", e );
            return null;
        }
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/task/add",
                    method = RequestMethod.POST, 
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public String addUsersToGroup(@RequestParam(value = "assessment_id") long assessmentId,
                                  @RequestParam(value = "taskIds") List<Long> taskIds) 
    {
        try 
        {
            assessmentManager.addTasks(assessmentId , taskIds);
            return "";
        }
        catch(Exception e) 
        {
            logger.error( " Error adding tasks to assessment:", e );
            return null;
        }
    }
    
    
}
