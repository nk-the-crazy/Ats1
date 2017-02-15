package web.controller.group;


import model.group.UserGroup;
import model.identity.User;
import service.api.group.GroupManager;


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
import web.common.view.ModelView;


@Controller
public class GroupController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    //---------------------------------

    @Autowired
	private GroupManager groupManager;
    
  
   
    /*******************************************************
     * 
     */
	@RequestMapping( value = "/group_list.vw")
    public ModelAndView getGroups( @RequestParam( name = "groupName" , defaultValue = "", required = false ) 
                                  String groupName, Pageable pageable )
    {
	     ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
	        
	        try
	        {
	            Page<UserGroup> groupsPage = groupManager.getGroupsByName( groupName,  pageable);
	                    
	            model.addObject( "groupsPage", groupsPage );
	            model.setViewName( ModelView.VIEW_GROUP_LIST_PAGE);
	        }
	        catch(Exception e)
	        {
	            logger.error( " **** Error getting group list:", e );        
	        }
	        
	        return model;
    }
	
	
	/*******************************************************
     * 
     */
    @RequestMapping( value = "/group_details.vw")
    public ModelAndView getGroupDetails(@RequestParam( "group_id" ) long groupId , Pageable pageable ) 
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            UserGroup group = groupManager.getGroupDetails( groupId);
            Page<User> usersPage = groupManager.getGroupUsers( groupId, pageable );
            
            model.addObject( "groupDetails", group );
            model.addObject( "usersPage", usersPage );
            model.setViewName( ModelView.VIEW_GROUP_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting group Details:", e );        
        }
        
        return model;
        
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/group_register.vw")
    public String registerGroupView()
    {
        return ModelView.VIEW_GROUP_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/group_register.do")
    public ModelAndView registerGroup( @ModelAttribute( "group" ) UserGroup group)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_ROLE_DETAILS_PAGE );
        
        try
        {
            group = groupManager.saveGroup( group );
            
            return new ModelAndView("redirect:group_details.vw?group_id=" + group.getId() );
        }
        catch(IllegalArgumentException e)
        {
            model.setViewName( ModelView.VIEW_GROUP_REGISTER_PAGE );
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering group:", e ); 
            model.setViewName( ModelView.VIEW_GROUP_REGISTER_PAGE );
            model.addObject( "errorMessage", e );
        }
        
        return model;
        
    }


}