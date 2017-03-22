package web.controller.assessment.task;

import java.util.ArrayList;
import java.util.Collections;
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

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import service.api.assessment.AssessmentTaskManager;
import web.model.assessment.task.TaskCategoryDTO;
import web.model.assessment.task.TaskDTO;


@RestController
@RequestMapping("/rest/assessment/task")
public class TaskRestController
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( TaskRestController.class );
    // ---------------------------------


    @Autowired
    private AssessmentTaskManager taskManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getTaskList( 
                       @RequestParam(value = "start", defaultValue = "") int start, 
                       @RequestParam(value = "length", defaultValue = "") int length, 
                       @RequestParam(value = "draw", defaultValue = "") int draw , 
                       @RequestParam(value = "search[value]", defaultValue = "") String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<Object> tasksPage = taskManager.getTasksByDetails( search, "", (short)0, pageable );
            
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
    @RequestMapping( value = "/category/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TaskCategoryDTO> getCategoryList() 
    {
        try 
        {
            List<TaskCategoryDTO> tree = new ArrayList<TaskCategoryDTO>(); 
            
            for(AssessmentTaskCategory cat:taskManager.getCategoryTree() )
            {
                TaskCategoryDTO dto = new TaskCategoryDTO();
                dto.setName( cat.getName() );
                dto.setId( cat.getId() );
                
                for(AssessmentTaskCategory child:cat.getChildren())
                {
                    dto.addChild(getChildren(child));
                }
                
                tree.add( dto );
            }
            return tree;
        }
        catch(Exception e) 
        {
            logger.error( " Error getting category list:", e );
            return Collections.emptyList();
        }
    } 
    
    
    /*******************************************************
     * 
     */
    private TaskCategoryDTO getChildren(AssessmentTaskCategory child)
    {
        TaskCategoryDTO dto = new TaskCategoryDTO();
        dto.setName( child.getName() );
        dto.setId( child.getId() );
        
        for(AssessmentTaskCategory subChild:child.getChildren())
        {
            dto.addChild(getChildren(subChild));
        }
        
        return dto;
    }
    
    
    /*******************************************************
     * 
     */
    private List<TaskDTO> createTaskDTO(List<Object> taskObjects)
    {
        List<TaskDTO>  taskDTOList = new ArrayList<TaskDTO>();
        
        for(Object object:taskObjects)
        {
            Object[] obj = (Object[])object;
            AssessmentTask task  = (AssessmentTask)obj[0];
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId( task.getId() );
            taskDTO.setItemName( task.getItemName() );
            taskDTO.setItemContent( task.getItemContent());
            taskDTO.setModeType( task.getModeType());
            
            taskDTOList.add( taskDTO );
        }
        
        return taskDTOList;
    }
    
}
