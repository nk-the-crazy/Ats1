package web.controller.assessment.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.assessment.task.AssessmentTaskCategory;
import service.api.assessment.AssessmentTaskManager;
import web.model.assessment.task.TaskCategoryDTO;


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
            logger.error( " Error getting user list:", e );
            return Collections.emptyList();
        }
    } 
    
    
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
    
}
