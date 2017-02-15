package web.controller.assessment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import service.api.assessment.AssessmentTaskManager;
import web.common.view.ModelView;


@Controller
public class AssessmentTaskController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentTaskController.class);
    //---------------------------------

    @Autowired
    private AssessmentTaskManager taskManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_task_list.vw")
    public ModelAndView getAssessmentTasks( @RequestParam( name = "taskItemName" , defaultValue = "", required = false ) 
                                            String taskItemName, 
                                            @RequestParam( name = "categoryName" , defaultValue = "", required = false ) 
                                            String categoryName, 
                                            @RequestParam( name = "taskModeType" , defaultValue = "0", required = false ) 
                                            short taskModeType,
                                            Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
        try
        {
            Page<AssessmentTask> tasksPage = taskManager.getTasksByDetails( taskItemName, categoryName, taskModeType, pageable );
                    
            model.addObject( "tasksPage", tasksPage );
            model.setViewName( ModelView.VIEW_ASMT_TASK_LIST_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment Task list:", e );        
        }
        
        return model;
    }
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_task_details.vw")
    public ModelAndView getUserDetailsView(@RequestParam( "asmt_task_id" ) long taskId)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            AssessmentTask taskDetails = taskManager.getTaskFullDetails( taskId);

            model.addObject( "taskDetails", taskDetails );
            model.setViewName( ModelView.VIEW_ASMT_TASK_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting assessment task Details:", e );        
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_category_list.vw")
    public ModelAndView getCategories( @RequestParam( name = "userName" , defaultValue = "", required = false ) 
                                  String userName, 
                                  @RequestParam( name = "lastName" , defaultValue = "", required = false ) 
                                  String lastName, 
                                  Pageable pageable )
    {
         ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
            try
            {
                List<AssessmentTaskCategory> categoryList = taskManager.getCategoryTree();
                        
                model.addObject( "categoryList", categoryList );
                model.setViewName( ModelView.VIEW_ASMT_CATEGORY_LIST_PAGE);
            }
            catch(Exception e)
            {
                logger.error( " **** Error getting Assessment Task Category list:", e );        
            }
            
            return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_category_details.vw")
    public ModelAndView getCategoryDetailsView(@RequestParam( "asmt_category_id" ) long categoryId, Pageable pageable)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            AssessmentTaskCategory categoryDetails = taskManager.getCategoryDetails( categoryId );
            Page< AssessmentTask> categoryTasks = taskManager.getCategoryTasks(categoryId, pageable);
            
            model.addObject( "categoryDetails", categoryDetails );
            model.addObject( "categoryTasks", categoryTasks );
            model.setViewName( ModelView.VIEW_ASMT_CATEGORY_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment Category Details:", e );        
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/amst_task_register.vw")
    public String registerOrganizationView()
    {
        return ModelView.VIEW_ASMT_TASK_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/amst_task_register.do")
    public ModelAndView registerOrganizationView( @ModelAttribute( "task" ) AssessmentTask task)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_ORGANIZATION_DETAILS_PAGE );
        
        try
        {
            task = taskManager.saveTask( task );
            
            return new ModelAndView("redirect:organization_details.vw?asmt_task_id=" + task.getId() );
        }
        catch(IllegalArgumentException e)
        {
            model.setViewName( ModelView.VIEW_ASMT_TASK_REGISTER_PAGE );
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering assessment task:", e ); 
            model.setViewName( ModelView.VIEW_ASMT_TASK_REGISTER_PAGE );
            model.addObject( "errorMessage", e );
        }
        
        return model;
        
    }
   
}



