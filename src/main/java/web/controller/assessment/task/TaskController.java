package web.controller.assessment.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import service.api.assessment.AssessmentTaskManager;
import web.view.ModelView;


@Controller
public class TaskController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    //---------------------------------

    @Autowired
    private AssessmentTaskManager taskManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_task_list.vw")
    public ModelAndView getAssessmentTasks( @RequestParam( name = "taskItemContent" , defaultValue = "", required = false ) 
                                            String taskItemContent, 
                                            @RequestParam( name = "categoryName" , defaultValue = "", required = false ) 
                                            String categoryName, 
                                            @RequestParam( name = "taskModeType" , defaultValue = "0", required = false ) 
                                            short taskModeType,
                                            Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
        try
        {
            Page<Object> tasksPage = taskManager.getTasksByDetails( taskItemContent, categoryName, taskModeType, pageable );
                    
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
    public String getCategoryDetailsView(@RequestParam( "asmt_category_id" ) long categoryId, Pageable pageable, Model model)
    {
       try
        {
            AssessmentTaskCategory categoryDetails = taskManager.getCategoryDetails( categoryId );
            Page< AssessmentTask> categoryTasks = taskManager.getCategoryTasks(categoryId, pageable);
            
            model.addAttribute("categoryDetails", categoryDetails );
            model.addAttribute( "categoryTasks", categoryTasks );
            
            return  ModelView.VIEW_ASMT_CATEGORY_DETAILS_PAGE;
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Assessment Category Details:", e );        
        }
        
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_task_register.vw")
    public String registerTaskView(Model model)
    {
        model.addAttribute( "categoryShortList", taskManager.getCategoryShortList( "" ));
        return ModelView.VIEW_ASMT_TASK_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_task_register.do")
    public String registerTask( @ModelAttribute( "task" ) AssessmentTask task, 
                                @RequestParam( name = "categoryId" ) long categoryId,
                                Model model)
    {
        try
        {
            task = taskManager.saveTask( task , categoryId );
            
            return "redirect:asmt_task_details.vw?asmt_task_id=" + task.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute("errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering assessment task:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return registerTaskView(model);
        
    }
    
    
    /*******************************************************
     * 
     */                        
    @RequestMapping( value = "/asmt_task_list.mvw")
    public ModelAndView getTaskListModalView(@RequestParam( name = "submitUrl", required = false ) String submitUrl)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            model.setViewName( ModelView.VIEW_ASMT_TASK_LIST_MD_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting task list modal :", e ); 
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        
        }
        
        return model;
        
    }

    
   
    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_task_edit.vw")
    public String editTaskView( @RequestParam( name = "asmt_task_id" ) long taskId, Model model)
    {
        model.addAttribute( "categoryShortList", taskManager.getCategoryShortList( "" ));
        model.addAttribute( "taskDetails", taskManager.getTaskFullDetails( taskId ));
        
        return ModelView.VIEW_ASMT_TASK_EDIT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_task_edit.do")
    public String editTask( @ModelAttribute( "task" ) AssessmentTask task, 
                            Model model)
    {
        try
        {
            task = taskManager.saveTask( task );
            
            return "redirect:asmt_task_details.vw?asmt_task_id=" + task.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute("errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing assessment task:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return editTaskView(task.getId(), model);
        
    }
    

    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_category_register.vw")
    public String registerCategoryView( @RequestParam( name = "asmt_category_id" , defaultValue = "0", required = false )
                                        long categoryId, Model model)
    {
        model.addAttribute ( "parentCategoryId", categoryId);
        return ModelView.VIEW_ASMT_CATEGORY_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_category_register.do")
    public ModelAndView registerCategory( @RequestParam( name = "parentCategoryId" , defaultValue = "0", required = false )
                                          long parentCategoryId,
                                          @ModelAttribute( "category" ) AssessmentTaskCategory category)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_ASMT_CATEGORY_REGISTER_PAGE );
        
        try
        {
            category = taskManager.saveTaskCategory( category , parentCategoryId );
            
            return new ModelAndView("redirect:asmt_category_details.vw?asmt_category_id=" + category.getId() );
        }
        catch(IllegalArgumentException e)
        {
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering assessment task:", e ); 
            model.addObject( "errorMessage", "message.error.system" );
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_category_edit.vw")
    public String editCategoryView(@RequestParam( "asmt_category_id" ) long categoryId, Model model)
    {
        model.addAttribute( "categoryDetails" , taskManager.getCategoryDetails( categoryId ));
        return ModelView.VIEW_ASMT_CATEGORY_EDIT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_category_edit.do")
    public String editCategory( @ModelAttribute( "category" ) AssessmentTaskCategory category, Model model)
    {

        try
        {
            category = taskManager.saveTaskCategory( category );
            
            return "redirect:asmt_category_details.vw?asmt_category_id=" + category.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing category data:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return editCategoryView(category.getId(), model);

    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_category_remove.do")
    public String removeCategory( @RequestParam( "asmt_category_id" ) long categoryId, Model model , Pageable pageable)
    {

        try
        {
            if(taskManager.removeCategory( categoryId ))
            {
                return "redirect:asmt_category_list.vw";
            }
            else
            {
                model.addAttribute( "errorMessage", "message.error.remove.has_child" );
            }
        }
        catch(Exception e)
        {
            logger.error( " **** Error removing category:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return getCategoryDetailsView( categoryId, pageable, model );

    }

    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/asmt_task_import.vw")
    public String importTaskView(Model model)
    {
        return ModelView.VIEW_ASMT_TASK_IMPORT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/asmt_task_import.do")
    public String importTask( @RequestParam("file") MultipartFile file, Model model)
    {
        try
        {
            if (!file.isEmpty()) 
            {
                taskManager.importTasks(file);
                return "redirect:asmt_task_list.vw";
            }
            else
            {
                throw new IllegalStateException("Invalid File");
            }
        } 
        catch (IllegalStateException e) 
        {
            model.addAttribute("errorMessage", "message.error.upload.file.invalid");
        } 
        catch(IllegalArgumentException e)
        {
            model.addAttribute("errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error importing assessment task:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return importTaskView(model);
        
    }
   
}



