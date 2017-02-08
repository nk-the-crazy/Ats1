package web.controller.assessment.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.assessment.task.AssessmentTaskCategory;
import service.api.assesment.task.AssessmentTaskManager;
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
    public ModelAndView getAssessmentTasks( @RequestParam( name = "taskName" , defaultValue = "", required = false ) 
                                            String taskName, 
                                            @RequestParam( name = "category" , defaultValue = "", required = false ) 
                                            String category, 
                                            Pageable pageable )
    {         
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
        try
        {
            //Page<User> usersPage = identityManager.getUsersByUserNameAndLastName( userName, lastName, pageable);
                    
            //model.addObject( "usersPage", usersPage );
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
    @RequestMapping( value = "/asmt_category_list.vw")
    public ModelAndView getUsers( @RequestParam( name = "userName" , defaultValue = "", required = false ) 
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
}



